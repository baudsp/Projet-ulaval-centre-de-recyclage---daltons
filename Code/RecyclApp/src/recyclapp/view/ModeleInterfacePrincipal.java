/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recyclapp.view;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import javax.swing.JPanel;

/**
 *
 * @author kevinsalles
 */
public class ModeleInterfacePrincipal{
    
    private InterfacePrincipale frame;
    // NEW
    private int idImage;

    public ModeleInterfacePrincipal(InterfacePrincipale frame) {
        this.frame = frame;
    }

    public void GridView() {
        frame.getPanelMap().inverseWithGrid();
        boolean b = frame.getPanelMap().isWithGrid();
        frame.repaint();
        if (b) {
            //frame.getjButton1().setText("Not view grid");
        } else {
            //frame.getjButton1().setText("View grid");
        }
    }

    public void changeCursor(int id) {
        if (id >= 0) {
            Toolkit tk = Toolkit.getDefaultToolkit();
            Cursor monCurseur = tk.createCustomCursor(frame.getPanelTools().getImages(id), new Point(16, 16), "Tools");
            this.frame.setCursor(monCurseur);
        } else if (id == -2) {
            this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        } else {
            this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    }
    
    public void drawImageFromFollowingCursor(int id,int x, int y){
        Image img = frame.getPanelTools().getImages(id);
        x -= frame.getPanelTools().getWidth()+img.getWidth(frame)/2;
        y -= img.getWidth(frame)/2;
        frame.getPanelMap().drawImageFollowingCursor(frame.getPanelTools().getImages(id),x,y);
    }
    
    
    
    public void setVisiblePaneTools(boolean b)
    {
        this.frame.getPanelTools().setVisible(b);
    }
    
    public void setVisiblePaneParam(boolean b)
    {
        this.frame.getPanelParam().setVisible(b);
    }
}
