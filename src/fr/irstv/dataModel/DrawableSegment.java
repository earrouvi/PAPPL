package fr.irstv.dataModel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;


public class DrawableSegment {
	
	private int x1;
	private int y1;
	private int x2;
	private int y2;
	private Color color;
	private Stroke stroke; 
	
	public DrawableSegment(Segment segment, Color color, float pw, WorkingArea wa){
		this.x1 = (int) segment.getBeginPoint().get(0)+(int)wa.getZeroPoint().get(0);
		this.y1 = (int) segment.getBeginPoint().get(1)+(int)wa.getZeroPoint().get(1);
		this.x2 = (int) segment.getEndPoint().get(0)+(int)wa.getZeroPoint().get(0);
		this.y2 = (int) segment.getEndPoint().get(1)+(int)wa.getZeroPoint().get(1);
		this.color = color;
		this.stroke = new BasicStroke(pw); 
		
	}
	
	public void paint(Graphics2D g2d) 
	{ 
		g2d.setStroke(stroke); 
		g2d.setColor(getColor()); 
		g2d.drawLine(x1,y1,x2,y2); 
	 }


	public int getX1() {
		return x1;
	}

	public void setX1(int x1) {
		this.x1 = x1;
	}

	public int getX2() {
		return x2;
	}

	public void setX2(int x2) {
		this.x2 = x2;
	}

	public int getY1() {
		return y1;
	}

	public void setY1(int y1) {
		this.y1 = y1;
	}

	public int getY2() {
		return y2;
	}

	public void setY2(int y2) {
		this.y2 = y2;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	} 

}
