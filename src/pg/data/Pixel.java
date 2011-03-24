package pg.data;
import java.awt.Component;
import java.awt.Graphics;

import Jama.Matrix;

/**
 * A class to handle pixel in projective geometry
 * @author Cedric Telegone, ECN 2010
 *
 */

public class Pixel implements Drawable{
	protected int x;
	protected int y;

	/**
	 * create a pixel from his x and y coordinates
	 * @param x - x coordinate
	 * @param y - y coordinate
	 */
	public Pixel(int x, int y){
		//construction de la représentation homogène d'un point à partir de sa représentation inhomogène
		this.x=x;
		this.y=y;
	}


	/**
	 * get an homogeneous vector from the pixel coordinates
	 *
	 */
	public Vector getVec(){
		return new Vector((double)x,(double)y,1);
	}

	/**
	 * get x coordinate
	 *
	 */
	public int getX(){
		return x;
	}

	/**
	 * get y coordinate
	 *
	 */
	public int getY(){
		return y;
	}

	/**
	 * Transform a pixel into Point
	 * @return the transformed Point
	 */
	public Point toPoint(){
		return new Point(this.getVec());

	}

	/**
	 * @param a Graphics
	 * @param mag zoom level
	 */

	public void paint(Graphics g,double mag){
		//System.out.println("x="+x+"y="+y);
		g.drawLine((int)(x*mag),(int)((y-2)*mag),(int)(x*mag),(int)((y+2)*mag));
		g.drawLine((int)((x+2)*mag),(int)(y*mag),(int)((x-2)*mag),(int)(y*mag));

	}





}
