zpackage OutlineComputation;

import ij.ImagePlus;
import ij.process.ImageProcessor;

import java.awt.Point;
import java.util.ArrayList;

import fr.irstv.dataModel.DataPoint;
import fr.irstv.kmeans.DataGroup;

public class ExtractFrontOutlineFunction {
	private ArrayList<Point> op;
	private DataGroup[] dataGroup;
	ImagePlus im;
	ImageProcessor imProc;
	
	public ExtractFrontOutlineFunction(ArrayList<Point> outlinePoints, DataGroup[] theDataGroup){
		this.op = outlinePoints;
		this.dataGroup = theDataGroup;
		this.im = new ImagePlus("Images/facade1.jpg");
		this.imProc = im.getProcessor();
	}

	public FinalOutlinePoints computeFrontOutlineWithVanishingPoints(ArrayList<Integer> groupsChosen){
		FinalOutlinePoints opComputed = new FinalOutlinePoints();
		
		Point vanishingPoint0 = getVanishingPoint(groupsChosen.get(0));
		System.out.println("plopplopplop. x ="+vanishingPoint0.getX() + "et  y = "+vanishingPoint0.getY());
		Point vanishingPoint1 = getVanishingPoint(groupsChosen.get(1));
		System.out.println("plopplopplop. x ="+vanishingPoint1.getX() + "et  y = "+vanishingPoint1.getY());
		Point baryCenter = getBarycenter();
		//TODO Verify regular point repartition on outline from scissors
		
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
		
		System.out.println("plop1.");
		opComputed.add(getCrossOf(afvp0PtGrp0,afvp1PtGrp0));
		System.out.println("plop2.");
		opComputed.add(getCrossOf(afvp0PtGrp0,afvp1PtGrp1));
		System.out.println("plop3.");
		opComputed.add(getCrossOf(afvp0PtGrp1,afvp1PtGrp0));
		System.out.println("plop4.");
		opComputed.add(getCrossOf(afvp0PtGrp1,afvp1PtGrp1));

		im.show();
		return opComputed;
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
			System.out.println("plopplop. x ="+op.get(i).getX() + "et  y = "+op.get(i).getY());
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
		//imProc.putPixel((int) x, (int) y, 255);
		imProc.drawOval((int) x, (int) y, 10, 10) ;
		return p;
	}
}
