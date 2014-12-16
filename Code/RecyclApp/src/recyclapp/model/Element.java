package recyclapp.model;

import java.awt.Image;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public abstract class Element extends Component {

    protected int id;
    protected int width, height;
    protected Coordinate coordinate;
    protected Image image;
    protected int nbEntrances;
    protected int nbEntrancesUsed;
    protected int nbExits;
    protected Arc[] exits;
    protected List<Arc> entrances;
    protected Map<String, Map<Integer, Map<String, Float>>> matrix;
    protected String name;
    protected String description;

    public Element(Coordinate coordinate, int nbEntrances, int nbExits, int width, int height) {
	this.coordinate = coordinate;
	this.nbEntrances = nbEntrances;
	this.nbEntrancesUsed = 0;
	this.nbExits = nbExits;
	exits = new Arc[nbExits];
	entranceProducts = new LinkedHashMap<>();
	matrix = new HashMap<>();
	entrances = new LinkedList<>();
	this.width = width;
	this.height = height;

	this.id = new Random().nextInt(40);
    }

    public Coordinate getCoordinate() {
	return coordinate;
    }

    public void setNbExits(int nbExits) {
	Arc[] newExits = new Arc[nbExits];
	for (int i = 0; (i < this.nbExits) && (i < nbExits); i++) {
	    newExits[i] = exits[i];
	}
	this.nbExits = nbExits;
	exits = newExits;
    }

    public int getNbExits() {
	return nbExits;
    }

    public int getNbEntranceUsed() {
	return nbEntrancesUsed;
    }

    public int getNbEntrances() {
	return nbEntrances;
    }

    public void setNbEntrance(int nbEntrance) {
	this.nbEntrances = nbEntrance;
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

    public int getFirstFreeEntrance() {
	int entrance = -1;

	for (int i = nbEntrancesUsed; i < nbEntrances; i++) {
	    entrance = i;
	    break;
	}
	return entrance;
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

    public void addArcToExit(int place, Arc arc) {
	exits[place] = arc;
    }

    public void addEntrance() {
	nbEntrancesUsed++;
    }

    public void setMatrix(Map<String, Map<Integer, Map<String, Float>>> matrix) {
	this.matrix = matrix;
    }

    public Map<String, Map<Integer, Map<String, Float>>> getMatrix() {
	return matrix;
    }

    public void pushExitProducts(Map<String, Float> entrance) {
	
	if (!this.getClass().equals(Jonction.class)) {
	    entranceProducts = new LinkedHashMap<>();
	}
	
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
		Map<String, Float> productFromExit = exitProductsFromArc(exitNumber);
		exits[exitNumber].pushExitProducts(productFromExit);
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

    public boolean equals(Element element) {
	return (element.coordinate.getX() == this.coordinate.getX()
		&& element.coordinate.getY() == this.coordinate.getY());
    }

    /*
     * Sauvegarde dans la matrix de tri de la station l'input
     */
    public void setMatrix(LinkedList<String> inputs) {
	Map<String, Map<Integer, Map<String, Float>>> matrixTri = new HashMap<>();

	Map<Integer, Map<String, Float>> matrixExits = new HashMap<>();

	String product = "";

	for (String input : inputs) {
	    String[] splitResult = input.split(":::");

	    int numSortie = Integer.valueOf(splitResult[0]);

	    // Si on change de produit, on recrée une nouvelle matrice 
	    if (!splitResult[1].equals(product)) {
		matrixExits = new HashMap<>();
		product = splitResult[1];
	    }

	    float value = Float.valueOf(splitResult[3]);

	    Map<String, Float> productToExit = new HashMap<>();

	    productToExit.put(product, value);

	    matrixExits.put(numSortie, productToExit);
	    matrixTri.put(product, matrixExits);
	}
	this.matrix = matrixTri;
    }

    @Override
    public abstract Element clone();

    protected Element helpClone(Element elt) {
	elt.coordinate = this.coordinate;
	elt.description = this.description;
	elt.setEntranceProducts(this.entranceProducts);
	elt.entrances = this.entrances;
	elt.exits = this.exits;
	elt.height = this.height;
	elt.id = this.id;
	elt.image = this.image;
	elt.matrix = this.matrix;
	elt.maxFlow = this.maxFlow;
	elt.name = this.name;
	elt.nbEntrances = this.nbEntrances;
	elt.nbEntrancesUsed = this.nbEntrancesUsed;
	elt.nbExits = this.nbExits;
	elt.width = this.width;

	return elt;
    }
}
