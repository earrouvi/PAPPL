package hough;
import ij.IJ;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;

/* W. Burger, M. J. Burge: "Digitale Bildverarbeitung" 
 * © Springer-Verlag, 2005
 * www.imagingbook.com
*/

public class LinearHT2 {
	protected ImageProcessor ip;	// reference to original image
	protected int nAng, nRad; 	// number of steps for angle and radius
	protected double dAng, dRad; 	// stepsize of angle and radius
	protected int xCtr, yCtr; 	// x/y-coordinate of image center
	protected int[][] houghArray; // Hough accumulator

	//constructor method:
	public LinearHT2(ImageProcessor ip, int aSteps, int rSteps) {
		this.ip = ip;
		xCtr = ip.getWidth()/2; yCtr = ip.getHeight()/2;
		nAng = aSteps; dAng = (Math.PI/nAng);
		nRad = rSteps;
		double rMax = Math.sqrt(xCtr * xCtr + yCtr * yCtr);
		dRad = (2*rMax)/nRad;
		houghArray = new int[nAng][nRad];
		//System.out.println("xCrt: "+xCtr+", yCrt : "+yCtr);
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
			double rayon = x*Math.cos(theta) + y*Math.sin(theta);
			int r = (int) Math.round(
				(x*Math.cos(theta) + y*Math.sin(theta)) / dRad) + nRad/2;
			//System.out.println("pixels (x,y): ("+x+","+y+"), a: "+a+", theta : "+theta+", rayon : "+rayon+", r: "+r);
			if (r >= 0 && r < nRad) {
				houghArray[a][r]++;
			}
		}
		
		/*String out = new String();
		for(int a=0; a<nAng; a++){
			for(int r=0; r<nRad; r++){
				out = out +" "+houghArray[a][r];
			}
			System.out.println(out);
			out="";
		}*/
	}
	
	/*double getAngle(int a) {	//return real angle for angle index a
		return a*dAng;
	}¬*/
	
	double getAngle(int a, int amax) {	//return real angle for angle index a (attention indice amax = angle 0
		return (amax-a)*dAng;
	}
	
	double getRadius(int r) {	
		return (r-nRad/2)*dRad;
	}

	public FloatProcessor createFloatProcessor() {
		int[] fpixels = new int[nAng * nRad];
		for (int a = 0; a < nAng; a++) {
			for (int r = 0; r < nRad; r++) {
				fpixels[r * nAng + a] = houghArray[a][r];
			}
		}
		FloatProcessor fp = new FloatProcessor(nAng, nRad, fpixels);
		return fp;
	}
	
	public FloatProcessor localMax(FloatProcessor fp) {
		//creates a new image with 0/1
		//where 1 = local maximum
//		final float markval = 255;
		int w = fp.getWidth();
		int h = fp.getHeight();
		float[] pix = (float[]) fp.getPixels();
		float[] lmax = new float[pix.length]; //initialized to zero
		/*for (int v = 1; v < h - 1; v++) {
			int r0 = v - 1;
			int r1 = v;
			int r2 = v + 1;
			for (int u = 1; u < w - 1; u++) {
				int c0 = u - 1;
				int c1 = u;
				int c2 = u + 1;
				float cp = pix[r1 * w + c1];
				boolean ismax =
					cp > pix[r0 * w + c0]
						&& cp > pix[r0 * w + c1]
						&& cp > pix[r0 * w + c2]
						&& cp > pix[r1 * w + c0]
						&& cp > pix[r1 * w + c2]
						&& cp > pix[r2 * w + c0]
						&& cp > pix[r2 * w + c1]
						&& cp > pix[r2 * w + c2];
				if (ismax)
					lmax[r1 * w + c1] = cp;
			}
		}*/
		for (int v = 0; v < h; v++) {
			int r0 = v - 1;
			int r1 = v;
			int r2 = v + 1;
			for (int u = 0; u < w ; u++) {
				int c0 = u - 1;
				int c1 = u;
				int c2 = u + 1;
				float cp = pix[r1 * w + c1];
				
				/*     c0   c1   c2
				 * r0 pix1 pix2 pix3
				 * r1 pix4  cp  pix5
				 * r2 pix6 pix7 pix8

				 */
				float pix1 = (r0<0 ||c0<0)?0:pix[r0 * w + c0];
				float pix2 = (r0<0)?0 : pix[r0 * w + c1];
				float pix3 = (r0<0 || c2>=w)?0 : pix[r0 * w + c2];
				float pix4 = (c0<0)?0:pix[r1 * w + c0];
				float pix5 = (c2>=w)?0 : pix[r1 * w + c2];
				float pix6 = (r2>=h || c0<0)?0 :  pix[r2 * w + c0];
				float pix7 = (r2>=h)?0:pix[r2 * w + c1];
				float pix8 = (r2>=h || c2>=w)?0 : pix[r2 * w + c2];
					
				boolean ismax =
					cp > pix1
						&& cp > pix2
						&& cp > pix3
						&& cp > pix4
						&& cp > pix5
						&& cp > pix6
						&& cp > pix7
						&& cp > pix8;
				if (ismax)
					lmax[r1 * w + c1] = cp;
			}
		}
		return new FloatProcessor(w, h, lmax, null);
	}

	public HoughSet getMaxList(FloatProcessor hmax, int n){
		HoughSet hs = new HoughSet(n);
		for (int a = 0; a < nAng; a++) {
			for (int r = 0; r < nRad; r++) {
				float hcountf = Float.intBitsToFloat(hmax.getPixel(a,r));
				int hcount = (int) hcountf;
				if (hcount > 0){
					hs.add(a,r,hcount);
				}
			}
		}
		return hs;
	}
	
	public void printHoughSet(HoughSet hs){
		HoughNode[] nodes = hs.nodes;
		for (int i=0; i<nodes.length; i++){
			HoughNode hn = nodes[i];
			if (hn.count > -2){
				//double angle = getAngle(hn.angle);
				double angle = getAngle(hn.angle, nAng-1);
				double radius = getRadius(hn.radius);
				IJ.write(i+": "+ angle + "," + radius + "," + hn.count+" a : "+hn.angle+", r :"+hn.radius);
			}
		}
	}

	public double getDAng() {
		return dAng;
	}

	public void setDAng(double ang) {
		dAng = ang;
	}

	public double getDRad() {
		return dRad;
	}

	public void setDRad(double rad) {
		dRad = rad;
	}

	public int[][] getHoughArray() {
		return houghArray;
	}

	public void setHoughArray(int[][] houghArray) {
		this.houghArray = houghArray;
	}

	public ImageProcessor getIp() {
		return ip;
	}

	public void setIp(ImageProcessor ip) {
		this.ip = ip;
	}

	public int getNAng() {
		return nAng;
	}

	public void setNAng(int ang) {
		nAng = ang;
	}

	public int getNRad() {
		return nRad;
	}

	public void setNRad(int rad) {
		nRad = rad;
	}

	public int getXCtr() {
		return xCtr;
	}

	public void setXCtr(int ctr) {
		xCtr = ctr;
	}

	public int getYCtr() {
		return yCtr;
	}

	public void setYCtr(int ctr) {
		yCtr = ctr;
	}

}






