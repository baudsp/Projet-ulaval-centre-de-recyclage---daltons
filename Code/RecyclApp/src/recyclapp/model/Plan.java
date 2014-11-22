
package recyclapp.model;

import java.awt.Point;
import java.util.Map;
import java.util.Hashtable;
import java.util.LinkedList;


public class Plan {
    
    private LinkedList<Element> elements;
    
    public class DataElement
    {
        public int id;
        public int x;
        public int y;
        
        public DataElement(int id,int x,int y){
            this.id = id;
            this.x = x;
            this.y = y;
        }
    };
    
    public Plan(){
        elements = new LinkedList<>();
    }
    
    public void newElement(int id,int x, int y)
    {
        //System.out.print("create => "+x+" ; "+y+"\n");
        elements.add(new Station(id,x,y,70,70));
    }
    
    // fonction de retour des coordonnées des elements dans la liste.
    // L'appel de fonction de cette fonction ce fais dans la facade du paquage interface usager.
    public LinkedList<DataElement> getPositionElement()
    {
        LinkedList<DataElement> dataElements = new LinkedList<DataElement>();
        for(int i=0;i<elements.size();i++)
        {
            System.out.print(" => id : "+elements.get(i).id+" "+elements.get(i).coordinate.getX()+" ; "+elements.get(i).coordinate.getY()+"\n");
            dataElements.add(new DataElement(elements.get(i).id,elements.get(i).coordinate.getX(),elements.get(i).coordinate.getY()));
        }
        System.out.print("\n");
        return dataElements;
    }
    
}
