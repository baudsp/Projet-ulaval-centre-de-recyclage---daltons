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
    protected int nbEntrances;
    protected int nbExits;
    protected Arc[] exits;
    protected List<Arc> entrances;
    protected Map<String, Map<Integer, Map<String, Float>>> matrix;

    public Element(Coordinate coordinate, int nbEntrances, int nbExits, int width, int height) {
	this.coordinate = coordinate;
        this.nbEntrances = nbEntrances;
        this.nbExits = nbExits;
        exits = new Arc[nbExits];
        entranceProducts = new HashMap<>();
        matrix = new HashMap<>();
        entrances = new LinkedList<>();
	this.width = width;
	this.height = height;
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
                return i;
            }
        }
        return exit;
    }

    public void addExit(Arc arc) {
        for (int i = 0; i < exits.length; i++) {
            if (exits[i] == null) {
                exits[i] = arc;
            }
        }
    }

    public boolean isPossibleToAddEntrance() {
        return true;
    }

    public boolean isPossibleToAddExit() {
        for (Arc exit : exits) {
            if (exit == null) {
                return true;
            }
        }
        return false;
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

    public float getActualFlow() {
        float flow = 0;
        Set<String> listProducts = entranceProducts.keySet();
        Iterator<String> listProductsIterator = listProducts.iterator();
        while (listProductsIterator.hasNext()) {
            String entranceProduct = listProductsIterator.next();
            flow += entranceProducts.get(entranceProduct);
        }
        return flow;
    }

    public Map<String, Float> exitProducts() {
        Map<String, Float> exitProducts = new HashMap<>();
        Map<String, Float> exitProductsFromArc;
        for (int i = 0; i < exits.length; i++) {
            exitProductsFromArc = exitProductsFromArc(i);
            Set<String> listProducts = exitProductsFromArc.keySet();
            Iterator<String> listProductsIterator = listProducts.iterator();
            while (listProductsIterator.hasNext()) {
                String entranceProduct = listProductsIterator.next();
                float entranceQuantity = entranceProducts.get(entranceProduct);
                if (exitProducts.containsKey(entranceProduct)) {
                    entranceQuantity += exitProducts.get(entranceProduct);
                }
                exitProducts.put(entranceProduct, entranceQuantity);
            }
        }
        return exitProducts;
    }

    // Récupère le dictionnaire de données Produit/Quantité pour un Arc en sortie (numéro de sortie)
    public Map<String, Float> exitProductsFromArc(int exitNumber) {
        Map<String, Float> result = new HashMap<>();
        // Verifie la Map des produits en entree est instanciee
        if (entranceProducts != null) {
            Set<String> listProducts = entranceProducts.keySet();
            Iterator<String> listProductsIterator = listProducts.iterator();
            //Pour chaque produit en entree de l'element
            while (listProductsIterator.hasNext()) {
                String entranceProduct = listProductsIterator.next();
                float entranceQuantity = entranceProducts.get(entranceProduct);
                // Si Produit en entree present dans la matrice && Si Arc est une sortie de la transformation du produit
                if ((matrix.containsKey(entranceProduct)) && (matrix.get(entranceProduct)).containsKey(exitNumber)) {
                    Map<String, Float> currentArc = matrix.get(entranceProduct).get(exitNumber);
                    Set<String> exitProducts = currentArc.keySet();
                    Iterator<String> exitProductIterator = exitProducts.iterator();
                    // Si le produit en sortie est deja dans la matrice, on ajoute la quantite produite par le produit en entree
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
        }
        return result;
    }

    public boolean isValid() {
        if (getActualFlow() > maxFlow) {
            return false;
        }
        for (Arc exit : exits) {
            if (exit == null) {
                return false;
            }
        }
        return true;
    }

    public Arc[] getArcs() {
        return exits;
    }

    public void addEntrance(Arc entrance) {
        entrances.add(entrance);
    }

    public void removeEntrance(Arc entrance) {
        if (entrances.contains(entrance)) {
            entrances.remove(entrance);
        }
    }

    public abstract int getType();
}
