package javaproject;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class methods {

    public static boolean imageValidation(String image) {
        Pattern pattern; // make neew object from the pattern class
        Matcher matcher;    // make neew object from the Matcher class
        final String IMAGE_PATTERN ="(.*/)*.+\\.(png|jpg|gif|bmp|jpeg|PNG|JPG|GIF|BMP)$"; // declare the regular expe
        pattern = Pattern.compile(IMAGE_PATTERN);  //Compiles the given regular expression into a pattern

        matcher = pattern.matcher(image); // mathches the string with the regular exper "Pattern "
        if (matcher.matches()) {
            try {
                BufferedImage originalImage = ImageIO.read(new File(image));
//                JOptionPane.showMessageDialog(null, "The image is read");

                return true;
            } catch (IOException e) {

                JOptionPane.showMessageDialog(null, e.getMessage());
                return false;
            }
        } else {
            JOptionPane.showMessageDialog(null, "The image isn't valid");
        }
        return false;
    }

    public static void run(String path, String resultPath, String filename) {
        try {
            Runtime rt = Runtime.getRuntime();
            
            if (resultPath.charAt(resultPath.length() - 1) != '\\') resultPath = resultPath + "\\";
            
            String command= String.format("cmd /c matlab /nosplash /nodesktop /minimize /r ocrProject(\'\"%s\"\',\'%s\',\'\"%s\"\')",path,resultPath,filename);
            Process pr = rt.exec(command);
            BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line;
            int exitCode = pr.waitFor();
          if (exitCode==1){
              JOptionPane.showMessageDialog(null,"Process failed");
          }
        } catch (IOException | InterruptedException e) {
            JOptionPane.showMessageDialog(null,"Exception !");
        }
    }
    public static void setBackgroundImage (String image , JFrame F)
   {
        try {
            F.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File(image)))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        F.pack();
   }
}
