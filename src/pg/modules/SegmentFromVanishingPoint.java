package pg.modules;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import pg.data.Line;
import pg.data.Point;
import pg.data.SegmentPG;
import pg.gui.ImageCanvasPG;
import pg.gui.ImageWindowPG;
import pg.gui.Menu3;

import ij.ImagePlus;
import ij.gui.ImageWindow;
import ij.process.ImageProcessor;


public class SegmentFromVanishingPoint implements MouseListener, MouseMotionListener{

	//this application test if a segment is associated to the attribute vanishing point.
	//the segment
	protected Point p1=new Point(0,0);
	protected Point p2=new Point(0,0);;
	//the vanishing point
	protected Point v;
	protected String path;
	protected ImagePlus image;
	protected ImageProcessor processor;
	//threshold must be between 0 and 1
	protected double threshold;
	protected double angle_threshold=5;
	protected int activatedButton=0;
	protected ImageCanvasPG canvas;
	protected ImageWindow window;
	protected Line l;


	public SegmentFromVanishingPoint(String path){

		this.path=path;
		threshold=1-Math.cos(angle_threshold*Math.PI/180);
		System.out.println(threshold);

		image=new ImagePlus(path);
		processor=image.getProcessor();
		canvas =new ImageCanvasPG(image);
		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);
		canvas.add(p1);
		canvas.add(p2);
		canvas.add(new SegmentPG(p1,p2));

		Menu3 menu=new Menu3(this);

		Point p1l11=new Point(129,501);
		p1l11.drawable();
		Point p2l11=new Point(624,461);
		p2l11.drawable();
		Line l11=p1l11.cross(p2l11);
		canvas.add(new SegmentPG(p1l11,p2l11));

		Point p1l12=new Point(321,533);
		Point p2l12=new Point(742,476);
		p1l12.drawable();
		p2l12.drawable();
		canvas.add(new SegmentPG(p1l12,p2l12));
		Line l12=p1l12.cross(p2l12);

		//Premier point de fuite du plan horizontal
		v=l11.cross(l12);

		show();



	}

	private void show() {
		// TODO Auto-generated method stub

		window=new ImageWindowPG(image,canvas);
		window.setResizable(false);

	}

	public void setActivatedButton(int i){
		activatedButton=i;
	}

	public void compute(){

		if(p1!=null&&p2!=null)
		{
			SegmentPG s=new SegmentPG(p1,p2);

			if(v.aligned(s,threshold)){
				System.out.println("oui");

			}else{
				System.out.println("non");
			}



		}

		}




	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(activatedButton!=0){
			int x = e.getX();
			int y = e.getY();
			int offscreenX = canvas.offScreenX(x);
			int offscreenY = canvas.offScreenY(y);

			switch (activatedButton) {
	        case 1:p1.getVec().setX(offscreenX);p1.getVec().setY(offscreenY);p1.drawable();activatedButton=2;break;
	        case 2:p2.getVec().setX(offscreenX);p2.getVec().setY(offscreenY);p2.drawable();activatedButton=0;break;

			}

			canvas.repaint();

	}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

		if(activatedButton!=0){
			window.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

			int x = e.getX();
			int y = e.getY();
			int offscreenX = canvas.offScreenX(x);
			int offscreenY = canvas.offScreenY(y);

			switch (activatedButton) {
	        case 1:p1.getVec().setX(offscreenX);p1.getVec().setY(offscreenY);p1.drawable();break;
	        case 2:p2.getVec().setX(offscreenX);p2.getVec().setY(offscreenY);p2.drawable();break;

			}




			canvas.repaint();



			}

	}




}
