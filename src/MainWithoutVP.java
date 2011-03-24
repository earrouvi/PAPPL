import OutlineComputation.ExtractFrontOutlineFunction;
import OutlineComputation.FinalOutlinePoints;
import Scissors.ScissorsOutlineFunction;


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
	}

}
