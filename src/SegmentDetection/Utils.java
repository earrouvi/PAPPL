package SegmentDetection;
import java.awt.Color;
import java.util.*;

import fr.irstv.dataModel.DataPoint;
import fr.irstv.kmeans.DataGroup;
import ij.*;

/**
 * Class implementing the useful tools for ImageSegment class.
 *
 * @author Leo COLLET, Cedric TELEGONE, Ecole Centrale Nantes
 * @version 1.0
 */

public class Utils {

	/**
	 * Default no-args constructor
	 */
	public Utils(){

	}

	/**
	 * X and Y gradients computation
	 *
	 * @param 	image	the ImagePlus object which gradients must be computed
	 * @return			an ImagePlus array of length 2 containing the X and Y gradient images of image
	 */
	public static ImagePlus[] getGradients(ImagePlus image){
		/**
		 *  Initialize gradient matrixes
		 */
		ImagePlus gradXimage = new ImagePlus("X-Gradient", image.getProcessor().duplicate());
		ImagePlus gradYimage = new ImagePlus("Y-Gradient", image.getProcessor().duplicate());
		/**
		 *  Initialize normalized convolution kernels
		 */
		//float coeff = (float) 0.5;
		float coeff = (float) 0.3333;
		/*float[] kerX = {0, 0, 0, -coeff, 0, coeff, 0, 0, 0};
		float[] kerY = {0, -coeff, 0, 0, 0, 0, 0, coeff, 0};*/
		float[] kerX = {-coeff, 0, coeff, -coeff, 0, coeff, -coeff, 0, coeff};
		float[] kerY = {-coeff, -coeff, -coeff, 0, 0, 0, coeff, coeff, coeff};
		gradXimage.getProcessor().convolve(kerX, 3, 3);
		gradYimage.getProcessor().convolve(kerY, 3, 3);
		ImagePlus[] result = new ImagePlus[2];
		result[0] = gradXimage;
		result[1] = gradYimage;
		//gradXimage.show();
		//gradYimage.show();
		return result;
	}

	/**
	 *
	 * @param 	image		the RGB (or whatever) image to be converted to grayscale and resized
	 * @param 	MAX_WIDTH	the maximal desired width
	 * @return				the converted image
	 */
	public static ImagePlus format(ImagePlus image, int MAX_WIDTH){
		/*if (image.getWidth() > MAX_WIDTH){
			ImagePlus out = new ImagePlus(image.getTitle(), image.getProcessor().resize(MAX_WIDTH).convertToByte(true));
			//out.show();
			return out;
		} else {*/

			ImagePlus out = new ImagePlus(image.getTitle(), image.getProcessor().convertToByte(true));
			return out;
		//}
	}

	/**
	 * Calculate the linear regression coefficients in a Segment context, viewed as a set of points.
	 * Based upon a classical linear regression method.
	 * From Introduction aux Probabilites et a la Statistique; BRILLOUET-BELLUOT, Nicole; Ecole Centrale Nantes; 2008
	 *
	 * @param 		s	the Segment to be processed
	 * @return			a Point array containing start and end points of Segment s
	 */
	public static void linRegSegment(Segment s){

		/**
		 * Linear regression coefficients
		 * a, b
		 * y = a*x + b
		 */
		double a, b;

		/**
		 * 'a' and 'b' coefficients are computed this way:
		 * s = {(Xi,Yi)}, i=1..n
		 * X = mean(Xi); Y = mean(Yi)
		 * num = sum[(Xi - X)*(Yi-Y)], i=1..n
		 * den = sum[(Xi - X)^2], i=1..n
		 * a = num / den
		 * b = Y - a*X
		 */
		double num 	= 0;
		double den 	= 0;
		double X	= 0;
		double Y	= 0;

		/**
		 * To get integer values for start point and end point, we keep in memory Xmin and Xmax.
		 * Startpoint will be (Xmin, a*Xmin + b)
		 * Endpoint will be (Xmax, a*Xmax + b)
		 */
		int Xmin = s.getPoints().get(0).getX();
		int Xmax = s.getPoints().get(0).getX();

		/**
		 * Calculate X and Y
		 */
		for (int i=0; i<s.getPoints().size(); i++){
			X = X + s.getPoints().get(i).getX();
			Y = Y + s.getPoints().get(i).getY();
			if (s.getPoints().get(i).getX() < Xmin){
				Xmin = s.getPoints().get(i).getX();
			} else if (s.getPoints().get(i).getX() > Xmax){
				Xmax = s.getPoints().get(i).getX();
			}
		}
		X = X / s.getPoints().size();
		Y = Y / s.getPoints().size();

		/**
		 * Calculate num and den
		 */
		for (int i=0; i<s.getPoints().size(); i++){
			num = num + (s.getPoints().get(i).getX() - X) * (s.getPoints().get(i).getY() - Y);
			den = den + (s.getPoints().get(i).getX() - X) * (s.getPoints().get(i).getX() - X);
		}

		/**
		 * Calculate a and b
		 */
		a = num / den;
		b = Y - (a * X);

		/**
		 * Calculate and modify start and end points
		 */
		Double Ymin = new Double(a*Xmin + b);
		Double Ymax = new Double(a*Xmax + b);
		s.setStartPoint(new Point(Xmin, Ymin.intValue()));
		s.setEndPoint(new Point(Xmax, Ymax.intValue()));
	}

