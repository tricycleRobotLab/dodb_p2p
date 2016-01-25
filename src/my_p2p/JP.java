/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my_p2p;
import javax.swing.*;
/**
 *
 * @author BiNI
 */
public class JP extends JPanel{
   JProgressBar pbar;
  static final int MY_MINIMUM=0;
  int MY_MAXIMUM;

  public JP(int max) {
      this.MY_MAXIMUM = max;
     pbar = new JProgressBar();
     pbar.setMinimum(MY_MINIMUM);
     pbar.setMaximum(MY_MAXIMUM);
     add(pbar);
  }

  public void updateBar(int newValue) {
    pbar.setValue(newValue);
  }
}
