package recyclapp;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JButton;
import recyclapp.model.Arc;

public class RecyclAppFrame extends javax.swing.JFrame implements ActionListener, MouseMotionListener, MouseListener {

    private UI ui;
    private MapPane mapPane;
    private ToolsPane toolPane;
    private JButton jButton1, reset;
    private boolean isAttachArc = false;
    private Arc curArc;

    /**
     * Creates new form Frame
     */
    public RecyclAppFrame() {
	init();
	ui = new UI(this);
	ui.changeBackgroundPanel();
    }

    private void init() {

	this.addMouseMotionListener(this);
	this.addMouseListener(this);
	toolPane = new ToolsPane();
	jButton1 = new javax.swing.JButton();
	reset = new javax.swing.JButton();
	mapPane = new MapPane();
	jButton1.addActionListener(this);
	reset.addActionListener(this);

	setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

	jButton1.setText("View grid");
	reset.setText("Reset");
	toolPane.add(jButton1, java.awt.BorderLayout.PAGE_START);
	toolPane.add(reset, java.awt.BorderLayout.PAGE_END);

	mapPane.setLayout(new java.awt.BorderLayout());

	javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
	getContentPane().setLayout(layout);
	layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		.addGroup(layout.createSequentialGroup()
			.addComponent(toolPane, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
			.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
			.addComponent(mapPane, javax.swing.GroupLayout.DEFAULT_SIZE, 597, Short.MAX_VALUE))
	);
	layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		.addComponent(toolPane, javax.swing.GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE)
		.addComponent(mapPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	);

	pack();
    }// </editor-fold>     

    private boolean isValidEntrance(int x, int y) {
	//TODO vraiment vérifier qu'une entrée est dispo à cet endroit
	return mapPane.checkElement(x, y) != null;
    }

    private boolean isValidExit(int x, int y) {
	//TODO vraiment vérifier qu'une entrée est dispo à cet endroit
	return mapPane.checkElement(x, y) != null;
    }

    public ToolsPane getjPanel1() {
	return toolPane;
    }

    public MapPane getjPanel2() {
	return mapPane;
    }

    public JButton getjButton1() {
	return jButton1;
    }

    // Variables declaration - do not modify                     
    // End of variables declaration                   
    @Override
    public void actionPerformed(ActionEvent e) {
	if (e.getSource().equals(reset)) {
	    this.mapPane.deleteElements();
	} else {
	    ui.GridView();
	}
    }

    @Override
    public void mouseMoved(MouseEvent e) {
	toolPane.moveTool(e.getX(), e.getY() - this.getjButton1().getHeight());
	if (toolPane.isMoveTools()) {
	    ui.changeCursor(-2);
	} else {
	    ui.changeCursor(-1);
	}

    }

    @Override
    public void mouseDragged(MouseEvent e) {
	if (toolPane.isMoveTools()) {
	    System.out.print("Drag Outils \n");
	    ui.changeCursor(toolPane.getIdTools());
	    this.toolPane.repaint();
	}
	System.out.print("------ \n");
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
	ui.changeCursor(-1);
	this.toolPane.setCoord();
	this.toolPane.repaint();
	
	int x = e.getX() - this.toolPane.getWidth() - 25 - 8;
	int y = e.getY() - this.jButton1.getHeight() - 25 - 8;
	    
	    
	if (this.toolPane.getIdTools() == 2) {

	    if (!isAttachArc) {
		// l'arc n'est pas encore attaché, on vérifie qu'on est sur 
		// un element avec une sortie de dispo
		if (isValidEntrance(x, y)) {
		    isAttachArc = true;
		    
		    curArc = new Arc(UI.transformCoordinateFromUI(x, y));
		}
	    } else {
		isAttachArc = false;
		// L'arc est déjà attaché à une sortie, on vérifie qu'on est sur une entrée de disponible
		if (this.isValidExit(x, y)) {
		    curArc.setEntrance(UI.transformCoordinateFromUI(x, y));
		    mapPane.addArc(curArc);
		}
		curArc = null;
	    }
	} else if (this.toolPane.getIdTools() >= 0) {
	    
	    Image img = toolPane.getImages(toolPane.getIdTools());
	    mapPane.addElement(toolPane.getIdTools(), x, y, toolPane.getCoordW(toolPane.getIdTools()), toolPane.getCoordH(toolPane.getIdTools()), toolPane.getImages(toolPane.getIdTools()));
	}
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
