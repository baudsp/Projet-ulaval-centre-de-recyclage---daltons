
package recyclapp.model;

import java.util.LinkedList;


public class Plan {
    
    private LinkedList<Element> elements;
    
    public Plan(){
        elements = new LinkedList<>();
    }

    public LinkedList<Element> getElements() {
        return elements;
    }

    public void setElements(LinkedList<Element> elements) {
        this.elements = elements;
    }
    
}
