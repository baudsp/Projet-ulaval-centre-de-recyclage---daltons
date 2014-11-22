package recyclapp.model;

public class Arc extends Component {
    
    private Coordinate exit = null;
    private Coordinate entrance = null;
    
    
    public Arc (Coordinate exit) {
	this.exit = exit;
    }
    
    public void setEntrance(Coordinate entrance) {
	this.entrance = entrance;
    }
    
    public boolean getStatus() {
	return (entrance != null);
    }
    
    /**
     * @return the exit
     */
    public Coordinate getExit() {
	return exit;
    }

    /**
     * @return the entrance
     */
    public Coordinate getEntrance() {
	return entrance;
    }
    
    
}
