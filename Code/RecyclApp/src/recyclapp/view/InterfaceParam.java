/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recyclapp.view;

import java.awt.FlowLayout;
import java.util.Iterator;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import recyclapp.model.Element;
import recyclapp.model.EntreeUsine;
import recyclapp.model.Station;

/**
 *
 * @author Baudouin
 */
public class InterfaceParam extends javax.swing.JPanel {

    Element element = null;

     /**
     * Creates new form InterfaceParamBis
     */
    public InterfaceParam() {
        initComponents();
        this.jPanelEditionStation.setVisible(false);
    }
       
    public void hideEditionStationInformations() {
        this.jPanelEditionStation.setVisible(false);
	element = null;
    }
  
    
    /**
     * Affichage des paramètres commun à tout les types d'element
     * @param element 
     */
    public void setParametersInformations(Element element) {
	this.element = element;
	
	jPanelEditionStation.setVisible(true);
	
	this.jTextFieldName.setText(element.getName());
	this.jTextFieldDescription.setText(element.getDescription());
	
	this.jSpinnerDebitMax.setValue(element.getMaxFlow());
	
	this.jValeursSorties.removeAll();
	
	
	// On ecrit le contenu de la matrice de transformation
	Map<String, Map<Integer, Map<String, Float>>> matrix = element.getMatrix();
	
	Iterator<String> iteratorProduits = matrix.keySet().iterator();
	
	while (iteratorProduits.hasNext()) {
	    
	    String produit = iteratorProduits.next();
	    jValeursSorties.add(new JLabel(produit));
	    Map<Integer, Map<String, Float>> matriceProduit = matrix.get(produit);	    
	    Iterator<Integer>iteratorSorties= matriceProduit.keySet().iterator();
	    while (iteratorSorties.hasNext()) {
		int numsortie = iteratorSorties.next();
		
		jValeursSorties.add(new JLabel("Sortie " + numsortie +" :"));
		
		float pourcentage = (float) matriceProduit.get(numsortie).values().toArray()[0];
		// A ce point-là, on ne gère pas les transformations, donc un seul paire clé=>valeur par produit
		
		jValeursSorties.add(new JLabel(pourcentage + " %"));
	    }
	}
	
	jValeursSorties.repaint();
	repaint();
    }
    
  
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabelParameters = new javax.swing.JLabel();
        jPanelEditionStation = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldName = new javax.swing.JTextField();
        jTextFieldDescription = new javax.swing.JTextField();
        jSpinnerDebitMax = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jValeursSorties = new javax.swing.JPanel();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        setBackground(new java.awt.Color(164, 183, 145));
        setMaximumSize(new java.awt.Dimension(300, 32767));

        jLabelParameters.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelParameters.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelParameters.setText("Paramètres");
        jLabelParameters.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabelParameters.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jPanelEditionStation.setBackground(new java.awt.Color(164, 183, 145));

        jButton1.setText("Valider");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setText("Nom");

        jLabel3.setText("Description");

        jLabel4.setText("Débit max (kg/h)");

        jTextFieldName.setText(" ");

        jSpinnerDebitMax.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), Float.valueOf(0.0f), null, Float.valueOf(1.0f)));

        jLabel6.setText("Essai");

        jLabel7.setText("Sorties de la station");

        jValeursSorties.setBackground(new java.awt.Color(164, 183, 145));
        jValeursSorties.setLayout(new javax.swing.BoxLayout(jValeursSorties, javax.swing.BoxLayout.LINE_AXIS));

        javax.swing.GroupLayout jPanelEditionStationLayout = new javax.swing.GroupLayout(jPanelEditionStation);
        jPanelEditionStation.setLayout(jPanelEditionStationLayout);
        jPanelEditionStationLayout.setHorizontalGroup(
            jPanelEditionStationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEditionStationLayout.createSequentialGroup()
                .addGroup(jPanelEditionStationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelEditionStationLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanelEditionStationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelEditionStationLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                                .addComponent(jSpinnerDebitMax, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelEditionStationLayout.createSequentialGroup()
                                .addGroup(jPanelEditionStationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanelEditionStationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextFieldName, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                                    .addComponent(jTextFieldDescription)))))
                    .addGroup(jPanelEditionStationLayout.createSequentialGroup()
                        .addGroup(jPanelEditionStationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelEditionStationLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jButton1))
                            .addGroup(jPanelEditionStationLayout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(jLabel6))
                            .addGroup(jPanelEditionStationLayout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addComponent(jLabel7))
                            .addGroup(jPanelEditionStationLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jValeursSorties, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelEditionStationLayout.setVerticalGroup(
            jPanelEditionStationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelEditionStationLayout.createSequentialGroup()
                .addComponent(jLabel6)
                .addGap(10, 10, 10)
                .addGroup(jPanelEditionStationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelEditionStationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelEditionStationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jSpinnerDebitMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jValeursSorties, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 168, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabelParameters)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanelEditionStation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelParameters)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelEditionStation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(19, 19, 19))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabelParameters;
    private javax.swing.JPanel jPanelEditionStation;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jSpinnerDebitMax;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextFieldDescription;
    private javax.swing.JTextField jTextFieldName;
    private javax.swing.JPanel jValeursSorties;
    // End of variables declaration//GEN-END:variables

}
