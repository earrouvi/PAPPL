package Apps;

import pg.data.Line;
import pg.data.Point;
import pg.modules.HeightComputation;
import ij.ImagePlus;
import ij.gui.ImageWindow;
import ij.process.ImageProcessor;


public class HeightComputationApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub



		/*
		Line l=new Line(-1,0,1);
		Point x=new Point(2,16,2);
		Line ll=new Line(0,-1,1);

		if(x.liesOn(l))
			System.out.println("x appartient à l");
		else
			System.out.println("x n'appartient pas à l");

		if(l.contains(x))
			System.out.println("x appartient à l");
		else
			System.out.println("x n'appartient pas à l");

		l.intersect(ll).print();
		*/

		String path="Images/DSCN3616.jpg";


		//Plan horizontal
		Point p1l11=new Point(129,501);
		Point p2l11=new Point(624,461);
		Line l11=p1l11.cross(p2l11);

		Point p1l12=new Point(321,533);
		Point p2l12=new Point(742,476);
		Line l12=p1l12.cross(p2l12);

		//Premier point de fuite du plan horizontal
		Point vh1=l11.cross(l12);
		//vh1.normalize();

		Point p1l21=new Point(58,400);
		Point p2l21=new Point(462,474);
		Line l21=p1l21.cross(p2l21);
		Point p1l22=new Point(0,432);
		Point p2l22=new Point(191,496);
		Line l22=p1l22.cross(p2l22);

		//Deuxième point de fuite du plan horizontal
		Point vh2=l21.cross(l22);
		//vh2.normalize();

		//Ligne de fuite
		Line l=vh1.cross(vh2);

		//Les points du problème
		Point t1,t2,b1,b2,v;



		HeightComputation hc=new HeightComputation(path,l);


			//hc.draw(l);



			//hc.show();

			//double resultat=hc.process();
			//System.out.println("Ratio : "+resultat);














	}

}
