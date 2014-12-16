package recyclapp.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.util.LinkedList;
import javax.swing.JPanel;
import recyclapp.model.Arc;
import recyclapp.model.Coordinate;
import recyclapp.model.DataElement;

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

        LinkedList<DataElement> listDataElements = interfacePrincipale.getListDataElements(isZoom);
        
        for (DataElement dataElement : listDataElements) {
            int id = dataElement.element.getType();
            int x = (int) (dataElement.x * zoom);
            int y = (int) (dataElement.y * zoom);
            int w = (int) (dataElement.width * zoom);
            int h = (int) (dataElement.height * zoom);

            g.setColor(dataElement.element.getColor());
            g.drawString(dataElement.element.getName(), x, y + h + 15);
            g.setColor(Color.BLACK);

            Image curImage = this.interfacePrincipale.getImageType(id);
            g.drawImage(curImage, x, y, w, h, this);
            Arc[] arcs = dataElement.element.getArcs();
            for (Arc arc : arcs) {
                if (arc != null) {
                    g.setColor(Color.BLUE);
                    drawArrow(g, x, y, arc.getEntranceElement().getCoordinate().getX(),
                            arc.getEntranceElement().getCoordinate().getY());
                    g.setColor(Color.BLACK);
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

    private void drawArrow(Graphics g1, int xExit, int yExit, int xEntrance, int yEntrance) {
        int TAILLE_FLECHE = (int) (20*zoom);
        int sizeImage = (int) (interfacePrincipale.getPanelTools().getSizeImage() * zoom);

        // ATTENTION : Le zoom s'applique déjà sur Exit grâce à la méthode appelante
        xExit = (int) (xExit);
        yExit = (int) (yExit);

        xEntrance = (int) (xEntrance * zoom);
        yEntrance = (int) (yEntrance * zoom);

        // Modifie la position de départ
        if (xExit < xEntrance && yExit < yEntrance) {
            xExit = (int) (xExit + sizeImage);
            yExit = (int) (yExit + sizeImage);
        } else if (xExit < xEntrance && yExit > yEntrance) {
            xExit = (int) (xExit + sizeImage);
        } else if (xExit > xEntrance && yExit < yEntrance) {
            yExit = (int) (yExit + sizeImage);
        } else if (xExit > xEntrance && yExit > yEntrance) {
            // Rien à changer (xExit vaut xExit et yExit vaut yExit)
        }
        // Modifie la position d'arrivée
        if (yExit > yEntrance) {
            yEntrance = (int) (yEntrance + sizeImage);
        }
        if (xExit > xEntrance) {
            xEntrance = (int) (xEntrance + sizeImage);
        }

        double dx = (xEntrance - xExit);
        double dy = (yEntrance - yExit);

        Graphics2D g = (Graphics2D) g1.create();

        double angle = Math.atan2(dy, dx);

        int distance = (int) Math.sqrt(dx * dx + dy * dy);
        AffineTransform at = AffineTransform.getTranslateInstance(xExit, yExit);
        at.concatenate(AffineTransform.getRotateInstance(angle));
        g.transform(at);

        g.drawLine(0, 0, distance, 0);
        g.fillPolygon(new int[]{distance, distance - TAILLE_FLECHE, distance - TAILLE_FLECHE, distance},
                new int[]{0, -TAILLE_FLECHE, TAILLE_FLECHE, 0}, 4);
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

    public void setZoom(float zoom) {
        this.zoom = zoom;
        interfacePrincipale.updateInterfacePlan(zoom);
        logZoom();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getWheelRotation() == -1) {
            zoomIn();
        } else {
            zoomOut();
        }
    }

    public void zoomIn() {
        if (zoom <= 2) {
            isZoom = true;
            zoom += 0.1;
            repaint();
        }
        interfacePrincipale.updateInterfacePlan(zoom);
        logZoom();
    }

    public void zoomOut() {
        if (zoom > 0.2) {
            isZoom = true;
            zoom -= 0.1;
            repaint();
        }
        interfacePrincipale.updateInterfacePlan(zoom);
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

    public void logZoom() {
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
    public void showSelectedElement(DataElement dataElement) {
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
