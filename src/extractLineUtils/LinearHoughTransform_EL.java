package extractLineUtils;

import java.awt.Color;
import java.util.LinkedList;

import fr.irstv.dataModel.DataPoint;
import fr.irstv.dataModel.Segment;
import hough.HoughSet;
import hough.HoughNode;
import hough.LinearHT2;
import ij.*;
import ij.plugin.filter.PlugInFilter;
import ij.process.*;

/* W. Burger, M. J. Burge: "Digitale Bildverarbeitung" 
 * © Springer-Verlag, 2005
 * www.imagingbook.com
*/

/** This plugin implements a simple Hough Transform for straight lines.
*/

public class LinearHoughTransform_EL{


	public static LinkedList<Segment> processHough(ImagePlus imp, int phi_resolution, int rad_resolution, int lineNb) {
		
		LinkedList<Segment> segResult = new LinkedList<Segment>();
		
		//assumes edge image with background = 0
		ImageProcessor ip = imp.getProcessor();
		//compute the Hough Transform
		LinearHT2 HT = new LinearHT2(ip, phi_resolution, rad_resolution);
		FloatProcessor hip = HT.createFloatProcessor();
		hip.flipHorizontal(); //flip because angle runs reverse (negative y)
		
		//ImagePlus him = new ImagePlus("HT of " + imp.getTitle(),hip);
		//him.show();
		//him.updateAndDraw();
		
		FloatProcessor hmax = HT.localMax(hip);
		ImagePlus hmaxim = new ImagePlus("Maxima of " + imp.getTitle(),hmax);
		//hmaxim.show();
		//hmaxim.updateAndDraw();
		
		HoughSet hs = HT.getMaxList(hmax,lineNb);
		HoughNode[] lines = hs.getNodes();
		
		//FloatProcessor result = new FloatProcessor(ip.getWidth(), ip.getHeight());
		//ImagePlus resultImage = new ImagePlus("result image", result);
		
		//System.out.println("Hough, xCrt "+HT.getXCtr()+" yCrt "+HT.getYCtr()+" nAng "+HT.getNAng()+" nRad "+HT.getNRad()+" dAng "+HT.getDAng()+" dRad "+HT.getDRad());
		
		for(int i=0; i<hs.getSize(); i++){

			DataPoint[] points = getLineExtremum(lines[i],imp.getWidth(),imp.getHeight(),HT.getXCtr(), HT.getYCtr(), HT.getDAng(), HT.getDRad(), HT.getNRad(), HT.getNAng()-1);
			
			Segment seg = new Segment(points[0], points[1], null);
			
			segResult.add(seg);
			//System.out.println(points[0][0]+" "+ points[0][1]+" "+ points[1][0]+" "+ points[1][1]);
			//resultImage.getProcessor().setColor(Color.red);
			//resultImage.getProcessor().drawLine(points[0][0], points[0][1], points[1][0], points[1][1]);
			
		}

		return segResult;
		//HT.printHoughSet(hs);
		
		//process the HoughSet now...
		
	}

	public void showAbout() {
		IJ.showMessage("LinearHoughTransform_...",
			"Simple HT plugin to be expanded."
		);
	}
	
