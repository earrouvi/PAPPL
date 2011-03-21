package straightenUp;

import java.awt.*;
import java.util.*;

import no.uib.cipr.matrix.*;
import ij.*;

public class MainTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String file = "Images/DSCN3616.jpg";

		ArrayList<Point> groupe1 = new ArrayList<Point>();
		groupe1.add(new Point(530,114));
		groupe1.add(new Point(533,620));
		groupe1.add(new Point(725,612));
		groupe1.add(new Point(715,159));
		ArrayList<Point> groupe2 = new ArrayList<Point>();
		groupe2.add(new Point(0,0));
		groupe2.add(new Point(0,500));
		groupe2.add(new Point(150,500));
		groupe2.add(new Point(150,0));
		
		Homography h = new Homography(groupe1, groupe2);
//		Homography hh = new Homography(groupe1, groupe2);
//		DenseMatrix hhh = new DenseMatrix(2,2);
//		DenseMatrix hhhh = new DenseMatrix(2,2);
//		DenseMatrix Id = new DenseMatrix(2,2);
//		Id.set(0, 0, 1); Id.set(1, 1, 1);
//		for (int i=0;i<2;i++) {
//			for (int j=0;j<2;j++) {
//				hhhh.set(i, j, h.squareHomography.get(i, j));
//			}
//		}
//		hhhh.solve(Id, hhh);
//		for (int i=0;i<2;i++) {
//			for (int j=0;j<2;j++) {
//				hh.squareHomography.set(i, j, hhh.get(i, j));
//			}
//		}
		
		DenseMatrix X = new DenseMatrix(3,1);
		DenseMatrix Xprime = new DenseMatrix(3,1);
		DenseMatrix Y = new DenseMatrix(3,1);
		X.set(0, 0, 720);
		X.set(1, 0, 158);
		X.set(2, 0, 1);
		Xprime.set(0, 0, 530);
		Xprime.set(1, 0, 114);
		Xprime.set(2, 0, 1);
		h.squareHomography.mult(X, Y);
		System.out.println(Y.get(0, 0)+" "+Y.get(1, 0)+" "+Y.get(2, 0));
		System.out.println(Y.get(0, 0)/Y.get(2, 0)+" "+Y.get(1, 0)/Y.get(2, 0)+" "+Y.get(2, 0));
		h.squareHomography.mult(Xprime, Y);
		System.out.println(Y.get(0, 0)+" "+Y.get(1, 0)+" "+Y.get(2, 0));
		System.out.println(Y.get(0, 0)/Y.get(2, 0)+" "+Y.get(1, 0)/Y.get(2, 0)+" "+Y.get(2, 0));
		
		ImageStraightening i = new ImageStraightening(file);
		i.straightenUp(h);
		
	}

}
