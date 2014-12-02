package recyclapp.model;

import java.io.Serializable;
import java.util.Map;

public abstract class Component  implements Serializable{

    protected float maxFlow;
    protected Map<String, Float> entranceProducts;

    public float getMaxFlow() {
        return maxFlow;
    }

    public void setMaxFlow(float maxFlow) {
        this.maxFlow = maxFlow;
    }
}
