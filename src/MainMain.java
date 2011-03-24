import java.io.IOException;
import java.util.*;

import Scissors.Scissor_Frame;
import Scissors.algo.VerticesList;
import Scissors.ScissorsOutlineFunction;
import SegmentDetection.Segment;
import SegmentDetection.SegmentDetectionFunction;
import SegmentDetection.Utils;
import OutlineComputation.ExtractFrontOutlineFunction;
import OutlineComputation.FinalOutlinePoints;
import fr.irstv.kmeans.*;

public class MainMain {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		String file = "facade1";
		
		UsefulMethods um = new UsefulMethods();

		SegmentDetectionFunction sdf = new SegmentDetectionFunction("Images/"+file+".jpg", false);
		RanSacFunction rsf = new RanSacFunction("XML/"+file+".xml");
		
		DataGroup[] theDataGroup = rsf.theDataGroup; // cleaned groups of DataPoints
		HashMap<Integer, Vector<Segment>> segmentsList = sdf.segmentsList; // list of detected segments
		
		HashMap<Integer, Vector<Segment>> segmentMap = um.groupBeforeDisplay(theDataGroup);
		sdf.segmentDisplayFunction("Images/"+file+".jpg", segmentMap, theDataGroup);
		
		SegmentSelectionFrame ssf = new SegmentSelectionFrame(segmentMap);
		while (!ssf.val) { System.out.print(""); }
		ArrayList<Integer> groupsChosen = ssf.groupsChosen;
		sdf.segmentDisplayFunction("Images/"+file+".jpg", segmentMap, groupsChosen);
		
		//Gets the outline.
		ScissorsOutlineFunction sof = new ScissorsOutlineFunction();

		ExtractFrontOutlineFunction efof = new ExtractFrontOutlineFunction(sof.getVl().getPoints(),theDataGroup);
		FinalOutlinePoints outlinePoints = efof.computeFrontOutlineWithVanishingPoints(groupsChosen);
	
	}

}
