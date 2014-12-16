/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recyclapp.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import recyclapp.model.Element;
import recyclapp.model.ParamObserver;

/**
 *
 * @author Baudouin
 */
public class InterfaceParam extends javax.swing.JPanel {

    Element element = null;
    Color selectedColor;
    LinkedList<ParamObserver> paramObserverList = new LinkedList<>();

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
     *
     * @param element
     */
    public void setParametersInformations(Element element) {
        this.element = element;

        jPanelEditionStation.setVisible(true);

        this.jTextFieldName.setText(element.getName());
        this.jTextFieldDescription.setText(element.getDescription());

        this.jSpinnerDebitMax.setValue(element.getMaxFlow());

        this.jSpinnerEntrances.setValue(element.getNbEntrances());
        this.jSpinnerExits.setValue(element.getNbExits());
        this.jSpinnerExits.setEnabled(false);
        this.jSpinnerEntrances.setEnabled(false);
        
        this.jSpinnerExits.setValue(element.getNbExits());

        this.jPanelMatrix.removeAll();

        this.jPanelExitValues.removeAll();

        if (element.getType() == InterfaceOutils.ID_TOOL_STATION) {
            filljPanelMatrix();
        }
        
        if (element.getType() == InterfaceOutils.ID_TOOL_ENTREE) {
            filljPanelExitValuesForEntreeUsine();
        }
        else if (element.getType() != InterfaceOutils.ID_TOOL_SORTIE) {
	    filljPanelExitValues();
	} else {
	    filljPanelExitValuesForSortieUsine();
	}

	jPanelExitValues.repaint();
	jPanelMatrix.repaint();

	repaint();
    }
    
