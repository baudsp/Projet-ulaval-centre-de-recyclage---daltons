package recyclapp.model;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Station {

    private int nbExits;
    private String name;
    private String description;
    private List<Arc> exits;
    private Coordinate coordinates;
    private Dimension dimension;
    private Map<Product, Map<Product, Float>> matrix;

    public Station(int nbExits) {
        this.nbExits = nbExits;
        this.exits = new LinkedList<>();
        this.matrix = new HashMap<>();
        this.dimension = new Dimension();
        this.coordinates = new Coordinate(0, 0);
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
        return coordinates;
    }

    public void setCoordinates(int x, int y) {
        coordinates = new Coordinate(x, y);
    }

    public void setCoordinates(Coordinate c) {
        coordinates = c;
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

    public Map<Product, Map<Product, Float>> getMatrix() {
        return matrix;
    }

    public Map<Product, Float> getMatrixPerProduct(Product p) {
        return matrix.get(p);
    }

    public List<Product> getEntranceProducts() {
        List<Product> products = new ArrayList<>();
        Set listKeys = matrix.keySet();
        for (Object p : listKeys) {
            products.add((Product) p);
        }
        return products;
    }

    public List<Product> getExitProducts() {
        List<Product> products = new ArrayList<>();
        Set listKeys = matrix.keySet();
        Iterator iterator = listKeys.iterator();
        while (iterator.hasNext()) {
            Map<Product, Float> exit = (Map<Product, Float>) iterator.next();
            Set exitKeys = exit.keySet();
            for (Object key : exitKeys) {
                Product p = (Product) key;
                Float f = exit.get(p);
                if ((f > 0) && (!products.contains(p))) {
                    products.add(p);
                }
            }
        }
        return products;
    }

    public float getExitRateForProduct(Product p) {
        float rate = 0;
        Set listKeys = matrix.keySet();
        Iterator iterator = listKeys.iterator();
        while (iterator.hasNext()) {
            Map<Product, Float> exit = (Map<Product, Float>) iterator.next();
            if (exit.containsKey(p)) {
                rate += exit.get(p);
            }
        }
        return rate;
    }

    public void setMatrix(HashMap<Product, Map<Product, Float>> matrix) {
        this.matrix = matrix;
    }

    public Station clone() {
        Station station = new Station(nbExits);
        station.name = name;
        station.description = description;
        for (Arc arc : exits) {
            station.addExit(arc);
        }
        return station;
    }

    public String toString() {
        String infos = "Station : " + name + "\n";
        infos += "Nombre de sorties : " + nbExits + "\n";
        if (description != null) {
            infos += description + "\n";
        }
        infos += "Dimentsions : " + dimension + "\n";
        if (exits.size() >= 0) {
            infos += "Sorties : \n";
            for (Arc arc : exits) {
                infos += arc;
            }
        }
        if (matrix.size() > 0) {
            infos += "Matrice de recyclage : \n";
            infos += "\tEntrées : \n";
            for (Product p : getEntranceProducts()) {
                infos += "\t\t" + p + "\n";
            }
            infos += "\tSorties : \n";
            for (Product p : getExitProducts()) {
                infos += "\t\t" + p + " : " + getExitRateForProduct(p) + "\n";
            }
        }
        return infos;
    }
}
