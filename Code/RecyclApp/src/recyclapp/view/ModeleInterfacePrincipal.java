/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recyclapp.view;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;

/**
 *
 * @author kevinsalles
 */
public class ModeleInterfacePrincipal{
    
    private InterfacePrincipale frame;
    private int idImage;

    public ModeleInterfacePrincipal(InterfacePrincipale frame) {
        this.frame = frame;
    }

    public void GridView() {
        frame.getPanelMap().inverseWithGrid();
        boolean b = frame.getPanelMap().isWithGrid();
        frame.repaint();
    }

    public void changeCursor(int id) {
	if(id == InterfaceOutils.ID_TOOL_ARC){
	    this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
	} else if (id >= 0) {
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
    
    public boolean isOverlapElement(int x,int y,int width, int height)
    {
        int X = x-(width/2);
        int Y = y-(height/2);
        int max = this.frame.getPositionElements().size();
        Rectangle r1 = new Rectangle(X,Y,width,height);
        
        for(int i=0;i<max;i++){

            Rectangle r2 = new Rectangle(this.frame.getPositionElements().get(i).x,this.frame.getPositionElements().get(i).y
                    ,this.frame.getPositionElements().get(i).width,this.frame.getPositionElements().get(i).height );
            if(r2.intersects(r1))
            {
                this.frame.getDebug().setText("Trouvé");
                return true;
            }
        }
        return false;
    }
}
