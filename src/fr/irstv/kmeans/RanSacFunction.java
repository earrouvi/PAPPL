package fr.irstv.kmeans;

import java.io.IOException;

import fr.irstv.dataModel.CircleKDistance;

/**
 * adaptated from MainRanSac.java, used in MainMain
 * @author Guillaume Moreau, Elsa Arrou-Vignod, Florent Buisson
 */
public class RanSacFunction {
	
	public DataGroup[] theDataGroup;

	public RanSacFunction(String file) throws IOException {

		CircleKDistance fd = new CircleKDistance();
		fd.setScilabCheck(true);

		DataMk dataSet = new DataMk(file);
		System.out.println("--debug-- "+dataSet.getCorpus().size()+" elements to classify");
		long a = -System.currentTimeMillis();
		// here we launch the real job
		RanSac r = new RanSac(6,dataSet,fd);
		// param init
		r.setMaxSample(7);
		r.setSigma(10d);
		r.setStopThreshold(0.01d);
		r.go();
		a += System.currentTimeMillis();
		
		System.err.println("--debug-- Computation terminated in "+((float)a)/1000f+"s");
		
		// cleaning the groups
		DataGroup[] groups = r.getGroups();
		CleaningDataGroups cdg = new CleaningDataGroups();
		DataGroup[] cleanedGroups = cdg.clean(groups);
		for (int i=0;i<cleanedGroups.length;i++) {
			//System.out.println(groups[i].getCentroid()+" hop "+groups[i].getComponents().size());
			System.out.println("group "+i+", centroid : "+cleanedGroups[i].getCentroid());
		}
		theDataGroup = cleanedGroups;
	}
	
}
