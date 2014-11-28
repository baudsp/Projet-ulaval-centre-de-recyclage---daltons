package recyclapp.model;

import java.awt.Image;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public abstract class Element extends Component {

    protected int type;
    protected int width, height;
    protected Coordinate coordinate;
    protected Image image;
    protected int nbExits;
    protected Arc[] exits;
    protected Map<String, Map<Integer, Map<String, Float>>> matrix;

    public Element() {
        exits = new Arc[nbExits];
        entranceProducts = new HashMap<>();
        matrix = new HashMap<>();
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public int getRankArc(Arc arc) {
        for (int i = 0; i < nbExits; i++) {
            if (exits[i].equals(arc)) {
                return i;
            }
        }
        return -1;
    }

    public Arc getArc(int i) {
        return exits[i];
    }

    public int getNbArcs() {
        int nb = 0;
        for (int i = 0; i < nbExits; i++) {
            if (exits[i] != null) {
                nb++;
            }
        }
        return nb;
    }

    public int getFirstFreeExit() {
        int exit = -1;

        for (int i = 0; i < nbExits; i++) {
            if (exits[i] == null) {
                exit = i;
                break;
            }
        }
        return exit;
    }

    public void addExit(int place, Arc arc) {
        exits[place] = arc;
    }

    public void setMatrix(Map<String, Map<Integer, Map<String, Float>>> matrix) {
        this.matrix = matrix;
    }

    public Map<String, Map<Integer, Map<String, Float>>> getMatrix() {
        return matrix;
    }

    public void pushExitProducts(Map<String, Float> entrance) {
        Set<String> listProducts = entrance.keySet();
        Iterator<String> productsIterator = listProducts.iterator();
        while (productsIterator.hasNext()) {
            String product = productsIterator.next();
            Float quantity = entrance.get(product);
            if (entranceProducts.containsKey(product)) {
                quantity += entranceProducts.get(product);
            }
            entranceProducts.put(product, quantity);
        }
        for (int exitNumber = 0; exitNumber < nbExits; exitNumber++) {
            if (exits[exitNumber] != null) {
                exits[exitNumber].pushExitProducts(exitProductsFromArc(exitNumber));
            }
        }
    }

    public Map<String, Float> exitProductsFromArc(int exitNumber) {
        Map<String, Float> result = new HashMap<>();
        if (entranceProducts == null) {
            return result;
        }
        Set<String> listProducts = entranceProducts.keySet();
        Iterator<String> listProductsIterator = listProducts.iterator();
        while (listProductsIterator.hasNext()) {
            String entranceProduct = listProductsIterator.next();
            float entranceQuantity = entranceProducts.get(entranceProduct);
            if ((matrix.containsKey(entranceProduct)) && (matrix.get(entranceProduct)).containsKey(exitNumber)) {
                Map<String, Float> currentArc = matrix.get(entranceProduct).get(exitNumber);
                Set<String> exitProducts = currentArc.keySet();
                Iterator<String> exitProductIterator = exitProducts.iterator();
                while (exitProductIterator.hasNext()) {
                    String exitProduct = exitProductIterator.next();
                    Float exitQuantity = entranceQuantity * currentArc.get(exitProduct) / 100;
                    if (result.containsKey(exitProduct)) {
                        exitQuantity += result.get(exitProduct);
                    }
                    result.put(exitProduct, exitQuantity);
                }
            }
        }
        return result;
    }

    public Arc[] getArcs() {
        return exits;
    }
}
