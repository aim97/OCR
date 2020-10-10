
package javaproject;

import javax.swing.UIManager;


public class JAVAPROJECt {

    public static void main(String[] args) {
        try {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); 
        }
        catch (Exception e )
        {
        }
      gui g =new gui() ;
    }
    
}
