package recyclapp.view;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import recyclapp.model.Coordinate;
import recyclapp.model.DataElement;
import recyclapp.model.Element;
import recyclapp.model.Plan;

public class InterfacePrincipale extends javax.swing.JFrame implements ActionListener, MouseMotionListener, MouseListener {

    private InterfacePlan interfacePlan;
    private ModeleInterfacePrincipal mip;
    private Plan plan;
    private JScrollPane jScrollPane1;

    private DataElement dataElementTemp;

    public InterfacePrincipale(Plan plan) {
	initComponents();
	initialize();
	panelParams.addObserver(this.plan);
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
	this.dataElementTemp = new DataElement();
	this.plan.createElement(InterfaceOutils.ID_TOOL_ENTREE, 20, this.getHeight() / 2, 0);
    }

    public void updateInterfacePlan(float zoom) {
	interfacePlan.setPreferredSize(new Dimension((int) (2000 * zoom), (int) (2000 * zoom)));

	int value = jScrollPane1.getVerticalScrollBar().getValue();
	int value2 = jScrollPane1.getHorizontalScrollBar().getValue();

	jScrollPane1.getVerticalScrollBar().setValue(value + 1);
	jScrollPane1.getHorizontalScrollBar().setValue(value2 + 1);

	jScrollPane1.getVerticalScrollBar().setValue(value);
	jScrollPane1.getHorizontalScrollBar().setValue(value2);

    }

    public JLabel getLogCoordinates() {
	return logCoordinates;
    }

    public JLabel getLogZoom() {
	return logZoom;
    }

    public JLabel getLogDebug() {
	return logDebug;
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
	    //System.out.println("egetx " + e.getX());
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
	if (e.getSource() != interfacePlan) { // On rentre dedans que si on part de Tools
	    if (e.getX() < panelTools.getWidth()) {
		// TODO : Dessiner l'image qui suit dans InterfaceOutils (On est parti de tools : AJOUT)
	    }
	    e.translatePoint(-panelTools.getWidth(), 0);
	} else {
	    if (e.getX() < 0) {
		// TODO Pouvoir revenir vers InterfaceTools et dessiner là bas (On est parti de plan : EDIT)
	    }
	}

	if (this.panelTools.getIdTools() == InterfaceOutils.ID_TOOL_ARC) {
	    mip.changeCursor(InterfaceOutils.ID_TOOL_ARC);
	}

	if (e.getSource().equals(interfacePlan)) { // QUAND ON DRAG AND DROP DEPUIS LE PLAN (DEPLACEMENT)
	    interfacePlan.logZoomAndCoordinates(mip.convertPixelToMeter(e.getX()), mip.convertPixelToMeter(e.getY()));

	    int meterX = mip.convertPixelToMeter(e.getX());
	    int meterY = mip.convertPixelToMeter(e.getY());

	    if (this.dataElementTemp != null && this.dataElementTemp.type >= 0) {

		if (jCheckBoxMenuItemMagnetique.isSelected() && interfacePlan.isWithGrid()) {
		    Coordinate coo = mip.findCooMagnetique(e.getX(), e.getY());
		    interfacePlan.drawImageFollowingCursor(this.panelTools.getImages(this.dataElementTemp.type), (int) (coo.getX()), coo.getY());
		} else {
		    interfacePlan.drawImageFollowingCursor(this.panelTools.getImages(this.dataElementTemp.type), (int) (e.getX()), e.getY());
		}
	    }
	    this.interfacePlan.repaint();
	} else if (this.panelTools.isMoveTools() && this.panelTools.getIdTools() != InterfaceOutils.ID_TOOL_ARC) { // QUAND ON DRAG AND DROP DEPUIS L'OUTILS
	    interfacePlan.logZoomAndCoordinates(mip.convertPixelToMeter(e.getX()), mip.convertPixelToMeter(e.getY()));
	    if (jCheckBoxMenuItemMagnetique.isSelected() && interfacePlan.isWithGrid()) {
		Coordinate coo = mip.findCooMagnetique(e.getX(), e.getY());

		interfacePlan.drawImageFollowingCursor(this.panelTools.getImages(this.panelTools.getIdTools()), coo.getX(), coo.getY());
	    } else {
		//System.out.println("egetx draw " + e.getX());
		interfacePlan.drawImageFollowingCursor(this.panelTools.getImages(this.panelTools.getIdTools()), e.getX(), e.getY());
	    }
	}
    }

