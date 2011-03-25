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
	/**
	 * Straightens the fronts delimited by edgesPoints to the given groundDistance/ratio or estimates it if ratio is null.
	 * @param edgesPoints
	 * @param file path, to display / gets information
	 * @param groundDistance
	 * @param ratio height/width
	 * @param pixelPerMeter
	 */
	public StraighteningFunction(FinalOutlinePoints edgesPoints, String file, int groundDistance, double ratio, int pixelPerMeter){
		this.beginPoints = new ArrayList<Point>(edgesPoints);
		this.horizontalVanishingPoint = edgesPoints.getVanishingPoint();
		this.endPoints = new ArrayList<Point>();
		if(ratio != 0){
			computeEndPoints(groundDistance * pixelPerMeter, ratio);
		}else{
			computeEndPoints(groundDistance* pixelPerMeter);
		}
		
		straightenFront(file);
	}
	//TODO Correct this when good vanishing points
	/**
	 * Compute end points from a ground distance in pixels and deduces height/width ratio from the corresponding horizontal vanishing point.
	 * @param groundDistance
	 */
	private void computeEndPoints(double pixelGroundDistance){
		//This algorithm is under the hypothesis vertical vanishing point is very further to image than others.
		this.beginPoints = Homography.sortPoints(this.beginPoints);
		int index = horizontalVanishingPoint.getX() > beginPoints.get(0).getX() ? 1 : 2;
		double CA = beginPoints.get(index).distance(horizontalVanishingPoint);
		double CN = beginPoints.get(index).distance(beginPoints.get(index == 2 ? 1 : 2));
		double v = CA * pixelGroundDistance / CN;
		System.out.println("v = " + v + ", CN = " + CN +", CA = " + CA);
		double ratio = Math.abs(CN / (CN - v));
		double width = pixelGroundDistance;
		double height = width / ratio;
		System.out.println("Width = " + width + ", height = " + height);
		
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
	 * Compute end points from a ground distance and a given height/width ratio.
	 * @param groundDistance
	 * @param height/width ratio
	 * @param pixelPerMeter
	 */
	private void computeEndPoints(double pixelGroundDistance, double ratio){
		int width = (int) Math.ceil(pixelGroundDistance);
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
	 * Computes the point to point homography from begin and end points
	 * @param file
	 */
	private void straightenFront(String file){
		Homography h = new Homography(beginPoints, endPoints);
		ImageStraightening i = new ImageStraightening(file);
		i.straightenUp(h);
	}
}
