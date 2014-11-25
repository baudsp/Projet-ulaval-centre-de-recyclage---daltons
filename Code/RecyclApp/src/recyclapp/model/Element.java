package recyclapp.model;

import java.awt.Image;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class Element extends Component {

    protected int id;
    protected int width, height;
    protected Coordinate coordinate;
    protected Image image;
    protected List<Arc> exits;
    protected Map<String, Map<Arc, Map<String, Float>>> matrix;

    public Element() {
        exits = new LinkedList<>();
        entranceProducts = new HashMap<>();
        matrix = new HashMap<>();
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    
    public void addExit(Arc arc) {
        if (!exits.contains(arc)) {
            exits.add(arc);
        }
    }

    public void setMatrix(Map<String, Map<Arc, Map<String, Float>>> matrix) {
        this.matrix = matrix;
    }
    
    public Map<String, Map<Arc, Map<String, Float>>> getMatrix() {
	return matrix;
    }

    public void pushExitProducts(Map<String, Float> entrance) {
        Set<String> listKeys = entrance.keySet();
        Iterator<String> iterator = listKeys.iterator();
        while (iterator.hasNext()) {
            String product = iterator.next();
            Float quantity = entrance.get(product);
            if (entranceProducts.containsKey(product)) {
                quantity += entranceProducts.get(product);
            }
            entranceProducts.put(product, quantity);
        }
        for (Arc arc : exits) {
            arc.pushExitProducts(exitProductsFromArc(arc));
        }
    }

    public Map<String, Float> exitProductsFromArc(Arc arc) {
        Map<String, Float> exit = new HashMap<>();
        if (entranceProducts == null) {
            return exit;
        }
        Set<String> listKeys = entranceProducts.keySet();
        Iterator<String> iterator = listKeys.iterator();
        while (iterator.hasNext()) {
            String productEntry = iterator.next();
            float quantityEntry = entranceProducts.get(productEntry);
            if ((matrix.containsKey(productEntry)) && (matrix.get(productEntry)).containsKey(arc)) {
                Map<String, Float> m = matrix.get(productEntry).get(arc);
                Set<String> keys = m.keySet();
                Iterator<String> it = keys.iterator();
                while (it.hasNext()) {
                    String productExit = it.next();
                    Float quantityExit = quantityEntry * m.get(productExit) / 100;
                    if (exit.containsKey(productExit)) {
                        quantityExit += exit.get(productExit);
                    }
                    exit.put(productExit, quantityExit);
                }
            }
        }
        return exit;
    }
    
    public List<Arc> getArcs(){
	return exits;
    }
}
