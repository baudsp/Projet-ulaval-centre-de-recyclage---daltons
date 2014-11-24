package recyclapp.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.LinkedList;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InterfaceOutils extends JPanel {

    private boolean moveTools;
    private int idTools;
    private int[][] coordImage;
    private int[] coord;
    private LinkedList<Image> images;
    private int sizeImage;

    public static final int ID_TOOL_STATION = 0;
    public static final int ID_TOOL_ENTREE = 1;
    public static final int ID_TOOL_SORTIE = 2;
    public static final int ID_TOOL_ARC = 3;

    public InterfaceOutils() {
	this.idTools = -1;
	this.moveTools = false;
	this.setLayout(new java.awt.BorderLayout());
	coord = new int[2];
	coord[0] = 0;
	coord[1] = 0;
	images = new LinkedList<>();
	coordImage = new int[4][2];
	images.add(ID_TOOL_STATION, getToolkit().getImage("resources/Station.png"));
	images.add(ID_TOOL_ENTREE, getToolkit().getImage("resources/Entree.png"));
	images.add(ID_TOOL_SORTIE,getToolkit().getImage("resources/Sortie.png"));
	images.add(ID_TOOL_ARC, getToolkit().getImage("resources/Arc.png"));
	sizeImage = 70;
	JLabel jLabel1 = new javax.swing.JLabel();
	jLabel1.setFont(new java.awt.Font("Waree", 1, 18));
	jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
	jLabel1.setText("Outils");

	this.setBackground(new Color(164, 183, 145));
	this.setPreferredSize(new java.awt.Dimension(150, 588));
	this.setLayout(new java.awt.BorderLayout());
	this.add(jLabel1, java.awt.BorderLayout.PAGE_START);
    }

    private int searchTool(int x, int y) {
	for (int i = 0; i < images.size(); i++) {
	    if (x >= coordImage[i][0] && x <= coordImage[i][0] + sizeImage && y >= coordImage[i][1] && y <= coordImage[i][1] + sizeImage) {
		coord[0] = coordImage[i][0];
		coord[1] = coordImage[i][1];
		return i;
	    }
	}
	return -1;
    }

    public void moveTool(int x, int y) {
	this.idTools = this.searchTool(x, y);
	if (idTools >= 0) {
	    this.moveTools = true;
	} else {
	    this.idTools = -1;
	    this.moveTools = false;
	}
    }

    public int getIdTools() {
	return idTools;
    }

    public void setMoveTools(boolean b) {
	this.moveTools = b;
    }

    public void resetTools(){
	this.idTools = -1;
    }
    
    public boolean isMoveTools() {
	return moveTools;
    }

    public int getCoordW(int i) {
	return coordImage[i][0] + sizeImage;
    }

    public int getCoordH(int i) {
	return coordImage[i][1] + sizeImage;
    }

    public Image getImages(int i) {
	return images.get(i);
    }

    public int getSizeImage() {
	return sizeImage;
    }

    @Override
    public void paintComponent(Graphics g) {
	super.paintComponent(g);
	int h = 100;

	for (int i = 0; i < images.size(); i++) {
	    coordImage[i][0] = this.getWidth() / 2 - (sizeImage / 2);
	    coordImage[i][1] = h * (i + 1);
	    g.drawImage(images.get(i), coordImage[i][0], coordImage[i][1], sizeImage, sizeImage, this);
	}
	g.setColor(Color.red);
	if (this.moveTools) {
	    g.drawRect(coord[0], coord[1], sizeImage, sizeImage);
	} else {
	    g.drawRect(0, 0, 0, 0);
	}
    }

}
