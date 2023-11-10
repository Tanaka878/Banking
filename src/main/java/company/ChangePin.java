package company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class ChangePin {

    static JTextArea area1;
    static String b;
    public ChangePin() {
        JFrame jFrame = new JFrame("Change Pin Dialog");
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(new File("C:\\Users\\User\\Desktop\\tanaka\\databaseaCCESS\\Download\\9221469.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("C:\\Users\\User\\Pictures\\New folder\\v27.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedImage bufferedImage1 = null;
        try {
            bufferedImage1 = ImageIO.read(new File("C:\\Users\\User\\Desktop\\tanaka\\databaseaCCESS\\Download\\6261542.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        JLabel label = new JLabel("ENTER THE NEW PIN");
        label.setFont(new Font("", Font.BOLD, 20));
        label.setBounds(0, 0, 300, 30);
        jFrame.add(label);

        JTextArea textArea = new JTextArea();
        textArea.setBounds(150, 0, 150, 20);
        //jFrame.add(textArea);

        JLabel label1 = new JLabel("New PIN");
        label1.setBounds(0, 100, 150, 30);
        label1.setFont(new Font("", Font.BOLD, 20));
        jFrame.add(label1);

        assert bufferedImage != null;
        JLabel newPinImage = new JLabel(new ImageIcon(bufferedImage));
        newPinImage.setBounds(110, 100, 30, 30);
        jFrame.add(newPinImage);

        JTextArea area = new JTextArea();
        area.setBounds(150, 100, 150, 30);
        jFrame.add(area);

        JLabel label2 = new JLabel("Confirm PIN");
        label2.setFont(new Font("", Font.BOLD, 20));
        label2.setBounds(0, 150, 150, 30);
        jFrame.add(label2);
        assert bufferedImage1 != null;
        JLabel label3 = new JLabel(new ImageIcon(bufferedImage1));
        label3.setBounds(115,150,30,30);
        jFrame.add(label3);

        JTextArea area1 = new JTextArea();
        area1.setBounds(150, 150, 150, 30);
        jFrame.add(area1);


        JButton button = new JButton("Confirm");
        button.setBounds(150, 210, 100, 25);
        button.addActionListener(e -> {
            String a = area1.getText();
            b = area.getText();
            if (Objects.equals(a, b)) {
                if ((a != null) && (b != null)) {
                    changePin();
                    JOptionPane.showMessageDialog(null, "Pin Successfully changed",
                            "Pin changes", JOptionPane.INFORMATION_MESSAGE);
                    jFrame.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Null values",
                            "Empty fields", JOptionPane.ERROR_MESSAGE);
                }

            } else {
                JOptionPane.showMessageDialog(null,
                        "Pin does not match", "no", JOptionPane.ERROR_MESSAGE);
            }
        });
        button.setFocusable(false);
        jFrame.add(button);

        assert image != null;
        JLabel backgroundLabel = new JLabel(new ImageIcon(image));
        backgroundLabel.setBounds(0, 0, 400, 400);
        jFrame.add(backgroundLabel);
        jFrame.setSize(400, 400);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLayout(new BorderLayout());
        jFrame.setVisible(true);
    }

    public static void changePin(){
        Connection connection = Design.connection();

        if (Operation.accType.equals("account_nostro")){
            String sql = "Update nostro set PIN = ? where Account_Number = ?";

            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1,b);
                statement.setString(2,Operation.newAccount);
                statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else if (Operation.accType.equals("account_rtgs")){
            String sql = "Update rtgs set PIN = ?  where Account_Number = ?";

            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1,area1.getText());
                statement.setString(2,Operation.newAccount);
                statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }
}
