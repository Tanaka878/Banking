package company;


import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;


public class Bills {
  static   Float amount; static Float a;     static String c;
    JComboBox receipentBox;

    public Bills(){

        JFrame frame = new JFrame("Billing System");

        JLabel bankLogo = new JLabel();
        bankLogo.setText("NMB Connect");
        bankLogo.setFont(new Font("Mv Boli", Font.BOLD,17));
        bankLogo.setBounds(0,0,399,70);
        frame.add(bankLogo);

        JLabel receipentName = new JLabel();
        receipentName.setBounds(0,70,150,40);
        receipentName.setText("Receipent");
        frame.add(receipentName);

        String[] receipentsList = {"ZOL","ZINWA","ZESA"};
        receipentBox = new JComboBox(receipentsList);
        receipentBox.setBounds(150,70,150,40);
        frame.add(receipentBox);

        JLabel amountLabel =new JLabel();
        amountLabel.setText("Amount");
       amountLabel.setFont(new Font("Mv Boli", Font.BOLD,17));
       amountLabel.setBounds(0,140,120,40);
       frame.add(amountLabel);

     JTextArea amountText = new JTextArea();
       amountText.setBounds(120,150,120,20);
       frame.add(amountText);


       JButton transact = new JButton("Transact");
       transact.setBounds(150,200,100,20);
       transact.setFocusable(false);
       transact.addActionListener(e -> {
            a = Float.valueOf(amountText.getText());
             c = (String) receipentBox.getSelectedItem();
             Transact();
       });
       frame.add(transact);

       JButton exitButton = new JButton("Exit");
       exitButton.setFocusable(false);
       exitButton.setBounds(350,200,100,20);
       exitButton.addActionListener(e -> {
           frame.dispose();
           new Operation();
       });









        frame.setSize(400,400);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);


    }

    public static void Transact(){
       Connection con1 = company.Design.connection();
        String sql = "Select * from  " + Operation.accType+" where Account_Number = "+Operation.newAccount;
        try {

            PreparedStatement statement = con1.prepareStatement(sql);
           // statement.setString(1,Operation.newAccount);
            ResultSet set = statement.executeQuery();

            while (set.next()){
                amount = set.getFloat("balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (amount>a){
            System.out.println("There is enough funds to proceed");
            float b =amount -a;
            System.out.println("Transaction Successful");
            String sql1 = "Update "+ Operation.accType+" set balance =?  where Account_Number = "+Operation.newAccount;

            try {
                PreparedStatement preparedStatement = con1.prepareStatement(sql1);
               preparedStatement.setFloat(1,b);
                int i= preparedStatement.executeUpdate();
                System.out.println(i);


            } catch (SQLException e) {
                e.printStackTrace();
            }


            SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
            LocalDate date = LocalDate.now();

         //   format.format(time);

            //nested if statement to determine the location where the transaction history is to be stored
            if (Operation.accType.equals("account_nostro")){
                String sqlNostro = "Insert into nostro_history values (default,?,?,?,?,?)";
                try {
                    PreparedStatement statement = con1.prepareStatement(sqlNostro);
                    statement.setString(1, format.format(Calendar.getInstance().getTime()));
                    statement.setString(2,String.valueOf(date));
                    statement.setFloat(3,a);
                    statement.setString(4,Operation.newAccount);
                    statement.setString(5,c);
                    statement.execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            else if (Operation.accType.equals("account_rtgs")){

                String sqlNostro = "Insert into rtgs_history values (default,?,?,?,?,?)";
                try {
                    PreparedStatement statement = con1.prepareStatement(sqlNostro);
                    statement.setString(1, format.format(Calendar.getInstance().getTime()));
                    statement.setString(2,String.valueOf(date));
                    statement.setFloat(3,a);
                    statement.setString(4,Operation.newAccount);
                    statement.setString(5,c);
                    statement.execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }


        }

    }
}
