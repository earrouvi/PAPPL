import java.io.IOException;
import java.util.*;

import straightenUp.StraighteningFunction;

import Scissors.Scissor_Frame;
import Scissors.algo.VerticesList;
import Scissors.ScissorsOutlineFunction;
import SegmentDetection.*;
import OutlineComputation.*;
import fr.irstv.kmeans.*;

public class MainMain {

	/**
	 * Here all begins.
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		// change here the name of your image
		String file = "ecole";

		String filepath = "Images/"+file+".jpg";
		
		UsefulMethods um = new UsefulMethods();

		SegmentDetectionFunction sdf = new SegmentDetectionFunction(filepath, false);
		RanSacFunction rsf = new RanSacFunction("XML/"+file+".xml", 4, 20d, 0.01d);
		
		DataGroup[] theDataGroup = rsf.theDataGroup; // cleaned groups of DataPoints
		HashMap<Integer, Vector<Segment>> segmentsList = sdf.segmentsList; // list of detected segments
		
		HashMap<Integer, Vector<Segment>> segmentMap = um.groupBeforeDisplay(theDataGroup);
		sdf.segmentDisplayFunction("Images/noir.jpg", segmentMap, theDataGroup);
		
		SegmentSelectionFrame ssf = new SegmentSelectionFrame(segmentMap);
		while (!ssf.val) {
			//Just to make the while not empty and work 
			System.out.print(""); }
		ArrayList<Integer> groupsChosen = ssf.groupsChosen;
		sdf.segmentDisplayFunction("Images/"+file+".jpg", segmentMap, groupsChosen);
		
		//Gets the outline.
		ScissorsOutlineFunction sof = new ScissorsOutlineFunction();

		ExtractFrontOutlineFunction efof = new ExtractFrontOutlineFunction(sof.getVl().getPoints(),theDataGroup, filepath);
		//Version for testing with given ratio
		FinalOutlinePoints outlinePoints = efof.computeFrontOutline();
		//Straighten the front
		StraighteningFunction stf = new StraighteningFunction(outlinePoints, filepath, 40, 0.5,10);
		/*
		//Version for testing with ratio estimated from one vanishing point
		FinalOutlinePoints outlinePoints = efof.computeFrontOutline(groupsChosen);
		//Straighten the front
		StraighteningFunction stf = new StraighteningFunction(outlinePoints, filepath, 40, 0,10);
		*/
	}

}