    @Override
    public void mouseClicked(MouseEvent e) {

	this.plan.calc();

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
	int xEvent = e.getX();
	if (e.getSource() != this.interfacePlan) {
	    xEvent -= panelTools.getWidth();
	}
	if (inMap(xEvent, e.getY())) {
	    float zoom = this.interfacePlan.getZoom();

	    if (this.panelTools.isMoveTools()) {
		mip.changeCursor(-1);
		if (this.panelTools.getIdTools() != InterfaceOutils.ID_TOOL_ARC) {
		    int x = e.getX();
		    int y = e.getY();

		    if (this.panelTools.getIdTools() >= 0) {

			if (!this.mip.isOverlapElement((int) (x / zoom - this.panelTools.getWidth()),
				(int) (y / zoom), this.panelTools.getSizeImage(), this.panelTools.getSizeImage())) {

			    int nbrSorties = 0;
			    if (this.panelTools.getIdTools() == InterfaceOutils.ID_TOOL_STATION) {
				nbrSorties = getNbrSorties();
				if (nbrSorties == -1) {
				    return;
				}
			    }
			    if (jCheckBoxMenuItemMagnetique.isSelected()) {
				Coordinate coo = mip.findCooMagnetique(x, y);
				x = coo.getX();
				y = coo.getY();
			    }

			    // J'ai l'impression qu'on ne doit pas mettre le zoom ici.... Ca me dépasse totalement mais ca marache !
			    int halfImageSize = panelTools.getSizeImage() / 2;

			    int createX = (int) ((x - this.panelTools.getWidth()) / zoom) - halfImageSize;
			    int createY = (int) (y / zoom) - halfImageSize;

			    this.plan.createElement(this.panelTools.getIdTools(), createX, createY, nbrSorties);

			    DataElement addedDataElement = this.plan.getListDataElements().get(this.plan.getListDataElements().size() - 1); // Le dernier
			    interfacePlan.showSelectedElement(addedDataElement);

			    this.panelParams.setParametersInformations(addedDataElement.element);

			}
		    }
		    this.panelTools.setMoveTools(false);
		} else { // CREATE ARC
		    int x = (int) (e.getX() / zoom - this.panelTools.getWidth());
		    int y = (int) (e.getY() / zoom);
		    if (this.panelTools.getIdTools() >= 0
			    && this.plan.findDataElement(x, y, zoom).element != null) { 
			if (this.plan.isDrawingArc()) {
			    if (this.plan.createArcEntrance(x, y)) {
				this.panelTools.setMoveTools(false);
				this.panelTools.resetTools();
				logDebug.setText("");
			    }
			} else {
			    DataElement dataElement = this.plan.createArcExit(x, y);
			    if (dataElement != null) {
				logDebug.setText("Selectionner l'élément à relier à " + dataElement.element.getName());
			    } else {
				logDebug.setText("");
			    }
			}
		    }
		}
		this.panelTools.repaint();
	    } else if (e.getSource().equals(interfacePlan)) {
		int x = e.getX();
		int y = e.getY();

		if (jCheckBoxMenuItemMagnetique.isSelected()) {
		    Coordinate coo = mip.findCooMagnetique(x, y);
		    x = coo.getX();
		    y = coo.getY();
		}

		int halfImageSize = panelTools.getSizeImage() / 2;

		int moveX = (int) (x / zoom) - halfImageSize;
		int moveY = (int) (y / zoom) - halfImageSize;

		this.plan.moveElement(dataElementTemp, moveX, moveY);
		dataElementTemp = new DataElement();
	    }

	    this.interfacePlan.repaint();
	} else {
	    if (this.dataElementTemp != null && this.dataElementTemp.type >= 0) {
		JOptionPane.showMessageDialog(null,
			"Déplacer l'élément dans le plan.",
			"Erreur lors du déplacement de l'élément",
			JOptionPane.OK_OPTION,
			null);
	    } else {
		if (this.panelTools.isMoveTools() && this.panelTools.getIdTools() != InterfaceOutils.ID_TOOL_ARC) { // Vérifier qu'on drag and drop quelque chose
		    JOptionPane.showMessageDialog(null,
			    "Créer l'élément dans le plan.",
			    "Erreur lors de la création de l'élément",
			    JOptionPane.OK_OPTION,
			    null);
		}
	    }
	    this.interfacePlan.repaint();
	    this.panelTools.repaint();
	}
    }

