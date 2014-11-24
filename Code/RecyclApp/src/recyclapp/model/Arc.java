package recyclapp.model;

import java.util.Map;

public class Arc extends Component {
    
    private Coordinate exit = null;
    private Coordinate entrance = null;
    private Element exitElement;
    
    public Arc(Coordinate exit) {
        this.exit = exit;
    }
    
    public void setExitElement(Element element) {
        exitElement = element;
    }
    
    public void pushExitProducts(Map<String, Float> entrance) {
        entranceProducts = entrance;
        if (exitElement != null) {
            exitElement.pushExitProducts(entranceProducts);
        }
    }
    
    public void setEntrance(Coordinate entrance) {
        this.entrance = entrance;
    }
    
    public boolean getStatus() {
        return (entrance != null);
    }

    /**
     * @return the exit
     */
    public Coordinate getExit() {
        return exit;
    }

    /**
     * @return the entrance
     */
    public Coordinate getEntrance() {
        return entrance;
    }
    
}
