package OutlineComputation;

import ij.ImagePlus;
import ij.process.ImageProcessor;

import java.awt.*;
import java.util.ArrayList;

import fr.irstv.dataModel.DataPoint;
import fr.irstv.kmeans.DataGroup;

public class ExtractFrontOutlineFunction {
	private ArrayList<Point> op;
	private DataGroup[] dataGroup;
	ImagePlus im;
	ImageProcessor imProc;
	boolean useVanishingPoints;
	
	/**
	 * Constructor for computing WITH vanishing points, either for outline or width/height ratio.
	 * @param outlinePoints (the canvas you drew with the scissors)
	 * @param theDataGroup (groups of segments and their vanishing point)
	 */
	public ExtractFrontOutlineFunction(ArrayList<Point> outlinePoints, DataGroup[] theDataGroup, String file){
		this.op = outlinePoints;
		this.dataGroup = theDataGroup;
		this.im = new ImagePlus(file);
		this.imProc = im.getProcessor();
		this.useVanishingPoints = true;
	}
	
	/**
	 * Constructor for computing WITHOUT vanishing points but WITH output width and height.
	 * @param outlinePoints (the canvas you drew with the scissors)
	 */
	public ExtractFrontOutlineFunction(ArrayList<Point> outlinePoints, String file){
		this.op = outlinePoints;
		this.im = new ImagePlus(file);
		this.imProc = im.getProcessor();
		this.useVanishingPoints = false;
	}
	
	//TODO Test it whenever vanishing points are ok
	/**
	 * Computes corners using vanishing points. Not efficient without correct vanishing points, and not tested with...
	 * @param groupsChosen
	 * @return FinalOutlinePoints
	 */
	public FinalOutlinePoints computeFrontOutlineFromVanishingPoints(ArrayList<Integer> groupsChosen){
		if(!this.useVanishingPoints)
			System.err.println("You cannot use this with the constructor you choosed")
		//inits
		FinalOutlinePoints opComputed = new FinalOutlinePoints();
		Point baryCenter = getBarycenter();
		
		// Gets the vp and computes the one corresponding to horizontal lines
		Point vanishingPoint0 = getVanishingPoint(groupsChosen.get(0));
		System.out.println("Vanishing point 0 x ="+vanishingPoint0.getX() + "et  y = "+vanishingPoint0.getY());
		Point vanishingPoint1 = getVanishingPoint(groupsChosen.get(1));
		System.out.println("Vanishing point 1 x ="+vanishingPoint1.getX() + "et  y = "+vanishingPoint1.getY());
		
		
		if((Math.abs(vanishingPoint0.getY() - im.getHeight()/2)) > (Math.abs(vanishingPoint1.getY()) - im.getHeight()/2)){
			opComputed.setVanishingPoint(vanishingPoint1);
			System.out.println("Vanishing point 1 KEPT x ="+vanishingPoint1.getX() + "et  y = "+vanishingPoint1.getY());
		}else{
			opComputed.setVanishingPoint(vanishingPoint0);
			System.out.println("Vanishing point 0 KEPT x ="+vanishingPoint0.getX() + "et  y = "+vanishingPoint0.getY());
		}
		
		
		//We are going to guess which outline segment each scissor outline point is associated with
		double x1 = 0, y1 = 0, x2 = 0, y2 = 0, x =0 , y = 0;
		double x1bary = 0, y1bary = 0, x2bary = 0, y2bary = 0;
		OutlinePointsGroup vp0PtGrp0 = new OutlinePointsGroup();
		OutlinePointsGroup vp0PtGrp1 = new OutlinePointsGroup();
		OutlinePointsGroup vp1PtGrp0 = new OutlinePointsGroup();
		OutlinePointsGroup vp1PtGrp1 = new OutlinePointsGroup();
		x1bary = baryCenter.getX() - vanishingPoint0.getX();
		y1bary = baryCenter.getY() - vanishingPoint0.getY();
		x2bary = baryCenter.getX() - vanishingPoint1.getX();
		y2bary = baryCenter.getY() - vanishingPoint1.getY();
		
		// Sorts by groups, each group corresponds to one side of the outline.
		for(int i = 0; i < op.size(); ++i){
			x1 = op.get(i).getX() - vanishingPoint0.getX();
			y1 = op.get(i).getY() - vanishingPoint0.getY();
			x2 = op.get(i).getX() - vanishingPoint1.getX();
			y2 = op.get(i).getY() - vanishingPoint1.getY();
			x = i == op.size() - 1 ? op.get(0).getX() - op.get(i).getX() : op.get(i+1).getX() - op.get(i).getX();
			y = i == op.size() - 1 ? op.get(0).getY() - op.get(i).getY() : op.get(i+1).getY() - op.get(i).getY();
			if(sortSegments( x1, y1, x2, y2, x, y)){
				if(x1bary * y1 - y1bary * x1 > 0){
					vp0PtGrp0.add(op.get(i));
				}else{
					vp0PtGrp1.add(op.get(i));
				}
			}else{
				if(x2bary * y2 - y2bary * x2 > 0){
					vp1PtGrp0.add(op.get(i));
				}else{
					vp1PtGrp1.add(op.get(i));
				}
			}
		}
		// Groups sorted
		
		
		// Computes the outline : af for Affine Parameters, vp Vanishing Poing, PtGrp for PointGroup
		AffineParameters afvp0PtGrp0 = new AffineParameters(vp0PtGrp0,vanishingPoint0);
		AffineParameters afvp0PtGrp1 = new AffineParameters(vp0PtGrp1,vanishingPoint0);
		AffineParameters afvp1PtGrp0 = new AffineParameters(vp1PtGrp0,vanishingPoint1);
		AffineParameters afvp1PtGrp1 = new AffineParameters(vp1PtGrp1,vanishingPoint1);
		
		System.out.println("Adding edge 1...");
		opComputed.add(getCrossOf(afvp0PtGrp0,afvp1PtGrp0));
		System.out.println("Adding edge 2...");
		opComputed.add(getCrossOf(afvp0PtGrp0,afvp1PtGrp1));
		System.out.println("Adding edge 3...");
		opComputed.add(getCrossOf(afvp0PtGrp1,afvp1PtGrp0));
		System.out.println("Adding edge 4...");
		opComputed.add(getCrossOf(afvp0PtGrp1,afvp1PtGrp1));
		System.out.println("Edges added!");
		im.show();
		
		return opComputed;
	}
	
