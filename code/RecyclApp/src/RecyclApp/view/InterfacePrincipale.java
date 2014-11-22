package RecyclApp.view;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
import java.util.LinkedList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import RecyclApp.model.Plan;
import RecyclApp.model.Plan.DataElement;

public class InterfacePrincipale extends javax.swing.JFrame implements ActionListener, MouseMotionListener, MouseListener {

    private InterfaceOutils panelTools;
    private InterfacePlan  panelMap;
    private ModeleInterfacePrincipal mip;
    private JLabel jLabel1;
    private Plan plan;

    public InterfacePrincipale(Plan plan) {
        initComponents();
        initialize();
    }

    private void initialize() {
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        panelMap = new InterfacePlan (this);
        panelMap.setBackground(java.awt.Color.white);
        javax.swing.GroupLayout panelMapLayout = new javax.swing.GroupLayout(panelMap);
        panelMap.setLayout(panelMapLayout);
        getContentPane().add(panelMap, java.awt.BorderLayout.CENTER);
        panelMap.addMouseMotionListener(this);
        panelMap.addMouseListener(this);
        jLabel1 = new javax.swing.JLabel();
        jLabel1.setFont(new java.awt.Font("Waree", 1, 18));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Outils");
        panelTools = new InterfaceOutils();
        panelTools.add(jLabel1, java.awt.BorderLayout.PAGE_START);
        panelTools = new InterfaceOutils();
        panelTools.setBackground(new Color(164, 183, 145));
        panelTools.setPreferredSize(new java.awt.Dimension(150, 588));
        panelTools.setLayout(new java.awt.BorderLayout());
        panelTools.add(jLabel1, java.awt.BorderLayout.PAGE_START);
        getContentPane().add(panelTools, java.awt.BorderLayout.WEST);
        panelTools.addMouseMotionListener(this);
        panelTools.addMouseListener(this);
        mip = new ModeleInterfacePrincipal(this);
        plan = new Plan();
    }

    public JLabel getLog() {
        return log;
    }

    public void setLog(JLabel log) {
        this.log = log;
    }

    public JPanel getPanelInfo() {
        return panelInfo;
    }

    public void setPanelInfo(JPanel panelInfo) {
        this.panelInfo = panelInfo;
    }

    public InterfacePlan  getPanelMap() {
        return panelMap;
    }

    public void setPanelMap(InterfacePlan  panelMap) {
        this.panelMap = panelMap;
    }

    public JPanel getPanelParam() {
        return panelParam;
    }

    public void setPanelParam(JPanel panelParam) {
        this.panelParam = panelParam;
    }

    public InterfaceOutils getPanelTools() {
        return panelTools;
    }

    public void setPanelTools(InterfaceOutils panelTools) {
        this.panelTools = panelTools;
    }
    
    public LinkedList<DataElement> getPositionElements()
    {
        return this.plan.getPositionElement();
    }
    
    public Image getImageType(int i)
    {
        return this.panelTools.getImages(i);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (e.getSource().equals(panelMap)) {
            
            log.setText("[" + e.getX() + ";" + e.getY() + "]");
        }
        if (e.getSource().equals(panelTools)) {
            panelTools.moveTool(e.getX(), e.getY());
        }
        else
        {
            panelTools.setMoveTools(false);
        }
        
        if (panelTools.isMoveTools()) {
            mip.changeCursor(-2);
        } else {
            mip.changeCursor(-1);
        }

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getSource().equals(panelMap)) {
            
            log.setText("[" + e.getX() + ";" + e.getY() + "]");
        }
        if (this.panelTools.isMoveTools()) {
            mip.drawImageFromFollowingCursor(this.panelTools.getIdTools(),e.getX(),e.getY()); 
            this.panelTools.repaint();   
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.panelTools.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mip.changeCursor(-1);
        if(this.panelTools.isMoveTools())
            if (this.panelTools.getIdTools() >= 0) {
                int x = e.getX() - this.panelTools.getWidth() - 35;
                int y = e.getY()  - 35;
                this.plan.newElement(this.panelTools.getIdTools(),x, y);
                this.panelMap.repaint();
            }
        this.panelTools.setMoveTools(false);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelParam = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        panelInfo = new javax.swing.JPanel();
        log = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        itemTools = new javax.swing.JCheckBoxMenuItem();
        itemParam = new javax.swing.JCheckBoxMenuItem();
        itemGrid = new javax.swing.JCheckBoxMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelParam.setBackground(new java.awt.Color(164, 183, 145));
        panelParam.setMaximumSize(new java.awt.Dimension(300, 32767));
        panelParam.setPreferredSize(new java.awt.Dimension(250, 588));
        panelParam.setLayout(new java.awt.BorderLayout());

        jLabel2.setFont(new java.awt.Font("Waree", 1, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Paramètres");
        panelParam.add(jLabel2, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(panelParam, java.awt.BorderLayout.LINE_END);

        panelInfo.setBackground(java.awt.Color.darkGray);
        panelInfo.setMaximumSize(new java.awt.Dimension(100, 20));
        panelInfo.setMinimumSize(new java.awt.Dimension(100, 20));
        panelInfo.setPreferredSize(new java.awt.Dimension(952, 20));
        panelInfo.setLayout(new java.awt.BorderLayout());

        log.setFont(new java.awt.Font("Waree", 1, 12)); // NOI18N
        log.setForeground(java.awt.Color.white);
        panelInfo.add(log, java.awt.BorderLayout.LINE_START);

        getContentPane().add(panelInfo, java.awt.BorderLayout.PAGE_END);

        jMenuBar1.setBackground(new java.awt.Color(164, 183, 145));

        jMenu1.setText("File");
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBoxMenuItem itemGrid;
    private javax.swing.JCheckBoxMenuItem itemParam;
    private javax.swing.JCheckBoxMenuItem itemTools;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JLabel log;
    private javax.swing.JPanel panelInfo;
    private javax.swing.JPanel panelParam;
    // End of variables declaration//GEN-END:variables

}
