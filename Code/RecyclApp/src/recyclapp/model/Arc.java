package recyclapp.model;

import java.util.Map;

public class Arc extends Component {

    private Coordinate coordinateExit = null;
    private Coordinate coordinateEntrance = null;
    private Element entranceElement;

    public Arc(Element entranceElement) {
        this.entranceElement = entranceElement;
    }

    public void setEntranceElement(Element element) {
        entranceElement = element;
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

    /**
     * @return the entrance
     */
    public Coordinate getEntranceCoordinate() {
        return coordinateEntrance;
    }

}