	/**
	 * Computes corners without using vanishing points
	 * @return FinalOutlinePoints
	 */
	public FinalOutlinePoints computeFrontOutline(ArrayList<Integer> groupsChosen) {		
		// inits
		double maxDis = 0;
		int p1, p2, p3, p4;
		p1 = 0; p2 = 0; p3 = 0; p4 = 0;
		Point center = getBarycenter();
		
		// output (FinalOutlinePoints is an ArrayList<Point>)
		FinalOutlinePoints fop = new FinalOutlinePoints();
		
		// corner #1 is farthest point from barycenter
		for (int i=0;i<op.size();i++) {
			if (GeomComputing.distance(op.get(i),center) > maxDis) {
				maxDis = GeomComputing.distance(op.get(i),center);
				p1 = i;
			}
		}
		fop.add(op.get(p1));
		
		maxDis = 0;
		// corner #2 is farthest point from #1
		for (int i=0;i<op.size();i++) {
			if (GeomComputing.distance(op.get(i),fop.get(0)) >= maxDis) {
				maxDis = GeomComputing.distance(op.get(i),fop.get(0));
				p2 = i;
			}
		}
		fop.add(op.get(p2));
		
		// corner 3 is farthest point from [#1 #2]
		p3 = GeomComputing.farthestPoint(op, fop.get(0), fop.get(1));
		if (p3 == -1) {
			p3 = GeomComputing.farthestPoint(op, fop.get(1), fop.get(0));
		}
		fop.add(op.get(p3));
		
		// corner 4 is farthest point from [#1 #3] or [#2 #3] that isn't in the triangle
		p4 = GeomComputing.farthestPoint(op, fop.get(0), fop.get(2));
		if (p4 == -1) {
			p4 = GeomComputing.farthestPoint(op, fop.get(2), fop.get(0));
		}
		if (GeomComputing.belongsToTriangle(fop.get(0), fop.get(1), fop.get(2), op.get(p4))) {
			p4 = GeomComputing.farthestPoint(op, fop.get(1), fop.get(2));
			if (p4 == -1) {
				p4 = GeomComputing.farthestPoint(op, fop.get(2), fop.get(1));
			}
		}
		fop.add(op.get(p4));
		
		for (int i=0;i<4;i++) {
			System.out.println("Corner #"+i+" : "+fop.get(i).x+" "+fop.get(i).y);
		}
		
		drawCorners(fop);
		im.show();
		
		
		return fop;
	}
	
	
	/**
	 * Computes corners using vanishing points only for ratio
	 * @return FinalOutlinePoints
	 */
	public FinalOutlinePoints computeFrontOutline() {		
		// inits
		double maxDis = 0;
		int p1, p2, p3, p4;
		p1 = 0; p2 = 0; p3 = 0; p4 = 0;
		Point center = getBarycenter();
		
		// output (FinalOutlinePoints is an ArrayList<Point>)
		FinalOutlinePoints fop = new FinalOutlinePoints();
		
		// corner #1 is farthest point from barycenter
		for (int i=0;i<op.size();i++) {
			if (GeomComputing.distance(op.get(i),center) > maxDis) {
				maxDis = GeomComputing.distance(op.get(i),center);
				p1 = i;
			}
		}
		fop.add(op.get(p1));
		
		maxDis = 0;
		// corner #2 is farthest point from #1
		for (int i=0;i<op.size();i++) {
			if (GeomComputing.distance(op.get(i),fop.get(0)) >= maxDis) {
				maxDis = GeomComputing.distance(op.get(i),fop.get(0));
				p2 = i;
			}
		}
		fop.add(op.get(p2));
		
		// corner 3 is farthest point from [#1 #2]
		p3 = GeomComputing.farthestPoint(op, fop.get(0), fop.get(1));
		if (p3 == -1) {
			p3 = GeomComputing.farthestPoint(op, fop.get(1), fop.get(0));
		}
		fop.add(op.get(p3));
		
		// corner 4 is farthest point from [#1 #3] or [#2 #3] that isn't in the triangle
		p4 = GeomComputing.farthestPoint(op, fop.get(0), fop.get(2));
		if (p4 == -1) {
			p4 = GeomComputing.farthestPoint(op, fop.get(2), fop.get(0));
		}
		if (GeomComputing.belongsToTriangle(fop.get(0), fop.get(1), fop.get(2), op.get(p4))) {
			p4 = GeomComputing.farthestPoint(op, fop.get(1), fop.get(2));
			if (p4 == -1) {
				p4 = GeomComputing.farthestPoint(op, fop.get(2), fop.get(1));
			}
		}
		fop.add(op.get(p4));
		
		for (int i=0;i<4;i++) {
			System.out.println("Corner #"+i+" : "+fop.get(i).x+" "+fop.get(i).y);
		}
		
		drawCorners(fop);
		im.show();
		
		
		return fop;
	}
	public void drawCorners(FinalOutlinePoints fop) {
		for (Point pt : fop) {
			imProc.setColor(Color.RED);
			imProc.fillOval(pt.x, pt.y, 6, 6);			
		}		
	}
	
