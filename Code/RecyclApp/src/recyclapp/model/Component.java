package recyclapp.model;

import java.util.Map;

public abstract class Component {

    private float maxFlow;
    protected Map<String, Float> entranceProducts;

    public float getMaxFlow() {
        return maxFlow;
    }

    public void setMaxFlow(float maxFlow) {
        this.maxFlow = maxFlow;
    }
}
