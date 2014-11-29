package recyclapp.model;

import java.util.Map;

public class Arc extends Component {

    private Coordinate coordinateExit;
    private Coordinate coordinateEntrance;
    private Element entranceElement;
    private Element exitElement;

    public Arc(Element entranceElement) {
        this.entranceElement = entranceElement;
    }

    public Arc(Element entrance, Element exit) {
        entranceElement = entrance;
        exitElement = exit;
    }

    public void pushExitProducts(Map<String, Float> listEntranceProducts) {
        entranceProducts = listEntranceProducts;
        if (entranceElement != null) {
            entranceElement.pushExitProducts(entranceProducts);
        }
    }

    public void setEntranceCoordinate(Coordinate coordinateEntrance) {
        this.coordinateEntrance = coordinateEntrance;
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

    public void setEntranceElement(Element entrance) {
        entranceElement = entrance;
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
