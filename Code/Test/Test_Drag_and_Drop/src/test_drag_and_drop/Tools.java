/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test_drag_and_drop;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import java.awt.event.MouseListener;
import java.util.Vector;

/**
 *
 * @author Kevin
 */
public class Tools extends JPanel implements MouseListener{
    
    private boolean moveTools;
    private int idTools;
    private class Coordonnees{
        public int X;
        public int Y;
        public int widht;
        public int height;

        public Coordonnees(int x,int y,int w, int h)
        {
            this.X = x;
            this.Y = y;
            this.widht = w;
            this.height = h;
        }
    }
    private Vector<Coordonnees> liste;
    private Vector<Image> images;
    private Coordonnees coord;
    
    
    public Tools(){
        this.idTools = -1;
        this.moveTools = false;
        this.setLayout(new java.awt.BorderLayout());
        //this.addMouseListener(this);
        images = new Vector<Image>();
        coord = new Coordonnees(0,0,0,0);
        liste = new Vector<Coordonnees>();
        String OS = System.getProperty("os.name").toLowerCase();
        if(OS.indexOf("win") >= 0)
        {
            images.add(getToolkit().getImage("resources\\tool1.png")); 
            images.add(getToolkit().getImage("resources\\tool2.png")); 
        }
        else
        {
            images.add(getToolkit().getImage("resources/tool1.png")); 
            images.add(getToolkit().getImage("resources/tool2.png")); 
        }
        
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        liste.add(new Coordonnees(this.getWidth()/2-25,200, 50, 50));
        liste.add(new Coordonnees(this.getWidth()/2-25,100, 50, 50));
        g.drawImage(images.elementAt(0),liste.elementAt(0).X,liste.elementAt(0).Y,liste.elementAt(0).widht,liste.elementAt(0).height, this);
        g.drawImage(images.elementAt(1),liste.elementAt(1).X,liste.elementAt(1).Y,liste.elementAt(1).widht,liste.elementAt(1).height, this);
        g.setColor(Color.red);  
        g.drawRect(coord.X,coord.Y,coord.widht,coord.height);
        g.setColor(Color.red);  
    }
    
    private int searchTool(int x,int y)
    {
        for(int i=0;i<liste.size();i++)
        {
            Coordonnees c = liste.elementAt(i);
            if(x >= c.X && x <= c.X+c.widht && y >= c.Y && y <= c.Y+c.height)
            {
                coord = new Coordonnees(liste.elementAt(i).X,liste.elementAt(i).Y,liste.elementAt(i).widht,liste.elementAt(i).height);
                return i;
            }       
        } 
        return -1;
    }
    
    public void moveTool(int x, int y)
    {
        this.idTools = this.searchTool(x,y);
        System.out.print("Id trouvé : "+this.idTools+"\n");
        if(idTools >= 0)
        {
            this.moveTools = true;
            System.out.print("Outils selectionné \n");
        }
        else
        {
            System.out.print("Outils non selectionné \n");
            this.idTools = -1;
            this.moveTools = false;
        }
    }

    public void setCoord() {
        this.coord.X = 0;
        this.coord.Y = 0;
        this.coord.height = 0;
        this.coord.widht = 0;
    }

    public int getCoordX(int i) {
        return liste.elementAt(i).X;
    }
    
    public int getCoordY(int i) {
        return liste.elementAt(i).X;
    }
    
    public int getCoordW(int i) {
        return liste.elementAt(i).widht;
    }
    
    public int getCoordH(int i) {
        return liste.elementAt(i).height;
    }
    
    public Image getImages(int i) {
        return images.elementAt(i);
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
}
