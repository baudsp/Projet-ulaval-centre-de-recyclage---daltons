package recyclapp.model;

public class DataElement {

    public int type;
    public int x;
    public int y;
    public int width;
    public int height;
    public Element element;

    public DataElement(Element elt) {
        this.type = elt.getType();
        this.x = elt.coordinate.getX();
        this.y = elt.coordinate.getY();
        this.width = elt.width;
        this.height = elt.height;
        this.element = elt;
    }

    public DataElement() {
        this.type = -1;
        this.x = 0;
        this.y = 0;
        this.width = 0;
        this.height = 0;
    }
}
