package Scissors.algo;

import java.awt.*;
import java.util.*;
import Scissors.*;

public class VerticesList extends Thread {
	
	private ArrayList<ScissorPolygon> list;
	private ArrayList<Point> points;
	private Scissor_Frame sf;
	
	public ArrayList<ScissorPolygon> getList() {
		return list;
	}

	public ArrayList<Point> getPoints() {
		return points;
	}

	public Scissor_Frame getSf() {
		return sf;
	}

	public VerticesList() {
		super();
		this.sf = new Scissor_Frame();
		list = new ArrayList<ScissorPolygon>();
		points = new ArrayList<Point>();
	}
	
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
		for (int j=0;j<list.size();j++) {
			ScissorPolygon sp = list.get(j);
			for (int i=0;i<sp.npoints;i++) {
				points.add(new Point(sp.xpoints[i], sp.ypoints[i]));
			}
		}
	}

}
