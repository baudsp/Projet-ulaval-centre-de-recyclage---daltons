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
public class ModeleInterfacePrincipal {

    private InterfacePrincipale frame;

    public ModeleInterfacePrincipal(InterfacePrincipale frame) {
        this.frame = frame;
    }

    public void GridView() {
        frame.getPanelMap().inverseWithGrid();
        frame.repaint();
    }

    public void changeCursor(int idTool) {
        if (idTool == InterfaceOutils.ID_TOOL_ARC) {
            this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        } else if (idTool >= 0) {
            Toolkit tk = Toolkit.getDefaultToolkit();
            Cursor monCurseur = tk.createCustomCursor(frame.getPanelTools().getImages(idTool), new Point(16, 16), "Tools");
            this.frame.setCursor(monCurseur);
        } else if (idTool == -2) {
            this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        } else {
            this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    }

    public void drawImageFollowingCursor(int imageId, int x, int y) {
        Image img = frame.getPanelTools().getImages(imageId);
        x -= frame.getPanelTools().getWidth() + (img.getWidth(frame) * this.frame.getPanelMap().getZoom()) / 2;
        y -= (img.getHeight(frame) * this.frame.getPanelMap().getZoom()) / 2;
        frame.getPanelMap().drawImageFollowingCursor(frame.getPanelTools().getImages(imageId), x, y);
    }

    public void setVisiblePaneTools(boolean visible) {
        this.frame.getPanelTools().setVisible(visible);
    }

    public void setVisiblePaneParam(boolean visible) {
        this.frame.getPanelParam().setVisible(visible);
    }

    public boolean isOverlapElement(int x, int y, int width, int height) {
        x = x - (width / 2);
        y = y - (height / 2);
        int max = this.frame.getListDataElements(false).size();
        Rectangle rectangle = new Rectangle(x, y, width, height);

        for (int i = 0; i < max; i++) {
            int r2X = this.frame.getListDataElements(false).get(i).x;
            int r2Y = this.frame.getListDataElements(false).get(i).y;
            int r2width = this.frame.getListDataElements(false).get(i).width;
            int r2height = this.frame.getListDataElements(false).get(i).height;

            Rectangle rectangle2 = new Rectangle(r2X, r2Y, r2width, r2height);

            if (rectangle2.intersects(rectangle)) {
                this.frame.getDebug().setText("Trouvé");
                return true;
            }
        }
        return false;
    }

    /**
     * *
     * summary : Permet de savoir si un élément se trouve aux coordonées
     * cliquées.
     *
     * @param x
     * @param y
     * @return
     */
    public boolean isThereAnElementHere(int x, int y) {
        int listDataElements = this.frame.getListDataElements(false).size();
        boolean found = false;

        for (int i = 0; i < listDataElements; i++) {
            int rX = this.frame.getListDataElements(false).get(i).x;
            int rY = this.frame.getListDataElements(false).get(i).y;
            int rWidth = this.frame.getListDataElements(false).get(i).width;
            int rHeight = this.frame.getListDataElements(false).get(i).height;

            Rectangle rectangle = new Rectangle(rX, rY, rWidth, rHeight);

            if (rectangle.contains(x, y)) {
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
