package fr.irstv.view;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;
import java.util.LinkedList;

import javax.media.jai.InterpolationNearest;
import javax.media.jai.JAI;

import com.sun.media.jai.widget.DisplayJAI;

import fr.irstv.dataModel.DrawableDataPoint;
import fr.irstv.dataModel.DrawableSegment;
import fr.irstv.dataModel.Segment;

@SuppressWarnings("serial")
public class DisplayJAIWorkingAreaImage extends DisplayJAI{
	
	LinkedList<DrawableSegment> drawableSegmentsList;
	LinkedList<DrawableDataPoint> drawableVanishingPointList;
	LinkedList<DrawableDataPoint> drawableHPointList;
	
	public DisplayJAIWorkingAreaImage(RenderedImage workingAreaImage) {
		super(workingAreaImage);
		/*for(Segment segment : segmentsList){
			this.drawableSegmentsList.add(new DrawableSegment(segment, Color.red, 2));
		}*/
		drawableSegmentsList = new LinkedList<DrawableSegment>();
		drawableVanishingPointList = new LinkedList<DrawableDataPoint>();
		drawableHPointList = new LinkedList<DrawableDataPoint>();
		
		float scale = (float) 1;
		
		//pour appliquer l'echelle
		ParameterBlock pb = new ParameterBlock();
	    pb.addSource(workingAreaImage);
	    pb.add(scale);
	    pb.add(scale);
	    pb.add(0.0F);
	    pb.add(0.0F);
	    pb.add(new InterpolationNearest());
	    // Creates a new, scaled image and uses it on the DisplayJAI component
	    this.set(JAI.create("scale", pb));
	}
	
	public void paint(Graphics g){
		super.paint(g);
		Graphics2D g2d = (Graphics2D)g;
		for(DrawableSegment drawableSegment : this.drawableSegmentsList){
			drawableSegment.paint(g2d);
		}
		for(DrawableDataPoint ddp : this.drawableVanishingPointList){
			ddp.paint(g2d);
		}
		for(DrawableDataPoint ddp : this.drawableHPointList){
			ddp.paint(g2d);
		}
	}
	
	public void addDrawableSegment(DrawableSegment ds){
		this.drawableSegmentsList.add(ds);
	}
	
	public void addDrawableVanishingPoint(DrawableDataPoint ddp){
		this.drawableVanishingPointList.add(ddp);
	}
	
	public void addDrawableHPoint(DrawableDataPoint ddp){
		this.drawableHPointList.add(ddp);
	}

}
