package recyclapp.model;

import java.awt.Color;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public abstract class Component  implements Serializable{

    protected float maxFlow;
    protected Map<String, Float> entranceProducts;
    private Color color = Color.BLACK;

    public float getMaxFlow() {
        return maxFlow;
    }

    public void setMaxFlow(float maxFlow) {
        this.maxFlow = maxFlow;
    }
    
    public float getActualFlow() {
        float res = 0f;
        Iterator<String> iteratorProducts = entranceProducts.keySet().iterator();
        while (iteratorProducts.hasNext()) {
            String product = iteratorProducts.next();
            res += entranceProducts.get(product);
        }
        return res;
    }

    /**
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(Color color) {
        this.color = color;
    }
    
    public Map<String, Float> getEntranceProducts() {
	return entranceProducts;
    }

    /**
     * @param entranceProducts the entranceProducts to set
     */
    public void setEntranceProducts(Map<String, Float> entranceProducts) {
	this.entranceProducts = entranceProducts;
    }
}
