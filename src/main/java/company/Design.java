package company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.Objects;

public class Design {
    public static Connection connection() {
        String username = "root";
        String password = "root";
        String url = "jdbc:mysql://localhost:3306/online_banking";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException r) {
            r.printStackTrace();
        }

        Connection con = null;
        try {
            con = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return con;

    }




    JTextArea accNum = new JTextArea();
    static String account ;
    static  String AccountType;


    public Design() {
        final JFrame frame = new JFrame();

        //components



        JLabel label = new JLabel();


        label.setBounds(0, 0, 600, 100);
        frame.add(label);


        JLabel accType = new JLabel("Type of Account :");

        accType.setBounds(0, 100, 150, 40);
        accType.setFont(new Font("", Font.BOLD, 16));
        frame.add(accType);
        String[] accounts = {"NOSTRO", "RTGS"};

        final JComboBox comboBox = new JComboBox(accounts);
        comboBox.setEditable(false);
        comboBox.setFont(new Font("", Font.BOLD, 16));
        comboBox.setBounds(150, 105, 100, 30);

        frame.add(comboBox);

        JLabel noticelabel = new JLabel();
        noticelabel.setText("Enter Login Details");
        noticelabel.setFont(new Font("", Font.BOLD, 16));

        noticelabel.setBounds(0, 170, 150, 30);
        frame.add(noticelabel);


        BufferedImage accountNumberLogo = null;
        try {
            accountNumberLogo = ImageIO.read(new File("C:\\Users\\User\\Desktop\\tanaka\\databaseaCCESS\\Download\\6261542.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JLabel accNumber = new JLabel("ACC #: ");
        accNumber.setFont(new Font("", Font.BOLD, 26));
        assert accountNumberLogo != null;
        JLabel label1 = new JLabel(new ImageIcon(accountNumberLogo));

        label1.setBounds(100, 230, 50, 50);
        frame.add(label1);
        accNumber.setBounds(0, 230, 150, 50);
        //   accNumber.setFont(new Font("MV boli", Font.BOLD, 14));
        frame.add(accNumber);


        accNum.setBounds(190, 230, 300, 50);
        accNum.setFont(new Font("", Font.PLAIN, 25));
        frame.add(accNum);

        JLabel pinLabel = new JLabel("PIN # :");
        pinLabel.setBounds(0, 320, 100, 30);
        pinLabel.setFont(new Font("", Font.BOLD, 25));
        frame.add(pinLabel);

        BufferedImage pinImage = null;
        try {
            pinImage = ImageIO.read(new File("C:\\Users\\User\\Desktop\\tanaka\\databaseaCCESS\\Download\\3064155.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert pinImage != null;
        JLabel plogo = new JLabel(new ImageIcon(pinImage));
        plogo.setBounds(100, 320, 30, 40);
        frame.add(plogo);
        final JTextArea pinNumber = new JTextArea();
        pinNumber.setBounds(190, 320, 300, 50);
        pinNumber.setFont(new Font("", Font.PLAIN, 25));
        frame.add(pinNumber);


        JButton Login = new JButton("Log In");
        Login.setBounds(220, 400, 70, 50);
        Login.addActionListener(e -> {


            if (comboBox.getSelectedItem() == "RTGS") {
                String accountNumber = accNum.getText();
                setAccount(accountNumber);
                setAccountType("account_rtgs");
                System.out.println(account);
                String P = pinNumber.getText();

                String sql = "select * from RTGS where Account_Number = ?";


                String pin = null;
                try {
                    PreparedStatement statement = connection().prepareStatement(sql);
                    statement.setString(1, accountNumber);

                    ResultSet resultSet = statement.executeQuery();

                    while (resultSet.next()) {
                        pin = resultSet.getString("PIN");


                    }


                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                if (Objects.equals(P, pin)) {
                    JOptionPane.showMessageDialog(null, "Sharp", "Verified", JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                    new Operation();
                } else {
                    System.out.println("Wrong credentials");
                    JOptionPane.showMessageDialog(null, "Try Again", "Wrong Credentials", JOptionPane.ERROR_MESSAGE);
                }

            } else if (comboBox.getSelectedItem() == "NOSTRO") {
                setAccountType("account_nostro");

                //
                String accountNumber = accNum.getText();
                setAccount(accountNumber);
                String P = pinNumber.getText();
                System.out.println(getAccount());

                String sql = "select * from NOSTRO where Account_Number = ?";


                String pin = null;
                try {
                    PreparedStatement statement = connection().prepareStatement(sql);
                    statement.setString(1, accountNumber);

                    ResultSet resultSet = statement.executeQuery();

                    while (resultSet.next()) {
                        pin = resultSet.getString("PIN");


                    }


                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                if (Objects.equals(P, pin)) {
                    JOptionPane.showMessageDialog(null, "Sharp", "Verified", JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                    new Operation();
                } else {
                    System.out.println("Wrong credentials");
                    JOptionPane.showMessageDialog(null, "Try Again", "Wrong Credentials", JOptionPane.ERROR_MESSAGE);
                }

                //
            }

        });
        frame.add(Login);


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLayout(new BorderLayout());
        frame.setBackground(Color.DARK_GRAY);
        frame.setVisible(true);
    }

    public static String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        Design.account = account;
    }

    public static String getAccountType() {
        return AccountType;
    }

    public static void setAccountType(String accountType) {
        AccountType = accountType;
    }
}