	private Point getVanishingPoint(int group){
		this.dataGroup[group].computeCentroid();
		DataPoint centroid = this.dataGroup[group].getCentroid();
		Point vanishingPoint = new Point();
		vanishingPoint.setLocation(2*centroid.get(0), 2*centroid.get(1));
		return vanishingPoint;
	}
	
	private Point getBarycenter(){
		Point p = new Point();
		double moyX = 0, moyY = 0;
		
		for(int i = 0; i < op.size(); ++i){
			imProc.putPixel((int)op.get(i).getX(), (int)op.get(i).getY(), 255);
			moyX += op.get(i).getX();
			moyY += op.get(i).getY();
		}
		p.setLocation(moyX/op.size(), moyY/op.size());
		System.out.println("Le point de coordonnées x = "+ moyX/op.size() +" , y = "+ moyY/op.size() + " est le barycentre.");
		return p;
	}
	private boolean sortSegments(double x1, double y1, double x2, double y2, double x, double y){
		return (((x2*y-y2*x) * (x2*y-y2*x) * (x1*x1+y1*y1) - (x1*y-y1*x) * (x1*y-y1*x) * (x2*x2+y2*y2)) > 0);
	}
	private Point getCrossOf(AffineParameters afGrp0, AffineParameters afGrp1){
		double x = (afGrp0.getOriginAbscissa() - afGrp1.getOriginAbscissa())/(afGrp1.getSlope() - afGrp0.getSlope());
		double y = x * afGrp1.getSlope() + afGrp1.getOriginAbscissa();
		Point p = new Point();
		p.setLocation( x, y);
		System.out.println("Le point de coordonnées x = "+ x +" , y = "+ y + " a été ajouté.");
		imProc.drawOval((int) x, (int) y, 10, 10) ;
		return p;
	}
}
