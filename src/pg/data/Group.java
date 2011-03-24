package pg.data;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 * a class to handle group of segment in projective geometry
 * @author Cedric Telegone, ECN 2010
 *
 */
public class Group implements Drawable{

	protected List<SegmentPG> segments;
	protected Point centroid;
	protected Color color;


	/**
	 *
	 */
	public Group(){
		segments=new ArrayList<SegmentPG>();
	}

	/**
	 * set the centroid of this group
	 * @param p the centroid
	 */

	public void setCentroid(Point p){
		centroid=p;
	}

	/**
	 * add a segment to the group
	 * @param s a segment
	 */
	public void add(SegmentPG s){
		segments.add(s);
		}

	/**
	 * get the segment list of the group
	 * @return a list of SegmentPG
	 */
	public List<SegmentPG> getSeg(){

		return segments;
	}

	/**
	 * set the display color for the group
	 * @param c a color
	 */
	public void setColor(Color c){
		color=c;
	}

	@Override
	public void paint(Graphics g,double mag) {
		// TODO Auto-generated method stub

		if(centroid!=null){
		Point origine=new Point(0,0);
		SegmentPG s=new SegmentPG(origine,centroid);

		//System.out.println(color.toString());
		g.setColor(color);
		double d=centroid.distance(origine);
		double alpha=Math.atan(centroid.getY()/centroid.getX());
		double x=Math.cos(alpha)*d-d;
		double y=Math.sin(alpha)*d-d;


		g.drawOval((int)x,(int)y, (int)(2*d), (int)(2*d));


		}


		g.setColor(color);

		for(int i=0;i<segments.size();i++){

			segments.get(i).drawable();
			segments.get(i).paint(g,mag);
		}



		//g.setColor(Color.black);
	}

	/**
	 * Remove segments in a rectangle specified by one of its diagonals
	 * @param a first Point of the diagonal
	 * @param b second Point of the diagonal
	 */
	public void removeSegments(Point a,Point b) {
		// TODO Auto-generated method stub
		Boolean finished=false;
		double xa=a.getX();
		double xb=b.getX();
		double ya=a.getY();
		double yb=b.getY();
		double temp=0;
		if(xb>xa&&yb>ya){

		}else
		{
			if(xb<xa)
			{
				temp=xa;
				xa=xb;
				xb=temp;
			}

			if(yb<ya)
			{
			temp=ya;
			ya=yb;
			yb=temp;
			}

		}

		while(!finished){
		for(int i=0;i<segments.size();i++){
			if(i==segments.size()-1)
				finished=true;


			if((segments.get(i).getP1().getX()>xa&&segments.get(i).getP1().getX()<xb&&segments.get(i).getP1().getY()>ya&&segments.get(i).getP1().getY()<yb)||(segments.get(i).getP2().getX()>xa&&segments.get(i).getP2().getX()<xb&&segments.get(i).getP2().getY()>ya&&segments.get(i).getP2().getY()<yb)){

				segments.remove(i);
				break;

			}


		}


		}

	}




}
