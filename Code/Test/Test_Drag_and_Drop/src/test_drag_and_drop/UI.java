/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test_drag_and_drop;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

/**
 *
 * @author Kevin
 */
public class UI {
    
    private Frame f;
   public UI(Frame frame){
       this.f = frame;    
   }
   
   public void changeBackgroundPanel()
   {
      f.getjPanel1().setBackground(javax.swing.UIManager.getDefaults().getColor("Button.focus"));
   }
   
   public void GridView()
   {
       boolean b = !f.getjPanel2().isDoGrid();
       f.getjPanel2().setDoGrid(b);
       f.repaint();
       if(b)
           f.getjButton1().setText("Not view grid");
       else
          f.getjButton1().setText("View grid");
   }
   
   public void changeCursor(int id)
   {
        if(id >= 0)
        {
            Toolkit tk = Toolkit.getDefaultToolkit();
            Cursor monCurseur = tk.createCustomCursor(this.f.getjPanel1().getImages(id), new Point(16, 16), "Tools");
            this.f.setCursor(monCurseur);
        }
        else if(id == -2)
            this.f.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR) );
        else
            this.f.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR) );
   }
    
}
