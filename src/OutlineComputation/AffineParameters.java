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
	
	public AffineParameters(OutlinePointsGroup points){
		this.slope = 0;
		this.originAbscissa = 0;
		Point p = getBarycenter(points);
		double x = 0, y = 0, X = points.get(0).getX(), Y = points.get(0).getY();
		System.out.println(X + "  " + Y);
		//for(int i = 1; i < points.size(); ++i){
			x = points.get(points.size() - 1).getX();
			y = points.get(points.size() - 1).getY();
			//ystem.out.println(x + "  " + y);
			assert(x != X); // The vanishing point is far from the front outline
			slope =  (y - Y) / (x- X);// * (double) points.size());
			originAbscissa = (x * Y - X * y)/(x- X);// * (double) points.size());
		//}
		//slope = slope;
		System.out.println("Slope = "+ slope );
		//originAbscissa = originAbscissa;
		System.out.println("Origin Abscissa is = "+ originAbscissa );
	}

	public double getSlope() {
		return slope;
	}

	public double getOriginAbscissa() {
		return originAbscissa;
	}
	private Point getBarycenter(OutlinePointsGroup points){
		Point p = new Point();
		double moyX = 0, moyY = 0;
		
		for(int i = 0; i < points.size(); ++i){
			moyX += points.get(i).getX();
			moyY += points.get(i).getY();
		}
		p.setLocation(moyX/(double)points.size(), moyY/(double)points.size());
		return p;
	}
	
}
