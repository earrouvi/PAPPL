package straightenUp;

import java.awt.Color;

import ij.*;
import ij.gui.NewImage;
import ij.process.ImageProcessor;
import ij.io.FileSaver;
import no.uib.cipr.matrix.*;

public class ImageStraightening extends ImagePlus {
	
	protected ImagePlus result;
	String file;
	
	public ImageStraightening(String file) {
		super(file);
		this.file = file;
	}
	/**
	 * Computes and displays the result of the homography h on the image attribute.
	 * @param h, the homography
	 */
	public void straightenUp(Homography h) {
		// inits
		DenseMatrix homography = h.squareHomography;
		DenseMatrix revHomography = h.reverseSquareHomography;
		int width = (int) Math.abs(Math.ceil(h.endPoints.get(3).getX()-h.endPoints.get(0).getX()));
		int height = (int) Math.abs(Math.ceil(h.endPoints.get(1).getY()-h.endPoints.get(0).getY()));
		
		result = NewImage.createRGBImage(file+"result", width, height, 1, NewImage.FILL_BLACK);
		result.getProcessor().setColor(Color.WHITE);
		this.show();
		
		// real work here
		System.out.println("Straightening up the image, please wait...");
		
		// Pixels vectors
		DenseMatrix X = new DenseMatrix(3,1);
		DenseMatrix Y = new DenseMatrix(3,1);
		X.set(2, 0, 1);
		
		for (int i=0;i<width;i++) {
			for (int j=0;j<height;j++) {
				// Pixels' coordinates
				X.set(0, 0, i);
				X.set(1, 0, j);
				// reverse side transformation
				revHomography.mult(X, Y);
				int[] pixel = getStartPixel((int) Math.floor(Y.get(0, 0)/Y.get(2, 0)),((int) Math.floor(Y.get(1, 0)/Y.get(2, 0))));
				setPixel(i, j, pixel);
			}
		}
		FileSaver fs = new FileSaver(result);
		fs.saveAsPng("result1.png");
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
