package javaproject;

import javax.swing.* ;
import java.awt.* ;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;


public class gui {
     public JFrame F1 = new JFrame ("OCR");
     public JPanel P  = new JPanel () ;
     public JButton browsePhoto = new JButton ("Browse") ; 
     public JButton browseDes = new JButton ("Browse") ;
     public JButton search = new JButton ("Search") ;
     public JButton extract = new JButton ("Extract") ;
     public JTextField photo = new JTextField (30) ;
     public JTextField Search_input = new JTextField(20) ;
     public JTextField Des = new JTextField (30) ;
     public JTextField name = new JTextField (15) ;
     public JLabel step1 = new JLabel () ;
     public JLabel step2 = new JLabel () ; 
     public JLabel step3 = new JLabel () ;
     public JLabel step4 = new JLabel () ;
     String path ,input ;//path of the extracted text file 
     JFileChooser Photo =new JFileChooser();
     JFileChooser Destination =new JFileChooser();
     public gui () {
      F1.setVisible(true);
      F1.setSize(600,400);
      F1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // to close the programe by clicking x 
      F1.add(P);//all add is to attach this component
      P.add(browsePhoto);
      P.add(photo);
      P.add(step1);
      P.add(browseDes);
      P.add(name);
      P.add(Des);
      P.add(step2);
      P.add(extract);
      P.add(step3);
      P.add(search);
      P.add(Search_input);
      P.add(step4);
      photo.setEditable(false);
      Des.setEditable(false);
      P.setBackground(Color.LIGHT_GRAY);
      step1.setText("Please ,select a photo to extract text");
      //actionlistener is preformed by clicking the button
      browsePhoto.addActionListener (new ActionListener(){
            public void actionPerformed (ActionEvent e)
            { 
                try{
                JButton B = new JButton("Ok");
                if (photo.getText()==""){
                Photo.setCurrentDirectory(new java.io.File ("C:/Users/Manar Arabi/Desktop"));}
                else 
                    Photo.setCurrentDirectory(new java.io.File (photo.getText()));
                Photo.setDialogTitle("Choose");
                Photo.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                if (Photo.showOpenDialog(B)==JFileChooser.APPROVE_OPTION){
                        System.out.println("dsd");
                        photo.setText(Photo.getSelectedFile().getAbsolutePath());
                        step2.setText("now ,you should select where to save the extracted file");
                }
                System.out.println(Photo.getSelectedFile().getAbsolutePath());
                }catch(Exception exp){}
            }
        });
      extract.addActionListener (new ActionListener(){
            public void actionPerformed (ActionEvent e)
            { 
                imageValidation(Photo.getSelectedFile().getAbsolutePath());
                step3.setText("you can search for a specific word in the extracted file");//it should be shown after extraction 
            }
        });
       browseDes.addActionListener (new ActionListener(){
            public void actionPerformed (ActionEvent e)
            { 
                try{
                    JButton B2 = new JButton("Ok");
                    if(Des.getText()==""){
                    Destination.setCurrentDirectory(new java.io.File ("C:/Users/Manar Arabi/Desktop"));}
                    else 
                        Destination.setCurrentDirectory(new java.io.File (Des.getText()));
                    Destination.setDialogTitle("Choose");
                    Destination.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    if (Destination.showOpenDialog(B2)==JFileChooser.APPROVE_OPTION){
                        System.out.println("dsd");
                        Des.setText(Destination.getSelectedFile().getAbsolutePath());
                    }
                    System.out.println(Destination.getSelectedFile().getAbsolutePath());
                }catch(Exception exp1){}
            }
        });
       input =Search_input.getText();
        search.addActionListener (new ActionListener(){
            public void actionPerformed (ActionEvent e)
            { 
           search_fun(path,input);
           step4.setText("Thank you ^_^ ");
         }
        });
     }
    public void imageValidation(String image) {
        Pattern pattern; // make neew object from the pattern class
        Matcher matcher;    // make neew object from the Matcher class
        final String IMAGE_PATTERN = "([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)"; // declare the regular expe
        pattern = Pattern.compile(IMAGE_PATTERN);  //Compiles the given regular expression into a pattern
        
        matcher = pattern.matcher(image); // mathches the string with the regular exper "Pattern "
       if (matcher.matches()) {
            try {
                
                BufferedImage originalImage = ImageIO.read(new File(image));
                
                System.out.println("The image read");
                  
                run(image);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Image not valid ");
        }
    }
    //filepath should contain the of extracted file txt and i don't know how to get this path 
    public String search_fun (String filePath ,String inputSearch) 
    {
    double count = 0,countBuffer=0,countLine=0;
    String lineNumber = "";
  //  String filePath = "C:\\Users\\XXX\\Desktop\\TestText.txt";
    BufferedReader br;
  //  String inputSearch = "are";
    String line = "";

    try {
        br = new BufferedReader(new FileReader(filePath));
        try {
            while((line = br.readLine()) != null)
            {
                countLine++;
                //System.out.println(line);
                String[] words = line.split(" ");
                for (String word : words) {
                  if (word.equals(inputSearch)) {
                    count++;
                    countBuffer++;
                  }
                }
                if(countBuffer > 0)
                {
                    countBuffer = 0;
                    lineNumber += countLine + ",";
                }
            }
            br.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    } catch (FileNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
 String res = "found"+count+"times at"+lineNumber; 
  //  System.out.println("Times found at--"+count);
   // System.out.println("Word found at--"+lineNumber);
   return res ;
    }
    public void run(String path) {
        try {
            Runtime rt = Runtime.getRuntime();
        //Scanner sc = new Scanner(System.in);
            //String path = sc.next();
            String command = String.format("cmd /c matlab /nosplash /nodesktop /minimize /r ocrProject(\'%s\')", path);
            Process pr = rt.exec(command);
            
            BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            
            String line;
            while ((line = input.readLine()) != null) {
                System.out.println(line);
            }
            int exitCode = pr.waitFor();
            System.out.println("operation exit code is : " + exitCode);
     //       step3.setText("you can search for a specific word in the extracted file");
        } catch (IOException | InterruptedException e) {
            System.out.println(e);
           
        }
    }
   }
    

