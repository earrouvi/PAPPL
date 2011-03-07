package pg.modules;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import pg.data.Line;
import pg.data.Pixel;
import pg.data.Point;
import pg.gui.Menu;


import ij.ImagePlus;
import ij.gui.GUI;
import ij.gui.ImageCanvas;
import ij.gui.ImageWindow;
import ij.plugin.frame.PlugInFrame;
import ij.process.ImageProcessor;


public class HeightComputation implements MouseListener{

	protected Point b1;
	protected Point b2;
	protected Point t1;
	protected Point t2;
	protected Point v;
	protected Line l;
	protected ImageProcessor processor;
	protected ImageCanvas canvas;
	protected ImageWindow window;
	protected ImagePlus image;
	protected int activatedButton=0;
	protected String path;



	public HeightComputation(String path,Line l){
		this.path=path;

		image=new ImagePlus(path);
		processor=image.getProcessor();

		this.l=l;
		draw(l);
		drawString(l,"linf");

		Menu menu=new Menu(this);
		/*this.b1=b1;
		this.b2=b2;
		this.t1=t1;
		this.t2=t2;
		this.v=v;
		this.l=l;
		*/

		show();


	}



	public void process(){
		if(b1!=null&&b2!=null&&t1!=null&&t2!=null){

		this.v=b1.cross(t1).cross(b2.cross(t2));
		double ratio;

		//1 compute the orthogonal vanishing point
		Point u;
		u=b1.cross(b2).cross(l);

		Line l2=v.cross(b2);
		Point t1_;
		t1_=t1.cross(u).cross(l2);

		//3 Represent the four points b2, t1_, t2 and v by their distance from b2
		double dt1_=b2.distance(t1_);
		double dt2=b2.distance(t2);
		double dv=b2.distance(v);

		//4 scaled distance
		ratio=dt1_*(dv-dt2)/(dt2*(dv-dt1_));

		System.out.println("ratio : "+ratio);
		}

		}




	public void setActivatedButton(int i){
		activatedButton=i;
	}

	public void draw(Point pt1, Point pt2){
		if(pt1.getZ()!=0&&pt2.getZ()!=0){
		Pixel p1=pt1.toPixel();
		Pixel p2=pt2.toPixel();
				processor.drawLine(p1.getX(),p1.getY(),p2.getX(),p2.getY());

	}
	}

	public void draw(Line l){
		Point p1=l.cross(new Line(1,0,0));

		Point p2=l.cross(new Line(1,0,-800));

		draw(p1,p2);
	}

	public void drawString(Point pt,String s){
		Pixel p=pt.toPixel();
		processor.drawString(s,p.getX()+5,p.getY()+5);

	}

	public void drawString(Line l, String s){
		Point p1=l.cross(new Line(1,0,0));
		drawString(p1,s);

	}

	public void update(){
		if(b1!=null&&t1!=null){
			draw(b1,t1);
			drawString(b1,"b1");
			drawString(t1,"t1");

		}
		if(b2!=null&&t2!=null){
			draw(b2,t2);
			drawString(b2,"b2");
			drawString(t2,"t2");
		}
	}


	public void show(){

		window=new ImageWindow(new ImagePlus("image",processor));
		window.setResizable(false);
		canvas = window.getCanvas();
		canvas.addMouseListener(this);

	}



	@Override
	public void mouseClicked(MouseEvent e) {

		if(activatedButton!=0){
		int x = e.getX();
		int y = e.getY();
		int offscreenX = canvas.offScreenX(x);
		int offscreenY = canvas.offScreenY(y);

		switch (activatedButton) {
        case 1:  if(b1==null) b1=new Point(offscreenX,offscreenY); break;
        case 2:  if(b2==null) b2=new Point(offscreenX,offscreenY); break;
        case 3:  if(t1==null) t1=new Point(offscreenX,offscreenY); break;
        case 4:  if(t2==null) t2=new Point(offscreenX,offscreenY); break;
		}

		activatedButton=0;
		update();
		canvas.repaint();



		}

		}
		// TODO Auto-generated method stub



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


}
