package fr.irstv.kmeans;

import java.io.IOException;

import fr.irstv.dataModel.CircleKDistance;

/**
 * test class for vanishing point classification
 * we will start by using simple images
 * @author moreau
 *
 */
public class MainKCercle {
	public static void main(String[] args) throws IOException {
		/*if (args.length != 1) {
			System.out.println("Usage: java MainKCercle Xmlfilename");
			return;
		}
		*/
		CircleKDistance fd = new CircleKDistance();
		fd.setScilabCheck(true);

		//DataMk dataSet = new DataMk(args[0]);
		DataMk dataSet = new DataMk("file:/Users/Cegeka/Desktop/images/DSCN3616.xml");
		System.out.println(dataSet.getCorpus().size()+" elements to classify");
		long a = -System.currentTimeMillis();
		KMeans k = new KMeans(3,dataSet,fd);
		k.go(10,3);
		a += System.currentTimeMillis();
		System.out.println("Computation terminated in "+((float)a)/1000f+"s");
	}

}
