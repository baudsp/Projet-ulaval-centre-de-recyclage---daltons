package recyclapp.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.JPanel;
import recyclapp.model.Arc;
import recyclapp.model.Coordinate;
import recyclapp.model.Plan;
import recyclapp.model.Plan.DataElement;

public class InterfacePlan extends JPanel implements MouseWheelListener, KeyListener {

    private boolean withGrid;
    private boolean isDrag;
    private int[] coordCursor;
    private Image imgCursor;
    private int ecart;
    private float zoom;
    private boolean isZoom;
    private InterfacePrincipale ip;
    
    private boolean stationIsSelected = false;
    private DataElement selectedElement;

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
	    g.drawImage(imgCursor, coordCursor[0], coordCursor[1], (int) (imgCursor.getWidth(this) * zoom), (int) (imgCursor.getHeight(this) * zoom), this);
	    isDrag = false;
	}

	for (int i = 0; i < this.ip.getPositionElements(isZoom).size(); i++) {

	    int id = this.ip.getPositionElements(isZoom).get(i).id;
	    int x = (int) (this.ip.getPositionElements(isZoom).get(i).x * zoom);
	    int y = (int) (this.ip.getPositionElements(isZoom).get(i).y * zoom);
	    int w = (int) (this.ip.getPositionElements(isZoom).get(i).width * zoom);
	    int h = (int) (this.ip.getPositionElements(isZoom).get(i).height * zoom);
	    g.drawImage(this.ip.getImageType(id), x, y, w, h, this);
	    Arc[] arcs = this.ip.getPositionElements(isZoom).get(i).elt.getArcs();
	    for (Arc arc : arcs) {
		if (arc != null) {
		    g.drawLine(x + w / 2, y + h / 2,
			    (int) ((arc.getEntranceElement().getCoordinate().getX() * zoom + w / 2)),
			    (int) (arc.getEntranceElement().getCoordinate().getY() * zoom + w / 2));
		    int[][] tabPts = getArrowArc(x + w / 2, y + h / 2,
			    (int) ((arc.getEntranceElement().getCoordinate().getX() * zoom + w / 2)),
			    (int) ((arc.getEntranceElement().getCoordinate().getY() * zoom + w / 2)), zoom);
		    g.fillPolygon(tabPts[0], tabPts[1], 3);
		}
	    }
            if(stationIsSelected){
                Coordinate coo = selectedElement.elt.getCoordinate();
                g.drawRect(coo.getX(), coo.getY(), selectedElement.width, selectedElement.height);
            }
	}
	isZoom = false;
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
	    x += ecart * zoom;
	    g.drawLine(x, 0, x, getHeight());
	}
	while (y < getHeight()) {
	    y += ecart * zoom;
	    g.drawLine(0, y, getWidth(), y);
	}
    }

    public int getEcart() {
	return ecart;
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

    private int[][] getArrowArc(int xExit, int yExit, int xEntrance, int yEntrance, float zoom) {

	int largeurFleche = (int) (30 * zoom);
	int longeurFleche = (int) (40 * zoom);

	double a = ((double) yExit - yEntrance) / ((double) xExit - xEntrance);

	double angleAvecHorizontale = Math.atan(a);

	double angle2 = angleAvecHorizontale + Math.PI / 2;

	int xPmilieu = (xEntrance + xExit) / 2;
	int yP = (yEntrance + yExit) / 2;

	double xPi;
	double yPi;

	if (xExit < xEntrance) {
	    xPi = xPmilieu + Math.cos(angleAvecHorizontale) * longeurFleche;
	    yPi = yP + Math.sin(angleAvecHorizontale) * longeurFleche;
	} else {
	    xPi = xPmilieu - Math.cos(angleAvecHorizontale) * longeurFleche;
	    yPi = yP - Math.sin(angleAvecHorizontale) * longeurFleche;
	}

	double xP2 = xPmilieu + Math.cos(angle2) * largeurFleche / 2;
	double yP2 = yP + Math.sin(angle2) * largeurFleche / 2;

	double xP3 = xPmilieu * 2 - xP2;
	double yP3 = yP * 2 - yP2;

	int[] tabX = {(int) xP2, (int) xP3, (int) xPi};
	int[] tabY = {(int) yP2, (int) yP3, (int) yPi};

	int[][] tabPts = new int[2][3];

	tabPts[0] = tabX;
	tabPts[1] = tabY;

	return tabPts;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
	if (e.getWheelRotation() == 1) {
	    if (zoom <= 2) {
		isZoom = true;
		zoom += 0.1;
		repaint();
	    }
	} else if (zoom > 0.2) {
	    isZoom = true;
	    zoom -= 0.1;
	    repaint();
	}
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
    public boolean showSelectedElement(Plan.DataElement dataElement){
        if(dataElement.id == InterfaceOutils.ID_TOOL_STATION){
            stationIsSelected = true;
            selectedElement = dataElement;
        }
        else if(dataElement.id != - 1){
            return true;
        }
        else{
            stationIsSelected = false;
        }
        return stationIsSelected;
    }
}
