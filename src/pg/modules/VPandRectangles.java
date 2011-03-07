package pg.modules;
import fr.irstv.dataModel.CircleKDistance;
import fr.irstv.dataModel.DataPoint;
import fr.irstv.dataModel.MkDataPoint;
import fr.irstv.dataModel.Segment;
import fr.irstv.kmeans.DataGroup;
import fr.irstv.kmeans.DataMk;
import fr.irstv.kmeans.RanSac;
import ij.IJ;
import ij.ImagePlus;
import ij.process.ImageProcessor;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;

import pg.data.Group;
import pg.data.Line;
import pg.data.Point;
import pg.data.Polygon;
import pg.data.SegmentPG;
import pg.gui.ImageCanvasPG;
import pg.gui.ImageWindowPG;
import pg.gui.Menu4;


import SegmentDetection.ImageSegment;

public class VPandRectangles implements MouseMotionListener, MouseListener{

	protected ImageCanvasPG canvas;
	protected ImagePlus image;
	protected ImageWindowPG window;
	protected String path;
	//Threshold for connected segments
	protected double threshold=20;
	//Datas groups and segments
	protected Group[] groups;
	protected Group dataSegments=new Group();
	//Colors for segment display
	protected Color[] colors={Color.blue, Color.red, Color.green, Color.yellow, Color.magenta, Color.pink, Color.cyan};
	//Number of vanishing point to detect
	protected int nbGroups=2;
	//Points used for the selection removal tool
	protected Point a=new Point(0,0);
	protected Point b=new Point(0,0);
	protected Point c=new Point(0,0);
	protected Point d=new Point(0,0);
	protected double maxLength=300;
	protected SegmentPG ab;
	protected Polygon selection=new Polygon();







	public VPandRectangles(String imageName, Boolean xml) throws IOException{

		path="Images/"+imageName+".jpg";
		String xmlPath="Images/"+imageName+".xml";
		image=new ImagePlus(path);
		canvas =new ImageCanvasPG(image);
		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);
		Menu4 menu=new Menu4(this);
		selection.add(a);
		selection.add(b);
		selection.add(c);
		selection.add(d);
		canvas.add(selection);


		//Do we load segments from xml file or do we launch the segment detection module?
		if(xml){
			segmentsFromXml(xmlPath);
		}else{
			segmentDetection();
		}

