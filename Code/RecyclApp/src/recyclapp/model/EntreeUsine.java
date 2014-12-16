package recyclapp.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import recyclapp.view.InterfaceOutils;

public class EntreeUsine extends Element {

    public EntreeUsine(int x, int y, int width, int height) {
        super(new Coordinate(x, y), 0, 1, width, height);
        entranceProducts = new LinkedHashMap<>();
	
	name = "Entree " + this.id;
	
	//Pour le livrable 3, on code ce truc en dur
	entranceProducts.put("Produit 1", 1000f);
	entranceProducts.put("Produit 2", 1000f);
	
	transformeEntranceProductToMatrix();
    }

    public void addEntranceProduct(String product, float quantity) {
        if (!entranceProducts.containsKey(product)) {
            entranceProducts.put(product, quantity);
        }
    }

    public void removeEntranceProduct(String product) {
        if (entranceProducts.containsKey(product)) {
            entranceProducts.remove(product);
        }
    }

    @Override
    public boolean isPossibleToAddEntrance() {
        return false;
    }

    @Override
    public int getType() {
	return InterfaceOutils.ID_TOOL_ENTREE;
    }
    
    private void transformeEntranceProductToMatrix() {
	Iterator<String> iteratorProduct = entranceProducts.keySet().iterator();
	
	while (iteratorProduct.hasNext()) {
	    
	    String product = iteratorProduct.next();
	    
	    Map <Integer, Map<String, Float>> mapEntrances =  new LinkedHashMap<>();
	    
	    Map<String, Float> mapProduits = new LinkedHashMap<>();
	    mapProduits.put(product, 100f);
	    
	    mapEntrances.put(0, mapProduits);
	    	    
	    matrix.put(product, mapEntrances);
	}
    }
    
    @Override
    public Map<String, Float> exitProducts() {
	return this.entranceProducts;
    }
    
    @Override
    public Element clone() {
        Element elt =  new EntreeUsine(this.coordinate.getX(), this.coordinate.getY(), this.width, this.height);
        
        elt = this.helpClone(elt);
        
        return elt;
    }

    void pushExitProducts() {
	for (int exitNumber = 0; exitNumber < nbExits; exitNumber++) {
	    if (exits[exitNumber] != null) {
		Map<String, Float> productFromExit = exitProductsFromArc(exitNumber);
		exits[exitNumber].pushExitProducts(productFromExit);
	    }
	}
    }
}
