package pg.data;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 * A class to handle polygon as a list of points
 * @author Cedric Telegone, ECN 2010
 *
 */
public class Polygon implements Drawable{

	protected List<Point> points;


	/**
	 *
	 */public Polygon(){
		points=new ArrayList<Point>();
	}

	/**
	 * add a point to the current polygon
	 * @param p a point
	 */
	 public void add(Point p){
		points.add(p);
	}


	/**
	 * tell if the polygon is allowed to be drawn
	 * @return
	 */
	 public boolean drawable(){
		boolean result=false;
		if(points.size()>0){
			result=true;
			for(int i=0;i<points.size();i++){
				if(!points.get(i).drawable)
					result=false;

			}

		}

		return result;
	}

	@Override
	public void paint(Graphics g,double mag) {
		// TODO Auto-generated method stub

		if(drawable()){
			int s=points.size();
			int[] xs=new int[s];
			int[] ys=new int[s];
			for(int i=0;i<s;i++){
				xs[i]=(int)((points.get(i).toPixel().getX())*mag);

				ys[i]=(int)((points.get(i).toPixel().getY())*mag);
			}


			g.setColor(new Color(0.5f,0.2f,0.3f,0.5f));
			g.fillPolygon(xs,ys,s);
			g.setColor(Color.black);
			g.drawPolygon(xs,ys,s);


		}





	}

}
