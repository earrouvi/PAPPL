package fr.irstv.dataModel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

public class DrawableDataPoint {

	private DataPoint point = new DataPoint(2);
	private Color color;
	private Stroke stroke; 
	private int size;
	//private WorkingArea wa;
	
	public DrawableDataPoint(DataPoint point, Color color, float pw, WorkingArea wa, int size){
		//this.wa = wa;
		this.point.set(0, point.get(0)+wa.getZeroPoint().get(0));
		this.point.set(1, point.get(1)+wa.getZeroPoint().get(1));
		this.color = color;
		this.stroke = new BasicStroke(pw); 
		this.size = size;
		
	}
	
	public void paint(Graphics2D g2d) 
	{ 
		g2d.setStroke(stroke); 
		g2d.setColor(getColor()); 
		g2d.fillOval((int)this.point.get(0)-size/2, (int)this.point.get(1)-size/2, size, size);
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public DataPoint getPoint() {
		return point;
	}

	public void setPoint(DataPoint point) {
		this.point = point;
	}

	public Stroke getStroke() {
		return stroke;
	}

	public void setStroke(Stroke stroke) {
		this.stroke = stroke;
	}

}
