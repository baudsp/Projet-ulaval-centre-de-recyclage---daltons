package recyclapp.model;

import recyclapp.view.InterfaceOutils;

public class SortieUsine extends Element {

    public SortieUsine(int x, int y, int width, int height) {
        super(new Coordinate(x, y), 1, 0, width, height);
        exits = new Arc[nbExits];
	name = "Sortie " + this.id;
    }

    @Override
    public boolean isPossibleToAddEntrance() {
        return entrances.isEmpty();
    }

    @Override
    public boolean isPossibleToAddExit() {
        return false;
    }

    @Override
    public int getType() {
	return InterfaceOutils.ID_TOOL_SORTIE;
    }

    @Override
    public Element clone() {
        Element elt = new SortieUsine(this.coordinate.getX(), this.coordinate.getY(), this.width, this.height);
                 
        elt = this.helpClone(elt);
        
        return elt;
    }
    
    @Override
    public boolean getIsMatrixValid() {
        return true;
    }
}
