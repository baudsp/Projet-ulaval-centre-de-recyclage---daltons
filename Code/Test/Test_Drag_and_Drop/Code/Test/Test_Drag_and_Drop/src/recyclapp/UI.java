package recyclapp;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import javax.swing.UIManager;
import recyclapp.model.Coordinate;

public class UI {

    private RecyclAppFrame frame;

    public UI(RecyclAppFrame frame) {
        this.frame = frame;
    }

    public void changeBackgroundPanel() {
        frame.getjPanel1().setBackground(UIManager.getDefaults().getColor("Button.focus"));
    }

    public void GridView() {
        frame.getjPanel2().inverseWithGrid();
        boolean b = frame.getjPanel2().isWithGrid();
        frame.repaint();
        if (b) {
            frame.getjButton1().setText("Not view grid");
        } else {
            frame.getjButton1().setText("View grid");
        }
    }
    
    public void changeCursor(int id) {
        if (id == 2) {
            this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        } else if (id >= 0) {
            Toolkit tk = Toolkit.getDefaultToolkit();
            Cursor monCurseur = tk.createCustomCursor(frame.getjPanel1().getImages(id), new Point(16, 16), "Tools");
            this.frame.setCursor(monCurseur);
        } else if (id == -2) {
            this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        } else {
            this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    }
    
    public void drawImageFromFollowingCursor(int id){
        frame.getjPanel2().drawImageFollowingCursor(frame.getjPanel1().getImages(id));
    }

    /**
     * Transforme les coordonnées récupérés dans l'interface (en pixels)
     * en un objet coordonnées contenant des données en m pour usage dans le 
     * domaine
     * @param x
     * @param y
     * @return 
     */
    public static Coordinate transformCoordinateFromUI(int x, int y) {
	// TODO accéder à la variable zoom quand elle existera pour faire le calcul
	
	int zoomFactor = 1;	
	
	return new Coordinate(x*zoomFactor, y*zoomFactor);
    }
    
       /**
     * Transforme les coordonnées récupérés dans l'interface (en pixels)
     * en un objet coordonnées contenant des données en m pour usage dans le 
     * domaine
     * @param coordinate
     * @return 
     */
    public static int[] transformCoordinateFromDomain(Coordinate coordinate) {
	// TODO accéder à la variable zoom quand elle existera pour faire le calcul
	
	int zoomFactor = 1;
	
	int[] coordonneTab = {coordinate.getX()*zoomFactor, coordinate.getY()*zoomFactor};
	
	return coordonneTab;
    }
}