	/**
	 * Calculate and show an image representing the original input image of an ImageSegment in which segments are shown.
	 *
	 * @param 	image		The input image
	 * @param 	map			The segment hashmap extracted from image
	 * @param 	colorMap	The mapping colors - OPTIONAL
	 */
	public static void getImageFromSegmentMap(ImagePlus image, HashMap<Integer, Vector<Segment>> map, HashMap<Integer,Color> colorMap){
		ImagePlus i = new ImagePlus ("Output segment mapping", image.getProcessor().convertToRGB());
		for (Iterator<Map.Entry<Integer, Vector<Segment>>> e = map.entrySet().iterator(); e.hasNext();){
			Map.Entry<Integer, Vector<Segment>> ent = (Map.Entry<Integer, Vector<Segment>>) e.next();
			i.getProcessor().setColor(colorMap.get(ent.getKey()));
			for (int m=0; m<ent.getValue().size(); m++){
				Segment s = ent.getValue().get(m);
				i.getProcessor().drawLine(s.getStartPoint().getX(), s.getStartPoint().getY(), s.getEndPoint().getX(), s.getEndPoint().getY());
			}
		}
		i.setTitle("Output segment mapping image");
		i.show();
	}
	
	/**
	 * @author Elsa Arrou-Vignod, Florent Buisson
	 * same method as above, but displays vanishing points in addition to segments
	 * @param image
	 * @param map
	 * @param colorMap
	 */
	public static void getImageFromSegmentMap(ImagePlus image, HashMap<Integer, Vector<Segment>> map, HashMap<Integer,Color> colorMap, DataGroup[] dg){
		ImagePlus i = new ImagePlus ("Output segment mapping", image.getProcessor().convertToRGB());
		for (Iterator<Map.Entry<Integer, Vector<Segment>>> e = map.entrySet().iterator(); e.hasNext();){
			Map.Entry<Integer, Vector<Segment>> ent = (Map.Entry<Integer, Vector<Segment>>) e.next();
			i.getProcessor().setColor(colorMap.get(ent.getKey()));
			displayVanishingPoints(i, dg[ent.getKey()].getCentroid());
			for (int m=0; m<ent.getValue().size(); m++){
				Segment s = ent.getValue().get(m);
				i.getProcessor().drawLine(s.getStartPoint().getX(), s.getStartPoint().getY(), s.getEndPoint().getX(), s.getEndPoint().getY());
			}
		}
		i.setTitle("Output segment mapping image");
		i.show();
	}
	
	public static void displayVanishingPoints(ImagePlus i, DataPoint vp) {
		i.getProcessor().fillOval((int) vp.get(0), (int) vp.get(1), 10, 10);
	}

	public static void getImageFromSegmentMap(ImagePlus image, HashMap<Integer, Vector<Segment>> map){
		ImagePlus i = new ImagePlus ("Output segment mapping", image.getProcessor().convertToRGB());
		i.getProcessor().setColor(Color.RED);
		for (Iterator<Map.Entry<Integer, Vector<Segment>>> e = map.entrySet().iterator(); e.hasNext();){
			Map.Entry<Integer, Vector<Segment>> ent = (Map.Entry<Integer, Vector<Segment>>) e.next();
			for (int m=0; m<ent.getValue().size(); m++){
				Segment s = ent.getValue().get(m);
				i.getProcessor().drawLine(s.getStartPoint().getX(), s.getStartPoint().getY(), s.getEndPoint().getX(), s.getEndPoint().getY());
			}
		}
		i.setTitle("Output segment mapping image");
		i.show();
	}

	/**
	 * Calculate and show an image representing an ImageSegment object.
	 *
	 * @param 	image		The input ImageSegment object
	 * @param 	colorMap	The mapping colors - OPTIONAL
	 */
	public static void getImageFromImageSegment(ImageSegment image, HashMap<Integer,Color> colorMap){
		getImageFromSegmentMap(image.baseImage, image.finalSegmentMap, colorMap);
	}
	public static void getImageFromImageSegment(ImageSegment image){
		getImageFromSegmentMap(image.baseImage, image.finalSegmentMap);
	}

}
