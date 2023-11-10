package company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

public class InterBank {

    static   Float amount; static Float a;     static String c;
    public InterBank() {

        JFrame frame = new JFrame("Bank To Wallet");


        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("C:\\Users\\User\\Pictures\\New folder\\v19.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert image != null;
        JLabel background = new JLabel(new ImageIcon(image));
        background.setBounds(0,0,400,400);



        JLabel label1 = new JLabel("Recipient :");
        label1.setFont(new Font("Mv Boli", Font.ITALIC,16));
        label1.setForeground(Color.RED);
        label1.setBackground(Color.cyan);
        frame.add(label1);


        label1.setBounds(0,0,150,20);
        frame.add(label1);

        JTextArea textArea = new JTextArea();
        textArea.setBounds(150,0,150,20);
        frame.add(textArea);

        JLabel jLabel = new JLabel("Amount :");
        jLabel.setBounds(0,50,150,20);
        frame.add(jLabel);

        JTextArea textArea1 = new JTextArea();
        textArea1.setBounds(150,50,150,20);
        frame.add(textArea1);


        JButton button = new JButton("Transfer");
        button.addActionListener(e -> {
            c= textArea.getText();
            a = Float.valueOf(textArea1.getText());
            Transact();

        });
        button.setBounds(170,90,100,20);
        frame.add(button);





        frame.add(background);
        frame.setResizable(true);
        frame.setLayout(new BorderLayout());
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    ///


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
    ///
}
