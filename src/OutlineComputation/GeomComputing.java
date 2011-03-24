package OutlineComputation;

import java.awt.Point;
import java.util.ArrayList;

/**
 * Methods for determining if a point is in a triangle, the farthest point from a straight line, ...
 * @author Elsa Arrou-Vignod, Florent Buisson
 *
 */
public class GeomComputing {

	/**
	 * compute distance in 2 dimensions
	 * @param p1
	 * @param p2
	 * @return distance
	 */
	public static double distance(Point p1, Point p2) {
		double x1, y1, x2, y2;

		x1 = p1.getX(); y1 = p1.getY();
		x2 = p2.getX(); y2 = p2.getY();

		double distance = Math.pow(Math.pow(x2-x1, 2)+Math.pow(y2-y1, 2), 0.5);
		return distance;
	}

	/**
	 * is toBeTested in the half-plane on the left from [p0p1]
	 * @param p0
	 * @param p1
	 * @param toBeTested
	 * @return boolean
	 */
	public static boolean isInHalfPlane(Point p0, Point p1, Point toBeTested) {
		double a, b, zero, sens;

		zero = 1; sens = p1.getX()-p0.getX();

		// Straight line (p0p1) parameters
		a = (p1.getY()-p0.getY())/(p1.getX()-p0.getX());
		b = p0.getY()-a*p0.getX();
		if (p1.getX()-p0.getX() == 0) {
			a = -1;
			b = p0.getX();
			zero = 0;
			sens = p0.getY()-p1.getY();
		}

		double x = toBeTested.getX(); double y = toBeTested.getY();
		double test = (zero*y-a*x-b)*sens;
		if (test < 0) {
			return true;
		}
		return false;
	}

	public static boolean belongsToTriangle(Point p0, Point p1, Point p2, Point toBeTested) {

		// to which planes does toBeTested belong ?
		boolean test0 = isInHalfPlane(p0, p1, toBeTested);
		boolean test1 = isInHalfPlane(p1, p2, toBeTested);
		boolean test2 = isInHalfPlane(p2, p0, toBeTested);

		// do the triangle corners belong to the same half planes ?
		boolean testP0 = isInHalfPlane(p0, p1, p2);
		boolean testP1 = isInHalfPlane(p1, p2, p0);
		boolean testP2 = isInHalfPlane(p2, p0, p1);

		// let's group the tests to lighten the "if"
		boolean oui0 = (test0==true&&testP0==true);
		boolean oui1 = (test1==true&&testP1==true);
		boolean oui2 = (test2==true&&testP2==true);

		boolean non0 = (test0==false&&testP0==false);
		boolean non1 = (test1==false&&testP1==false);
		boolean non2 = (test2==false&&testP2==false);

		if ((oui0&&oui1&&oui2)||(non0&&non1&&non2)) {
			return true;
		}
		return false;
	}

	/**
	 * find the farthest point from a given segment [p1p2] on the right
	 * @param fop
	 * @param p1
	 * @param p2
	 * @return farthestPoint
	 */
	public static int farthestPoint(ArrayList<Point> fop, Point p1, Point p2) {
		int result = -1;
		double distanceMax = 10;

		// determining the straight line parameters
		double m, zero, p;
		// case 1 : the line is vertical
		if (p2.getX()-p1.getX() == 0) {
			m = 1;
			zero = 0;
			p = p2.getX();
			// case 2 : the line isn't vertical
		} else {
			m = (p2.getY()-p1.getY())/(p2.getX()-p1.getX());
			zero = 1;
			p = p2.getY() - m * p2.getX();
		}
		double norme = Math.pow(1 + Math.pow(m, 2), 0.5);

		for (int i=0;i<fop.size();i++) {
			double distance = Math.abs(m*fop.get(i).getX()-zero*fop.get(i).getY()+p)/norme;

			// we do a half-plane test to distinguish the points on the left from those on the right
			boolean demiPlan = isInHalfPlane(p1, p2, fop.get(i));
			if ((distance >= distanceMax)&&(demiPlan)) {
				distanceMax = distance;
				result = i;
			}
		}
		return result;
	}

}
