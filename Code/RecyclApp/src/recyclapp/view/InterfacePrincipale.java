package recyclapp.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class InterfacePrincipale extends javax.swing.JFrame implements ActionListener, MouseMotionListener, MouseListener {

    private InterfaceOutils subPaneTools;

    public InterfacePrincipale() {
        initComponents();
        initialize();
    }

    private void initialize() {
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        panelMap.addMouseMotionListener(this);
        subPaneTools = new InterfaceOutils();
        subPaneTools.setBackground(javax.swing.UIManager.getDefaults().getColor("CheckBoxMenuItem.selectionBackground"));
        javax.swing.GroupLayout subPaneToolsLayout = new javax.swing.GroupLayout(subPaneTools);
        subPaneTools.setLayout(subPaneToolsLayout);
        panelTools.add(subPaneTools, java.awt.BorderLayout.CENTER);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /*if (e.getSource().equals(reset)) {
            this.jPanel2.deleteElements();
        } else {
            ui.GridView();
        }*/
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (e.getSource().equals(panelMap)) {
            System.out.print(e.getSource().toString());
            log.setText("[" + e.getX() + ";" + e.getY() + "]");
        }
        subPaneTools.moveTool(e.getX(), e.getY() -100);
        if (subPaneTools.isMoveTools()) {
            //ui.changeCursor(-2);
        } else {
            //ui.changeCursor(-1);
        }

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        /*if (jPanel1.isMoveTools()) {
            System.out.print("Drag Outils \n");
            ui.changeCursor(jPanel1.getIdTools());
            this.jPanel1.repaint();
        }
        System.out.print("------ \n");*/
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //jPanel1.moveTool(e.getX(),e.getY()-this.getjButton1().getHeight());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        /* ui.changeCursor(-1);
        this.jPanel1.setCoord();
        this.jPanel1.repaint();
        if (this.jPanel1.getIdTools() >= 0) {
            int x = e.getX() - this.jPanel1.getWidth() - 25 - 8;
            int y = e.getY() - this.jButton1.getHeight() - 25 - 8;
            Image img = jPanel1.getImages(jPanel1.getIdTools());
            jPanel2.addElement(jPanel1.getIdTools(), x, y, jPanel1.getCoordW(jPanel1.getIdTools()), jPanel1.getCoordH(jPanel1.getIdTools()), jPanel1.getImages(jPanel1.getIdTools()));
        }*/
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

        panelTools = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        panelParam = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        panelInfo = new javax.swing.JPanel();
        log = new javax.swing.JLabel();
        panelMap = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelTools.setBackground(javax.swing.UIManager.getDefaults().getColor("List.selectionBackground"));
        panelTools.setPreferredSize(new java.awt.Dimension(150, 588));
        panelTools.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Waree", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Outils");
        panelTools.add(jLabel1, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(panelTools, java.awt.BorderLayout.LINE_START);

        panelParam.setBackground(javax.swing.UIManager.getDefaults().getColor("CheckBoxMenuItem.selectionBackground"));
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

        panelMap.setBackground(java.awt.Color.white);

        javax.swing.GroupLayout panelMapLayout = new javax.swing.GroupLayout(panelMap);
        panelMap.setLayout(panelMapLayout);
        panelMapLayout.setHorizontalGroup(
            panelMapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 719, Short.MAX_VALUE)
        );
        panelMapLayout.setVerticalGroup(
            panelMapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 588, Short.MAX_VALUE)
        );

        getContentPane().add(panelMap, java.awt.BorderLayout.CENTER);

        jMenuBar1.setBackground(new java.awt.Color(164, 183, 145));

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        jMenu3.setText("vue");

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("Afficher le panneaux de paramètre");
        jMenu3.add(jCheckBoxMenuItem1);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JLabel log;
    private javax.swing.JPanel panelInfo;
    private javax.swing.JPanel panelMap;
    private javax.swing.JPanel panelParam;
    private javax.swing.JPanel panelTools;
    // End of variables declaration//GEN-END:variables
}
