package company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransHistory {
    Connection connection;
    String sql;
    public TransHistory(){
        JFrame frame = new JFrame("Bank Statement");

        JLabel localtime = new JLabel("Local Time");
        localtime.setBounds(0,0,70,70);
//        frame.add(localtime);

        String[] cols = {"1", "2", "3","4","5"};
        DefaultTableModel tableModel = new DefaultTableModel();
       /* tableModel.addColumn("Lacal_Time");
        tableModel.addColumn("Local_Date");
        tableModel.addColumn("Amount");
        tableModel.addColumn("Account_Number");
        tableModel.addColumn("Receipent");*/



        JTable table = new JTable(tableModel);

        Object[] data={"A", "b","c", "d"};
        tableModel.addColumn(data);


         connection = company.Design.connection();





         if (Operation.accType.equals("account_nostro")) {
              sql = "Select Local_Time, Local_Date, Amount,Account_Number,Receipent from nostro_history where " +
                     " Account_Number = ?";
         }else if (Operation.accType.equals("account_rtgs")){

              sql = "Select Local_Time, Local_Date, Amount,Account_Number,Receipent from rtgs_history where" +
                     "Account_Number = ?";
         }

            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1,Operation.newAccount);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()){
                    String localTime = resultSet.getString("Local_Time");
                    String localDate = resultSet.getString("Local_Date");
                    String amount = resultSet.getString("Amount");
                    String accNumber = resultSet.getString("Account_Number");
                    String receipent = resultSet.getString("Receipent");
                    tableModel.addRow(new Object[] {localTime,localDate,amount,accNumber,receipent});

                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            table.setBounds(0,70,500,330);

        frame.setSize(500,400);
        frame.add(table);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
