package pg.data;
import java.awt.Color;
import java.awt.Graphics;

/**
 * A class to handle segments in projective geometry
 * @author Cedric Telegone, ECN 2010
 *
 */
public class SegmentPG implements Drawable{
	protected Point p1;
	protected Point p2;

	/**
	 * create a new SegmentPG from 2 points
	 * @param p1 - first point
	 * @param p2 - second point
	 */
	public SegmentPG(Point p1, Point p2){

		this.p1=p1;
		this.p2=p2;
	}

	@Override
	public void paint(Graphics g, double mag) {

		// TODO Auto-generated method stub
		if(p1.isDrawable()&&p2.isDrawable())
		g.drawLine((int)(p1.toPixel().getX()*mag), (int)(p1.toPixel().getY()*mag), (int)(p2.toPixel().getX()*mag), (int)(p2.toPixel().getY()*mag));


	}

	/**
	 * allow the segment to be drawn
	 */
	public void drawable(){
		p1.drawable();
		p2.drawable();
	}

	/**
	 * get the first segment point
	 * @return
	 */
	public Point getP1(){
		return p1;
	}

	/**
	 * get the second segment point
	 * @return
	 */
	public Point getP2(){
		return p2;
	}

	/**
	 *
	 * @param s
	 * @return
	 */

	public double distance(SegmentPG s){
		double d1=Math.min(p1.distance(s.getP1()),p1.distance(s.getP2()));
		double d2=Math.min(p2.distance(s.getP1()),p2.distance(s.getP2()));
		return Math.min(d1, d2);

	}

	/**
	 * display segment informations
	 */
	public void print(){

		p1.print();
		p2.print();
	}

}