    /**
     * **
     * summary : Permet de savoir si on est bien dans le plan pour travailler.
     */
    private boolean inMap(int x, int y) {
	if (mip.convertPixelToMeter(x) <= 0 || mip.convertPixelToMeter(y) <= 0) {
	    return false;
	}

	int marginLeft = 0;
	if (this.itemTools.getState()) {
	    x += this.panelTools.getWidth();
	}

	return x + marginLeft >= this.jScrollPane1.getX()
		&& x <= this.jScrollPane1.getX() + this.jScrollPane1.getWidth()
		&& y >= this.jScrollPane1.getY()
		&& y <= this.jScrollPane1.getY() + this.jScrollPane1.getHeight();
    }

    public JLabel getDebug() {
	return logDebug;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    private void restartPlan() {
	plan = new Plan();
	this.plan.createElement(InterfaceOutils.ID_TOOL_ENTREE, 20, this.getHeight() / 2, 0);
	openPlan();
    }

    private void openPlan() {
	interfacePlan.setStationIsSelected(false);
	interfacePlan.setZoom(1);
	this.panelParams.hideEditionStationInformations();
	interfacePlan.repaint();
    }

    private void savePlan() {
	JFileChooser chooser = new JFileChooser();
	chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	int option = chooser.showSaveDialog(null);
	if (option == JFileChooser.APPROVE_OPTION) {
	    // TODO : Verifier que l'on écrase pas un fichier existant

	    File selectedPfile = chooser.getSelectedFile();
	    String path = selectedPfile.getAbsolutePath() + ".ser";

	    try {
		FileOutputStream fout = new FileOutputStream(path);
		ObjectOutputStream oos = new ObjectOutputStream(fout);
		oos.writeObject(plan);
		oos.close();
	    } catch (Exception ex) {
		String questionEr = "Erreur : " + ex.getMessage();

		JOptionPane.showMessageDialog(null,
			questionEr,
			"Erreur lors de la sauvegarde",
			JOptionPane.OK_OPTION,
			null);
	    }
	}
    }

    private void chargePlan() {
	String question = "Voulez vous sauvegarder le plan en cours d'abord ?";
	Object[] optionsReponse = {"Oui, bien sûr !", "Non, merci"};
	int n = JOptionPane.showOptionDialog(null,
		question,
		"Sauvegarder le plan en cours ?",
		JOptionPane.YES_NO_CANCEL_OPTION,
		JOptionPane.QUESTION_MESSAGE,
		null,
		optionsReponse,
		optionsReponse[1]);

	if (n == 0) {
	    savePlan(); // Gérer le cas où l'enregistrement se passe mal pour pas perdre les infos à sauvegarder
	}

	JFileChooser chooser = new JFileChooser();
	chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	int option = chooser.showOpenDialog(null);
	if (option == JFileChooser.APPROVE_OPTION) {

	    File selectedPfile = chooser.getSelectedFile();
	    String path = selectedPfile.getAbsolutePath();
	    try {
		FileInputStream fin = new FileInputStream(path);
		ObjectInputStream ois = new ObjectInputStream(fin);
		plan = new Plan();
		plan = (Plan) ois.readObject();
		ois.close();
		openPlan();
	    } catch (IOException | ClassNotFoundException ex) {
		String questionEr = "Erreur : " + ex.getMessage();

		JOptionPane.showMessageDialog(null,
			questionEr,
			"Erreur lors du chargement",
			JOptionPane.OK_OPTION,
			null);
	    }
	}
    }

    public void undo() {
	LinkedList<Element> listElements = plan.getChangeManager().undo();
	plan.resetElements(listElements);
	interfacePlan.repaint();
    }

    public void redo() {
	LinkedList<Element> listElements = plan.getChangeManager().redo();
	plan.resetElements(listElements);
	interfacePlan.repaint();
    }

    /**
     * demande à l'utilisateur de rentrer le nombre de sorties de la station
     * Retourne -1 si l'utilisateur choisis d'annuler
     *
     * @return
     */
    private int getNbrSorties() {
	int nbrSorties;
	SpinnerModel spinnerModel = new SpinnerNumberModel(1, 1, 5, 1);

	JSpinner spinner = new JSpinner(spinnerModel);
	int res = JOptionPane.showOptionDialog(null, spinner, "Choix du nombre d'entrées", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

	if (res == JOptionPane.CANCEL_OPTION) {
	    nbrSorties = -1;
	} else {
	    nbrSorties = (int) spinner.getValue();
	}

	return nbrSorties;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelInfo = new javax.swing.JPanel();
        logCoordinates = new javax.swing.JLabel();
        logZoom = new javax.swing.JLabel();
        logDebug = new javax.swing.JLabel();
        panelParams = new recyclapp.view.InterfaceParam();
        panelTools = new recyclapp.view.InterfaceOutils();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuFile = new javax.swing.JMenu();
        jMenuItemNew = new javax.swing.JMenuItem();
        jMenuItemOpen = new javax.swing.JMenuItem();
        jMenuItemSave = new javax.swing.JMenuItem();
        jMenuItemExport = new javax.swing.JMenuItem();
        jMenuItemClose = new javax.swing.JMenuItem();
        jMenuEdit = new javax.swing.JMenu();
        jMenuItemUndo = new javax.swing.JMenuItem();
        jMenuItemRedo = new javax.swing.JMenuItem();
        jMenuVue = new javax.swing.JMenu();
        itemTools = new javax.swing.JCheckBoxMenuItem();
        itemParam = new javax.swing.JCheckBoxMenuItem();
        itemGrid = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItemMagnetique = new javax.swing.JCheckBoxMenuItem();
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

        logDebug.setForeground(new java.awt.Color(254, 254, 254));
        panelInfo.add(logDebug, java.awt.BorderLayout.EAST);

        getContentPane().add(panelInfo, java.awt.BorderLayout.PAGE_END);
        getContentPane().add(panelParams, java.awt.BorderLayout.LINE_END);

        getContentPane().add(panelTools, java.awt.BorderLayout.LINE_START);

        jMenuBar1.setBackground(new java.awt.Color(164, 183, 145));

        jMenuFile.setText("File");

        jMenuItemNew.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        jMenuItemNew.setText("Nouveau");
        jMenuItemNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemNewActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemNew);

        jMenuItemOpen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemOpen.setText("Ouvrir");
        jMenuItemOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemOpenActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemOpen);

        jMenuItemSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemSave.setText("Sauvegarder");
        jMenuItemSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSaveActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemSave);

