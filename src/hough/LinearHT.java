package hough;
import ij.process.ImageProcessor;

/* W. Burger, M. J. Burge: "Digitale Bildverarbeitung" 
 * © Springer-Verlag, 2005
 * www.imagingbook.com
*/

/** This is a rudimentary implementation of the Hough Transform for straight
 * lines. Use e.g. as  
 *    LinearHT HT = new LinearHT(ip, 256, 256);
 * where "ip" is assumed to be an edge image (byte processor) with
 * background = 0.
*/

public class LinearHT {
	ImageProcessor ip;	// reference to original image
	int nAng, nRad; 	// number of steps for angle and radius
	double dAng, dRad; 	// stepsize of angle and radius
	int xCtr, yCtr; 	// x/y-coordinate of image center
	int[][] houghArray; // Hough accumulator

	//constructor method:
	LinearHT(ImageProcessor ip, int aSteps, int rSteps) {
		this.ip = ip;
		xCtr = ip.getWidth()/2; yCtr = ip.getHeight()/2;
		nAng = aSteps; dAng = (Math.PI/nAng);
		nRad = rSteps;
		double rMax = Math.sqrt(xCtr * xCtr + yCtr * yCtr);
		dRad = (2*rMax)/nRad;
		houghArray = new int[nAng][nRad];
		fillHoughAccumulator();
	}
	
	void fillHoughAccumulator() {
		for (int v = 0; v < ip.getHeight(); v++) {
			for (int u = 0; u < ip.getWidth(); u++) {
				if (ip.getPixel(u, v) > 0) {
					doPixel(u, v);
				}
			}
		}
	}

	void doPixel(int u, int v) {
		int x = u-xCtr,  y = v-yCtr;
		for (int a = 0;	a < nAng; a++) {
			double theta = dAng * a;
			int r = (int) Math.round(
				(x*Math.cos(theta) + y*Math.sin(theta)) / dRad) + nRad/2;
			if (r >= 0 && r < nRad) {
				houghArray[a][r]++;
			}
		}
	}
}
