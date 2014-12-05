package recyclapp.model;

import java.awt.Color;
import java.io.Serializable;
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
}
