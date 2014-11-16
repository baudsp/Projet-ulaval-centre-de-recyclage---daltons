package recyclapp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.util.LinkedList;
import javax.swing.JPanel;
import recyclapp.model.Arc;
import recyclapp.model.Element;

public class MapPane extends JPanel {

    private LinkedList<Element> elements;
    private boolean withGrid;
    private Element eltCursor;
    private LinkedList<Arc> arcs;
    private int t;
    

    public MapPane() {
        elements = new LinkedList<>();
        withGrid = false;
	arcs = new LinkedList<>();
        setBackground(new java.awt.Color(255, 255, 255));
	eltCursor = null;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (withGrid) {
            drawGrid(g);
        }
	
	if (eltCursor != null) {
	    g.drawImage(eltCursor.image, eltCursor.x, eltCursor.y, eltCursor.height, eltCursor.width, this);
	}
	for (Arc arc : arcs) {
	    drawArc(arc, g);
	}
	
        for (Element e : elements) {
            g.drawImage(e.image, (e.x), e.y, e.height, e.width, this);
        }
    }

    private void drawGrid(Graphics g) {
        g.setColor(Color.BLACK);
        int ecart = 50;
        int x = 0;
        int y = 0;

        while (x < getWidth()) {
            x += ecart;
            g.drawLine(x, 0, x, getHeight());
        }
        while (y < getHeight()) {
            y += ecart;
            g.drawLine(0, y, getWidth(), y);
        }
    }

    public boolean inverseWithGrid() {
        withGrid = !withGrid;
        return withGrid;
    }

    public boolean isWithGrid() {
        return withGrid;
    }

    public void addElement(int id, int x, int y, int width, int height, Image image) {
	eltCursor = null;
        elements.add(new Element(id, x, y, width, height, image));
        repaint();
    }

    public void moveElement(int id, int x, int y) {
        for (Element e : elements) {
            if (e.id == id) {
                moveElement(e, x, y);
                return;
            }
        }
    }

    private void moveElement(Element e, int x, int y) {
        e.x = x;
        e.y = y;
        repaint();
    }

    public void resizeElement(int id, int width, int height) {
        for (Element e : elements) {
            if (e.id == id) {
                resizeElement(e, width, height);
                return;
            }
        }
    }

    private void resizeElement(Element e, int width, int height) {
        e.width = width;
        e.height = height;
        repaint();
    }

    /**
     * retourne l'element existant aux coordonnées en paramètres
     */
    public Element checkElement(int x, int y) {
	for (Element e : elements) {
	    if (x>= e.x && x<=(e.x+e.width) && y>=e.y && y<=(e.y + e.height)) {
		return e;
	    }
	}	
	return null;
    }
    
    public void deleteElements() {
        elements.clear();
        repaint();
    }
    
    public void deleteElement(Element element) {
        elements.removeLastOccurrence(element);
        repaint();
    }
    
    
    public void drawImageFollowingCursor(Image image) {
        Point mousePosition = MouseInfo.getPointerInfo().getLocation();
		
	if (eltCursor == null) {
	    eltCursor = new Element(4, mousePosition.x - 220, mousePosition.y - 75, 50, 50, image);
	}
	
	this.eltCursor.x = mousePosition.x - 263;
	this.eltCursor.y = mousePosition.y - 59;
	this.repaint();
    }

    private void drawArc(Arc arc, Graphics g) {
	if (arc.getStatus()) {
	    int xExit = UI.transformCoordinateFromDomain(arc.getExit())[0];
	    int yExit = UI.transformCoordinateFromDomain(arc.getExit())[1];
	    int xEntrance = UI.transformCoordinateFromDomain(arc.getEntrance())[0];
	    int yEntrance = UI.transformCoordinateFromDomain(arc.getEntrance())[1];
	    
	    g.drawLine(xExit, yExit, xEntrance, yEntrance);
	}
    }

    void addArc(Arc curArc) {
	this.arcs.add(curArc);
    }
}
