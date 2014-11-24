
package recyclapp.model;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
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
        public int width;
        public int height;
        
        public DataElement(int id,int x,int y,int width, int height){
            this.id = id;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
        
        public DataElement(){
            this.id = -1;
            this.x = 0;
            this.y = 0;
            this.width = 0;
            this.height = 0;
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
    
    public LinkedList<DataElement> getPositionElement()
    {
        LinkedList<DataElement> dataElements = new LinkedList<DataElement>();
        for(Element e:elements)
        {
            dataElements.add(new DataElement(e.id,e.coordinate.getX(),e.coordinate.getY(),e.width,e.height));
        }
        return dataElements;
    }
    
    public DataElement findDataElement(int x,int y)
    {
        
        for(Element e:elements){
            if (x >= e.coordinate.getX() && x <= e.coordinate.getX()+e.width && y >= e.coordinate.getY()  && y <= e.coordinate.getY() + e.height) {
                
                return new DataElement(e.id,e.coordinate.getX(),e.coordinate.getY(),e.width,e.height);
            }
        }
        
        return new DataElement();
    }
    
    public void remplacePositionElements(DataElement de,int x,int y)
    {
        for(Element e:elements){
            if (de.x >= e.coordinate.getX() && de.x <= e.coordinate.getX()+e.width && de.y >= e.coordinate.getY()  && de.y <= e.coordinate.getY() + e.height) {
                e.setCoordinate(new Coordinate(x,y));
            }
        }
        
        System.out.print("Nb elements : "+elements.size()+"\n");
    }
    
}
