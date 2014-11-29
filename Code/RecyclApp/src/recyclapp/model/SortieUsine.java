package recyclapp.model;

public class SortieUsine extends Element {

    public SortieUsine() {
        super();
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
}
