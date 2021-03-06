package recyclapp.model;

import java.util.Map;
import recyclapp.view.InterfaceOutils;

public class Jonction extends Element {

    public Jonction(int x, int y, int width, int height) {
	super(new Coordinate(x, y), 2, 1, width, height);
	name = "Jonction " + this.id;
    }

    @Override
    public int getType() {
	return InterfaceOutils.ID_TOOL_JONCTION;
    }

    @Override
    public Element clone() {
	Element elt = new Jonction(this.coordinate.getX(), this.coordinate.getY(), this.width, this.height);

	elt = this.helpClone(elt);

	return elt;
    }

    @Override
    public int getFirstFreeEntrance() {
	int entrance = super.getFirstFreeEntrance();

	// il n'y a plus d'netrée de libre, on en rajoute
	if (entrance < 0) {
	    this.nbEntrances++;
	}

	return this.nbEntrances;
    }

    @Override
    public Map<String, Float> exitProductsFromArc(int exitNumber) {
	
	return entranceProducts;
    }
}
