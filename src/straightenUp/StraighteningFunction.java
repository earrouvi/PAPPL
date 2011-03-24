package straightenUp;

import java.awt.*;
import java.util.*;

import no.uib.cipr.matrix.*;
import ij.*;

public class StraighteningFunction {

	private ArrayList<Point> beginPoints;
	private ArrayList<Point> endPoints;
	
	public StraighteningFunction(ArrayList<Point> edgesPoints, String file){
		this.beginPoints = new ArrayList<Point>(edgesPoints);
		computeEndPoints();
		straightenFront(file);
	}
	
	private void computeEndPoints(){
		
	}
	private void straightenFront(String file){
		Homography h = new Homography(beginPoints, endPoints);
		ImageStraightening i = new ImageStraightening(file);
		i.straightenUp(h);
	}
}
