package recyclapp.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Station {
    private int nbExits;
    private String name;
    private String description;
    private List<Arc> exits;
    private Map<Product, Map<Product, Float>> matrix;
    
    public Station(int nbExits) {
        this.nbExits = nbExits;
        this.exits = new LinkedList<>();
        this.matrix = new HashMap<>();
    }
    
    public void addExit(Arc arc) {
        if (exits.size() < nbExits) {
            exits.add(arc);
        }
    }
    
    public void removeExit(Arc arc) {
        if (exits.contains(arc)) {
            exits.remove(arc);
        }
    }
    
    public void setDescription(String descr) {
        description = descr;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setMatrix(HashMap<Product, Map<Product, Float>> matrix) {
        this.matrix = matrix;
    }
    
    public Station clone() {
        Station station = new Station(nbExits);
        station.name = name;
        station.description = description;
        for (Arc arc : exits) {
            station.addExit(arc);
        }
        return station;
    }
    
    public String toString() {
        String infos = "Station : " + name + "\n";
        infos += "Nombre de sorties : " + nbExits + "\n";
        infos += description + "\n";
        if (exits.size() >= 0) {
            infos += "Sorties : \n";
            for (Arc arc : exits) {
                infos += arc.toString();
            }
        }
        if (matrix.size() >0) {
        }
                
        return infos;
    }
}
