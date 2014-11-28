package recyclapp.model;

import java.util.LinkedList;

public class Plan {

    private LinkedList<Element> listElements;
    private DataElement tempDataElement = null;

    public class DataElement {

        public int type;
        public int x;
        public int y;
        public int width;
        public int height;
        public Element element;

        public DataElement(int type, int x, int y, int width, int height, Element element) {
            this.type = type;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.element = element;
        }

        public DataElement() {
            this.type = -1;
            this.x = 0;
            this.y = 0;
            this.width = 0;
            this.height = 0;
        }
    };

    public Plan() {
        listElements = new LinkedList<>();
    }

    public void createArcExit(int x, int y) {
        DataElement dataElement = findDataElement(x, y, 1);
        if (dataElement.element != null) {
            tempDataElement = dataElement;
        }
    }

    public boolean createArcEntrance(int x, int y) {
        boolean found = false;
        DataElement dataElement = findDataElement(x, y, 1);
        if ((dataElement.element != null) && (tempDataElement.element.getFirstFreeExit() >= 0)) {
            tempDataElement.element.addExit(tempDataElement.element.getFirstFreeExit(), new Arc(findDataElement(x, y, 1).element));
            tempDataElement = null;
            found = true;
        }

        return found;
    }

    public void createElement(int type, int x, int y) {
        listElements.add(new Station(type, x, y, 70, 70));
    }

    public LinkedList<DataElement> getListDataElements() {
        LinkedList<DataElement> listDataElements = new LinkedList<DataElement>();
        for (Element elt : listElements) {
            listDataElements.add(new DataElement(elt.type, elt.coordinate.getX(), elt.coordinate.getY(), elt.width, elt.height, elt));
        }
        return listDataElements;
    }

    public DataElement findDataElement(int x, int y, float zoom) {
        for (Element elt : listElements) {
            if (x >= elt.coordinate.getX() * zoom && x <= (elt.coordinate.getX() + elt.width) * zoom && y >= elt.coordinate.getY() * zoom && y <= (elt.coordinate.getY() + elt.height) * zoom) {

                return new DataElement(elt.type, elt.coordinate.getX(), elt.coordinate.getY(), elt.width, elt.height, elt);
            }
        }

        return new DataElement();
    }

    public void moveElement(DataElement dataElement, int x, int y) {
        for (Element elt : listElements) {
            if (dataElement.x >= elt.coordinate.getX() && dataElement.x <= elt.coordinate.getX() + elt.width
                    && dataElement.y >= elt.coordinate.getY() && dataElement.y <= elt.coordinate.getY() + elt.height) {
                elt.setCoordinate(new Coordinate(x, y));
            }
        }
    }

    public boolean isDrawingArc() {
        return (tempDataElement != null);
    }
}
