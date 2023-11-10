package company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.*;

public class Test {

   public Test() throws IOException {
      JFrame frame = new JFrame();
      BufferedImage image= ImageIO.read(new File("C:\\Users\\User\\Pictures\\art (1).jpg"));

      JLabel label = new JLabel("Press");
      label.setBounds(0,0,150,20);
      frame.add(label);
      label.setOpaque(true);

      frame.setContentPane(new JLabel(new ImageIcon(image)));
       frame.setSize(400,400);
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setVisible(true);
   }
}
