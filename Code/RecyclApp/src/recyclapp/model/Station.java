package recyclapp.model;

import java.awt.Dimension;
import recyclapp.view.InterfaceOutils;

public class Station extends Element {


    private Dimension dimension;
    
    public Station(int x, int y, int width, int height, int nbrSorties) {
        super(new Coordinate(x, y), 1, nbrSorties, width, height);
        
        exits = new Arc[nbExits];
	
	name = "Station " + this.id;
    }

    public void removeExit(Arc arc) {
        for (int i = 0; i < nbExits; i++) {
            if (exits[i].equals(arc)) {
                exits[i] = null;
            }
        }
    }

    @Override
    public boolean isPossibleToAddEntrance() {
        return entrances.isEmpty();
    }

    public Arc[] getExits() {
        return exits;
    }

    public int getNumberOfExits() {
        return nbExits;
    }

    public void setNumberOfExits(int numberOfExits) {
        Arc[] newExits = new Arc[numberOfExits];
        for (int i = 0; (i < nbExits) && (i < numberOfExits); i++) {
            newExits[i] = exits[i];
        }
        this.nbExits = numberOfExits;
        exits = newExits;

    }

    public Coordinate getCoordinates() {
        return coordinate;
    }

    public void setCoordinates(int x, int y) {
        this.coordinate = new Coordinate(x, y);
    }

    public void setCoordinates(Coordinate coordinates) {
        this.coordinate = coordinates;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public int getHeight() {
        return dimension.height;
    }

    public void setHeight(int height) {
        this.dimension.height = height;
    }

    public int getWidth() {
        return dimension.width;
    }

    public void setWidth(int width) {
        this.dimension.width = width;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    public void setDimension(int width, int height) {
        dimension = new Dimension(width, height);
    }

    @Override
    public int getType() {
	return InterfaceOutils.ID_TOOL_STATION;
    }

    @Override
    public Element clone() {
        Station station = new Station(this.coordinate.getX(), this.coordinate.getY(), this.width, this.height, this.nbExits);
        
        station = (Station) helpClone(station);
        
        station.dimension = this.dimension;
        
        return station;
    }
}
