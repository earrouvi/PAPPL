package fr.irstv.kmeans;

import java.io.IOException;

import fr.irstv.dataModel.CircleKDistance;

/**
 * adaptated from MainRanSac.java, used in MainMain
 * @author Guillaume Moreau, Elsa Arrou-Vignod, Florent Buisson
 */
public class RanSacFunction {
	
	public DataGroup[] theDataGroup;

	/**
	 * Launches the Ransac function to sort the groups.
	 * @param file
	 * @param maxSample
	 * @param sigma
	 * @param stopTreshold
	 * @throws IOException
	 */
	public RanSacFunction(String file, int maxSample, double sigma, double stopTreshold) throws IOException {

		CircleKDistance fd = new CircleKDistance();
		fd.setScilabCheck(true);

		DataMk dataSet = new DataMk(file);
		System.out.println("--debug-- "+dataSet.getCorpus().size()+" elements to classify");
		long a = -System.currentTimeMillis();
		// here we launch the real job
		RanSac r = new RanSac(6,dataSet,fd);
		// param init
		r.setMaxSample(maxSample);
		r.setSigma(sigma);
		r.setStopThreshold(stopTreshold);
		r.go();
		a += System.currentTimeMillis();

		System.err.println("--debug-- Computation terminated in "+((float)a)/1000f+"s");
		
		//Cleaning the groups
		DataGroup[] groups = r.getGroups();
		CleaningDataGroups cdg = new CleaningDataGroups();
		DataGroup[] cleanedGroups = cdg.clean(groups);
		for (int i=0;i<cleanedGroups.length;i++) {
			System.out.println("group "+i+", centroid : "+cleanedGroups[i].getCentroid());
		}
		theDataGroup = cleanedGroups;
	}
	
}
