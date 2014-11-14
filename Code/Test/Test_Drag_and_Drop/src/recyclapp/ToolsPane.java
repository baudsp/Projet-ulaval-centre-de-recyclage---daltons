package recyclapp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import javax.swing.JPanel;

public class ToolsPane extends JPanel implements MouseListener {

    private boolean moveTools;
    private int idTools;
    private LinkedList<Coordinates> coordonnees;
    private LinkedList<Image> images;
    private Coordinates coord;

    public ToolsPane() {
        this.idTools = -1;
        this.moveTools = false;
        this.setLayout(new java.awt.BorderLayout());
        //this.addMouseListener(this);
        images = new LinkedList<>();
        coord = new Coordinates(0, 0, 0, 0);
        coordonnees = new LinkedList<>();
        String OS = System.getProperty("os.name").toLowerCase();
        if (OS.contains("win")) {
            images.add(getToolkit().getImage("resources\\tool1.png"));
            images.add(getToolkit().getImage("resources\\tool2.png"));
            images.add(getToolkit().getImage("resources\\arc.png"));
        } else {
            images.add(getToolkit().getImage("resources/tool1.png"));
            images.add(getToolkit().getImage("resources/tool2.png"));
            images.add(getToolkit().getImage("resources/arc.png"));
        }

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        coordonnees.add(new Coordinates(this.getWidth() / 2 - 25, 200, 50, 50));
        coordonnees.add(new Coordinates(this.getWidth() / 2 - 25, 100, 50, 50));
        coordonnees.add(new Coordinates(this.getWidth() / 2 - 25, 300, 50, 50));
        g.drawImage(images.get(0), coordonnees.get(0).x, coordonnees.get(0).y, coordonnees.get(0).width, coordonnees.get(0).height, this);
        g.drawImage(images.get(1), coordonnees.get(1).x, coordonnees.get(1).y, coordonnees.get(1).width, coordonnees.get(1).height, this);
        g.drawImage(images.get(2), coordonnees.get(2).x, coordonnees.get(2).y, coordonnees.get(2).width, coordonnees.get(2).height, this);
        g.setColor(Color.red);
        g.drawRect(coord.x, coord.y, coord.width, coord.height);
        g.setColor(Color.red);
    }

    private int searchTool(int x, int y) {
        for (Coordinates c : coordonnees) {
            if (x >= c.x && x <= c.x + c.width && y >= c.y && y <= c.y + c.height) {
                coord = new Coordinates(c.x, c.y, c.width, c.height);
                return coordonnees.indexOf(c);
            }
        }
        return -1;
    }

    public void moveTool(int x, int y) {
        this.idTools = this.searchTool(x, y);
        System.out.print("Id trouvé : " + this.idTools + "\n");
        if (idTools >= 0) {
            this.moveTools = true;
            System.out.print("Outils selectionné \n");
        } else {
            System.out.print("Outils non selectionné \n");
            this.idTools = -1;
            this.moveTools = false;
        }
    }

    public void setCoord() {
        this.coord.x = 0;
        this.coord.y = 0;
        this.coord.height = 0;
        this.coord.width = 0;
    }

    public int getCoordX(int i) {
        return coordonnees.get(i).x;
    }

    public int getCoordY(int i) {
        return coordonnees.get(i).y;
    }

    public int getCoordW(int i) {
        return coordonnees.get(i).width;
    }

    public int getCoordH(int i) {
        return coordonnees.get(i).height;
    }

    public Image getImages(int i) {
        return images.get(i);
    }

    public boolean isMoveTools() {
        return moveTools;
    }

    public void setMoveTools(boolean moveTools) {
        this.moveTools = moveTools;
    }

    public int getIdTools() {
        return idTools;
    }

    public void setIdTools(int idTools) {
        this.idTools = idTools;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    private class Coordinates {

        public int x;
        public int y;
        public int width;
        public int height;

        public Coordinates(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
    }
}
