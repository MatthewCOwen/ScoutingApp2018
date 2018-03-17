package main;
import java.io.FileNotFoundException;

import components.ScoutingApp;
import processing.FileManager;
import processing.StaticMatchInfo;

public class Main {

	public static void main(String[] args) {
		
		FileManager.createMatchFileFolder();
		FileManager.createReadMe();
		StaticMatchInfo.resetFields();
		
		try
		{
			FileManager.populateMatchList();
		}
		catch (FileNotFoundException e)
		{
			
		}
		
		try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
		
		java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
            	ScoutingApp app = new ScoutingApp();
            }
        });
	}

}
