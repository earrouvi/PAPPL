package straightenUp;

import no.uib.cipr.matrix.*;
import fr.irstv.dataModel.*;

public class ChangeCoordSystem {

	protected DenseMatrix changeBaseMatrix;
	protected DenseMatrix firstBaseMatrix;
	protected DenseMatrix secondBaseMatrix;

	// constructeur toujours le même
	public ChangeCoordSystem(DataPoint u1, DataPoint v1, DataPoint u2, DataPoint v2) {
		firstBaseMatrix = new DenseMatrix(2, 2);
		secondBaseMatrix = new DenseMatrix(2, 2);
		// initialise the base matrices with the given vectors
		for (int i=0;i<2;i++) {
			firstBaseMatrix.set(i, 0, u1.get(i));
			firstBaseMatrix.set(i, 1, v1.get(i));
			secondBaseMatrix.set(i, 0, u2.get(i));
			secondBaseMatrix.set(i, 1, v2.get(i));
			System.out.println(secondBaseMatrix.get(i, 0)+" "+secondBaseMatrix.get(i, 1));
		}
	}

	// méthode avec deux dimensions connues, calcul en une fois
	// vecteurs de base non normés
	public void computeWith2Dim() {
		changeBaseMatrix = new DenseMatrix(2, 2);
		firstBaseMatrix.mult(invert(secondBaseMatrix), changeBaseMatrix);
		for (int i=0;i<2;i++) {
			for (int j=0;j<2;j++) {
				System.out.println(changeBaseMatrix.get(i,j));				
			}
		}
	}

	// méthode avec une dimension connue, on calcule le chgt de repère puis l'homothétie
	// vecteurs de base normés ou à normer
	public void computeWith1Dim() {

	}

	// inversion de matrice
	public DenseMatrix invert(DenseMatrix A) {
		DenseMatrix Aprime = new DenseMatrix(A.numRows(), A.numColumns());
		double alpha = (A.get(0, 0)*A.get(1, 1) - A.get(0, 1)*A.get(1, 0));
		System.out.println("alpha : "+alpha);
		if (alpha == 0) {
			System.err.println("Cannot invert matrix : determinant is null");
		} else {
			Aprime.set(0, 0, A.get(1, 1)/alpha);
			Aprime.set(0, 1, -A.get(0, 1)/alpha);
			Aprime.set(1, 0, -A.get(1, 0)/alpha);
			Aprime.set(1, 1, A.get(0, 0)/alpha);
		}
		return Aprime;
	}

	// accesseurs :

	public DenseMatrix getChangeBaseMatrix() {
		return changeBaseMatrix;
	}

	public DenseMatrix getFirstBaseMatrix() {
		return firstBaseMatrix;
	}

	public DenseMatrix getSecondBaseMatrix() {
		return secondBaseMatrix;
	}

	public void setChangeBaseMatrix(DenseMatrix changeBaseMatrix) {
		this.changeBaseMatrix=changeBaseMatrix;
	}

	public void setFirstBaseMatrix(DenseMatrix firstBaseMatrix) {
		this.firstBaseMatrix=firstBaseMatrix;
	}

	public void setSecondBaseMatrix(DenseMatrix secondBaseMatrix) {
		this.secondBaseMatrix=secondBaseMatrix;
	}

}
