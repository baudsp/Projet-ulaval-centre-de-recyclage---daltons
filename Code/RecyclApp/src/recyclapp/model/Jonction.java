package recyclapp.model;

public class Jonction extends Element {
    
    public Jonction(int x, int y, int width, int height) {
        super(new Coordinate(x, y),1,1, width, height);
    }

    @Override
    public int getType() {
	return 3;
    }
}
