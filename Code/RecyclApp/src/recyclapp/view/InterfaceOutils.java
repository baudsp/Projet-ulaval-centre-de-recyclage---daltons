
package recyclapp.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.LinkedList;
import javax.swing.JPanel;

public class InterfaceOutils extends JPanel{
    private boolean moveTools;
    private int idTools;
    private int[][] coordImage;
    private int[] coord;
    private LinkedList<Image> images;
    private int sizeImage;
    private int paddingTop;

    public InterfaceOutils() {
        this.idTools = -1;
        this.moveTools = false;
        this.setLayout(new java.awt.BorderLayout());
        coord = new int[2];
        coord[0] = 0;
        coord[1] = 0;
        images = new LinkedList<>();
        coordImage = new int [2][2];
        images.add(getToolkit().getImage("resources/Station.png"));
        images.add(getToolkit().getImage("resources/tool2.png"));
        sizeImage = 70;
        
    }
    
    private int searchTool(int x, int y) {
        for(int i=0;i<2;i++){
            if (x >= coordImage[i][0] && x <= coordImage[i][0] + sizeImage && y >= coordImage[i][1]  && y <= coordImage[i][1]  + sizeImage) {
                coord[0] = coordImage[i][0];
                coord[1] = coordImage[i][1];
                return i;
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
    
    public int getIdTools() {
        return idTools;
    }
    
    public void setMoveTools(boolean b)
    {
        this.moveTools = b;
    }
    
    public boolean isMoveTools() {
        return moveTools;
    }
    
    public int getCoordW(int i) {
        return coordImage[i][0]+sizeImage;
    }

    public int getCoordH(int i) {
        return coordImage[i][1]+sizeImage;
    }
    
    public Image getImages(int i) {
        return images.get(i);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        coordImage[0][0] = this.getWidth() / 2 - (sizeImage/2);
        coordImage[0][1] = 100;
        coordImage[1][0] = this.getWidth() / 2 - (sizeImage/2);
        coordImage[1][1] = 200;
        g.drawImage(images.get(0), coordImage[0][0],coordImage[0][1],sizeImage,sizeImage, this);
        g.drawImage(images.get(1), coordImage[1][0],coordImage[1][1],sizeImage,sizeImage, this);
        g.setColor(Color.red);
        if(this.moveTools)
            g.drawRect(coord[0],coord[1],sizeImage,sizeImage);
        else
            g.drawRect(0,0,0,0);
    }
    
}
