package recyclapp.model;

import java.util.HashMap;

public class EntreeUsine extends Element {

    public EntreeUsine(int x, int y, int width, int height) {
        super(new Coordinate(x, y), 0,1, width, height);
        nbEntrances = 0;
        entranceProducts = new HashMap<>();
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
	return 1;
    }
}
