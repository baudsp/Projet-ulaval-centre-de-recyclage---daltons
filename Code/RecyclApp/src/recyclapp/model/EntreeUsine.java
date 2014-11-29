package recyclapp.model;

import java.util.HashMap;

public class EntreeUsine extends Element {

    public EntreeUsine() {
        super();
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
}
