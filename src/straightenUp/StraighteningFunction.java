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
	
	public StraighteningFunction(FinalOutlinePoints edgesPoints, String file, double groundDistance){
		this.beginPoints = new ArrayList<Point>(edgesPoints);
		//this.horizontalVanishingPoint = edgesPoints.getVanishingPoint();
		this.endPoints = new ArrayList<Point>();
		computeEndPoints(groundDistance);
		straightenFront(file);
	}
	
	private void computeEndPoints(double groundDistance){
		//This algorithm is under the hypothesis vertical vanishing point is very further to image than others.
		//sortBeginPoints();
		//int index = horizontalVanishingPoint.getX() > beginPoints.get(0).getX() ? 3 : 2;
		//double CA = beginPoints.get(index).distance(horizontalVanishingPoint);
		//double CN = beginPoints.get(index).distance(beginPoints.get(index == 3 ? 2 : 3));
		//double v = CA * groundDistance / CN;
		
		double width = 600;//CA / (CA - v);
		double height = 400;//beginPoints.get(index == 3 ? 0 : 1).getY() - beginPoints.get(index).getY();
		
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
	 * 
	 * @param file
	 */
	private void straightenFront(String file){
		Homography h = new Homography(beginPoints, endPoints);
		ImageStraightening i = new ImageStraightening(file);
		i.straightenUp(h,false);
	}
	/**
	 * Sorts the begin points so they are stored in the upLeft-upRight-downRight-downLeft order
	 */
	private void sortBeginPoints(){
		ArrayList<Point> tempSortedBeginPoints = new ArrayList<Point>();
		tempSortedBeginPoints.add(beginPoints.get(0));
		for(int i = 1; i < 4; ++i){
			int j = 0;
			while(beginPoints.get(i).getX() < beginPoints.get(j).getX() && j < tempSortedBeginPoints.size()){
				++j;
			}
			tempSortedBeginPoints.add(j, beginPoints.get(i));
		}
		beginPoints.clear();
		if(tempSortedBeginPoints.get(0).getY() > tempSortedBeginPoints.get(1).getY()){
			beginPoints.add(tempSortedBeginPoints.get(0));
			beginPoints.add(tempSortedBeginPoints.get(1));
		}else{
			beginPoints.add(tempSortedBeginPoints.get(1));
			beginPoints.add(tempSortedBeginPoints.get(0));
		}
		if(tempSortedBeginPoints.get(2).getY() > tempSortedBeginPoints.get(3).getY()){
			beginPoints.add(tempSortedBeginPoints.get(2));
			beginPoints.add(tempSortedBeginPoints.get(3));
		}else{
			beginPoints.add(tempSortedBeginPoints.get(3));
			beginPoints.add(tempSortedBeginPoints.get(2));
		}
	}
}
