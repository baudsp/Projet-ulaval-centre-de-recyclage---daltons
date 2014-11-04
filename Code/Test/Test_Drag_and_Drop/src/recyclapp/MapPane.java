package recyclapp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.LinkedList;
import javax.swing.JPanel;

public class MapPane extends JPanel {

    private LinkedList<Element> elements;
    private boolean withGrid;

    public MapPane() {
        elements = new LinkedList<>();
        withGrid = false;
        setBackground(new java.awt.Color(255, 255, 255));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (withGrid) {
            drawGrid(g);
        }
        for (Element e : elements) {
            g.drawImage(e.image, e.x, e.y, e.height, e.width, this);
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

    public void deleteElements() {
        elements.clear();
        repaint();
    }

    private class Element {

        public int id;
        public int x, y;
        public int width, height;
        public Image image;

        public Element(int id, int x, int y, int width, int height, Image image) {
            this.id = id;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.image = image;
        }
    }
}