	private static DataPoint[] getLineExtremum(HoughNode node, int w, int h, int xCrt, int yCrt, double dAng, double dRad, int nRad, int amax){
		
		//int w = imp.getWidth();
		//int h = imp.getHeight();
		DataPoint[] Points = new DataPoint[2];
		
		//on calcule l'angle et le rayon avec les facteur d'echantillonnage dans LinearHT2 (nRad, dRad, dAng)
		double theta = (amax-node.getAngle())*dAng;//-Math.PI/2; //on ne prend que des angles positifs entre 0 et Pi
		
		double r = (node.getRadius()-nRad/2)*dRad;
		
		System.out.println("droite r: "+r+" theta: "+theta);
		
		//calcul des points d'intersection continus avec les axes du repere centre en (xCrt,yCrt)
		//la droite de parametre (r,theta) est d'equation y = -y2/x1 x + y2 
		double x1 = r/Math.cos(theta);
		double y2 = r/Math.sin(theta);
		
		System.out.println("x1 :"+x1+" y2 : "+y2);
		
		//intercections discretes avec les bords de l'image
		//il y a 4 points d'intersection possibles, si ils sont en dehords de l'image on ne les gardes pas
		// etre en dans l'image signifie avoir un x entre -xCrt et imageW-xCrt  et un y entre -yCrt et imageH-yCrt 
		if(theta == 0 ){
			Points[0].set(0, (int)Math.round(r));
			Points[0].set(1, -yCrt);
			Points[1].set(0, (int)Math.round(r));
			Points[1].set(1, h-yCrt-1);
		}else if(r == 0){
			Points= getIntersectionWithImage(Math.tan(Math.PI/2 - theta), 0, w, h, xCrt, yCrt);
		}else{
			Points = getIntersectionWithImage(-y2/x1, y2, w, h, xCrt, yCrt);
		}
		
		return Points;
		
	}
	
	
	private static DataPoint[] getIntersectionWithImage(double a, double b, int w, int h, int xCrt, int yCrt){
		
		int[][] points = new int[2][2]; 
		
		int pointCpt = 0;
		
		int x,y;
		
		//test intersection avec x = -xCrt
		x = -xCrt;
		y = (int)Math.round(a*x+b);
		
		if(isInBondingBox(x, y, -xCrt, w-xCrt-1, -yCrt, h-yCrt-1) && pointCpt <2){
			points[pointCpt][0] = x;
			points[pointCpt][1] = y;
			pointCpt++;
		}
		
		//test intersection avec x = imagew-xCrt-1
		x = w-xCrt -1;
		y = (int)Math.round(a*x+b);
		
		if(isInBondingBox(x, y, -xCrt, w-xCrt-1, -yCrt, h-yCrt-1) && pointCpt <2){
			points[pointCpt][0] = x;
			points[pointCpt][1] = y;
			pointCpt++;
		}
		//test intersection avec y = -yCrt
		y = -yCrt;
		x = (int) Math.round((y-b)/a);
		
		if(isInBondingBox(x, y, -xCrt, w-xCrt-1, -yCrt, h-yCrt-1) && pointCpt <2){
			points[pointCpt][0] = x;
			points[pointCpt][1] = y;
			pointCpt++;
		}
		//test intersection avec y = imageh-yCrt-1
		y = h-yCrt-1;
		x = (int) Math.round((y-b)/a);
		if(isInBondingBox(x, y, -xCrt, w-xCrt-1, -yCrt, h-yCrt-1) && pointCpt <2){
			points[pointCpt][0] = x;
			points[pointCpt][1] = y;
			pointCpt++;
		}
		
		if(pointCpt != 2){System.out.println(pointCpt +" points d'intersection avec les bords de l'image");}
		
		System.out.println("avant recalage \n"+points[0][0]+" "+ points[0][1]+" "+ points[1][0]+" "+ points[1][1]);
		
		//recalage par rapport au 0,0 en haut ˆ gauche
		points[0][0] += xCrt;
		points[1][0] += xCrt;
		points[0][1] += yCrt;
		points[1][1] += yCrt;
		
		DataPoint begin = new DataPoint(2);
		DataPoint end = new DataPoint(2);
		
		begin.set(0, points[0][0]);
		begin.set(1, points[0][1]);
		end.set(0, points[1][0]);
		end.set(1, points[1][1]);
		
		DataPoint[] result = new DataPoint[2];
		result[0] = begin;
		result[1] = end;
		
		return result;
	}
		
		
	private static boolean isInBondingBox(int x, int y, int xMin, int xMax, int yMin, int yMax){
		
		boolean test = true ;
		if(x<xMin || x > xMax){
			test = false;
		}
		if(y<yMin || y > yMax){
			test = false;
		}
		
		return test;
	}
}





	
	
