package Scissors.GUI;

import java.awt.Color;
import java.awt.Point;
import java.util.ListIterator;
import java.util.Vector;
/**
 * This class extends Vector in order to treat with markers as vectors
 * @author CAO
 *
 */
public class ScissorMarkerVector extends Vector{
    private int type;
    private Color color;
    /** Creates a new instance of MarkerVector */
    public ScissorMarkerVector(int type) {
        super();
        this.type=type;
        color = createColor(type);
        //scissor.setCurrentTpye(type);
    }
    /**
     * Add markers in the vector list
     * @param marker ScissorMarker
     */
    public void addMarker(ScissorMarker marker){
        add(marker);
    }
 
    /**
     * Get the index of a vector
     * @param marker ScissorMarker
     * @return  an integer of marker index
     */
    public int getVectorIndex(ScissorMarker marker){
        return indexOf(marker);
    }

    /**
     * Set color of each object type 
     * @param typeID  object type
     * @return  color
     */
     private Color createColor(int typeID){
        switch(typeID){
            case(1):
                return Color.blue;
            case(2):
                return Color.cyan;
            case(3):
                return Color.green;
            case(4):
                return Color.yellow;
            case(5):
                return Color.orange;
            default:
                Color c = new Color((int)(255*Math.random()),(int)(255*Math.random()),(int)(255*Math.random()));
                while(c.equals(Color.blue) | 
                        c.equals(Color.cyan) | 
                        c.equals(Color.green) | 
                        c.equals(Color.orange) | 
                        c.equals(Color.yellow)){
                    c = new Color((int)(255*Math.random()),(int)(255*Math.random()),(int)(255*Math.random()));
                }
                return c;
        }
    }

    /**
     * Identifier the point by the coordinate
     */
    public ScissorMarker getMarkerFromPosition(Point p, int sliceIndex){
        Vector v = new Vector();
        ListIterator it = 
        	this.listIterator();
        while(it.hasNext()){
            ScissorMarker m = (ScissorMarker)it.next();
            if (m.getZ()==sliceIndex){
                v.add(m);
            }
        }
        ScissorMarker currentsmallest = (ScissorMarker)v.get(0);
        for (int i=1; i<v.size(); i++){
            ScissorMarker m2 = (ScissorMarker)v.get(i);
            Point p1 = new Point(currentsmallest.getX(),currentsmallest.getY());
            Point p2 = new Point(m2.getX(),m2.getY());
            boolean closer = Math.abs(p1.distance(p)) > Math.abs(p2.distance(p));
            if (closer){
                currentsmallest=m2;
            }
        }
                
        return currentsmallest;
    }
    /**
     * Get object type
     * @return type
     */
    public int getType() {
        return type;
    }
    /**
     * Set object type
     * @param type  object type
     */
    public void setType(int type) {
        this.type = type;
    }
    /**
     * Get the color of a point
     * @return  color
     */
    public Color getColor() {
        return color;
    }
    /**
     * Set the color of a point
     * @param color  point color
     */
    public void setColor(Color color) {
        this.color = color;
    }

}

