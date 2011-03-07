package Scissors.algo;

import java.awt.*;
import java.util.*;
import Scissors.*;

public class VerticesList extends Thread {
	
	public ArrayList<ScissorPolygon> list;
	public ArrayList<Point> points;
	private Scissor_Frame sf;
	
	public VerticesList(Scissor_Frame sf) {
		super();
		this.sf = sf;
		list = new ArrayList<ScissorPolygon>();
		points = new ArrayList<Point>();
	}
	
	public void run() {
		boolean valid = false;
		while (!valid) {
			valid = sf.getScissorFrame().validCanvas;
			System.out.print("");
		}
		findPoints();
	}
	
	public void findPoints() { // this method only takes the first canvas
		list = sf.getScissorFrame().getIc().getScissor().getScissorElement().getScissorLine();
		// find points coordinates in the polygon list
		ScissorPolygon sp = list.get(0);
		for (int i=0;i<sp.npoints;i++) {
			points.add(new Point(sp.xpoints[i], sp.ypoints[i]));
		}
	}

}
