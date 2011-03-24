package fr.irstv.kmeans;

import java.io.IOException;

import junit.framework.TestCase;

public class TestKMeans extends TestCase {
	public void testFichierSimple1() {
		DataCorpus dc=null;
		try {
			dc = new DataCorpus("http://moreaug44.free.fr/mahzad/testsimple1.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("impossible de lire le fichier");
		}
		DataDistance f = new EuclidianDistance();
		KMeans k1 = new KMeans(2,dc,f);
		k1.go(15);
		// on vérifie les résultats
		DataGroup[] dr = new DataGroup[2];
		dr[0] = new DataGroup(f);
		dr[1] = new DataGroup(f);
		dr[0].add(dc.getCorpus().get(0));
		dr[0].add(dc.getCorpus().get(1));
		dr[1].add(dc.getCorpus().get(2));
		dr[1].add(dc.getCorpus().get(3));
		dr[1].add(dc.getCorpus().get(4));
		assertFalse("Resultat de calcul incorrect (dim 1)",k1.changed(dr, k1.getGroupes()));
	}

	public void testFichierSimple2() {
		DataCorpus dc=null;
		try {
			dc = new DataCorpus("http://moreaug44.free.fr/mahzad/testsimple2.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("impossible de lire le fichier");
		}
		DataDistance f = new EuclidianDistance();
		KMeans k1 = new KMeans(3,dc,f);
		k1.go(15);
		// on vérifie les résultats
		DataGroup[] dr = new DataGroup[3];
		dr[0] = new DataGroup(f);
		dr[1] = new DataGroup(f);
		dr[2] = new DataGroup(f);
		dr[0].add(dc.getCorpus().get(0));
		dr[0].add(dc.getCorpus().get(1));
		dr[1].add(dc.getCorpus().get(2));
		dr[1].add(dc.getCorpus().get(3));
		dr[2].add(dc.getCorpus().get(4));
		dr[2].add(dc.getCorpus().get(5));
		dr[2].add(dc.getCorpus().get(6));
		
		System.out.println(dr[0]);
		System.out.println(dr[1]);
		System.out.println(dr[2]);
		
		assertFalse("Resultat de calcul incorrect (dim 2)",k1.changed(dr, k1.getGroupes()));
	}

}