        jMenuItemExport.setText("Exporter en image");
        jMenuItemExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemExportActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemExport);

        jMenuItemClose.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        jMenuItemClose.setText("Fermer");
        jMenuItemClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemCloseActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemClose);

        jMenuBar1.add(jMenuFile);

        jMenuEdit.setText("Edit");

        jMenuItemUndo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemUndo.setText("Undo");
        jMenuItemUndo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemUndoActionPerformed(evt);
            }
        });
        jMenuEdit.add(jMenuItemUndo);

        jMenuItemRedo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemRedo.setText("Redo");
        jMenuItemRedo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemRedoActionPerformed(evt);
            }
        });
        jMenuEdit.add(jMenuItemRedo);

        jMenuBar1.add(jMenuEdit);

        jMenuVue.setText("Vue");

        itemTools.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        itemTools.setSelected(true);
        itemTools.setText("Afficher le panneau d'outils");
        itemTools.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemToolsActionPerformed(evt);
            }
        });
        jMenuVue.add(itemTools);

        itemParam.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        itemParam.setSelected(true);
        itemParam.setText("Afficher le panneaux de paramètre");
        itemParam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemParamActionPerformed(evt);
            }
        });
        jMenuVue.add(itemParam);

        itemGrid.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_MASK));
        itemGrid.setText("Afficher la grille");
        itemGrid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemGridActionPerformed(evt);
            }
        });
        jMenuVue.add(itemGrid);

        jCheckBoxMenuItemMagnetique.setText("Grille magnétique");
        jMenuVue.add(jCheckBoxMenuItemMagnetique);

        jMenuItemZoomIn.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_EQUALS, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemZoomIn.setText("Zoom +");
        jMenuItemZoomIn.setName("jMenuItemZoomIn"); // NOI18N
        jMenuItemZoomIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemZoomInActionPerformed(evt);
            }
        });
        jMenuVue.add(jMenuItemZoomIn);

        jMenuItemZoomOut.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_MINUS, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemZoomOut.setText("Zoom -");
        jMenuItemZoomOut.setName("jMenuItemZoomOut"); // NOI18N
        jMenuItemZoomOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemZoomOutActionPerformed(evt);
            }
        });
        jMenuVue.add(jMenuItemZoomOut);

        jMenuBar1.add(jMenuVue);

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
	chargePlan();
    }//GEN-LAST:event_jMenuItemOpenActionPerformed

    private void jMenuItemSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSaveActionPerformed
	savePlan();
    }//GEN-LAST:event_jMenuItemSaveActionPerformed

    private void jMenuItemCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemCloseActionPerformed
	System.exit(0);
    }//GEN-LAST:event_jMenuItemCloseActionPerformed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
	if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
	    if (interfacePlan.getStationIsSelected()) {
		plan.removeElement(interfacePlan.getSelectedDataElement());
		interfacePlan.setStationIsSelected(false);
		this.panelParams.hideEditionStationInformations();
	    }
	} else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
	    interfacePlan.setStationIsSelected(false);
	    this.panelParams.hideEditionStationInformations();
	}
	interfacePlan.repaint();
    }//GEN-LAST:event_formKeyPressed

    private void jMenuItemUndoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemUndoActionPerformed
	undo();
    }//GEN-LAST:event_jMenuItemUndoActionPerformed

    private void jMenuItemRedoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemRedoActionPerformed
	redo();
    }//GEN-LAST:event_jMenuItemRedoActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBoxMenuItem itemGrid;
    private javax.swing.JCheckBoxMenuItem itemParam;
    private javax.swing.JCheckBoxMenuItem itemTools;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItemMagnetique;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jMenuEdit;
    private javax.swing.JMenu jMenuFile;
    private javax.swing.JMenuItem jMenuItemClose;
    private javax.swing.JMenuItem jMenuItemExport;
    private javax.swing.JMenuItem jMenuItemNew;
    private javax.swing.JMenuItem jMenuItemOpen;
    private javax.swing.JMenuItem jMenuItemRedo;
    private javax.swing.JMenuItem jMenuItemSave;
    private javax.swing.JMenuItem jMenuItemUndo;
    private javax.swing.JMenuItem jMenuItemZoomIn;
    private javax.swing.JMenuItem jMenuItemZoomOut;
    private javax.swing.JMenu jMenuVue;
    private javax.swing.JLabel logCoordinates;
    private javax.swing.JLabel logDebug;
    private javax.swing.JLabel logZoom;
    private javax.swing.JPanel panelInfo;
    private recyclapp.view.InterfaceParam panelParams;
    private recyclapp.view.InterfaceOutils panelTools;
    // End of variables declaration//GEN-END:variables
}