    private void filljPanelExitValuesForEntreeUsine() {
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        int y = 0;

        Map<String, Float> exitValues = element.getEntranceProducts();
        if (exitValues != null && !exitValues.isEmpty()) {
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = y;
            jPanelExitValues.add(new JLabel("Sortie : "), gridBagConstraints);
            y++;

            Iterator<String> productIterator = exitValues.keySet().iterator();
            while (productIterator.hasNext()) {
                String product = productIterator.next();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = y;
                jPanelExitValues.add(new JLabel(product + " => "), gridBagConstraints);
                gridBagConstraints.gridx = 1;
                JSpinner jspin = new JSpinner();
                jspin.setModel(new SpinnerNumberModel(exitValues.get(product), 0.0f, null, 1.0f));
                jspin.setSize(80, 20);
                jspin.setPreferredSize(new Dimension(80, 20));
                jspin.setName(product + "_jSpinner");
                jPanelExitValues.add(jspin, gridBagConstraints);
                gridBagConstraints.gridx = 2;
                JButton delButton = new JButton("X");
                delButton.setName(product + "_jButton");
                delButton.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        JButton button = (JButton) evt.getSource();
                        String product = button.getName().split("_")[0];
                        if (confirmDeleteProduct()) {
                            Map<String, Float> exitValues = element.getEntranceProducts();
                            exitValues.remove(product);
                            JOptionPane.showConfirmDialog(null,
                            "La suppression de produit s'est passé avec succès.",
                            "Enregistrement de la suppression de produit",
                            JOptionPane.CLOSED_OPTION,
                            JOptionPane.INFORMATION_MESSAGE);
                            element.setEntranceProducts(exitValues);
                            setParametersInformations(element);
                        }
                    }
                });
                delButton.setPreferredSize(new Dimension(40, 20));
                jPanelExitValues.add(delButton, gridBagConstraints);
                y++;
            }
        }
        
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = y;
        JButton addProductB = new JButton("Ajouter Produit");
        addProductB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                String product = getNewProduct();
                Map<String, Float> entranceProducts = element.getEntranceProducts();
                if ((!product.equals("")) && (!entranceProducts.containsKey(product))) {
                    entranceProducts.put(product, 0.0f);
                    JOptionPane.showConfirmDialog(null,
                        "L'ajout de produit s'est passé avec succès.",
                        "Enregistrement de l'ajout de produit",
                        JOptionPane.CLOSED_OPTION,
                        JOptionPane.INFORMATION_MESSAGE);
                    element.setEntranceProducts(entranceProducts);
                    setParametersInformations(element);
                } 
            }
        });
        jPanelExitValues.add(addProductB, gridBagConstraints);
    }
    
    private boolean confirmDeleteProduct() {
        int res = JOptionPane.showConfirmDialog(null, 
                "Souhaitez-vous vraiment supprimer ce produit ?", 
                "Confirmer suppression", JOptionPane.YES_NO_OPTION);
        return (res == JOptionPane.OK_OPTION);
    }
    
    private String getNewProduct() {
        String product = "";
        JTextField productTextField = new JTextField();
        int res = JOptionPane.showOptionDialog(null, productTextField, 
                "Ajouter un produit en entrée", JOptionPane.OK_CANCEL_OPTION, 
                JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (res != JOptionPane.CANCEL_OPTION) {
            product = productTextField.getText();
        }
        return product;
    }

    private void filljPanelExitValuesForSortieUsine() {
	
	GridBagConstraints gridBagConstraints = new GridBagConstraints();

	Map<String, Float> entranceValue = element.getEntranceProducts();

	int line = 0;
	Iterator<String> iteratorProduct = entranceValue.keySet().iterator();
	while (iteratorProduct.hasNext()) {
	    String product = iteratorProduct.next();
	    float value = entranceValue.get(product);
	    
	    gridBagConstraints.gridy = line;
	    jPanelExitValues.add(new JLabel(product + " : " + value), gridBagConstraints);
	    line ++;
	}
    }

    private void filljPanelMatrix() {
        // On ecrit le contenu de la matrice de transformation
        Map<String, Map<Integer, Map<String, Float>>> matrix = element.getMatrix();

        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        int i = 0;

	// A ameliorer
        // On verifie que la matrice trie bien le bon nombre de 
        // dechets (et pas les bons dechets)
        boolean dechetsGood = true;
        Map<String, Float> entranceProducts = element.getEntranceProducts();
        Iterator<String> iteratorProduits = entranceProducts.keySet().iterator();
        while (iteratorProduits.hasNext()) {
            if (!matrix.containsKey(iteratorProduits.next())) {
                dechetsGood = false;
            }
        }
        if (dechetsGood) {
            iteratorProduits = matrix.keySet().iterator();

            while (iteratorProduits.hasNext()) {

                String produit = iteratorProduits.next();

                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = i;
                jPanelMatrix.add(new JLabel(produit), gridBagConstraints);

                Map<Integer, Map<String, Float>> matriceProduit = matrix.get(produit);
                Iterator<Integer> iteratorSorties = matriceProduit.keySet().iterator();
                while (iteratorSorties.hasNext()) {
                    i++;

                    int numsortie = iteratorSorties.next();

                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = i;
                    jPanelMatrix.add(new JLabel("Sortie " + Integer.toString(numsortie + 1) + " :"), gridBagConstraints);

                    Map<String, Float> a = matriceProduit.get(numsortie);

                    float pourcentage = (float) a.values().toArray()[0];
                    // A ce point-là, on ne gère pas les transformations, donc un seul paire clé=>valeur par produit


		    gridBagConstraints.gridx = 1;
		    gridBagConstraints.gridy = i;

		    JTextField jtSortie = new JTextField(pourcentage + "");

		    // ajout d'un nom pour identifier chaque 
                    // textField :
                    //  = sortie (partant de 0) ::: produit = nom du dechet ::: i = identifiant unique
                    // ::: parce que Guillaume est parano
                    jtSortie.setName(numsortie + ":::" + produit + ":::" + i);
                    jtSortie.setPreferredSize(new Dimension(70, 20));
                    jPanelMatrix.add(jtSortie, gridBagConstraints);
                    jPanelMatrix.add(jtSortie, gridBagConstraints);

                    gridBagConstraints.gridx = 2;
                    gridBagConstraints.gridy = i;
                    jPanelMatrix.add(new JLabel(" %"), gridBagConstraints);
                }

                i++;

            }
        } else {

            Iterator<String> iteratorProducts = element.getEntranceProducts().keySet().iterator();

            int k = 0;

            while (iteratorProducts.hasNext()) {

                String produit = iteratorProducts.next();

                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = i;
                jPanelMatrix.add(new JLabel(produit + " : "), gridBagConstraints);

                for (int j = 0; j < element.getNbExits(); j++) {
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = i + 1;
                    int numSortie = (j + 1);
                    jPanelMatrix.add(new JLabel("Sortie " + numSortie), gridBagConstraints);
                    gridBagConstraints.gridx = 1;
                    JTextField jtSortie = new JTextField();
                    if (matrix.containsKey(produit)) {
                        jtSortie.setText(matrix.get(produit).get(numSortie-1).get(produit).toString());
                    }
		    // ajout d'un nom pour identifier chaque 
                    // textField :
                    // j = sortie (partant de 0) ::: produit = nom du dechet ::: k = identifiant unique
                    // ::: parce que Guillaume est parano
                    jtSortie.setName(j + ":::" + produit + ":::" + k);
                    jtSortie.setPreferredSize(new Dimension(70, 20));
                    jPanelMatrix.add(jtSortie, gridBagConstraints);

                    gridBagConstraints.gridx = 2;
                    jPanelMatrix.add(new JLabel(" %"), gridBagConstraints);

                    i++;

                    k++;
                }

                i++;
            }
        }
    }
    
    private boolean confirmDeleteEntranceMatrix() {
        int res = JOptionPane.showConfirmDialog(null, 
                "Souhaitez-vous vraiment supprimer cette entrée ?", 
                "Confirmer suppression", JOptionPane.YES_NO_OPTION);
        return (res == JOptionPane.OK_OPTION);
    }

    private void filljPanelExitValues() {

        GridBagConstraints gridBagConstaints = new GridBagConstraints();

        int nbExits = this.element.getNbExits();
        int y = 0;
        for (int exit = 0; exit < nbExits; exit++) {

            Map<String, Float> exitValues = this.element.exitProductsFromArc(exit);

            if (exitValues != null && !exitValues.isEmpty()) {
                int numExit = exit + 1;

                gridBagConstaints.gridx = 0;
                gridBagConstaints.gridy = y;
                jPanelExitValues.add(new JLabel("Sortie " + numExit + " : "), gridBagConstaints);
                y++;

                Iterator<String> productIterator = exitValues.keySet().iterator();

                while (productIterator.hasNext()) {
                    String product = productIterator.next();
                    gridBagConstaints.gridx = 0;
                    gridBagConstaints.gridy = y;
                    jPanelExitValues.add(new JLabel(product + " => "), gridBagConstaints);
                    gridBagConstaints.gridx = 1;
                    jPanelExitValues.add(new JLabel("" + exitValues.get(product)), gridBagConstaints);
                    y++;
                }
            }
        }
    }

    private void updateElement(String name, String description, float debitMax, Color color) {
        element.setName(name);
        element.setDescription(description);
        element.setMaxFlow(debitMax);
        element.setColor(color);

        for (ParamObserver paramObserver : paramObserverList) {
            paramObserver.update(element.clone());
        }
    }

    public void addObserver(ParamObserver paramObserver) {
        paramObserverList.add(paramObserver);
    }
    
    private void showProblemInMatrix(){
        JOptionPane.showConfirmDialog(null,
                        "La matrice est incorrecte.",
                        "L'élement " + element.getName() + " n'a pas été sauvegardé",
                        JOptionPane.CLOSED_OPTION,
                        JOptionPane.ERROR_MESSAGE);
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
        jButtonValidate = new javax.swing.JButton();
        jLabelName = new javax.swing.JLabel();
        jLabelDescription = new javax.swing.JLabel();
        jLabelDebitMax = new javax.swing.JLabel();
        jTextFieldName = new javax.swing.JTextField();
        jTextFieldDescription = new javax.swing.JTextField();
        jSpinnerDebitMax = new javax.swing.JSpinner();
        jLabelExits = new javax.swing.JLabel();
        jPanelMatrix = new javax.swing.JPanel();
        jButtonChoseColor = new javax.swing.JButton();
        jSpinnerExits = new javax.swing.JSpinner();
        jLabelEntrances = new javax.swing.JLabel();
        jSpinnerEntrances = new javax.swing.JSpinner();
        jPanelExitValues = new javax.swing.JPanel();

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

        jButtonValidate.setText("Valider");
        jButtonValidate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonValidateActionPerformed(evt);
            }
        });

        jLabelName.setText("Nom");

        jLabelDescription.setText("Description");

        jLabelDebitMax.setText("Débit max (kg/h)");

        jTextFieldName.setText(" ");

        jSpinnerDebitMax.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), Float.valueOf(0.0f), null, Float.valueOf(1.0f)));

        jLabelExits.setText("Sorties");

        jPanelMatrix.setBackground(new java.awt.Color(164, 183, 145));
        jPanelMatrix.setLayout(new java.awt.GridBagLayout());

        jButtonChoseColor.setText("Choisir couleur");
        jButtonChoseColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonChoseColorActionPerformed(evt);
            }
        });

        jSpinnerExits.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));

        jLabelEntrances.setText("Entrées");

        jSpinnerEntrances.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));

        jPanelExitValues.setBackground(new java.awt.Color(164, 183, 145));
        jPanelExitValues.setLayout(new java.awt.GridBagLayout());

        javax.swing.GroupLayout jPanelEditionStationLayout = new javax.swing.GroupLayout(jPanelEditionStation);
        jPanelEditionStation.setLayout(jPanelEditionStationLayout);
        jPanelEditionStationLayout.setHorizontalGroup(
            jPanelEditionStationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEditionStationLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelEditionStationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelEditionStationLayout.createSequentialGroup()
                        .addComponent(jLabelDebitMax)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                        .addComponent(jSpinnerDebitMax, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelEditionStationLayout.createSequentialGroup()
                        .addGroup(jPanelEditionStationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelDescription)
                            .addComponent(jLabelName))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanelEditionStationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldName, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                            .addComponent(jTextFieldDescription)))
                    .addGroup(jPanelEditionStationLayout.createSequentialGroup()
                        .addComponent(jLabelExits)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSpinnerExits, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelEditionStationLayout.createSequentialGroup()
                        .addComponent(jLabelEntrances)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelEditionStationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelEditionStationLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jSpinnerEntrances, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelEditionStationLayout.createSequentialGroup()
                                .addGroup(jPanelEditionStationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanelMatrix, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanelExitValues, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanelEditionStationLayout.createSequentialGroup()
                        .addGroup(jPanelEditionStationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonValidate)
                            .addComponent(jButtonChoseColor))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelEditionStationLayout.setVerticalGroup(
            jPanelEditionStationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelEditionStationLayout.createSequentialGroup()
                .addGroup(jPanelEditionStationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelName)
                    .addComponent(jTextFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelEditionStationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelDescription)
                    .addComponent(jTextFieldDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelEditionStationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelDebitMax)
                    .addComponent(jSpinnerDebitMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelEditionStationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelExits)
                    .addComponent(jSpinnerExits, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelEditionStationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelEntrances)
                    .addComponent(jSpinnerEntrances, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addComponent(jButtonChoseColor)
                .addGap(18, 18, 18)
                .addComponent(jPanelMatrix, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanelExitValues, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 129, Short.MAX_VALUE)
                .addComponent(jButtonValidate)
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
                .addGap(14, 14, 14))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonValidateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonValidateActionPerformed
        Component[] components = jPanelMatrix.getComponents();

        LinkedList<String> inputs = new LinkedList<>();

        Map<String, Float> products = new HashMap<>();

        try {
            for (Component component : components) {
                if (component.getClass().equals(JTextField.class)) {
                    JTextField jTextField = (JTextField) component;
                    inputs.add(jTextField.getName() + ":::" + jTextField.getText());

                    String product = jTextField.getName().split(":::")[1];
                    if (products.containsKey(product)) {
                        products.put(product, products.get(product) + Float.parseFloat(jTextField.getText()));
                    } else {
                        products.put(product, Float.parseFloat(jTextField.getText()));
                    }
                }
            }
        } catch(NumberFormatException ex){
            showProblemInMatrix();
            return;
        }

        // Vérifier que la matrice est bonne.
        Iterator<Float> productsValuesIterator = products.values().iterator();

        while (productsValuesIterator.hasNext()) {
            Float productsValue = productsValuesIterator.next();
            if (productsValue != 100) {
                showProblemInMatrix();
                return;
            }
        }
	if (inputs.size() != 0) {
	    element.setMatrix(inputs);
	}
        
        if (element.getType() == InterfaceOutils.ID_TOOL_ENTREE) {
            Map<String, Float> entrees = element.getEntranceProducts();
            for (Component comp : jPanelExitValues.getComponents()) {
                if (comp.getClass().equals(JSpinner.class)) {
                    String product = comp.getName().split("_")[0];
                    if (entrees.containsKey(product)) {
                        JSpinner jspin = (JSpinner) comp;
                        entrees.put(product, (Float) jspin.getValue());
                    }
                }
            }
        }

        updateElement(jTextFieldName.getText(), jTextFieldDescription.getText(), (Float) jSpinnerDebitMax.getValue(),
                selectedColor);

        JOptionPane.showConfirmDialog(null,
                "L'enregistrement s'est passé avec succès.",
                "Enregistrement de " + element.getName(),
                JOptionPane.CLOSED_OPTION,
                JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jButtonValidateActionPerformed

    private void jButtonChoseColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonChoseColorActionPerformed
        JFrame guiFrame = new JFrame();
        selectedColor = JColorChooser.showDialog(guiFrame, "Choisissez une couleur", element.getColor());
    }//GEN-LAST:event_jButtonChoseColorActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonChoseColor;
    private javax.swing.JButton jButtonValidate;
    private javax.swing.JLabel jLabelDebitMax;
    private javax.swing.JLabel jLabelDescription;
    private javax.swing.JLabel jLabelEntrances;
    private javax.swing.JLabel jLabelExits;
    private javax.swing.JLabel jLabelName;
    private javax.swing.JLabel jLabelParameters;
    private javax.swing.JPanel jPanelEditionStation;
    private javax.swing.JPanel jPanelExitValues;
    private javax.swing.JPanel jPanelMatrix;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jSpinnerDebitMax;
    private javax.swing.JSpinner jSpinnerEntrances;
    private javax.swing.JSpinner jSpinnerExits;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextFieldDescription;
    private javax.swing.JTextField jTextFieldName;
    // End of variables declaration//GEN-END:variables
}
