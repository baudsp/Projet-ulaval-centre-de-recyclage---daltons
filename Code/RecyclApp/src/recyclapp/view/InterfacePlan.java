package recyclapp.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.List;
import javax.swing.JPanel;
import recyclapp.model.Arc;
import recyclapp.model.Plan.DataElement;

public class InterfacePlan extends JPanel implements MouseWheelListener,KeyListener{

    private boolean withGrid;
    private boolean isDrag;
    private int[] coordCursor;
    private Image imgCursor;
    private int ecart;
    private float zoom;
    private boolean isZoom;
    private InterfacePrincipale ip;

    public InterfacePlan(InterfacePrincipale ip) {

	withGrid = false;
	isDrag = false;
	coordCursor = new int[2];
        ecart = 50;
        zoom = 1;
        isZoom = false;
	//arcs = new LinkedList<>();
	//setBackground(new java.awt.Color(255, 255, 255));
	//eltCursor = null;

	this.setBackground(java.awt.Color.white);
	javax.swing.GroupLayout panelMapLayout = new javax.swing.GroupLayout(this);
	this.setLayout(panelMapLayout);
	this.ip = ip;
        this.addKeyListener(this);
        this.addMouseWheelListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
	super.paintComponent(g);
	if (withGrid) {
	    drawGrid(g);
	}

	if (isDrag) {
            g.drawImage(imgCursor, coordCursor[0], coordCursor[1], (int) (imgCursor.getWidth(this)*zoom), (int) (imgCursor.getHeight(this)*zoom), this);
            isDrag = false;
	}

	for(int i=0;i<this.ip.getPositionElements(isZoom).size();i++){
	    
            int id = this.ip.getPositionElements(isZoom).get(i).id;
            int x = (int) (this.ip.getPositionElements(isZoom).get(i).x*zoom);
            int y = (int) (this.ip.getPositionElements(isZoom).get(i).y*zoom);
            int w = (int) (this.ip.getPositionElements(isZoom).get(i).width*zoom);
            int h = (int) (this.ip.getPositionElements(isZoom).get(i).height*zoom);
            g.drawImage(this.ip.getImageType(id),x,y,w,h, this);
	    List<Arc> arcs = this.ip.getPositionElements(isZoom).get(i).elt.getArcs();
	    for (Arc arc : arcs) {
		g.drawLine(x + 35, y + 35, arc.getEntranceElement().getCoordinate().getX() + 35, 
			arc.getEntranceElement().getCoordinate().getY() + 35);
	    }
	}
    }

    public void drawImageFollowingCursor(Image image, int x, int y) {

	imgCursor = image;
	isDrag = true;
	this.coordCursor[0] = x;
	this.coordCursor[1] = y;
	this.repaint();
    }

    private void drawGrid(Graphics g) {
	g.setColor(Color.BLACK);
	int x = 0;
	int y = 0;

	while (x < getWidth()) {
	    x += ecart*zoom;
	    g.drawLine(x, 0, x, getHeight());
	}
	while (y < getHeight()) {
	    y += ecart*zoom;
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
	repaint();
    }
    
    public float getZoom() {
        return zoom;
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

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) { 
        if(e.getWheelRotation() == 1)
        {
            if(zoom <= 2)
            {
                isZoom = true;
                zoom += 0.1;
                repaint();
            }
        }
        else if(zoom > 0.2)
        {
            isZoom = true;
            zoom -= 0.1;
            repaint();
        } 
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) { }

    @Override
    public void keyReleased(KeyEvent e) {}

    
}
