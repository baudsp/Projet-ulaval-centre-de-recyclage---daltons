package recyclapp.model;

public class SortieUsine extends Element {

    public SortieUsine(int x, int y, int width, int height) {
        super(new Coordinate(x, y), 1,0, width, height);
        nbExits = 0;
        exits = new Arc[nbExits];
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
	return 2;
    }
}
