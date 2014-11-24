package recyclapp.model;

import java.awt.Dimension;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Station extends Element {

    private int nbExits;
    private String name;
    private String description;
    private Dimension dimension;

    public Station(int id, int x, int y, int width, int height) {
        super();
        this.id = id;
        this.exits = new LinkedList<>();
        this.matrix = new HashMap<>();
        this.height = height;
        this.width = width;
        this.coordinate = new Coordinate(x, y);
	nbExits = 1;
    }
    

    public void addExit(Arc arc) {
        if (exits.size() < nbExits) {
            exits.add(arc);
        }
    }

    public void removeExit(Arc arc) {
        if (exits.contains(arc)) {
            exits.remove(arc);
        }
    }

    public List<Arc> getExits() {
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

    public Map<String, Map<Arc, Map<String, Float>>> getMatrix() {
        return matrix;
    }
}
