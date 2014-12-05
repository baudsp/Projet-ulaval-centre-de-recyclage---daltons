package recyclapp.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import recyclapp.view.InterfaceOutils;

public class EntreeUsine extends Element {
    Map<String, Float> a;

    public EntreeUsine(int x, int y, int width, int height) {
        super(new Coordinate(x, y), 0, 1, width, height);
        a = new HashMap<>();
	
	name = "Entree " + this.id;
	
	//Pour le livrable 3, on code ce truc en dur
	a.put("Produit 1", 1000f);
	a.put("Produit 2", 1000f);
	
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
	Iterator<String> iteratorProduct = a.keySet().iterator();
	
	while (iteratorProduct.hasNext()) {
	    
	    String product = iteratorProduct.next();
	    
	    Map <Integer, Map<String, Float>> mapEntrances =  new HashMap<>();
	    
	    Map<String, Float> mapProduits = new HashMap<>();
	    mapProduits.put(product, 100f);
	    
	    mapEntrances.put(0, mapProduits);
	    	    
	    matrix.put(product, mapEntrances);
	}
    }
    
    @Override
    public Map<String, Float> exitProducts() {
	return this.entranceProducts;
    }
}
