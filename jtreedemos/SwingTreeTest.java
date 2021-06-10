package jtreedemos;

import javax.swing.SwingUtilities;

public class SwingTreeTest{


    public static void main(String[] args) {
        
        var title = "JTree Demo - by CodeParl";
        SwingUtilities.invokeLater(()->{
            WindowTest  windowTest = new WindowTest(title, null);
            windowTest.setWinLookAndFeel();
            AppGUI  app =  new AppGUI();
            windowTest.setContentPane(app);
            windowTest.setResizable(false);
            windowTest.setVisible(true);
        });
// /E:\MY_PROJECTS
       
    }


}