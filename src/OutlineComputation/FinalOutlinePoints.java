package OutlineComputation;

import java.awt.Point;
import java.util.ArrayList;

public class FinalOutlinePoints extends ArrayList<Point>{
		private Point vanishingPoint;

		public Point getVanishingPoint() {
			return vanishingPoint;
		}

		public void setVanishingPoint(Point vanishingPoint) {
			this.vanishingPoint = vanishingPoint;
		}
		
}
