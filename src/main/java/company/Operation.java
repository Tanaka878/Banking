package company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

class Operation {
    static String newAccount = company.Design.getAccount();
     public static String accType = company.Design.getAccountType();
    static float Balance;



    //a method to calculate the remaining account balance
    public static void ReturnBalance(){
        String username = "root";
        String password = "root";
        String url = "jdbc:mysql://localhost:3306/online_banking";
        String sql = "select * from " + accType + " where Account_Number = ?";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException r) {
            r.printStackTrace();
        }

        Connection con = null;
        try {
            con = DriverManager.getConnection(url, username, password);

            PreparedStatement statement = con.prepareStatement(sql);
           // statement.setString(1,accType);
           statement.setString(1,newAccount);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
               Balance = resultSet.getFloat("balance");
                System.out.println(Balance);
                System.out.println("happy");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    // a constructor to show the actions that can be performed on
    public Operation(){

        System.out.println(newAccount);
        ReturnBalance();

        JFrame frame = new JFrame("Option Page");



        JLabel label = new JLabel("Account Number: ");
        label.setBounds(0,0,200,30);
        frame.add(label);
        JLabel accountLabel = new JLabel(""+newAccount);
        accountLabel.setBounds(120,0,200,30);
        frame.add(accountLabel);


        JLabel balanceDisp = new JLabel("Balance: ");
        balanceDisp.setBounds(0,35,100,25);
        frame.add(balanceDisp);
        JTextArea textArea = new JTextArea();
        textArea.setText(String.valueOf(Balance));
        textArea.setBounds(100,35,100,25);
        frame.add(textArea);

        JScrollPane pane = new JScrollPane();
        JPanel panel = new JPanel();
        pane.add(panel);
        panel.setBounds(0,70,400,340);
        panel.setBackground(Color.DARK_GRAY);
        panel.setLayout(new FlowLayout());
        frame.add(panel);

        //button of the panel
        JButton checkBalance = new JButton("Bank Statement");
        checkBalance.setBounds(0,0,180,30);
        checkBalance.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TransHistory();
            }
        });
        panel.add(checkBalance);

        JButton payBills = new JButton("Pay Bills");
        payBills.setBounds(200,0,180,30);
        payBills.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Bills();
            }
        });
        panel.add(payBills);


        JButton PayFees = new JButton("Pay Fees");
        PayFees.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PayFees();
            }
        });
        PayFees.setBounds(0,35,180,30);

        panel.add(PayFees);

        JButton BankToWallet = new JButton("Bank To Wallet");
        BankToWallet.setBounds(200,35,180,30);
        BankToWallet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                //called when user wants to do an interbank transfer
                new InterBank();
            }
        });
        panel.add(BankToWallet);

        JButton changePin = new JButton("Change Pin");
        changePin.setBounds(200,70,180,30);
        changePin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                //a constructor that is called to execute code to allow user to change pin
                new ChangePin();
            }
        });
        panel.add(changePin);


        frame.setSize(400,400);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       // System.out.println(com.company.Design.getAccount() + "this is true");
        frame.setVisible(true);
    }
}
