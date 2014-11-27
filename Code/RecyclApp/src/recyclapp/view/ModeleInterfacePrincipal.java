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
import recyclapp.model.Element;

/**
 *
 * @author kevinsalles
 */
public class ModeleInterfacePrincipal {

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
        if (id == InterfaceOutils.ID_TOOL_ARC) {
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

    public void drawImageFromFollowingCursor(int id, int x, int y) {
        Image img = frame.getPanelTools().getImages(id);
        x -= frame.getPanelTools().getWidth() + (img.getWidth(frame) * this.frame.getPanelMap().getZoom()) / 2;
        y -= (img.getHeight(frame) * this.frame.getPanelMap().getZoom()) / 2;
        frame.getPanelMap().drawImageFollowingCursor(frame.getPanelTools().getImages(id), x, y);
    }

    public void setVisiblePaneTools(boolean b) {
        this.frame.getPanelTools().setVisible(b);
    }

    public void setVisiblePaneParam(boolean b) {
        this.frame.getPanelParam().setVisible(b);
    }

    public boolean isOverlapElement(int x, int y, int r1width, int r1height) {
        int r1X = x - (r1width / 2);
        int r1Y = y - (r1height / 2);
        int max = this.frame.getPositionElements(false).size();
        Rectangle r1 = new Rectangle(r1X, r1Y, r1width, r1height);

        for (int i = 0; i < max; i++) {
            int r2X = this.frame.getPositionElements(false).get(i).x;
            int r2Y = this.frame.getPositionElements(false).get(i).y;
            int r2width = this.frame.getPositionElements(false).get(i).width;
            int r2height = this.frame.getPositionElements(false).get(i).height;

            Rectangle r2 = new Rectangle(r2X, r2Y, r2width, r2height);

            if (r2.intersects(r1)) {
                this.frame.getDebug().setText("Trouvé");
                return true;
            }
        }
        return false;
    }

    // Pas encore utilisé
    public boolean isThereAnElementHere(int x, int y) {
        int max = this.frame.getPositionElements(false).size();
        boolean found = false;

        for (int i = 0; i < max; i++) {
            int rX = this.frame.getPositionElements(false).get(i).x;
            int rY = this.frame.getPositionElements(false).get(i).y;
            int rWidth = this.frame.getPositionElements(false).get(i).width;
            int rHeight = this.frame.getPositionElements(false).get(i).height;

            Rectangle r = new Rectangle(rX, rY, rWidth, rHeight);

            if (r.contains(x, y)) {
                this.frame.getDebug().setText("Trouvé");
                found = true;
                break;
            }
        }
        return found;
    }

    public int convertPixelToMeter(int pixel) {
        int normeMeter = 5;
        int normePixel = this.frame.getPanelMap().getEcart();
        float zoom = this.frame.getPanelMap().getZoom();
        float meter = (pixel * normeMeter) / (zoom * normePixel);
        return (int) (meter);
    }
}
