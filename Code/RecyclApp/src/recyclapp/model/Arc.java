package recyclapp.model;

import java.util.Map;

public class Arc extends Component {

    private Coordinate coordinateExit;
    private Coordinate coordinateEntrance;
    /**
     * Origine de l'arc
     */
    private Element exitElement;
    /**
     * Destination de l'arc
     */
    private Element entranceElement;

    public Arc(Element entranceElement, Element exitElement) {
        this.entranceElement = entranceElement;
	this.exitElement = exitElement;
    }

    public void pushExitProducts(Map<String, Float> listEntranceProducts) {
        entranceProducts = listEntranceProducts;
        if (entranceElement != null) {
            entranceElement.pushExitProducts(entranceProducts);
        }
    }

    public boolean getEntranceCoordinateStatus() {
        return (coordinateEntrance != null);
    }

    /**
     * @return the exit
     */
    public Element getEntranceElement() {
        return entranceElement;
    }

    public Element getExitElement() {
        return exitElement;
    }

    public void setExitElement(Element exit) {
        exitElement.removeEntrance(this);
        exitElement = exit;
        exitElement.addEntrance(this);
    }

    /**
     * @return the entrance
     */
    public Coordinate getEntranceCoordinate() {
        return coordinateEntrance;
    }

}
