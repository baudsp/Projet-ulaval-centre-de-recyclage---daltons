package recyclapp;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JButton;

public class RecyclAppFrame extends javax.swing.JFrame implements ActionListener, MouseMotionListener, MouseListener {

    private UI ui;
    private MapPane jPanel2;
    private ToolsPane jPanel1;
    private JButton jButton1, reset;

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
        jPanel1 = new ToolsPane();
        jButton1 = new javax.swing.JButton();
        reset = new javax.swing.JButton();
        jPanel2 = new MapPane();
        jButton1.addActionListener(this);
        reset.addActionListener(this);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("View grid");
        reset.setText("Reset");
        jPanel1.add(jButton1, java.awt.BorderLayout.PAGE_START);
        jPanel1.add(reset, java.awt.BorderLayout.PAGE_END);

        jPanel2.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 597, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>     

    public ToolsPane getjPanel1() {
        return jPanel1;
    }

    public MapPane getjPanel2() {
        return jPanel2;
    }

    public JButton getjButton1() {
        return jButton1;
    }

    // Variables declaration - do not modify                     
    // End of variables declaration                   
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(reset)) {
            this.jPanel2.deleteElements();
        } else {
            ui.GridView();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        jPanel1.moveTool(e.getX(), e.getY() - this.getjButton1().getHeight());
        if (jPanel1.isMoveTools()) {
            ui.changeCursor(-2);
        } else {
            ui.changeCursor(-1);
        }

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (jPanel1.isMoveTools()) {
            System.out.print("Drag Outils \n");
            // Show cursor with the selected tool's image
            ui.drawImageFromFollowingCursor(jPanel1.getIdTools());
            //ui.changeCursor(jPanel1.getIdTools());
	    
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
        this.jPanel1.setCoord();
        this.jPanel1.repaint();
        if (this.jPanel1.getIdTools() >= 0) {
            int x = e.getX() - this.jPanel1.getWidth() - 25 - 8;
            int y = e.getY() - this.jButton1.getHeight() - 25 - 8;
            Image img = jPanel1.getImages(jPanel1.getIdTools());
            jPanel2.addElement(jPanel1.getIdTools(), x, y, jPanel1.getCoordW(jPanel1.getIdTools()), jPanel1.getCoordH(jPanel1.getIdTools()), jPanel1.getImages(jPanel1.getIdTools()));
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
