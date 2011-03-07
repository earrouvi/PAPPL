/**
 * 
 */
package fr.irstv.kmeans;


import java.io.IOException;
import java.io.PrintStream;

import fr.irstv.dataModel.CircleKDistance;
import fr.irstv.dataModel.DataPoint;
import java.io.FileWriter;



/**
 * @author  gmoreau
 */
public class KMeans {
	/**
	 * group number
	 */
	protected int ngroups;
	
	/**
	 * data corpus
	 */
	protected DataCorpus dc;
	
	/**
	 * distance function
	 */
	DataDistance fctDist;
	
	
	/**
	 * data groups
	 * @uml.property  name="groups"
	 * @uml.associationEnd  multiplicity="(0 -1)"
	 */
	DataGroup[] groups;
	
	/**
	 * constructor 
	 * @param ngroups number of groups
	 * @param dc data corpus
	 * @param fctDist distance function 
	 * 
	 * @see DataDistance
	 */
	
	public KMeans(int ngroups, DataCorpus dc, DataDistance fctDist) {
		this.ngroups = ngroups;
		this.dc = dc;
		this.fctDist = fctDist;
		// on construit les groupes de données nécessaires
		groups = new DataGroup[ngroups];
		for (int i=0 ; i<ngroups ; i++) {
			groups[i] = new DataGroup(fctDist);
		}
	}

	public void go(int nbEtapesMax) {
		go(nbEtapesMax,1);
	}
	
	/**
	 * ierations are runned here
	 * @param nStepMax Maximum number of iterations
	 * @param initialGroupSize size of the group at init time
	 */
	public void go(int nbEtapesMax,int initialGroupSize) {
		initialisation(initialGroupSize);
		int i=0;
		boolean changed = true;
		while (i < nbEtapesMax && changed) {
			System.out.println("---- Iteration "+i+" ----");
			changed = iteration(i);
			//displayState(true);
			i++;
		}
		System.out.println(changed ? "---- Maximum number of iteration reached ----" :
			"---- System is stable ----");
		//displayState(true);
	}

	/**
	 * flat file export for visualization and test
	 * 
	 * naming protocol 
	 * 
	 * @param  iteration ieration number
	 * @throws IOException 
	 */
	void exportFichierPlat(int iteration) throws IOException {
		String fileNameBase = new String("debug_")+iteration;
		for (int i=0 ; i<ngroups ; i++) {
			String fileName = new String(fileNameBase)+"_"+i;
			FileWriter out = new FileWriter(fileName);
			// circle center
			out.write(groups[i].getCentroid().toString()+"\n");
			// segment dump
			for (DataPoint dp : groups[i].getComponents()) {
				out.write(dp.toString()+"\n");
			}
		}
	}
	
	/**
	 * initialization of standard algorithm : 1 element per group
	 * 
	 * for compatibility reasons only
	 */
	public void initialisation() {
		initialisation(1);
	}
	
	/**
	 * algorithm initialization
	 * <ul>
	 * <li>creation of ngroups centers randomly chosen between corpus points </li>
	 * </ul>
	 * <em>we care about generating unique inits (a single element can only be into one group)
	 */
	public void initialisation(int nb) {
		boolean alreadyUsed[] = new boolean[dc.getCorpus().size()];
		int idx;
		
		for (int i=0 ; i<ngroups*nb ; i++) {
			boolean ok1 = false;
			idx = -1;
			while (!ok1) {
				idx = (int) Math.floor(Math.random()*dc.getCorpus().size());
				boolean ok2 = true;
				if (alreadyUsed[idx]) {
					ok2 = false;
				}
				if (ok2) {
					ok1 = true;
				}
			}
			
			groups[i%ngroups].add(dc.getCorpus().get(idx));
			alreadyUsed[idx] = true;
		} // for
		for (int i=0 ; i<ngroups ; i++) {
			System.out.println("Group "+i%ngroups+" content");
			System.out.println(groups[i%ngroups]);
		}
	}
	/**
	 * one iteration of the algorithm (number i)
	 * <ul>
	 * <li>recompute all centroids (except for the first one where they are fixed)</li>
	 * <li>create temporary groups</li>
	 * <li>For each datapoint</li><ul>
	 * <li>compute the distance between each element and the group</li>
	 * <li>assign into new groups</li></ul>
	 * <li>check whether groups have been modified</li>
	 * <li>update old groups with the new ones</li>
	 * </ul>
	 * 
	 * @bug check equality between groups !
	 * @param num
	 */
	private boolean iteration(int num) {
		// recompute centroids
		if (true) {
			for (int i=0 ; i<ngroups ; i++) {
				// debug code
				if (fctDist instanceof CircleKDistance) {
					((CircleKDistance)fctDist).setDebugGroupNumber(i);
					((CircleKDistance)fctDist).setDebugIteration(num);
				}
				groups[i].computeCentroid();
			}
		}
		
		// create new groups
		DataGroup[] nvx = new DataGroup[ngroups];
		for (int i=0 ; i<ngroups ; i++) {
			nvx[i] = new DataGroup(fctDist);
		}

		// compute the distance between each element and the group
		for (DataPoint dp : dc.getCorpus()) {
			double dmin = fctDist.distance(dp, groups[0].getCentroid());
			int imin = 0;
			for (int i=1 ; i<ngroups ; i++) {
				if (fctDist.distance(dp,groups[i].getCentroid()) < dmin) {
					dmin = fctDist.distance(dp,groups[i].getCentroid());
					imin = i;
				}
			}
		// assign elements
		nvx[imin].add(dp);
		//System.out.println("assigning "+dp+" to group "+imin);
		}
		
		// did some group change ?
		boolean gchanged = changed(groups,nvx);
		
		// update groups
		for (int i=0 ; i<ngroups ; i++) {
			groups[i] = nvx[i];
			// debug code
			if (fctDist instanceof CircleKDistance) {
				((CircleKDistance)fctDist).setDebugGroupNumber(i);
			}
			groups[i].computeCentroid();
		}
		
		return gchanged;
	}
	
	/**
	 * check whether two groups are different
	 * 
	 * Each group existing in d1 can match with a group with the same elements in d2. 
	 * If a group in d1 does not have any match in d2, return true 
	 * 
	 * could be optimized !
	 * 
	 * @param d1 first group
	 * @param d2 second group
	 * @return false if there exist a match between d1 and d2
	 */
	boolean changed(DataGroup[] d1,DataGroup[] d2) {
		int id1=0,id2;
		while (id1 < ngroups) {
			// seek a match into d2
			id2=0;
			boolean found = false;
			while (id2 < ngroups && !found) {
				found = d1[id1].equals(d2[id2]);
				id2++;
			}
			if (!found) {
				return true;
			}
			id1++;
		}
		return false;
	}
	
	public void displayState(boolean debug) {
		for (int i=0 ; i<ngroups ; i++) {
			System.out.println("Groupe "+i+" ("+groups[i].getSize()+")");
			System.out.println("centroide : "+groups[i].getCentroid());
			if (debug) {
				System.out.println(groups[i]);
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		DataCorpus dc = new DataCorpus("c:\\guillaume\\workspace\\pointsdefuite\\tests\\testsimple1.txt");
		System.out.println(dc.getCorpus().size()+" elements to classify");
		DataDistance f = new EuclidianDistance();
		long a = -System.currentTimeMillis();
		KMeans k = new KMeans(2,dc,f);
		k.go(15);
		a += System.currentTimeMillis();
		System.out.println("Computation terminated in "+((float)a)/1000f+"s");
	}

	public DataGroup[] getGroupes() {
		// TODO Auto-generated method stub
		return groups;
	}
}
