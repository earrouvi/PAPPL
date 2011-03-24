import OutlineComputation.ExtractFrontOutlineFunction;
import OutlineComputation.FinalOutlinePoints;
import Scissors.ScissorsOutlineFunction;
import java.awt.*;
import java.util.*;
import straightenUp.*;


public class MainWithoutVP {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String file = "DSCN3616";
		
		String filepath = "Images/"+file+".jpg";
		
		ScissorsOutlineFunction sof = new ScissorsOutlineFunction();

		ExtractFrontOutlineFunction efof = new ExtractFrontOutlineFunction(sof.getVl().getPoints(), filepath);
		FinalOutlinePoints outlinePoints = efof.computeFrontOutline();
		
		// enter coordinates of the corners :
		Point p1, p2, p3, p4;
		p1 = new Point(0,0);
		p2 = new Point(0,500);
		p3 = new Point(250,500);
		p4 = new Point(250,0);
		ArrayList<Point> endPoints = new ArrayList<Point>();
		endPoints.add(p1); endPoints.add(p2); endPoints.add(p3); endPoints.add(p4); 
		
		Homography h = new Homography(outlinePoints, endPoints);
		
		ImageStraightening im = new ImageStraightening(filepath);
		im.straightenUp(h);
	}

}