		//display window and canvas
		show();


	}

	public void computeVP(){
		//Clean current canvas
		canvas.clean();
		//Initialize variables
		CircleKDistance fd = new CircleKDistance();
		fd.setScilabCheck(true);
		DataMk dataSet=null;
		dataSet = new DataMk(dataSegments);
		long a = -System.currentTimeMillis();
		// here we launch the real job
		RanSac r = new RanSac(nbGroups,dataSet,fd);
		// param init
		r.setMaxSample(5);
		r.setSigma(40d);
		r.setStopThreshold(0.1d);
		r.go();

		DataGroup[] data_groups=r.getGroups();
		groups=new Group[nbGroups];


		//Convert DataGroup to Groupe
		for (int i=0 ; i<nbGroups; i++) {

			groups[i]=new Group();

			DataPoint c=data_groups[i].getCentroid();
			Point centroid=new Point(c.get(0),c.get(1));
			System.out.println("cenroid n"+i+" : "+c.get(0)+" , "+c.get(1));
			groups[i].setCentroid(centroid);
			List<DataPoint> ds=data_groups[i].getComponents();

			//addSegments(groups[i].getComponents());
			for (DataPoint dp : ds)
			{
				if (dp instanceof MkDataPoint)
				{
					Segment s = ((MkDataPoint) dp).getSeg();
					Point p1=new Point(s.getBeginPoint().get(0),s.getBeginPoint().get(1));
					Point p2=new Point(s.getEndPoint().get(0),s.getEndPoint().get(1));

					groups[i].add(new SegmentPG(p1,p2));


				}
			}

			//groupes[i].setColor(Color.red);
			//System.out.print(colors[i].toString());
			canvas.add(groups[i]);
			groups[i].setColor(colors[i]);


		}
		canvas.remove(dataSegments);
		canvas.repaint();
	}



	/*
	 *
	 */
	public void segmentsFromXml(String xmlPath) {
		Document document = null;
		try {
			//read xml file
			BufferedReader bf = new BufferedReader(new FileReader(xmlPath));
			DOMParser parser = new DOMParser();
			parser.parse(new InputSource(bf));
			document = parser.getDocument();
			NodeList nl = document.getElementsByTagName("Coordonnees");
			System.out.println("--debug-- "+nl.getLength()+" segments in file");
			for (int i=0 ; i<nl.getLength() ; i++) {

				Node n = nl.item(i);
				NamedNodeMap nnm = n.getAttributes();
				Point p2 = new Point(Double.parseDouble(nnm.getNamedItem("Segments-xp1").getNodeValue()),Double.parseDouble(nnm.getNamedItem("Segments-yp1").getNodeValue()));
				Point p1 = new Point(Double.parseDouble(nnm.getNamedItem("Segments-xp2").getNodeValue()),Double.parseDouble(nnm.getNamedItem("Segments-yp2").getNodeValue()));

				SegmentPG s = new SegmentPG(p1,p2);
				dataSegments.add(s);


			}
			canvas.add(dataSegments);

		} catch (Exception e) {
			System.out.println("damn it!");
			e.printStackTrace();
		}
	}

	/*
	 *
	 */
	public void segmentDetection(){

		HashMap<Integer, Vector<SegmentDetection.Segment>>  segments;

		ImageSegment imageSeg=new ImageSegment(path);
		imageSeg.getLargeConnectedEdges(false,8);
		segments=imageSeg.getFinalSegmentMap();




		for (Iterator<Map.Entry<Integer, Vector<SegmentDetection.Segment>>> iter = segments.entrySet().iterator(); iter.hasNext();){
			Map.Entry<Integer, Vector<SegmentDetection.Segment>> ent = (Map.Entry<Integer, Vector<SegmentDetection.Segment>>) iter.next();
			for (int i=0; i<ent.getValue().size(); i++){
				Point p1 = new Point(ent.getValue().get(i).getStartPoint().getX(),ent.getValue().get(i).getStartPoint().getY());
				Point p2 = new Point(ent.getValue().get(i).getEndPoint().getX(),ent.getValue().get(i).getEndPoint().getY());
				SegmentPG s = new SegmentPG(p1,p2);
				dataSegments.add(s);


			}
		}

		canvas.add(dataSegments);



	}

	public void show(){



		window=new ImageWindowPG(image,canvas);
		//window.setResizable(false);


	}

	public void computeRectangles(){

		for(int i=0;i<nbGroups-1;i++){

			for(int ii=0;ii<groups[i].getSeg().size()-1;ii++){

				for(int ij=ii+1;ij<groups[i].getSeg().size();ij++){

					SegmentPG s1=groups[i].getSeg().get(ii);
					SegmentPG s2=groups[i].getSeg().get(ij);


					for(int j=i+1;j<nbGroups;j++){

						for(int ji=0;ji<groups[j].getSeg().size()-1;ji++){

							for(int jj=ji+1;jj<groups[j].getSeg().size();jj++){

								SegmentPG s3=groups[j].getSeg().get(ji);
								SegmentPG s4=groups[j].getSeg().get(jj);

								if(s3.distance(s1)<threshold&&s4.distance(s1)<threshold&&s3.distance(s2)<threshold){

									Line l1,l2,l3,l4;
									l1=s1.getP1().cross(s1.getP2());
									l2=s2.getP1().cross(s2.getP2());
									l3=s3.getP1().cross(s3.getP2());
									l4=s4.getP1().cross(s4.getP2());

									Point p1,p2,p3,p4;
									p1=l1.cross(l3);
									p1.drawable();
									p2=l1.cross(l4);
									p2.drawable();
									p3=l2.cross(l3);
									p3.drawable();
									p4=l2.cross(l4);
									p4.drawable();

									if(p1.distance(p2)<maxLength&&p4.distance(p2)<maxLength&&p4.distance(p3)<maxLength&&p1.distance(p3)<maxLength){

										Polygon p=new Polygon();
										p.add(p1);
										p.add(p2);
										p.add(p4);
										p.add(p3);
										canvas.add(p);
									}

								}



							}


						}


					}
				}
			}








		}

		canvas.repaint();


	}

	@Override
	public void mouseDragged(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		int offscreenX = canvas.offScreenX(x);
		int offscreenY = canvas.offScreenY(y);


		b.getVec().setX(offscreenX);
		b.getVec().setY(a.getVec().getY());
		b.drawable();

		c.getVec().setX(offscreenX);
		c.getVec().setY(offscreenY);
		c.drawable();

		d.getVec().setX(a.getVec().getX());
		d.getVec().setY(offscreenY);
		d.drawable();


		canvas.repaint();






	}



	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub


	}

	@Override
	public void mouseEntered(MouseEvent e) {


	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		int x = e.getX();
		int y = e.getY();
		int offscreenX = canvas.offScreenX(x);
		int offscreenY = canvas.offScreenY(y);


		a.getVec().setX(offscreenX);
		a.getVec().setY(offscreenY);
		a.drawable();
		b.notDrawable();
		c.notDrawable();
		d.notDrawable();



		canvas.repaint();

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		int x = e.getX();
		int y = e.getY();
		int offscreenX = canvas.offScreenX(x);
		int offscreenY = canvas.offScreenY(y);


		b.getVec().setX(offscreenX);
		b.getVec().setY(a.getVec().getY());
		b.drawable();

		c.getVec().setX(offscreenX);
		c.getVec().setY(offscreenY);
		c.drawable();

		d.getVec().setX(a.getVec().getX());
		d.getVec().setY(offscreenY);
		d.notDrawable();

		dataSegments.removeSegments(a,c);

		canvas.repaint();

	}

	public void setNbGroups(int value) {
		// TODO Auto-generated method stub
		nbGroups=value;

	}



}
