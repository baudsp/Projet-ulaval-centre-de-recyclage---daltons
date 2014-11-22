
package RecyclApp.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.LinkedList;
import javax.swing.JPanel;
import javax.swing.RowFilter.Entry;

public class InterfacePlan extends JPanel{
    private boolean withGrid;
    private boolean isDrag;
    private int[] coordCursor;
    private Image imgCursor;
    private InterfacePrincipale ip;

    public InterfacePlan(InterfacePrincipale ip) {

        withGrid = false;
        isDrag = false;
        coordCursor = new int[2];
	//arcs = new LinkedList<>();
        //setBackground(new java.awt.Color(255, 255, 255));
	//eltCursor = null;
	
	this.setBackground(java.awt.Color.white);
        javax.swing.GroupLayout panelMapLayout = new javax.swing.GroupLayout(this);
        this.setLayout(panelMapLayout);
        this.ip = ip;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (withGrid) {
            drawGrid(g);
        }

	if (isDrag) {
	    g.drawImage(imgCursor, coordCursor[0], coordCursor[1], imgCursor.getWidth(this),imgCursor.getWidth(this), this);
            isDrag = false;
	}

        for(int i=0;i<this.ip.getPositionElements().size();i++)
        {
            //System.out .print("Value : "+entry.getValue().x);
            int id = this.ip.getPositionElements().get(i).id;
            int x = this.ip.getPositionElements().get(i).x;
            int y = this.ip.getPositionElements().get(i).y;
            g.drawImage(this.ip.getImageType(id),x,y,70,70, this);
        }
    }
    
    public void drawImageFollowingCursor(Image image,int x, int y) {
        
	imgCursor = image;
        isDrag = true;
        this.coordCursor[0] = x;
        this.coordCursor[1] = y;
	this.repaint();
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

    public void moveElement(int id, int x, int y) {
        /*for (Element e : elements) {
            if (e.id == id) {
                moveElement(e, x, y);
                return;
            }
        }*/
    }

    /*private void moveElement(Element e, int x, int y) {
        e.x = x;
        e.y = y;
        repaint();
    }*/

    public void resizeElement(int id, int width, int height) {
       /* for (Element e : elements) {
            if (e.id == id) {
                resizeElement(e, width, height);
                return;
            }
        }*/
    }

   /* private void resizeElement(Element e, int width, int height) {
        e.width = width;
        e.height = height;
        repaint();
    }*/

    /**
     * retourne l'element existant aux coordonnées en paramètres
     */
   /* public Element checkElement(int x, int y) {
	for (Element e : elements) {
	    if (x>= e.x && x<=(e.x+e.width) && y>=e.y && y<=(e.y + e.height)) {
		return e;
	    }
	}	
	return null;
    }*/
    
    /*public void deleteElements() {
        elements.clear();
        repaint();
    }*/
    /*
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
    }*/
}
