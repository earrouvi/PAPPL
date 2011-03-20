package straightenUp;

import fr.irstv.dataModel.*;
import java.awt.*;
import java.util.*;
import ij.*;

public class MainTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String file = "Images/DSCN3616.jpg";

		ArrayList<Point> groupe1 = new ArrayList<Point>();
		groupe1.add(new Point(530,110));
		groupe1.add(new Point(530,620));
		groupe1.add(new Point(720,610));
		groupe1.add(new Point(720,160));
		ArrayList<Point> groupe2 = new ArrayList<Point>();
		groupe2.add(new Point(0,0));
		groupe2.add(new Point(0,500));
		groupe2.add(new Point(150,500));
		groupe2.add(new Point(150,0));
		
		Homography h = new Homography(groupe1, groupe2);
		
		
		ImageStraightening i = new ImageStraightening(file);
		i.straightenUp(h);
		
	}

}
