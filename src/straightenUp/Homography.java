package straightenUp;

import java.util.*;
import java.awt.*;

import no.uib.cipr.matrix.*;
import fr.irstv.dataModel.*;

public class Homography {
	
	protected DenseMatrix homography;
	protected DenseMatrix squareHomography;
	protected DenseMatrix reverseSquareHomography;
	protected DenseMatrix leftMember;
	protected DenseMatrix rightMember;
	
	protected ArrayList<Point> beginPoints;
	protected ArrayList<Point> endPoints;
	java.awt.Point point;
	
	//protected DataPoint a1,a2,a3,a4,b1,b2,b3,b4;
	
	// construction avec 4 points de contr�le
	public Homography(ArrayList<Point> beginPoints, ArrayList<Point> endPoints) {
		homography = new DenseMatrix(8,1);
		leftMember = new DenseMatrix(8,8);
		rightMember = new DenseMatrix(8,1);
		
		this.beginPoints = sortPoints(beginPoints);
		this.endPoints = sortPoints(endPoints);
		
		computeLeft();
		computeRight();
		computeHomography();
		matrixToSquare();
	}

	// calcul de la matrice d'homographie
	public void computeHomography() throws MatrixSingularException {
		leftMember.solve(rightMember, homography);
	}
	
	public void computeLeft() {
		for (int i=0;i<8;i++) {
			int row, column1, column2, coord;
			row = i/2;
			if (i%2 == 0) {
				column1 = 0;
				column2 = 3;
				leftMember.set(i, 6, Math.pow(-1, i)*endPoints.get(row).getY()*beginPoints.get(row).getX());
				leftMember.set(i, 7, Math.pow(-1, i)*endPoints.get(row).getY()*beginPoints.get(row).getY());
			} else {
				column1 = 3;
				column2 = 0;
				leftMember.set(i, 6, Math.pow(-1, i)*endPoints.get(row).getX()*beginPoints.get(row).getX());
				leftMember.set(i, 7, Math.pow(-1, i)*endPoints.get(row).getX()*beginPoints.get(row).getY());
			}

			leftMember.set(i, column2, Math.pow(-1, i+1)*beginPoints.get(row).getX());
			leftMember.set(i, column2+1, Math.pow(-1, i+1)*beginPoints.get(row).getY());
			leftMember.set(i, column2+2, Math.pow(-1, i+1));
			
		}
	}
	
	public void computeRight() {
		for (int i=0;i<4;i++) {
			rightMember.set(2*i, 0, - endPoints.get(i).getY());
			rightMember.set(2*i+1, 0, endPoints.get(i).getX());
		}
	}
	
	public void matrixToString(DenseMatrix mat) {
		System.out.println("-------------- Homography matrix ------------------\n");
		for (int i=0;i<mat.numRows();i++) {
			for (int j=0;j<mat.numColumns();j++) {
				System.out.print(mat.get(i, j)+" ");
			}
			System.out.println("\n");
		}
		System.out.println("---------------------------------------------------\n");
	}
	
	public void matrixToSquare() {
		squareHomography = new DenseMatrix(3,3);
		reverseSquareHomography = new DenseMatrix(3,3);
		
		// Identity matrix
		DenseMatrix Id = new DenseMatrix(3,3);
		Id.set(0, 0, 1); Id.set(1, 1, 1); Id.set(2, 2, 1); 
		
		// Column to square
		for (int i=0;i<8;i++) {
			squareHomography.set(i/3, i%3, homography.get(i, 0));
		}
		squareHomography.set(2, 2, 1);
		
		// Reverse matrix for reverse transformations
		squareHomography.solve(Id, reverseSquareHomography);
		matrixToString(squareHomography);
	}
	/**
	 * Return the points of the list inputPoints sorted in the upLeft-downLeft-downRight-upRight order
	 * @param The input points "inputPoints" in an ArrayList<Points>
	 * @return The sorted points in an ArrayList<Points>
	 */
	private ArrayList<Point> sortPoints(ArrayList<Point> inputPoints){
		ArrayList<Point> tempSortedPoints = new ArrayList<Point>();
		ArrayList<Point> sortedPoints = new ArrayList<Point>();
		tempSortedPoints.add(inputPoints.get(0));
		for(int i = 1; i < 4; ++i){
			int j = 0;
			while(j < tempSortedPoints.size() && inputPoints.get(i).getX() < tempSortedPoints.get(j).getX()){
				++j;
			}
			tempSortedPoints.add(j, inputPoints.get(i));
		}
		if(tempSortedPoints.get(2).getY() > tempSortedPoints.get(3).getY()){
			sortedPoints.add(tempSortedPoints.get(3));
			sortedPoints.add(tempSortedPoints.get(2));
		}else{
			sortedPoints.add(tempSortedPoints.get(2));
			sortedPoints.add(tempSortedPoints.get(3));
		}
		if(tempSortedPoints.get(0).getY() > tempSortedPoints.get(1).getY()){
			sortedPoints.add(tempSortedPoints.get(0));
			sortedPoints.add(tempSortedPoints.get(1));
		}else{
			sortedPoints.add(tempSortedPoints.get(1));
			sortedPoints.add(tempSortedPoints.get(0));
		}
		return sortedPoints;
	}
	
}
