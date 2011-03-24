package fr.irstv.kmeans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import SegmentDetection.ImageSegment;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;

import fr.irstv.dataModel.DataPoint;
import fr.irstv.dataModel.MkDataPoint;
import fr.irstv.dataModel.Segment;

import pg.data.Group;
import pg.data.SegmentPG;

/**
 * specific class for reading MK points data files
 * see in XML file, no DTD or schema yet
 *
 * @author moreau
 *
 */
public class DataMk extends DataCorpus {

	/**
	 *@author Cedric Telegone & Leo Collet
	 *reading data from pg.data.group
	 *
	 *modified on March 7th 2011 by Elsa Arrou-Vignod & Florent Buisson
	 *(added MkCorpus in order not to lose the segment information)
	 *@param a pg.data.group 
	 */

	protected LinkedList<MkDataPoint> MkCorpus;

	public DataMk(Group g){
		MkCorpus = new LinkedList<MkDataPoint>();
		readDataFromSegments(g);
	}

	private void readDataFromSegments(Group g) {

		List<SegmentPG> segments=g.getSeg();

		for(int i=0;i<segments.size();i++){
			DataPoint dp1 = new DataPoint(2);
			DataPoint dp2 = new DataPoint(2);

			dp1.set(0, segments.get(i).getP1().getX());
			dp1.set(1, segments.get(i).getP1().getY());
			dp2.set(0,segments.get(i).getP2().getX());
			dp2.set(1,segments.get(i).getP2().getY());
			Segment s = new Segment(dp1,dp2,null);
			MkDataPoint mkp = new MkDataPoint(s.getHPoint(),s);
			corpus.add(mkp);
			MkCorpus.add(mkp);
		}
	}

	public LinkedList<MkDataPoint> getMkCorpus() {
		return MkCorpus;
	}

	public DataMk(String fileName) throws IOException {
		super();
		MkCorpus = new LinkedList<MkDataPoint>();
		readXmlMk(fileName);

		//readImageSegment(fileName);


		// TODO Auto-generated constructor stub
	}

	/**
	 * reading datafile
	 *
	 * @author moreau
	 * @version 1
	 *
	 * @param fileName name of the file
	 *
	 */
	public void readXmlMk(String url) {
		Document document = null;
		try {
			/*
			 *
			 * lecture du fichier xml � partir d'une url
			URL urlDesc = new URL(url);
			URLConnection connection = urlDesc.openConnection();
			InputStream flux = connection.getInputStream();
			 */


			//lecture du fichier xml en local
			BufferedReader bf = new BufferedReader(new FileReader(url));
			DOMParser parser = new DOMParser();
			parser.parse(new InputSource(bf));
			document = parser.getDocument();
			NodeList nl = document.getElementsByTagName("Coordonnees");
			System.out.println("--debug-- "+nl.getLength()+" segments in file");
			for (int i=0 ; i<nl.getLength() ; i++) {
				DataPoint dp1 = new DataPoint(2);
				DataPoint dp2 = new DataPoint(2);
				Node n = nl.item(i);
				NamedNodeMap nnm = n.getAttributes();
				dp1.set(0, Double.parseDouble(nnm.getNamedItem("Segments-xp1").getNodeValue()));
				dp1.set(1, Double.parseDouble(nnm.getNamedItem("Segments-yp1").getNodeValue()));
				dp2.set(0, Double.parseDouble(nnm.getNamedItem("Segments-xp2").getNodeValue()));
				dp2.set(1, Double.parseDouble(nnm.getNamedItem("Segments-yp2").getNodeValue()));
				Segment s = new Segment(dp1,dp2,null);
				MkDataPoint mkp = new MkDataPoint(s.getHPoint(),s);
				corpus.add(mkp);
				MkCorpus.add(mkp);
			}
		} catch (Exception e) {
			System.out.println("damn it!");
			e.printStackTrace();
		}
	}

	/*L�o Collet, C�dric T�l�gone,
	 * Lecture des segments � partir d'un image segment.
	 *
	 *
	 */
	public void readImageSegment(String path){

		HashMap<Integer, Vector<SegmentDetection.Segment>>  segments;

		ImageSegment image=new ImageSegment(path);
		image.getLargeConnectedEdges(false,8);
		segments=image.getFinalSegmentMap();




		for (Iterator<Map.Entry<Integer, Vector<SegmentDetection.Segment>>> iter = segments.entrySet().iterator(); iter.hasNext();){
			Map.Entry<Integer, Vector<SegmentDetection.Segment>> ent = (Map.Entry<Integer, Vector<SegmentDetection.Segment>>) iter.next();
			for (int i=0; i<ent.getValue().size(); i++){
				DataPoint dp1 = new DataPoint(2);
				DataPoint dp2 = new DataPoint(2);

				dp1.set(0, ent.getValue().get(i).getStartPoint().getX());
				dp1.set(1, ent.getValue().get(i).getStartPoint().getY());
				dp2.set(0, ent.getValue().get(i).getEndPoint().getX());
				dp2.set(1, ent.getValue().get(i).getEndPoint().getY());
				Segment s = new Segment(dp1,dp2,null);
				MkDataPoint mkp = new MkDataPoint(s.getHPoint(),s);
				corpus.add(mkp);
				MkCorpus.add(mkp);
			}
		}
	}

}
