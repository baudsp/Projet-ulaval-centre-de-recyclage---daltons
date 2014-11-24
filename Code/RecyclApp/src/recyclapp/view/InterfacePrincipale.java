package recyclapp.view;

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
import recyclapp.model.Plan;
import recyclapp.model.Plan.DataElement;
import recyclapp.model.Arc;

public class InterfacePrincipale extends javax.swing.JFrame implements ActionListener, MouseMotionListener, MouseListener {

    private InterfacePlan panelMap;
    private ModeleInterfacePrincipal mip;
    private JLabel jLabel1;
    private Plan plan;
    // NEW
    private DataElement elementTemp;

    public InterfacePrincipale(Plan plan) {
	initComponents();
	initialize();
    }

    private void initialize() {
	this.addMouseMotionListener(this);
	this.addMouseListener(this);

	panelMap = new InterfacePlan(this);
	panelMap.setBackground(java.awt.Color.white);
	javax.swing.GroupLayout panelMapLayout = new javax.swing.GroupLayout(panelMap);
	panelMap.setLayout(panelMapLayout);
	getContentPane().add(panelMap, java.awt.BorderLayout.CENTER);
	panelMap.addMouseMotionListener(this);
	panelMap.addMouseListener(this);

	panelTools.addMouseMotionListener(this);
	panelTools.addMouseListener(this);

	mip = new ModeleInterfacePrincipal(this);
	plan = new Plan();
	// NEW
	this.elementTemp = plan.new DataElement();
	this.plan.createElement(1, 20, this.getHeight() / 2);
	
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

    public InterfacePlan getPanelMap() {
	return panelMap;
    }

    public void setPanelMap(InterfacePlan panelMap) {
	this.panelMap = panelMap;
    }

    public JPanel getPanelParam() {
	return panelParams;
    }

    public void setPanelParam(InterfaceParam panelParam) {
	this.panelParams = panelParam;
    }

    public InterfaceOutils getPanelTools() {
	return panelTools;
    }

    public void setPanelTools(InterfaceOutils panelTools) {
	this.panelTools = panelTools;
    }

    public LinkedList<DataElement> getPositionElements() {
	// NEW
	LinkedList<DataElement> de = this.plan.getPositionElement();
	if (this.elementTemp != null && this.elementTemp.id >= 0) {
	    DataElement t = null;
	    for (DataElement e : de) {
		if (e.x == this.elementTemp.x && e.y == this.elementTemp.y) {
		    t = e;
		}
	    }
	    de.remove(t);
	}

	return de;

    }

    public Image getImageType(int i) {
	return this.panelTools.getImages(i);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
	if (e.getSource().equals(panelMap)) {
	    log.setText("[" + e.getX() + ";" + e.getY() + "]");
	    // NEW
	    this.elementTemp = this.plan.findDataElement(e.getX(), e.getY());

	}

	if (e.getSource().equals(panelTools)) {
	    panelTools.moveTool(e.getX(), e.getY());
	} else {
	    panelTools.setMoveTools(false);
	}

	if (panelTools.isMoveTools() || this.elementTemp.id >= 0) {
	    mip.changeCursor(-2);
	} else {
	    mip.changeCursor(-1);
	}

    }

    @Override
    public void mouseDragged(MouseEvent e) {
	if (e.getSource().equals(panelMap)) {

	    log.setText("[" + e.getX() + ";" + e.getY() + "]");
	    if (this.elementTemp != null && this.elementTemp.id >= 0) {
		mip.drawImageFromFollowingCursor(this.elementTemp.id, e.getX() + this.panelTools.getWidth(), e.getY());
		this.panelMap.repaint();
	    }
	}
	if (this.panelTools.isMoveTools()) {
	    mip.drawImageFromFollowingCursor(this.panelTools.getIdTools(), e.getX(), e.getY());
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

	if (this.panelTools.isMoveTools()) {
	    mip.changeCursor(-1);
	    // NEW

	    if (e.getX() >= this.panelMap.getX() && e.getX() <= this.panelMap.getX() + this.panelMap.getWidth()
		    && e.getY() >= this.panelMap.getY() && e.getY() <= this.panelMap.getY() + this.panelMap.getHeight()) {
		int x = e.getX() - this.panelTools.getWidth() - this.panelTools.getSizeImage() / 2;
		int y = e.getY() - this.panelTools.getSizeImage() / 2;
		if (this.panelTools.getIdTools() >= 0 && !this.mip.isOverlapElement(e.getX() - this.panelTools.getWidth(), e.getY(), this.panelTools.getSizeImage(), this.panelTools.getSizeImage())) {
		    this.plan.createElement(this.panelTools.getIdTools(), x, y);
		}
	    }

	    this.panelTools.setMoveTools(false);
	    this.panelTools.repaint();
	} else if (e.getSource().equals(panelMap)) {

	    int marginLeft = 0;
	    if (this.itemTools.getState()) {
		marginLeft = this.panelTools.getWidth();
	    }
	    if (e.getX() + marginLeft >= this.panelMap.getX() && e.getX() <= this.panelMap.getWidth()
		    && e.getY() >= this.panelMap.getY() && e.getY() <= this.panelMap.getHeight()) {
		int x = e.getX() - elementTemp.width / 2;
		int y = e.getY() - elementTemp.height / 2;
		this.debug.setText(" X : " + (e.getX() + marginLeft) + " et Y : " + e.getY() + "\n");
		if (!this.mip.isOverlapElement(e.getX(), e.getY(), elementTemp.width, elementTemp.height)) {
		    this.plan.remplacePositionElements(elementTemp, x, y);
		}
	    }
	    elementTemp = this.plan.new DataElement();
	}
	this.panelMap.repaint();
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelInfo = new javax.swing.JPanel();
        log = new javax.swing.JLabel();
        debug = new javax.swing.JLabel();
        panelParams = new recyclapp.view.InterfaceParam();
        panelTools = new recyclapp.view.InterfaceOutils();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        itemTools = new javax.swing.JCheckBoxMenuItem();
        itemParam = new javax.swing.JCheckBoxMenuItem();
        itemGrid = new javax.swing.JCheckBoxMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelInfo.setBackground(java.awt.Color.darkGray);
        panelInfo.setMaximumSize(new java.awt.Dimension(100, 20));
        panelInfo.setMinimumSize(new java.awt.Dimension(100, 20));
        panelInfo.setPreferredSize(new java.awt.Dimension(952, 20));
        panelInfo.setLayout(new java.awt.BorderLayout());

        log.setFont(new java.awt.Font("Waree", 1, 12)); // NOI18N
        log.setForeground(java.awt.Color.white);
        panelInfo.add(log, java.awt.BorderLayout.LINE_START);

        debug.setForeground(new java.awt.Color(254, 254, 254));
        panelInfo.add(debug, java.awt.BorderLayout.CENTER);

        getContentPane().add(panelInfo, java.awt.BorderLayout.PAGE_END);
        getContentPane().add(panelParams, java.awt.BorderLayout.EAST);
        getContentPane().add(panelTools, java.awt.BorderLayout.LINE_START);

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
    private javax.swing.JLabel debug;
    private javax.swing.JCheckBoxMenuItem itemGrid;
    private javax.swing.JCheckBoxMenuItem itemParam;
    private javax.swing.JCheckBoxMenuItem itemTools;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JLabel log;
    private javax.swing.JPanel panelInfo;
    private recyclapp.view.InterfaceParam panelParams;
    private recyclapp.view.InterfaceOutils panelTools;
    // End of variables declaration//GEN-END:variables

}
