/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my_p2p;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author BiNI
 */
public class ProgressMonitorExample implements ActionListener {
    static ProgressMonitor pbar;
    static int counter;
    public ProgressMonitorExample(int counter){
        this.counter = counter;
        //super("Progress Monitor Demo");
        //setSize(250,100);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pbar = new ProgressMonitor(null,"Downloading File","Initializing...",0,100);
        Timer timer = new Timer(500,this);
        timer.start();
       // setVisible(true);
        UIManager.put("ProgressMonitor.progressText", "Downloading...");
        UIManager.put("OptionPane.cancelButtonText", "Cancel");
    }
   /* public static void main(String args[]){
       
        new ProgressMonitorExample();
    }*/
    @Override
    public void actionPerformed(ActionEvent e) {
        SwingUtilities.invokeLater(new Update());
    }
    class Update implements Runnable{
        public void run(){
            if(pbar.isCanceled()){
                pbar.close();
                System.exit(1);
            }
            pbar.setProgress(counter);
            pbar.setNote("Download " + counter + "% complete");
            counter+=1;
        }
    }
    
}
