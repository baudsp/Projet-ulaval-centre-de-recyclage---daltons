package recyclapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;
import recyclapp.view.InterfaceOutils;

public class Plan implements Serializable, ParamObserver {

    private LinkedList<Element> listElements;
    private DataElement tempDataElement = null;
    private ChangeManager changeManager = new ChangeManager();

    /**
     * @return the tempDataElement
     */
    public DataElement getTempDataElement() {
	return tempDataElement;
    }

    public Plan() {
        listElements = new LinkedList<>();
    }
    
    public void calc() {
	resetEntranceValues();
	
	List<EntreeUsine> listEntreeUsine = findEntreesUsine();
		
	if(!listEntreeUsine.isEmpty()) {
	    for (EntreeUsine entreeUsine : listEntreeUsine) {
		entreeUsine.pushExitProducts();
	    }
	}
    }
    
    /**
     * Reset the entranceValues of all elements of the plan
     */
    private void resetEntranceValues() {
	List<EntreeUsine> listEntreeUsine = new ArrayList<>();
	
	for (Element element : listElements) {
	    if (!element.getClass().equals(EntreeUsine.class)) {
		element.setEntranceProducts(new HashMap<String, Float>());
	    }
	}
	
    }
    
    public List<EntreeUsine> findEntreesUsine() {
	
	List<EntreeUsine> listEntreeUsine = new ArrayList<>();
	
	for (Element listElement : listElements) {
	    if (listElement.getClass().equals(EntreeUsine.class)) {
		listEntreeUsine.add((EntreeUsine) listElement);
	    }
	}
	
	return listEntreeUsine;
    }

    public DataElement createArcExit(int x, int y) {
        DataElement dataElement = findDataElement(x, y, 1);
        if (dataElement.element != null && (dataElement.element.getFirstFreeExit() >= 0)) {
            tempDataElement = dataElement;
        } else {
            String message;

            if (dataElement.type == InterfaceOutils.ID_TOOL_SORTIE) {
                message = "Rien ne peut partir d'une sortie, tout doit y arriver.";
            } else {
                if (dataElement.type == InterfaceOutils.ID_TOOL_ENTREE) {
                    message = "Ajoutez une autre entrée d'usine.";
                } else {
                    message = "Plus de sorties disponibles pour l'élément. Augmentez ce nombre.";
                }
            }

            JOptionPane.showMessageDialog(null,
                    message,
                    "Erreur lors de l'ajout de l'arc",
                    JOptionPane.OK_OPTION,
                    null);
        }
        return getTempDataElement();
    }

    public boolean createArcEntrance(int x, int y) {
        boolean found = false;
        DataElement dataElement = findDataElement(x, y, 1);

        if (dataElement.element != null) {
            String message = "Erreur lors de l'ajout de l'arc.";
            if (dataElement.type != InterfaceOutils.ID_TOOL_SORTIE || getTempDataElement().type != InterfaceOutils.ID_TOOL_ENTREE) {
                if (dataElement.element.getFirstFreeEntrance() >= 0) {
                    if (!dataElement.element.equals(tempDataElement.element)) { // si les éléments sont différents on enregistre
                        getTempDataElement().element.addExit(getTempDataElement().element.getFirstFreeExit(), new Arc(findDataElement(x, y, 1).element, getTempDataElement().element));
                        dataElement.element.addEntrance();
                        tempDataElement = null;
                        found = true;
                    } else {
                        message = "Selectionnez un élément différent la deuxième fois.";
                    }
                } else {
                    if (dataElement.type == InterfaceOutils.ID_TOOL_ENTREE) {
                        message = "Une entrée doit être la base du système.";
                    } else {
                        message = "Plus d'entrées disponibles pour l'élément. Utilisez une jonction.";
                    }
                }
            } else {
                message = "Relier une entrée à une sortie directement n'a pas de sens";
            }
            if (!found) {
                JOptionPane.showMessageDialog(null,
                        message,
                        "Erreur lors de l'ajout de l'arc",
                        JOptionPane.OK_OPTION,
                        null);
            }
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
        return (getTempDataElement() != null);
    }

    public void removeElement(DataElement dataElement) {
        listElements.remove(dataElement.element);
        getChangeManager().addChange(listElements);
    }

    public void createElement(int type, int x, int y, int nbrSorties) {

        Element elt = null;

        switch (type) {
            case InterfaceOutils.ID_TOOL_STATION:
                elt = new Station(x, y, 70, 70, nbrSorties);
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

    @Override
    public void update(Element element) {
        getChangeManager().addChange(listElements);
    }
}
