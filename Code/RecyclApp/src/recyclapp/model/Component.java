package recyclapp.model;

import java.util.Map;

public abstract class Component {
    private float maxFlow;
    protected Map<String, Float> entranceProducts;
   
    public void updateValues() {}
}
