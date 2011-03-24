package OutlineComputation;

import java.awt.Point;

public class AffineParameters {
	private double slope;
	private double originAbscissa;
	
	public AffineParameters(OutlinePointsGroup points, Point vanishingPoint){
		this.slope = 0;
		this.originAbscissa = 0;
		double x = 0, y = 0, X = vanishingPoint.getX(), Y = vanishingPoint.getY();
		for(int i = 0; i < points.size(); ++i){
			x = points.get(i).getX();
			y = points.get(i).getY();
			assert(x != X); // The vanishing point is far from the front outline
			slope += (y - Y) / (x- X);
			originAbscissa += (x * Y - X * y)/(x - X);
		}
		slope = slope / (double) points.size();
		originAbscissa = originAbscissa / (double) points.size();
	}

	public double getSlope() {
		return slope;
	}

	public double getOriginAbscissa() {
		return originAbscissa;
	}
	
}
