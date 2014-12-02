package recyclapp.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.LinkedList;
import static javax.swing.GroupLayout.Alignment.CENTER;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InterfaceOutils extends JPanel {
    
    private boolean moveTools;
    private int idTools;
    private int[][] coordImage = new int[4][2];
    private int[] coord = new int[2];
    private LinkedList<Image> images = new LinkedList<>();
    private int sizeImage = 70;
    
    public static final int ID_NOTHING = -1;
    public static final int ID_TOOL_STATION = 0;
    public static final int ID_TOOL_ENTREE = 1;
    public static final int ID_TOOL_SORTIE = 2;
    public static final int ID_TOOL_ARC = 3;
    public static final int ID_TOOL_JONCTION = 4;
    
    public InterfaceOutils() {
        this.idTools = ID_NOTHING;
        this.moveTools = false;
        this.setLayout(new java.awt.BorderLayout());
        coord = new int[2];
        coord[0] = 0;
        coord[1] = 0;
        images = new LinkedList<>();
        coordImage = new int[5][2];
        images.add(ID_TOOL_STATION, getToolkit().getImage("src/recyclapp/resources/Station.png"));
        images.add(ID_TOOL_ENTREE, getToolkit().getImage("src/recyclapp/resources/Entree.png"));
        images.add(ID_TOOL_SORTIE, getToolkit().getImage("src/recyclapp/resources/Sortie.png"));
        images.add(ID_TOOL_ARC, getToolkit().getImage("src/recyclapp/resources/Arc.png"));
        images.add(ID_TOOL_JONCTION, getToolkit().getImage("src/recyclapp/resources/Jonction.png"));
        sizeImage = 70;
        
        this.setBackground(new Color(164, 183, 145));
        this.setPreferredSize(new java.awt.Dimension(150, 588));
        this.setLayout(new java.awt.BorderLayout());
        
        initComponents();
        
        imagePanelStation.setImage(getToolkit().getImage("src/recyclapp/resources/Station.png"));
        imagePanelEntree.setImage(getToolkit().getImage("src/recyclapp/resources/Entree.png"));
        imagePanelSortie.setImage(getToolkit().getImage("src/recyclapp/resources/Sortie.png"));
        imagePanelJonction.setImage(getToolkit().getImage("src/recyclapp/resources/Jonction.png"));
        imagePanelArc.setImage(getToolkit().getImage("src/recyclapp/resources/Arc.png"));
        
    }

    /**
     * *
     * summary : Retourne l'ID de l'élément aux coordonées indiquées.
     *
     * @param x
     * @param y
     * @return
     */
    private int searchIdTool(int x, int y) {
        int result = ID_NOTHING;
        
        for (int imageId = 0; imageId < images.size(); imageId++) {
            if (x >= coordImage[imageId][0] && x <= coordImage[imageId][0] + sizeImage
                    && y >= coordImage[imageId][1] && y <= coordImage[imageId][1] + sizeImage) {
                coord[0] = coordImage[imageId][0];
                coord[1] = coordImage[imageId][1];
                result = imageId;
                break;
            }
        }
        return result;
    }

    /**
     * *
     * summary : Permet de définir si l'outil aux coordonnées cliquées est
     * déplacable.
     *
     * @param x
     * @param y
     */
    public void moveTool(int x, int y) {
        this.idTools = this.searchIdTool(x, y);
        if (getIdTools() >= 0) {
            this.moveTools = true;
        } else {
            this.idTools = ID_NOTHING;
            this.moveTools = false;
        }
    }
    
    public int getIdTools() {
        return idTools;
    }
    
    public void setMoveTools(boolean isMoveable) {
        this.moveTools = isMoveable;
    }
    
    public void resetTools() {
        this.idTools = ID_NOTHING;
    }
    
    public boolean isMoveTools() {
        return moveTools;
    }
    
    public int getCoordWidth(int i) {
        return coordImage[i][0] + sizeImage;
    }
    
    public int getCoordHeigth(int i) {
        return coordImage[i][1] + sizeImage;
    }
    
    public Image getImages(int imageId) {
        return images.get(imageId);
    }
    
    public int getSizeImage() {
        return sizeImage;
    }
    
    @Override
    public void paintComponent(Graphics graphic) {
        super.paintComponent(graphic);
        int height = 80;
        
        for (int imageId = 0; imageId < images.size(); imageId++) {
            coordImage[imageId][0] = this.getWidth() / 2 - (sizeImage / 2);
            coordImage[imageId][1] = height * (imageId + 1);
            graphic.drawImage(images.get(imageId), coordImage[imageId][0], coordImage[imageId][1], sizeImage, sizeImage, this);
        }
        graphic.setColor(Color.red);
        if (this.moveTools) {
            graphic.drawRect(coord[0], coord[1], sizeImage, sizeImage);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelOutils = new javax.swing.JPanel();
        jLabelOutils = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabelStation = new javax.swing.JLabel();
        imagePanelStation = new recyclapp.view.ImagePanel();
        jLabelEntreeUsine = new javax.swing.JLabel();
        imagePanelEntree = new recyclapp.view.ImagePanel();
        jLabelSortieUsine = new javax.swing.JLabel();
        imagePanelSortie = new recyclapp.view.ImagePanel();
        jLabelArc = new javax.swing.JLabel();
        imagePanelArc = new recyclapp.view.ImagePanel();
        jLabelJonction = new javax.swing.JLabel();
        imagePanelJonction = new recyclapp.view.ImagePanel();

        jPanelOutils.setBackground(new java.awt.Color(164, 183, 145));

        jLabelOutils.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabelOutils.setText("Outils");

        jPanel1.setMinimumSize(new java.awt.Dimension(140, 70));
        jPanel1.setPreferredSize(new java.awt.Dimension(140, 100));
        jPanel1.setLayout(new java.awt.GridLayout(5, 2, 5, 5));

        jLabelStation.setText("  Station");
        jPanel1.add(jLabelStation);
        jPanel1.add(imagePanelStation);

        jLabelEntreeUsine.setText("  Entrée usine");
        jPanel1.add(jLabelEntreeUsine);
        jPanel1.add(imagePanelEntree);

        jLabelSortieUsine.setText("  Sortie usine");
        jPanel1.add(jLabelSortieUsine);
        jPanel1.add(imagePanelSortie);

        jLabelArc.setText("  Arc");
        jLabelArc.setToolTipText("");
        jPanel1.add(jLabelArc);
        jPanel1.add(imagePanelArc);

        jLabelJonction.setText("  Jonction");
        jLabelJonction.setToolTipText("");
        jPanel1.add(jLabelJonction);
        jPanel1.add(imagePanelJonction);

        javax.swing.GroupLayout jPanelOutilsLayout = new javax.swing.GroupLayout(jPanelOutils);
        jPanelOutils.setLayout(jPanelOutilsLayout);
        jPanelOutilsLayout.setHorizontalGroup(
            jPanelOutilsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelOutilsLayout.createSequentialGroup()
                .addGroup(jPanelOutilsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelOutilsLayout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addComponent(jLabelOutils))
                    .addGroup(jPanelOutilsLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanelOutilsLayout.setVerticalGroup(
            jPanelOutilsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelOutilsLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabelOutils)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 484, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelOutils, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelOutils, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private recyclapp.view.ImagePanel imagePanelArc;
    private recyclapp.view.ImagePanel imagePanelEntree;
    private recyclapp.view.ImagePanel imagePanelJonction;
    private recyclapp.view.ImagePanel imagePanelSortie;
    private recyclapp.view.ImagePanel imagePanelStation;
    private javax.swing.JLabel jLabelArc;
    private javax.swing.JLabel jLabelEntreeUsine;
    private javax.swing.JLabel jLabelJonction;
    private javax.swing.JLabel jLabelOutils;
    private javax.swing.JLabel jLabelSortieUsine;
    private javax.swing.JLabel jLabelStation;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelOutils;
    // End of variables declaration//GEN-END:variables
}
