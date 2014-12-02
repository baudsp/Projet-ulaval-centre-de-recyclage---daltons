package recyclapp.model;

import java.io.Serializable;
import java.util.LinkedList;
import recyclapp.view.InterfaceOutils;

public class Plan implements Serializable{

    private LinkedList<Element> listElements;
    private DataElement tempDataElement = null;
    private ChangeManager changeManager = new ChangeManager();

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

    public LinkedList<DataElement> getListDataElements() {
	LinkedList<DataElement> listDataElements = new LinkedList<DataElement>();
	for (Element elt : listElements) {
	    listDataElements.add(new DataElement(elt));
	}
	return listDataElements;
    }

    public DataElement findDataElement(int x, int y, float zoom) {
	for (Element elt : listElements) {
	    if (x >= elt.coordinate.getX() * zoom && x <= (elt.coordinate.getX() + elt.width) * zoom && y >= elt.coordinate.getY() * zoom && y <= (elt.coordinate.getY() + elt.height) * zoom) {

		return new DataElement(elt);
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

    public void removeElement(DataElement dataElement) {
	listElements.remove(dataElement.element);
	getChangeManager().addChange(listElements);
    }

    public void createElement(int type, int x, int y) {

	Element elt = null;

	switch (type) {
	    case InterfaceOutils.ID_TOOL_STATION:
		elt = new Station( x, y, 70, 70);
		break;
	    case InterfaceOutils.ID_TOOL_ENTREE:
		elt = new EntreeUsine(x, y, 70, 70);
		break;
	    case InterfaceOutils.ID_TOOL_SORTIE:
		elt = new SortieUsine(x, y, 70, 70);
		break;
	    case InterfaceOutils.ID_TOOL_JONCTION:
		elt = new Jonction(x, y, 70, 70);
		break;
	    default:
		throw new RuntimeException("Mauvais type d'element");
	}

	listElements.add(elt);
	getChangeManager().addChange(listElements);
    }

    public void resetElements(LinkedList<Element> elements) {
	listElements = elements;
    }

    public ChangeManager getChangeManager() {
	return changeManager;
    }
}
