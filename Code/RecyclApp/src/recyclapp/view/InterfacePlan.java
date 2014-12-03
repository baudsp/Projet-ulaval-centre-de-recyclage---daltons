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
    private InterfacePrincipale interfacePrincipale;

    private boolean stationIsSelected = false;
    private DataElement selectedDataElement;

    public InterfacePlan(InterfacePrincipale ip) {
        withGrid = false;
        isDrag = false;
        coordCursor = new int[2];
        ecart = 70;
        zoom = 1;
        isZoom = false;

        this.setBackground(java.awt.Color.white);
        javax.swing.GroupLayout panelMapLayout = new javax.swing.GroupLayout(this);
        this.setLayout(panelMapLayout);
        this.interfacePrincipale = ip;
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

        for (int i = 0; i < this.interfacePrincipale.getListDataElements(isZoom).size(); i++) {

            DataElement dataElement = this.interfacePrincipale.getListDataElements(isZoom).get(i);
            
            int id = dataElement.element.getType();
            int x = (int) (dataElement.x * zoom);
            int y = (int) (dataElement.y * zoom);
            int w = (int) (dataElement.width * zoom);
            int h = (int) (dataElement.height * zoom);
            
            g.setColor(dataElement.element.getColor());
            g.drawString(dataElement.element.getName(), x, y + h + 15);
            
	    Image curImage = this.interfacePrincipale.getImageType(id);
	    g.drawImage(curImage, x, y, w, h, this);
            Arc[] arcs = dataElement.element.getArcs();
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
            if (getStationIsSelected()) {
                Coordinate coo = getSelectedDataElement().element.getCoordinate();
                g.drawRect((int) (coo.getX() * zoom), (int) (coo.getY() * zoom), 
                        (int) (getSelectedDataElement().width * zoom), (int) (getSelectedDataElement().height * zoom));
            }
        }
        isZoom = false;
    }

    public void drawImageFollowingCursor(Image image, int x, int y) {
        int halfImageSize = (int) ((interfacePrincipale.getPanelTools().getSizeImage() / 2) * zoom);
        
        imgCursor = image;
        isDrag = true;
        this.coordCursor[0] = x - halfImageSize;
        this.coordCursor[1] = y - halfImageSize;
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

    /**
     * *
     * summary : Ecart est le nombre de pixel entre chaque trait de la grille.
     *
     * @return
     */
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

    public float getZoom() {
        return zoom;
    }
    
    public void setZoom(float zoom){
        this.zoom = zoom;
        interfacePrincipale.updateInterfacePlan(zoom);
        logZoom();
    }

    private int[][] getArrowArc(int xExit, int yExit, int xEntrance, int yEntrance, float zoom) {

        int largeurFleche = (int) (30 * zoom);
        int longeurFleche = (int) (40 * zoom);

        double opposeSurAdjacent = ((double) yExit - yEntrance) / ((double) xExit - xEntrance);

        double angleAvecHorizontale = Math.atan(opposeSurAdjacent);

        double angle2 = angleAvecHorizontale + Math.PI / 2;

        // Equation de droite y = ax + b
        //trouver le coef de la droite : a
//        double coefDroite = (yEntrance - yExit) / (xEntrance - xExit);
        // trouver l'ordonnée à l'origine b = y - a * x
//        double ordonneeOrigine = yExit - (coefDroite * xExit);
//      // distance entre deux points RAC((x2 - x1)² + (y2 - y1)²)
        int xMilieu = (xEntrance + xExit) / 2;
        int yMilieu = (yEntrance + yExit) / 2;

        double xPi;
        double yPi;

        if (xExit < xEntrance) {
            xPi = xMilieu + Math.cos(angleAvecHorizontale) * longeurFleche;
            yPi = yMilieu + Math.sin(angleAvecHorizontale) * longeurFleche;
        } else {
            xPi = xMilieu - Math.cos(angleAvecHorizontale) * longeurFleche;
            yPi = yMilieu - Math.sin(angleAvecHorizontale) * longeurFleche;
        }

        double xP2 = xMilieu + Math.cos(angle2) * largeurFleche / 2;
        double yP2 = yMilieu + Math.sin(angle2) * largeurFleche / 2;

        double xP3 = xMilieu * 2 - xP2;
        double yP3 = yMilieu * 2 - yP2;

        int[] tabX = {(int) xP2, (int) xP3, (int) xPi};
        int[] tabY = {(int) yP2, (int) yP3, (int) yPi};

        int[][] tabPts = new int[2][3];

        tabPts[0] = tabX;
        tabPts[1] = tabY;

        return tabPts;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getWheelRotation() == -1) {
            zoomIn();
        } else {
            zoomOut();
        }
        
        interfacePrincipale.updateInterfacePlan(zoom);
    }

    public void zoomIn() {
        if (zoom <= 2) {
            isZoom = true;
            zoom += 0.1;
            repaint();
        }
        logZoom();
    }

    public void zoomOut() {
        if (zoom > 0.2) {
            isZoom = true;
            zoom -= 0.1;
            repaint();
        }
        logZoom();
    }

    /**
     * **
     * summary : prends en paramètres les coordonées X et Y en mètres. Le zoom
     * est calculé ensuite.
     *
     * @param x, coordonnées X
     * @param y, coordonées Y
     */
    public void logZoomAndCoordinates(int x, int y) {
        logCoordinates(x, y);
        logZoom();
    }

    private void logCoordinates(int x, int y) {
        interfacePrincipale.getLogCoordinates().setText("[" + x + ";" + y + "]");
    }

    private void logZoom() {
        int newZoom = Math.round(zoom * 100);
        interfacePrincipale.getLogZoom().setText("    |    Zoom : " + newZoom + "%");
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

    /**
     * *
     * summary : Affiche le carré de sélection si le dataElement a un élément,
     * sinon cache l'ancien carré.
     *
     * @param dataElement
     * @return
     */
    public void showSelectedElement(Plan.DataElement dataElement) {
        if (dataElement.type >= InterfaceOutils.ID_TOOL_STATION) {
            if (getSelectedDataElement() != null) {
                if (getSelectedDataElement().element != dataElement.element) {
                    // On a changé d'élément selectionné
                    setSelectedDataElement(dataElement);
                }
            } else {
                setSelectedDataElement(dataElement);
            }

            setStationIsSelected(true);
        } else {
            setSelectedDataElement(dataElement);
            setStationIsSelected(false);
        }
    }

    public DataElement getSelectedDataElement() {
        return selectedDataElement;
    }

    public void setSelectedDataElement(DataElement selectedDataElement) {
        this.selectedDataElement = selectedDataElement;
    }

    public boolean getStationIsSelected() {
        return stationIsSelected;
    }

    public void setStationIsSelected(boolean stationIsSelected) {
        this.stationIsSelected = stationIsSelected;
    }
}
