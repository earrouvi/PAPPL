import java.io.IOException;

import SegmentDetection.SegmentDetectionFunction;
import fr.irstv.kmeans.RanSacFunction;


public class Tests {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		String file = "DSCN3616";
		SegmentDetectionFunction sdf = new SegmentDetectionFunction("Images/"+file+".jpg", false);
		RanSacFunction rsf = new RanSacFunction("XML/"+file+".xml");
	}

}
