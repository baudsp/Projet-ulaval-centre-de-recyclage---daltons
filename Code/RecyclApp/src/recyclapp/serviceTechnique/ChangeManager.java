package recyclapp.model;

import java.io.Serializable;
import java.util.LinkedList;

public class ChangeManager implements Serializable{

    private int currentIndex = 0;
    private LinkedList<LinkedList<Element>> lklkElement = new LinkedList<>();

    public void addChange(LinkedList<Element> elements) {
        LinkedList<Element> lkElement = new LinkedList<>();
        for (Element elt : elements) {
            lkElement.add(elt.clone());
        }
        
        if(currentIndex != lklkElement.size()){
            for (int i = currentIndex; i < lklkElement.size(); i++) {
                lklkElement.remove(i);
            }
        }
        lklkElement.add(currentIndex, lkElement);
        
        currentIndex = lklkElement.size();
    }

    public LinkedList<Element> undo() {
        moveLeft();
        return (LinkedList<Element>) lklkElement.get(currentIndex - 1).clone();
    }

    public LinkedList<Element> redo() {
        moveRight();
        return (LinkedList<Element>) lklkElement.get(currentIndex - 1).clone();
    }

    private void moveLeft() {
        if (currentIndex != 1) {
            currentIndex--;
        }
    }

    private void moveRight() {
        if (currentIndex != lklkElement.size()) {
            currentIndex++;
        }
    }
}
