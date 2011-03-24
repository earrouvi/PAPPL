package straightenUp;

import java.awt.*;
import java.util.*;

import OutlineComputation.FinalOutlinePoints;

import no.uib.cipr.matrix.*;
import ij.*;

public class StraighteningFunction {

	private ArrayList<Point> beginPoints;
	private ArrayList<Point> endPoints;
	private Point horizontalVanishingPoint;
	
	public StraighteningFunction(FinalOutlinePoints edgesPoints, String file, double groundDistance, double ratio){
		this.beginPoints = new ArrayList<Point>(edgesPoints);
		this.horizontalVanishingPoint = edgesPoints.getVanishingPoint();
		this.endPoints = new ArrayList<Point>();
		computeEndPoints(groundDistance, ratio, 10);
		straightenFront(file);
	}
	//TODO Correct this when good vanishing points
	/**
	 * Compute end points from a ground distance and deduces height/width ratio from the corresponding horizontal vanishing point.
	 * @param groundDistance
	 */
	private void computeEndPoints(double groundDistance){
		//This algorithm is under the hypothesis vertical vanishing point is very further to image than others.
		int index = horizontalVanishingPoint.getX() > beginPoints.get(0).getX() ? 3 : 2;
		double CA = beginPoints.get(index).distance(horizontalVanishingPoint);
		double CN = beginPoints.get(index).distance(beginPoints.get(index == 3 ? 2 : 3));
		double v = CA * groundDistance / CN;
		
		double width = CA / (CA - v);
		double height = beginPoints.get(index == 3 ? 0 : 1).getY() - beginPoints.get(index).getY();
		
		Point upLeft = new Point();
		upLeft.setLocation(height,0);
		Point upRight = new Point();
		upRight.setLocation(height,width);
		Point downRight = new Point();
		downRight.setLocation(0,width);
		Point downLeft = new Point();
		downLeft.setLocation(0,0);
		
		endPoints.add(upLeft);
		endPoints.add(upRight);
		endPoints.add(downRight);
		endPoints.add(downLeft);
	}
	/**
	 * Compute end points from a ground distance and a given height/width ratio.
	 * @param groundDistance
	 * @param height/width ratio
	 * @param pixelPerMeter
	 */
	private void computeEndPoints(double groundDistance, double ratio, double pixelPerMeter){
		int width = (int) Math.ceil(groundDistance * pixelPerMeter);
		int height = (int) Math.ceil(width * ratio);
		
		Point upLeft = new Point();
		upLeft.setLocation(width,0);
		Point upRight = new Point();
		upRight.setLocation(width,height);
		Point downRight = new Point();
		downRight.setLocation(0,height);
		Point downLeft = new Point();
		downLeft.setLocation(0,0);
		
		endPoints.add(upLeft);
		endPoints.add(upRight);
		endPoints.add(downRight);
		endPoints.add(downLeft);
	}
	/**
	 * 
	 * @param file
	 */
	private void straightenFront(String file){
		Homography h = new Homography(beginPoints, endPoints);
		ImageStraightening i = new ImageStraightening(file);
		i.straightenUp(h);
	}
}
