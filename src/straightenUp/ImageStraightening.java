package straightenUp;

import java.awt.Color;

import ij.*;
import ij.gui.NewImage;
import ij.process.ImageProcessor;
import ij.io.FileSaver;
import no.uib.cipr.matrix.*;

public class ImageStraightening extends ImagePlus {
	
	protected ImagePlus result;
	
	public ImageStraightening(String file) {
		super(file);
	}
	
	public void straightenUp(Homography h) {
		// inits
		DenseMatrix hh = h.squareHomography;
		int width = (int) Math.ceil(h.endPoints.get(3).getX()-h.endPoints.get(0).getX());
		int height = (int) Math.ceil(h.endPoints.get(1).getY()-h.endPoints.get(0).getY());
		int startX, startY, endX, endY;
		startX = (int) Math.ceil(h.beginPoints.get(0).getX());
		startY = (int) Math.ceil(h.beginPoints.get(0).getY());
		endX = (int) Math.ceil(h.beginPoints.get(2).getX());
		endY = (int) Math.ceil(h.beginPoints.get(2).getY());
		
		result = NewImage.createRGBImage("result", width*5, height*5, 1, NewImage.FILL_BLACK);
		result.getProcessor().setColor(Color.WHITE);
		this.show();
		
		// real work here
		System.out.println("Straightening up the image, please wait...");
		for (int i=startX;i<endX;i++) {
			for (int j=startY;j<endY;j++) {
				int[] pixel = getStartPixel(i,j);
				DenseMatrix X = new DenseMatrix(3,1);
				DenseMatrix Y = new DenseMatrix(3,1);
				X.set(0, 0, i);
				X.set(1, 0, j);
				X.set(2, 0, 1);
				hh.mult(X, Y);
				setPixel((int) Math.floor(Y.get(0, 0)/Y.get(2, 0)), ((int) Math.floor(Y.get(1, 0)/Y.get(2, 0))), pixel);
				//System.out.println(pixel[0]+" "+pixel[1]+" "+pixel[2]);
				//int[] pixel2 = getStartPixel((int) Math.floor(Y.get(0, 0)/Y.get(2, 0)),((int) Math.floor(Y.get(1, 0)/Y.get(2, 0))));
				//setPixel(i, j, pixel2);
			}
		}
		System.out.println("Computation is over.");
		result.show();
	}
	
	public int[] getStartPixel(int x, int y) {
		return getPixel(x,y);
	}
	
	// switch on the pixel (x,y) with a greyscale value
	public void setPixel(int x, int y, int value) {
		result.getProcessor().putPixel(x, y, value);
	}
	
	// switch on the pixel (x,y) with an RGB value
	public void setPixel(int x, int y, int[] value) {
		result.getProcessor().putPixel(x, y, value);
	}
	
	public void test(int[] value) {
		setPixel(10,10,value);
		setPixel(10,11,value);
		setPixel(11,10,value);
		setPixel(11,11,value);
	}

}
