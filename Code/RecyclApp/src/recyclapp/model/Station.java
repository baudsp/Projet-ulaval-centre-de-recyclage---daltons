package recyclapp.model;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Station extends Element {

    private String name;
    private String description;
    private Dimension dimension;

    public Station(int id, int x, int y, int width, int height) {
        super();
        this.id = id;
        this.matrix = new HashMap<>();
        this.height = height;
        this.width = width;
        this.coordinate = new Coordinate(x, y);
	nbExits = 1;
        exits = new Arc[nbExits];
    }
    

    public void removeExit(Arc arc) {
        for (int i = 0; i < nbExits; i++) {
            if (exits[i].equals(arc)) {
                exits[i] = null;
            }
        }
    }

    public Arc[] getExits() {
        return exits;
    }

    public int getNumberOfExits() {
        return nbExits;
    }

    public void setNumberOfExits(int nb) {
        this.nbExits = nb;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String descr) {
        description = descr;
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
        coordinate = new Coordinate(x, y);
    }

    public void setCoordinates(Coordinate c) {
        coordinate = c;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public int getHeight() {
        return dimension.height;
    }

    public void setHeight(int height) {
        dimension.height = height;
    }

    public int getWidth() {
        return dimension.width;
    }

    public void setWidth(int width) {
        dimension.width = width;
    }

    public void setDimension(Dimension d) {
        dimension = d;
    }

    public void setDimension(int width, int height) {
        dimension = new Dimension(width, height);
    }    
}
