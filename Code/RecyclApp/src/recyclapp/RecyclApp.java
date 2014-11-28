package recyclapp;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import recyclapp.model.Plan;
import recyclapp.view.InterfacePrincipale;

/**
 *
 * @author kevinsalles
 */
public class RecyclApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

	try {
	    // Set cross-platform Java L&F (also called "Metal")
	    UIManager.setLookAndFeel(
		    UIManager.getSystemLookAndFeelClassName());
	} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
	    // On ne fait rien, et on utilise le look & feel de base
	}

	Plan plan = new Plan();
	InterfacePrincipale frame = new InterfacePrincipale(plan);
	frame.setVisible(true);
    }

}
