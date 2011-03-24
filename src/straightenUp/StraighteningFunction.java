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
		this.horizontalVanishingPoint = edgesPoints.getVanishingPoint();
		computeEndPoints(groundDistance);
		straightenFront(file);
	}
	
	private void computeEndPoints(double groundDistance){
		//This algorithm is under the hypothesis vertical vanishing point is very further to image than others.
		sortBeginPoints();
		int index = horizontalVanishingPoint.getX() > beginPoints.at(0).getX() ? 4 : 3;
		double CA = beginPoints.at(index).horizontalVanishingPoint ;
		double CN = ;
		double v = CA * groundDistance / CN;
		
		
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
			beginPoints.add(2,tempSortedBeginPoints.get(0));
			beginPoints.add(3,tempSortedBeginPoints.get(1));
		}else{
			beginPoints.add(2,tempSortedBeginPoints.get(1));
			beginPoints.add(3,tempSortedBeginPoints.get(0));
		}
		if(tempSortedBeginPoints.get(3).getY() > tempSortedBeginPoints.get(4).getY()){
			beginPoints.add(1,tempSortedBeginPoints.get(3));
			beginPoints.add(4,tempSortedBeginPoints.get(4));
		}else{
			beginPoints.add(1,tempSortedBeginPoints.get(4));
			beginPoints.add(4,tempSortedBeginPoints.get(3));
		}
	}
}
