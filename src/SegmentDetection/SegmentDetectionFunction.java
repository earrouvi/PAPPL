package SegmentDetection;

import fr.irstv.kmeans.DataGroup;
import ij.ImagePlus;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;


/**
 * @author Leo COLLET, Cedric TELEGONE, Ecole Centrale Nantes, 2010
 *	taken from App.java - used in MainMain.java
 */
public class SegmentDetectionFunction {
	
	public HashMap<Integer, Vector<Segment>> segmentsList;
	
	public SegmentDetectionFunction(String file, boolean differentColours) {
		String path = file;

		// creating the color map
		HashMap<Integer,Color> colorMap = new HashMap<Integer,Color>(8);
		colorMap.put(0, Color.RED);
		colorMap.put(1, Color.BLUE);
		colorMap.put(2, Color.GREEN);
		colorMap.put(3, Color.ORANGE);
		colorMap.put(4, Color.YELLOW);
		colorMap.put(5, Color.MAGENTA);
		colorMap.put(6, Color.CYAN);
		colorMap.put(7, Color.WHITE);

		/** si besoin de plusieurs images à la fois */
		/*Vector<String> theImages = FilesFinder.findFiles(path);
		for (int i=0; i<theImages.size(); i++){
			System.out.println(path + theImages.get(i));
			ImageSegment is = new ImageSegment(path + theImages.get(i));
			Utils.getImageFromSegmentMap(is.baseImage, is.getLargeConnectedEdges(false), colorMap);
		}*/

		try {
			ImageSegment is = new ImageSegment(path);
			is.getLargeConnectedEdges(false, 8);
			segmentsList = is.getFinalSegmentMap();
			
			// displaying the image
			if (differentColours) {
				Utils.getImageFromImageSegment(is, colorMap);
			} else {
				Utils.getImageFromImageSegment(is);
			}
			
			String[] splitPath = path.split("/");
			String fileBaseName;
			if (splitPath.length >= 1){
				fileBaseName = splitPath[splitPath.length - 1];
			} else {
				fileBaseName = path;
			}
			String fileBaseNameWithoutExtension = (fileBaseName != null) ? fileBaseName.substring(0,fileBaseName.indexOf('.')) : "";
			System.out.println("dszerzger");
			exportToXML(is, fileBaseNameWithoutExtension);
		} catch (NullPointerException fnfe){
			System.out.println("The file you tried to process cannot be reached.");
			fnfe.printStackTrace();
		}

	}

	public static void exportToXML(ImageSegment is, String path){
		PrintWriter file;

	    try{
	    	file =  new PrintWriter(new BufferedWriter(new FileWriter("XML/"+path + ".xml")));

		    file.println("<?xml version=\"1.0\" ?>");

		    file.println("<root>");
		    	file.println("\t" + "<Epsilon Epsilon=\"40\"/>");
		    	file.println("\t \t" + "<LES-SEGMENTS>");
		    	for (Iterator<Map.Entry<Integer, Vector<Segment>>> iter = is.finalSegmentMap.entrySet().iterator(); iter.hasNext();){
					Map.Entry<Integer, Vector<Segment>> ent = (Map.Entry<Integer, Vector<Segment>>) iter.next();
					for (int i=0; i<ent.getValue().size(); i++){
						file.print("\t \t \t" + "<Coordonnees Segments-xp1=\"" + ent.getValue().get(i).getStartPoint().getX() + "\" ");
						file.print("Segments-yp1=\"" + ent.getValue().get(i).getStartPoint().getY() + "\" ");
						file.print("Segments-xp2=\"" + ent.getValue().get(i).getEndPoint().getX() + "\" ");
						file.println("Segments-yp2=\"" + ent.getValue().get(i).getEndPoint().getY() + "\" />");
					}
				}
		    	file.println("\t \t" + "</LES-SEGMENTS>");
		    file.println("</root>");

		    file.close();
	    } catch (IOException ioe){
	    	System.out.println("Cannot write on file " + path + ".xml");
	    	ioe.printStackTrace();
	    } catch (NullPointerException npe){
	    	System.out.println("Cannot access to image segment map. Must be computed.");
	    	npe.printStackTrace();
	    }
	}
	
	// same method as above but without segment detection - only displaying segments
	public void segmentDisplayFunction(String file, HashMap<Integer, Vector<Segment>> segmentMap, DataGroup[] dg) {
		String path = file;

		// creating the color map
		HashMap<Integer,Color> colorMap = new HashMap<Integer,Color>(8);
		colorMap.put(0, Color.RED);
		colorMap.put(1, Color.BLUE);
		colorMap.put(2, Color.GREEN);
		colorMap.put(3, Color.ORANGE);
		colorMap.put(4, Color.YELLOW);
		colorMap.put(5, Color.MAGENTA);
		colorMap.put(6, Color.CYAN);
		colorMap.put(7, Color.WHITE);
		
		ImageSegment is = new ImageSegment(path);
		ImagePlus is2 = is.baseImage;
		// displaying the image
		Utils.getImageFromSegmentMap(is2, segmentMap, colorMap, dg);
	}

}
