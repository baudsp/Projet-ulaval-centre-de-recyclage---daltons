/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recyclapp.view;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.swing.table.TableModel;
import recyclapp.model.Element;
import recyclapp.model.Station;

/**
 *
 * @author Baudouin
 */
public class InterfaceParam extends javax.swing.JPanel {

    private Station station;

    private int nbDechets = 2; // TODO : Permettre à l'utilisateur de modifier cela.

    /**
     * Creates new form InterfaceParamBis
     */
    public InterfaceParam() {
        initComponents();
        this.jPanelEditionStation.setVisible(false);
    }

    // A mettre dans une classe modelInterfaceParam
    private void updateMatrix() {

        Map<String, Map<Integer, Map<String, Float>>> matriceDeTri = station.getMatrix();

        int nbrDechets = matriceDeTri.size();

        int nbrSorties = (Integer) jSpinnerNbrExit.getValue() + 1;

        Object[][] valeursMatrice = new Object[nbrDechets][nbrSorties];

        String tabDechets[] = new String[0];
        tabDechets = matriceDeTri.keySet().toArray(tabDechets);

        Arrays.sort(tabDechets);

        for (int i = 0; i < nbrDechets; i++) {
            Map<Integer, Map<String, Float>> mapSortieDechets;
            mapSortieDechets = matriceDeTri.get(tabDechets[i]);
            for (int j = 1; j < nbrSorties; j++) {
                Integer tabNumSortie[] = new Integer[nbrSorties];
                Integer exitNumber = mapSortieDechets.keySet().toArray(tabNumSortie)[i];
                if (exitNumber == null) {
                    valeursMatrice[i][j] = 0f;
                } else {
                    valeursMatrice[i][j] = mapSortieDechets.get(exitNumber).get(tabDechets[i]);
                }
            }
            valeursMatrice[i][0] = tabDechets[i];
        }

        String[] columnNames = new String[nbrSorties];

        columnNames[0] = "";
        for (int i = 1; i < nbrSorties; i++) {
            columnNames[i] = "Sortie " + (i);
        }

        jMatrixTri.setModel(
                new javax.swing.table.DefaultTableModel(
                        valeursMatrice,
                        columnNames
                ));

        repaint();
    }
    
    // A mettre dans une classe modelInterfaceParam
    public void hideEditionStationInformations() {
        this.jPanelEditionStation.setVisible(false);
    }
    
    /***
     * summary : Initialise la matrice à partir d'un élément.
     * @param element 
     */
    // A mettre dans une classe modelInterfaceParam
    public void setParametersInformations(Element element) {

        if (element.getClass().getName().equals(Station.class.getName())) {
            jPanelEditionStation.setVisible(true);
            station = (Station) element;
            jTextFieldName.setText(station.getName());
            jTextFieldDescription.setText(station.getDescription());
            jSpinnerNbrExit.setValue(station.getNumberOfExits());

            jSpinnerDebitMax.setValue(station.getMaxFlow());

            Map<String, Map<Integer, Map<String, Float>>> matrixDeTri = station.getMatrix();
            if (matrixDeTri.isEmpty() || 0 == station.getNumberOfExits()) {
                for (int k = 0; k < nbDechets; k++) {

                    Map<Integer, Map<String, Float>> b = new HashMap<>();

                    for (int i = 0; i < station.getNumberOfExits(); i++) {
                        Map<String, Float> a = new HashMap<>();
                        for (int j = 0; j < nbDechets; j++) {
                            a.put("Dechet " + (1 + j), 0f);
                        }
                        b.put(i, a);
                    }
                    matrixDeTri.put("Dechet " + (k + 1), b);
                }
                station.setMatrix(matrixDeTri);
            }
            updateMatrix();
        } else {
            jPanelEditionStation.setVisible(false);
            this.station = null;
        }
    }
    
