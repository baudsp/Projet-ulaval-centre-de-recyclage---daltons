/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recyclapp.view;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import recyclapp.model.Coordinate;

/**
 *
 * @author kevinsalles
 */
public class ModeleInterfacePrincipal {

    private InterfacePrincipale frame;

    public ModeleInterfacePrincipal(InterfacePrincipale frame) {
        this.frame = frame;
    }

    public void GridView() {
        frame.getPanelMap().inverseWithGrid();
        frame.repaint();
    }

    public void changeCursor(int idTool) {
        if (idTool == InterfaceOutils.ID_TOOL_ARC) {
            this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        } else if (idTool >= 0) {
            Toolkit tk = Toolkit.getDefaultToolkit();
            Cursor monCurseur = tk.createCustomCursor(frame.getPanelTools().getImages(idTool), new Point(16, 16), "Tools");
            this.frame.setCursor(monCurseur);
        } else if (idTool == -2) {
            this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        } else {
            this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    }

    public void setVisiblePaneTools(boolean visible) {
        this.frame.getPanelTools().setVisible(visible);
    }

    public void setVisiblePaneParam(boolean visible) {
        this.frame.getPanelParam().setVisible(visible);
    }

    public boolean isOverlapElement(int x, int y, int width, int height) {
        x = x - (width / 2);
        y = y - (height / 2);
        int max = this.frame.getListDataElements(false).size();
        Rectangle rectangle = new Rectangle(x, y, width, height);

        for (int i = 0; i < max; i++) {
            int r2X = this.frame.getListDataElements(false).get(i).x;
            int r2Y = this.frame.getListDataElements(false).get(i).y;
            int r2width = this.frame.getListDataElements(false).get(i).width;
            int r2height = this.frame.getListDataElements(false).get(i).height;

            Rectangle rectangle2 = new Rectangle(r2X, r2Y, r2width, r2height);

            if (rectangle2.intersects(rectangle)) {
                this.frame.getDebug().setText("Trouvé");
                return true;
            }
        }
        return false;
    }

    /**
     * *
     * summary : Permet de savoir si un élément se trouve aux coordonées
     * cliquées.
     *
     * @param x
     * @param y
     * @return
     */
    public boolean isThereAnElementHere(int x, int y) {
        int listDataElements = this.frame.getListDataElements(false).size();
        boolean found = false;

        for (int i = 0; i < listDataElements; i++) {
            int rX = this.frame.getListDataElements(false).get(i).x;
            int rY = this.frame.getListDataElements(false).get(i).y;
            int rWidth = this.frame.getListDataElements(false).get(i).width;
            int rHeight = this.frame.getListDataElements(false).get(i).height;

            Rectangle rectangle = new Rectangle(rX, rY, rWidth, rHeight);

            if (rectangle.contains(x, y)) {
                this.frame.getDebug().setText("Trouvé");
                found = true;
                break;
            }
        }
        return found;
    }

    public int convertPixelToMeter(int pixel) {
        int normeMeter = 5;
        int normePixel = this.frame.getPanelMap().getEcart();
        float zoom = this.frame.getPanelMap().getZoom();
        float meter = (pixel * normeMeter) / (zoom * normePixel);
        return (int) (meter);
    }
    
    public void exportImageAsPng(){
        BufferedImage bufferedImage = new BufferedImage(frame.getSize().width, frame.getSize().height, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = bufferedImage.createGraphics();
        frame.paint(graphics);
        graphics.dispose();

        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int option = chooser.showSaveDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            // TODO : Verifier que l'on écrase pas un fichier existant
            
            File selectedPfile = chooser.getSelectedFile();
            String path = selectedPfile.getAbsolutePath() + ".png";
            
            try {
                // Enregistrer le fichier
                if (ImageIO.write(bufferedImage, "png", new File(path))) {
                    String question = "Voulez vous ouvrir l'image nouvellement créée ?";
                    Object[] optionsReponse = {"Oui", "Non, merci",};

                    int n = JOptionPane.showOptionDialog(null,
                            question,
                            "Ouvrir l'image ?",
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            optionsReponse,
                            optionsReponse[1]);

                    // Réponse oui
                    if (n == 0) {
                        // Ouvrir l'image
                        Process p = new ProcessBuilder("explorer.exe", "/open," + path).start();
                    }
                } else{
                    // Problème lors de l'écriture
                }
            } catch (IOException ex) {
                Logger.getLogger(InterfacePrincipale.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Coordinate findCooMagnetique(int x, int y) {
        int ecart = frame.getPanelMap().getEcart();
        float zoom = frame.getPanelMap().getZoom();
        
        int newX = (int)((x - x%(ecart * zoom)) + (ecart/2)*zoom);
        int newY = (int)((y - y%(ecart * zoom)) + (ecart/2)*zoom);
        
        return new Coordinate(newX, newY);
    }
}
