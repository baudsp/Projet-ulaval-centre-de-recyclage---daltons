package recyclapp.model;

import java.awt.Dimension;
import java.util.HashMap;

public class Station extends Element {

    private String name;
    private String description;
    private Dimension dimension;

    public Station(int id, int x, int y, int width, int height) {
        super(new Coordinate(x, y), 1,1, width, height);
        this.id = id;
        this.matrix = new HashMap<>();
        exits = new Arc[nbExits];
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
	return 0;
    }
}
