package pg.modules;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import pg.data.Homography;
import pg.data.Line;
import pg.data.Pixel;
import pg.data.PlanToPlanHomography;
import pg.data.Point;
import pg.data.Polygon;
import pg.data.SegmentPG;
import pg.gui.ImageCanvasPG;
import pg.gui.ImageWindowPG;
import pg.gui.Menu2;

import Jama.Matrix;
import ij.ImagePlus;
import ij.ImageStack;
import ij.gui.ImageCanvas;
import ij.gui.ImageWindow;
import ij.gui.NewImage;
import ij.gui.PolygonRoi;
import ij.gui.Roi;
import ij.gui.StackWindow;
import ij.process.ImageProcessor;


public class RemovePerspectiveDistortion implements MouseListener, MouseMotionListener{

	//A class to remove perspective distortion on a facade in an image.

	protected int h=200;
	protected int w=300;
	protected Point x1=new Point(0,0);
	protected Point x2=new Point(0,0);
	protected Point x3=new Point(0,0);
	protected Point x4=new Point(0,0);
	protected List<Point> vanishing_points=new ArrayList<Point>();
	protected Point v;
	protected Point x1_;
	protected Point x2_;
	protected Point x3_;
	protected Point x4_;
	protected String path;
	protected ImageProcessor processor;
	protected ImagePlus image;
	protected int activatedButton=0;
	protected ImageCanvasPG canvas;
	protected ImageWindow window;
	protected ImagePlus image_;
	protected ImageProcessor processor_;
	protected ImageWindow window_;

	public RemovePerspectiveDistortion(String path){


		this.path=path;
		image=new ImagePlus(path);
		canvas =new ImageCanvasPG(image);

		Polygon p=new Polygon();
		p.add(x1);
		p.add(x2);
		p.add(x3);
		p.add(x4);

		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);
		canvas.add(x1);
		canvas.add(x2);
		canvas.add(x3);
		canvas.add(x4);
		canvas.add(p);
		canvas.add(new SegmentPG(x1,x2));
		canvas.add(new SegmentPG(x2,x3));
		canvas.add(new SegmentPG(x3,x4));
		canvas.add(new SegmentPG(x4,x1));

		Point p1l11=new Point(129,501);
		Point p2l11=new Point(624,461);
		Line l11=p1l11.cross(p2l11);


		Point p1l12=new Point(321,533);
		Point p2l12=new Point(742,476);
Line l12=p1l12.cross(p2l12);

		//Premier point de fuite du plan horizontal
		v=l11.cross(l12);


		processor=image.getProcessor();




		x1_=new Point(1,1,1);
		x2_=new Point(w,1,1);
		x3_=new Point(w,h,1);
		x4_=new Point(1,h,1);

		Menu2 menu=new Menu2(this);

		show();





}

	public ImageWindow getWindow(){
		return window;
	}

	public void reset(){
		x1.notDrawable();
		x2.notDrawable();
		x3.notDrawable();
		x4.notDrawable();
		canvas.repaint();

	}

		public void compute(){
			image=new ImagePlus(path);
			processor=image.getProcessor();
			image_=NewImage.createRGBImage("Distortion Removed", 300, 200, 2, 0);
			processor_=image_.getProcessor();
		Point pcur;
		Point pcur_;
		Pixel pixcur;
		int value;

		PlanToPlanHomography ptph= new PlanToPlanHomography(x1,x1_,x2,x2_,x3,x3_,x4,x4_);
		Homography hom=ptph.compute();


		hom.print();
		Homography hom_=hom.invert();
		//hom_.print();





		 for(int i=0;i<w-1;i++){
			for(int j=0;j<h-1;j++){
				pcur_=new Point(i,j,1);
				pcur=pcur_.homography(hom_);
				pcur.normalize();

				pixcur=pcur.toPixel();
				value=processor.getPixel(pixcur.getX(), pixcur.getY());
				processor_.setValue(value);
				processor_.drawPixel(i,j);


			}
		}




		ImageWindow window_=new ImageWindow(new ImagePlus("Distortion removed",processor_));
		window_.setResizable(false);


	}




	public void setActivatedButton(int i){
		activatedButton=i;
	}



	public void show(){



		window=new ImageWindowPG(image,canvas);
		window.setResizable(false);











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
	        case 1:x1.getVec().setX(offscreenX);x1.getVec().setY(offscreenY);x1.drawable();break;
	        case 2:x2.getVec().setX(offscreenX);x2.getVec().setY(offscreenY);x2.drawable();break;
	        case 3:x3.getVec().setX(offscreenX);x3.getVec().setY(offscreenY);x3.drawable();break;
	        case 4:x4.getVec().setX(offscreenX);x4.getVec().setY(offscreenY);x4.drawable();break;
			}

			activatedButton=0;


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
			Point curPos=new Point(offscreenX,offscreenY);


			switch (activatedButton) {
	        case 1:x1.getVec().setX(offscreenX);x1.getVec().setY(offscreenY);x1.drawable();break;
	        case 2:
	        	/*
	        	Line curLine=v.cross(x1);
	        	Vector c=curLine.getVec();
	        	x2.getVec().setY((-c.getX()*offscreenX-c.getZ())/c.getY())
	        	*/

	        	x2.getVec().setX(offscreenX);

	        	x2.getVec().setY(offscreenY);;x2.drawable();break;
	        case 3:x3.getVec().setX(offscreenX);x3.getVec().setY(offscreenY);x3.drawable();break;
	        case 4:x4.getVec().setX(offscreenX);x4.getVec().setY(offscreenY);x4.drawable();break;
			}




			canvas.repaint();



			}

	}



}
