import java.io.IOException;
import java.util.*;

import SegmentDetection.*;
import fr.irstv.kmeans.*;

public class MainMain {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		String file = "facade5";
		
		UsefulMethods um = new UsefulMethods();

		SegmentDetectionFunction sdf = new SegmentDetectionFunction("Images/"+file+".jpg", false);
		RanSacFunction rsf = new RanSacFunction("XML/"+file+".xml");
		
		DataGroup[] theDataGroup = rsf.theDataGroup; // cleaned groups of DataPoints
		HashMap<Integer, Vector<Segment>> segmentsList = sdf.segmentsList; // list of detected segments
		
		HashMap<Integer, Vector<Segment>> segmentMap = um.groupBeforeDisplay(theDataGroup);
		sdf.segmentDisplayFunction("Images/"+file+".jpg", segmentMap, theDataGroup);
		
		SegmentSelectionFrame ssf = new SegmentSelectionFrame(segmentMap);
		ArrayList<Integer> groupsChosen = ssf.groupsChosen;
		
	}

}
