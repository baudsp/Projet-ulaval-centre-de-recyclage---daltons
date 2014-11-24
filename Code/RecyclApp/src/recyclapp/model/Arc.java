package recyclapp.model;

import java.util.Map;

public class Arc extends Component {
    
    private Coordinate coordinateExit = null;
    private Coordinate coordinateEntrance = null;
    private Element entranceElement;
    
    public Arc(Element entElement) {
        this.entranceElement = entElement;
    }
   
    
    public void setEntranceElement(Element element) {
        entranceElement = element;
    }
    
    public void pushExitProducts(Map<String, Float> entrance) {
        entranceProducts = entrance;
        if (entranceElement != null) {
            entranceElement.pushExitProducts(entranceProducts);
        }
    }
    
    public void setCoordinateEntrance(Coordinate entrance) {
        this.coordinateEntrance = entrance;
    }
    
    public boolean getStatus() {
        return (coordinateEntrance != null);
    }

    /**
     * @return the exit
     */
    public Element getEntranceElement() {
        return entranceElement;
    }

    /**
     * @return the entrance
     */
    public Coordinate getEntrance() {
        return coordinateEntrance;
    }
    
}
