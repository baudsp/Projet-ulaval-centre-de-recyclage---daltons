package recyclapp.view;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import recyclapp.model.Plan;
import recyclapp.model.Plan.DataElement;

public class InterfacePrincipale extends javax.swing.JFrame implements ActionListener, MouseMotionListener, MouseListener {

    private InterfacePlan interfacePlan;
    private ModeleInterfacePrincipal mip;
    private Plan plan;
    private JScrollPane jScrollPane1;
    // NEW
    private DataElement dataElementTemp;

    public InterfacePrincipale(Plan plan) {
        initComponents();
        initialize();
    }

    private void initialize() {
        this.addMouseMotionListener(this);
        this.addMouseListener(this);

        interfacePlan = new InterfacePlan(this);
        interfacePlan.setBackground(java.awt.Color.white);
        javax.swing.GroupLayout panelMapLayout = new javax.swing.GroupLayout(interfacePlan);
        interfacePlan.setLayout(panelMapLayout);
        getContentPane().add(interfacePlan, java.awt.BorderLayout.CENTER);
        interfacePlan.addMouseMotionListener(this);
        interfacePlan.addMouseListener(this);
        interfacePlan.setPreferredSize(new Dimension(2000, 2000));
        jScrollPane1 = new javax.swing.JScrollPane(interfacePlan);
        jScrollPane1.setBorder(BorderFactory.createEmptyBorder());
        interfacePlan.setAutoscrolls(true);
        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);
        panelTools.addMouseMotionListener(this);
        panelTools.addMouseListener(this);

