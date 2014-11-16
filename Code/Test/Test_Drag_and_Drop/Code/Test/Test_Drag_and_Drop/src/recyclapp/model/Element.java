package recyclapp.model;

import java.awt.Image;

public class Element extends Component{
    
    public int id;
        public int x, y;
        public int width, height;
        public Image image;

        public Element(int id, int x, int y, int width, int height, Image image) {
            this.id = id;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.image = image;
        }
	
    public void move() {
        
    }
    
    public void validate() {
        
    }
    
    public void setSize(int height, int width) {
        
    }
}
