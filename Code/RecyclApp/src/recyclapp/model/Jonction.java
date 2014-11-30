package recyclapp.model;

import recyclapp.view.InterfaceOutils;

public class Jonction extends Element {
    
    public Jonction(int x, int y, int width, int height) {
        super(new Coordinate(x, y),1,1, width, height);
	name = "Jonction " + this.id;
    }

    @Override
    public int getType() {
	return InterfaceOutils.ID_TOOL_JONCTION;
    }
}
