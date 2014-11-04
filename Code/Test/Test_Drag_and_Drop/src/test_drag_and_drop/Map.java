/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test_drag_and_drop;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Vector;
import javax.swing.JPanel;

/**
 *
 * @author Kevin
 */
class Map extends JPanel {

    private boolean doGrid;
    private class Element
    {
        public int id;
        public Image img;
        public int X,Y,widht,height;
        
        public Element(int id,Image img,int x, int y, int w, int h)
        {
            this.id =id;
            this.X = x;
            this.Y = y;
            this.height = h;
            this.widht = w;
            this.img = img;
        }
    }
    private Vector<Element> elements;
    
    public Map() {
        doGrid = false;
        setBackground(new java.awt.Color(255, 255, 255));
        elements = new Vector<Element>();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(doGrid == true)
        {
            int ecart= 50;
            g.setColor(Color.BLACK);

            int x, y;
            x = y = 0;

            while (x < getWidth()) {
                x += ecart;
                g.drawLine(x, 0, x, getHeight());
            }

            while (y < getHeight()) {
                y += ecart;
                g.drawLine(0, y, getWidth(), y);
            }
        }
        
        for(int i = 0;i<elements.size();i++)
        {
            g.drawImage(elements.elementAt(i).img,elements.elementAt(i).X,elements.elementAt(i).Y,elements.elementAt(i).height,elements.elementAt(i).widht, this);
        }
    }

    public void setDoGrid(boolean doGrid) {
        this.doGrid = doGrid;
    }

    public boolean isDoGrid() {
        return doGrid;
    }
    
    public void addElement(int id,Image img,int x, int y, int w, int h)
    {
        elements.add(new Element(id,img,x,y,w,h));
        this.repaint();
    }
    
    public void deleteElements()
    {
        elements.clear();
        this.repaint();
    }
    
    
}
