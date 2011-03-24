package Scissors.GUI;
/**
 * Generate scissormarker when we click on the image canvas
 * @author CAO
 *
 */
public class ScissorMarker {
    private int x;
    private int y;
    private int z;
    
    /**
     * Constructor of marker, creates a new instance of Marker
     */
    public ScissorMarker() {
    }
    /**
     * Contructor of Scissormarker
     * @param x   X-coordinate of a point
     * @param y   Y-coordinate of a point
     * @param z   Z-coordinate of a point
     */
    public ScissorMarker(int x, int y, int z) {
        this.x=x;
        this.y=y;
        this.z=z;
    }
    /**
     * Get x-coordinate of a point
     * @return x-coordinate
     */
    public int getX() {
        return x;
    }
    /**
     * Set the x-coordinate of a point
     * @param x  an integer of x-coordinate
     */
    public void setX(int x) {
        this.x = x;
    }
    /**
     * Get y-coordinate of a point
     * @return y an integer of y-coordinate
     */
    public int getY() {
        return y;
    }
    /**
     * Set the y-coordinate of a point
     * @param y  an integer of y-coordinate
     */
    public void setY(int y) {
        this.y = y;
    }
    /**
     * Get z-coordinate of a point
     * @return z an integer of z-coordinate
     */
    public int getZ() {
        return z;
    }
    /**
     * Set the z-coordinate of a point
     * @param z  an integer of z-coordinate
     */
    public void setZ(int z) {
        this.z = z;
    }

}

