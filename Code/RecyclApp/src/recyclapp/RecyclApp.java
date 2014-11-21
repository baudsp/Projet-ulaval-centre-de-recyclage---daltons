
package recyclapp;

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
        Plan plan = new Plan();
        InterfacePrincipale frame = new InterfacePrincipale(plan);
        frame.setVisible(true);
    }
    
}
