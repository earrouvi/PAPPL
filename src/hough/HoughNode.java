package hough;
/* W. Burger, M. J. Burge: "Digitale Bildverarbeitung" 
 * © Springer-Verlag, 2005
 * www.imagingbook.com
*/

public class HoughNode implements Comparable {
	protected int angle;
	protected int radius;
	protected int count;
	
	HoughNode(int a, int r, int c){
		angle = a;
		radius = r;
		count = c;	//number of contributing image points
	}
	
	public int compareTo (Object o){
		HoughNode hn1 = this;
		HoughNode hn2 = (HoughNode) o;
		if (hn1.count > hn2.count)
			return -1;
		else if (hn1.count < hn2.count)
			return 1;
			else
				return 0;
	}

	public int getAngle() {
		return angle;
	}

	public void setAngle(int angle) {
		this.angle = angle;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}
}

