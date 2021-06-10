package jtreedemos;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.*;
public class WindowTest extends JFrame {
  
    public WindowTest(String title, Dimension size){
        super(title);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        if(size == null){
            //control the size 
            setWindowSizeByPlattform();
        }
        
    }


      private void setWindowSizeByPlattform(){
  
      //make the size of this window 50% of the plattform size
      Toolkit  kit  =  Toolkit.getDefaultToolkit();

      int percentage = 50;

      //check for laptops
      if(kit.getScreenSize().width <= 1200)
          percentage =  60;

      Dimension  size  =  kit.getScreenSize();
      size.width = (int) (size.getWidth()/ 100 ) * percentage;
      size.height = (int) (size.getHeight()/ 100 ) * percentage;

      
      setSize(size);
      setPreferredSize(size);
      Dimension  winSize  =  size;
      //try to center the window 
      size  =  kit.getScreenSize();
      int x  =  (int)(size.getWidth() - winSize.getWidth())/2;
      int y  =  (int)(size.getHeight()- winSize.getHeight())/2;
      setLocation(x, y);
     
  
  } 

  public void  setWinLookAndFeel(){
    try {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
        e.printStackTrace();
    } 

  }
}
