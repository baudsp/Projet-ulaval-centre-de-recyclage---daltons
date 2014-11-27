package recyclapp.model;

import java.util.LinkedList;

public class Plan {

    private LinkedList<Element> elements;
    private DataElement tempDataElt = null;

    public class DataElement {

        public int id;
        public int x;
        public int y;
        public int width;
        public int height;
        public Element elt;

        public DataElement(int id, int x, int y, int width, int height, Element elt) {
            this.id = id;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.elt = elt;
        }

        public DataElement() {
            this.id = -1;
            this.x = 0;
            this.y = 0;
            this.width = 0;
            this.height = 0;
        }
    };

    public Plan() {
        elements = new LinkedList<>();
    }

    public void createArcExit(int x, int y) {
        DataElement de = findDataElement(x, y, 1);
        if (de.elt != null) {
            tempDataElt = de;
        }
    }

    public boolean createArcEntrance(int x, int y) {
        DataElement de = findDataElement(x, y, 1);
        if ((de.elt != null) && (tempDataElt.elt.getFirstFreeExit() >= 0)) {
            tempDataElt.elt.addExit(tempDataElt.elt.getFirstFreeExit(), new Arc(findDataElement(x, y, 1).elt));
            tempDataElt = null;
            return true;
        } else {
            return false;
        }
    }

    public void createElement(int id, int x, int y) {
        elements.add(new Station(id, x, y, 70, 70));
    }

    public LinkedList<DataElement> getListDataElements() {
        LinkedList<DataElement> dataElements = new LinkedList<DataElement>();
        for (Element e : elements) {
            dataElements.add(new DataElement(e.id, e.coordinate.getX(), e.coordinate.getY(), e.width, e.height, e));
        }
        return dataElements;
    }

    public DataElement findDataElement(int x, int y, float zoom) {
        for (Element e : elements) {
            if (x >= e.coordinate.getX() * zoom && x <= (e.coordinate.getX() + e.width) * zoom && y >= e.coordinate.getY() * zoom && y <= (e.coordinate.getY() + e.height) * zoom) {

                return new DataElement(e.id, e.coordinate.getX(), e.coordinate.getY(), e.width, e.height, e);
            }
        }

        return new DataElement();
    }

    public void moveElement(DataElement de, int x, int y) {
        for (Element e : elements) {
            if (de.x >= e.coordinate.getX() && de.x <= e.coordinate.getX() + e.width && de.y >= e.coordinate.getY()
                    && de.y <= e.coordinate.getY() + e.height) {
                e.setCoordinate(new Coordinate(x, y));
            }
        }

        System.out.print("Nb elements : " + elements.size() + "\n");
    }

    public boolean isDrawingArc() {
        return (tempDataElt != null);
    }
}
