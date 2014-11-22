/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RecyclApp.view;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Baudouin
 */
public class InterfaceParam extends JPanel {

    public InterfaceParam() {
	JLabel jLabel2 = new javax.swing.JLabel();
	jLabel2.setFont(new java.awt.Font("Waree", 1, 18)); // NOI18N
	jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
	jLabel2.setText("Paramètres");
	this.setBackground(new java.awt.Color(164, 183, 145));
	this.setMaximumSize(new java.awt.Dimension(300, 32767));
	this.setPreferredSize(new java.awt.Dimension(150, 588));
	this.setLayout(new java.awt.BorderLayout());
	this.add(jLabel2, java.awt.BorderLayout.PAGE_START);
    }

}