        mip = new ModeleInterfacePrincipal(this);
        plan = new Plan();
        // NEW
        this.dataElementTemp = plan.new DataElement();
        this.plan.createElement(1, 20, this.getHeight() / 2);

    }

    public JLabel getLogCoordinates() {
        return logCoordinates;
    }

    public JLabel getLogZoom() {
        return logZoom;
    }

    public void setLogCoordinates(JLabel logCoordinates) {
        this.logCoordinates = logCoordinates;
    }

    public JPanel getPanelInfo() {
        return panelInfo;
    }

    public void setPanelInfo(JPanel panelInfo) {
        this.panelInfo = panelInfo;
    }

    public InterfacePlan getPanelMap() {
        return interfacePlan;
    }

    public void setPanelMap(InterfacePlan panelMap) {
        this.interfacePlan = panelMap;
    }

    public JPanel getPanelParam() {
        return panelParams;
    }

    public void setPanelParam(InterfaceParam panelParams) {
        this.panelParams = panelParams;
    }

    public InterfaceOutils getPanelTools() {
        return panelTools;
    }

    public void setPanelTools(InterfaceOutils panelTools) {
        this.panelTools = panelTools;
    }

    public LinkedList<DataElement> getListDataElements(boolean isZoom) {
        LinkedList<DataElement> listDataElements = this.plan.getListDataElements();
        if (this.dataElementTemp != null && this.dataElementTemp.type >= 0 && !isZoom) {
            DataElement currentDataElement = null;
            for (DataElement dataElement : listDataElements) {
                if (dataElement.x == this.dataElementTemp.x && dataElement.y == this.dataElementTemp.y) {
                    currentDataElement = dataElement;
                }
            }
            listDataElements.remove(currentDataElement);
        }

        return listDataElements;
    }

    public Image getImageType(int imageId) {
        return this.panelTools.getImages(imageId);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (e.getSource().equals(interfacePlan)) {
            interfacePlan.logZoomAndCoordinates(mip.convertPixelToMeter(e.getX()), mip.convertPixelToMeter(e.getY()));
            this.dataElementTemp = this.plan.findDataElement(e.getX(), e.getY(), this.interfacePlan.getZoom());
        }

        if (e.getSource().equals(panelTools)) {
            panelTools.moveTool(e.getX(), e.getY());
        } else {
            panelTools.setMoveTools(false);
        }

        if (panelTools.isMoveTools() || this.dataElementTemp.type >= 0) {
            mip.changeCursor(-2);
        } else {
            mip.changeCursor(InterfaceOutils.ID_NOTHING);
        }

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (this.panelTools.getIdTools() == InterfaceOutils.ID_TOOL_ARC) {
            mip.changeCursor(InterfaceOutils.ID_TOOL_ARC);
        }

        if (e.getSource().equals(interfacePlan)) {
            interfacePlan.logZoomAndCoordinates(mip.convertPixelToMeter(e.getX()), mip.convertPixelToMeter(e.getY()));
            if (this.dataElementTemp != null && this.dataElementTemp.type >= 0) {
                mip.drawImageFollowingCursor(this.dataElementTemp.type, e.getX() + this.panelTools.getWidth(), e.getY());
            }
            this.interfacePlan.repaint();
        }
        if (this.panelTools.isMoveTools() && this.panelTools.getIdTools() != InterfaceOutils.ID_TOOL_ARC) {
            mip.drawImageFollowingCursor(this.panelTools.getIdTools(), e.getX(), e.getY());
            this.panelTools.repaint();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        DataElement dataElement = this.plan.findDataElement(e.getX(), e.getY(), this.interfacePlan.getZoom());
        interfacePlan.showSelectedElement(dataElement);

        if (dataElement.element != null) {
            this.panelParams.setParametersInformations(dataElement.element);
        } else {
            this.panelParams.hideEditionStationInformations();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.panelTools.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (inMap(e)) {
            float zoom = this.interfacePlan.getZoom();

            if (this.panelTools.isMoveTools()) {
                mip.changeCursor(-1);
                // NEW
                if (this.panelTools.getIdTools() != InterfaceOutils.ID_TOOL_ARC) {
                    int x = (int) (e.getX() / zoom - this.panelTools.getWidth() / zoom - this.panelTools.getSizeImage() / 2);
                    int y = (int) (e.getY() / zoom - (this.panelTools.getSizeImage()) / 2);

                    if (this.panelTools.getIdTools() >= 0
                            && !this.mip.isOverlapElement((int) (e.getX() / zoom - this.panelTools.getWidth()),
                                    (int) (e.getY() / zoom), this.panelTools.getSizeImage(), this.panelTools.getSizeImage())) {
                        this.plan.createElement(this.panelTools.getIdTools(), x, y);

                        DataElement addedDataElement = this.plan.getListDataElements().get(this.plan.getListDataElements().size() - 1);
                        interfacePlan.showSelectedElement(addedDataElement);
                        this.panelParams.setParametersInformations(addedDataElement.element);
                    }
                    this.panelTools.setMoveTools(false);
                } else {
                    int x = (int) (e.getX() / zoom - this.panelTools.getWidth() / zoom);
                    int y = (int) (e.getY() / zoom);
                    if (this.panelTools.getIdTools() >= 0
                            && this.plan.findDataElement(x, y, 1).element != null) { // 1 de findDataElement, n'est ce pas bizzare ?
                        if (this.plan.isDrawingArc()) {
                            if (this.plan.createArcEntrance(x, y)) {
                                this.panelTools.setMoveTools(false);
                                this.panelTools.resetTools();
                            }
                        } else {
                            this.plan.createArcExit(x, y);
                        }
                    }
                }
                this.panelTools.repaint();
            } else if (e.getSource().equals(interfacePlan)) {
                int x = (int) (e.getX() / zoom - (dataElementTemp.width) / 2);
                int y = (int) (e.getY() / zoom - (dataElementTemp.height) / 2);

                if (!this.mip.isOverlapElement((int) (e.getX() / zoom), (int) (e.getY() / zoom), dataElementTemp.width, dataElementTemp.height)) {
                    this.plan.moveElement(dataElementTemp, x, y);
                }
                dataElementTemp = this.plan.new DataElement();
            }

            this.interfacePlan.repaint();
        }
    }

    /**
     * **
     * summary : Permet de savoir si on est bien dans le plan pour travailler.
     */
    private boolean inMap(MouseEvent e) {
        int marginLeft = 0;
        if (this.itemTools.getState()) {
            marginLeft = this.panelTools.getWidth();
        }

        return e.getX() + marginLeft >= this.jScrollPane1.getX()
                && e.getX() <= this.jScrollPane1.getX() + this.jScrollPane1.getWidth()
                && e.getY() >= this.jScrollPane1.getY()
                && e.getY() <= this.jScrollPane1.getY() + this.jScrollPane1.getHeight();
    }

    public JLabel getDebug() {
        return debug;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    private void restartPlan() {
        plan = new Plan();
        this.plan.createElement(1, 20, this.getHeight() / 2);
        interfacePlan.setStationIsSelected(false);
        this.panelParams.hideEditionStationInformations();
        interfacePlan.repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelInfo = new javax.swing.JPanel();
        logCoordinates = new javax.swing.JLabel();
        logZoom = new javax.swing.JLabel();
        debug = new javax.swing.JLabel();
        panelParams = new recyclapp.view.InterfaceParam();
        panelTools = new recyclapp.view.InterfaceOutils();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItemNew = new javax.swing.JMenuItem();
        jMenuItemOpen = new javax.swing.JMenuItem();
        jMenuItemSave = new javax.swing.JMenuItem();
        jMenuItemExport = new javax.swing.JMenuItem();
        jMenuItemClose = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        itemTools = new javax.swing.JCheckBoxMenuItem();
        itemParam = new javax.swing.JCheckBoxMenuItem();
        itemGrid = new javax.swing.JCheckBoxMenuItem();
        jMenuItemZoomIn = new javax.swing.JMenuItem();
        jMenuItemZoomOut = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("RecyclApp - Daltons");
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        panelInfo.setBackground(java.awt.Color.darkGray);
        panelInfo.setMaximumSize(new java.awt.Dimension(100, 20));
        panelInfo.setMinimumSize(new java.awt.Dimension(100, 20));
        panelInfo.setPreferredSize(new java.awt.Dimension(952, 20));
        panelInfo.setLayout(new java.awt.BorderLayout());

        logCoordinates.setFont(new java.awt.Font("Waree", 1, 12)); // NOI18N
        logCoordinates.setForeground(java.awt.Color.white);
        panelInfo.add(logCoordinates, java.awt.BorderLayout.WEST);

        logZoom.setForeground(new java.awt.Color(255, 255, 255));
        logZoom.setText("jLabel1");
        logZoom.setMaximumSize(new java.awt.Dimension(0, 0));
        logZoom.setMinimumSize(new java.awt.Dimension(0, 0));
        logZoom.setPreferredSize(new java.awt.Dimension(0, 0));
        panelInfo.add(logZoom, java.awt.BorderLayout.CENTER);

        debug.setForeground(new java.awt.Color(254, 254, 254));
        panelInfo.add(debug, java.awt.BorderLayout.EAST);

        getContentPane().add(panelInfo, java.awt.BorderLayout.PAGE_END);
        getContentPane().add(panelParams, java.awt.BorderLayout.LINE_END);
        getContentPane().add(panelTools, java.awt.BorderLayout.LINE_START);

        jMenuBar1.setBackground(new java.awt.Color(164, 183, 145));

        jMenu1.setText("File");

        jMenuItemNew.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        jMenuItemNew.setText("Nouveau");
        jMenuItemNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemNewActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemNew);

        jMenuItemOpen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemOpen.setText("Ouvrir");
        jMenuItemOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemOpenActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemOpen);

        jMenuItemSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemSave.setText("Sauvegarder");
        jMenuItemSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSaveActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemSave);

        jMenuItemExport.setText("Exporter en image");
        jMenuItemExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemExportActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemExport);

        jMenuItemClose.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        jMenuItemClose.setText("Fermer");
        jMenuItemClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemCloseActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemClose);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        jMenu3.setText("vue");

        itemTools.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        itemTools.setSelected(true);
        itemTools.setText("Afficher le panneau d'outils");
        itemTools.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemToolsActionPerformed(evt);
            }
        });
        jMenu3.add(itemTools);

        itemParam.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        itemParam.setSelected(true);
        itemParam.setText("Afficher le panneaux de paramètre");
        itemParam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemParamActionPerformed(evt);
            }
        });
        jMenu3.add(itemParam);

        itemGrid.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_MASK));
        itemGrid.setText("Afficher la grille");
        itemGrid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemGridActionPerformed(evt);
            }
        });
        jMenu3.add(itemGrid);

        jMenuItemZoomIn.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ADD, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemZoomIn.setText("Zoom +");
        jMenuItemZoomIn.setName("jMenuItemZoomIn"); // NOI18N
        jMenuItemZoomIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemZoomInActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItemZoomIn);

        jMenuItemZoomOut.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_SUBTRACT, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemZoomOut.setText("Zoom -");
        jMenuItemZoomOut.setName("jMenuItemZoomOut"); // NOI18N
        jMenuItemZoomOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemZoomOutActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItemZoomOut);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void itemGridActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemGridActionPerformed
        this.mip.GridView();
    }//GEN-LAST:event_itemGridActionPerformed

    private void itemParamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemParamActionPerformed
        this.mip.setVisiblePaneParam(this.itemParam.getState());
    }//GEN-LAST:event_itemParamActionPerformed

    private void itemToolsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemToolsActionPerformed
        this.mip.setVisiblePaneTools(this.itemTools.getState());
    }//GEN-LAST:event_itemToolsActionPerformed

    private void jMenuItemZoomInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemZoomInActionPerformed
        interfacePlan.zoomIn();
    }//GEN-LAST:event_jMenuItemZoomInActionPerformed

    private void jMenuItemZoomOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemZoomOutActionPerformed
        interfacePlan.zoomOut();
    }//GEN-LAST:event_jMenuItemZoomOutActionPerformed

    private void jMenuItemExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemExportActionPerformed
        mip.exportImageAsPng();
    }//GEN-LAST:event_jMenuItemExportActionPerformed

    private void jMenuItemNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemNewActionPerformed
        restartPlan();
    }//GEN-LAST:event_jMenuItemNewActionPerformed

    private void jMenuItemOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemOpenActionPerformed


    }//GEN-LAST:event_jMenuItemOpenActionPerformed

    private void jMenuItemSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSaveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItemSaveActionPerformed

    private void jMenuItemCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemCloseActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItemCloseActionPerformed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        if (evt.getKeyCode() == 127) { // Code pour SUPPR
            if (interfacePlan.getStationIsSelected()) {
                plan.removeFromElements(interfacePlan.getSelectedDataElement());
                interfacePlan.setStationIsSelected(false);
                this.panelParams.hideEditionStationInformations();
            }
        } else if (evt.getKeyCode() == 27) { // Code pour ECHAP
            interfacePlan.setStationIsSelected(false);
            this.panelParams.hideEditionStationInformations();
        }
        interfacePlan.repaint();
    }//GEN-LAST:event_formKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel debug;
    private javax.swing.JCheckBoxMenuItem itemGrid;
    private javax.swing.JCheckBoxMenuItem itemParam;
    private javax.swing.JCheckBoxMenuItem itemTools;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItemClose;
    private javax.swing.JMenuItem jMenuItemExport;
    private javax.swing.JMenuItem jMenuItemNew;
    private javax.swing.JMenuItem jMenuItemOpen;
    private javax.swing.JMenuItem jMenuItemSave;
    private javax.swing.JMenuItem jMenuItemZoomIn;
    private javax.swing.JMenuItem jMenuItemZoomOut;
    private javax.swing.JLabel logCoordinates;
    private javax.swing.JLabel logZoom;
    private javax.swing.JPanel panelInfo;
    private recyclapp.view.InterfaceParam panelParams;
    private recyclapp.view.InterfaceOutils panelTools;
    // End of variables declaration//GEN-END:variables
}