    // A mettre dans une classe modelInterfaceParam
    private void saveInformations() {
        int nbrSorties = (int) jSpinnerNbrExit.getValue();

        station.setName(jTextFieldName.getText());
        station.setDescription(jTextFieldDescription.getText());
        station.setNumberOfExits(nbrSorties);
        station.setMaxFlow((Float) jSpinnerDebitMax.getValue());

        TableModel model = jMatrixTri.getModel();

        int nbreDechets = jMatrixTri.getRowCount();

        Map<String, Map<Integer, Map<String, Float>>> matriceDeTri = station.getMatrix();

        String[] tabDechets = new String[nbreDechets];
        for (int i = 0; i < nbreDechets; i++) {
            tabDechets[i] = (String) model.getValueAt(i, 0);
        }

        for (int i = 1; i < nbrSorties + 1; i++) {
            for (int j = 0; j < nbreDechets; j++) {

                Map<Integer, Map<String, Float>> mapCurExit;
                if (matriceDeTri.containsKey(tabDechets[j])) {
                    mapCurExit = matriceDeTri.get(tabDechets[j]);
                } else {
                    mapCurExit = new HashMap<>();
                }

                Object data = jMatrixTri.getValueAt(j, i);

                Float value;

                if (data.getClass().getName().equals(String.class.getName())) {
                    value = Float.parseFloat((String) data);
                } else if (data.getClass().getName().equals(Integer.class.getName())) {
                    value = ((Integer) data) * 1.0f;
                } else {
                    value = (Float) data;
                }

                Map<String, Float> curMapTri;
                if (mapCurExit.containsKey(i)) {
                    curMapTri = mapCurExit.get(i);
                } else {
                    curMapTri = new HashMap<>();
                }
                curMapTri.put(tabDechets[j], value);
                mapCurExit.put(i, curMapTri);

                matriceDeTri.put(tabDechets[j], mapCurExit);
            }
        }

        for (int i = 1; i < nbrSorties + 1; i++) {
            for (int j = 0; j < nbreDechets; j++) {

                Map<Integer, Map<String, Float>> mapCurExit;
                if (matriceDeTri.containsKey(tabDechets[j])) {
                    mapCurExit = matriceDeTri.get(tabDechets[j]);
                } else {
                    mapCurExit = new HashMap<>();
                }

                Object data = jMatrixTri.getValueAt(j, i);

                Float currentValue;

                if (data.getClass().getName().equals(String.class.getName())) {
                    currentValue = Float.parseFloat((String) data);
                } else if (data.getClass().getName().equals(Integer.class.getName())) {
                    currentValue = ((Integer) data) * 1.0f;
                } else {
                    currentValue = (Float) data;
                }

                Map<String, Float> curMapTri;
                if (mapCurExit.containsKey(i)) {
                    curMapTri = mapCurExit.get(i);
                } else {
                    curMapTri = new HashMap<>();
                }
                curMapTri.put(tabDechets[j], currentValue);
                mapCurExit.put(i, curMapTri);

                matriceDeTri.put(tabDechets[j], mapCurExit);
            }
        }
        station.setMatrix(matriceDeTri);
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
        jLabel5 = new javax.swing.JLabel();
        jTextFieldName = new javax.swing.JTextField();
        jTextFieldDescription = new javax.swing.JTextField();
        jSpinnerDebitMax = new javax.swing.JSpinner();
        jSpinnerNbrExit = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jMatrixTri = new javax.swing.JTable();

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

        jLabel5.setText("Nombre de sorties");

        jTextFieldName.setText(" ");

        jSpinnerDebitMax.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), Float.valueOf(0.0f), null, Float.valueOf(1.0f)));

        jSpinnerNbrExit.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));
        jSpinnerNbrExit.setToolTipText("");
        jSpinnerNbrExit.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinnerNbrExitStateChanged(evt);
            }
        });

        jLabel6.setText("Edition de la station");

        jLabel7.setText("Matrice de tri");

        jMatrixTri.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        )

    );
    jScrollPane2.setViewportView(jMatrixTri);

    javax.swing.GroupLayout jPanelEditionStationLayout = new javax.swing.GroupLayout(jPanelEditionStation);
    jPanelEditionStation.setLayout(jPanelEditionStationLayout);
    jPanelEditionStationLayout.setHorizontalGroup(
        jPanelEditionStationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanelEditionStationLayout.createSequentialGroup()
            .addGroup(jPanelEditionStationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelEditionStationLayout.createSequentialGroup()
                    .addGroup(jPanelEditionStationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanelEditionStationLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jButton1))
                        .addGroup(jPanelEditionStationLayout.createSequentialGroup()
                            .addGap(30, 30, 30)
                            .addComponent(jLabel6)))
                    .addGap(0, 73, Short.MAX_VALUE))
                .addGroup(jPanelEditionStationLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanelEditionStationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanelEditionStationLayout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSpinnerDebitMax, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanelEditionStationLayout.createSequentialGroup()
                            .addGroup(jPanelEditionStationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel7)
                                .addComponent(jLabel5))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSpinnerNbrExit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelEditionStationLayout.createSequentialGroup()
                            .addGroup(jPanelEditionStationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel3)
                                .addComponent(jLabel2))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanelEditionStationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTextFieldName, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                                .addComponent(jTextFieldDescription))))))
            .addContainerGap())
        .addGroup(jPanelEditionStationLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(jPanelEditionStationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel5)
                .addComponent(jSpinnerNbrExit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLabel7)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 248, Short.MAX_VALUE)
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
        saveInformations();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jSpinnerNbrExitStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinnerNbrExitStateChanged
        updateMatrix();
    }//GEN-LAST:event_jSpinnerNbrExitStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabelParameters;
    private javax.swing.JTable jMatrixTri;
    private javax.swing.JPanel jPanelEditionStation;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSpinner jSpinnerDebitMax;
    private javax.swing.JSpinner jSpinnerNbrExit;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextFieldDescription;
    private javax.swing.JTextField jTextFieldName;
    // End of variables declaration//GEN-END:variables
}
