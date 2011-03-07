package Scissors.algo;

//import ij.IJ;
//import ij.ImagePlus;
import ij.gui.PolygonRoi;
//import ij.process.ImageProcessor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
/**
 * ScissorPolygon inherts from Polygon class. It provides additional
 * basic operations of a Polygon such as find the last point, find the separation point 
 * between two Polygon.
 * @author LIU Xinchang
 *
 */
class ScissorPolygon extends Polygon
{
	/**
	 * Version 1.0
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Same as the constructor of Polygon
	 */
	ScissorPolygon()
	{
		super();
	}
	/**
	 * 
	 * Same as the constructor of Polygon
	 * @param x int array, x coordinates of vertices of a polygon
	 * @param y int array, y coordinates of vertices of a polygon
	 * @param n int number, number of vertices
	 */
	ScissorPolygon(int[] x,int[] y,int n)
	{
		super(x,y,n);
	}
	/**
	 * @return x coordinate of last vertex 
	 */
	public int getBeginX()
	{
		return this.xpoints[npoints-1];
	}
	/**
	 * @return y coordinate of last vertex 
	 */
	public int getBeginY()
	{
		return this.ypoints[npoints-1];
	}
	/**
	 * Append a polygon to this polygon<br>
	 * if we use p1.appends(p2), we store first the 
	 * points of p2, then points of p1.
	 * @param sp a polygon to be appended
	 */
	public void appends(ScissorPolygon sp)
	{
		int n=this.npoints+sp.npoints;
		int[] xp=new int[n],yp=new int[n];
		for (int i=0;i<sp.npoints;i++)
		{
			xp[i]=sp.xpoints[i];
			yp[i]=sp.ypoints[i];
		}
		for (int i=sp.npoints;i<n;i++)
		{
			xp[i]=xpoints[i-sp.npoints];
			yp[i]=ypoints[i-sp.npoints];
		}
		this.xpoints=xp;
		this.ypoints=yp;
		this.npoints=n;
	}
	/**
	 * If the point (x,y) is a vertex of this polygon, return true, otherwise false.
	 * @param x a int number, x coordinate
	 * @param y	a int number, y coordinate
	 * @return  a boolean value to indicate if the point is a vertex of the polygon
	 */
	public boolean containVertex(int x,int y)
	{
		for (int i=0;i<npoints;i++)
			if (xpoints[i]==x && ypoints[i]==y)
				return true;
		return false;
	}	
	/**
	 * If the point (x,y) is a vertex of this polygon and isn't included in last n vertices, return true, otherwise false.
	 * @param x a int number, x coordinate
	 * @param y	a int number, y coordinate
	 * @param n a int number, number of last vertices
	 * @return  a boolean value to indicate if the point is a vertex of the polygon and isn't included in last n vertices
	 */
	public boolean containVertex(int x,int y,int n)
	{
		for (int i=0;i<npoints-n;i++)
			if (xpoints[i]==x && ypoints[i]==y)
				return true;
		return false;
	}
	/**
	 * Find the last same vertex of this polygon and another polygon.
	 * This vertex is considered as a separationPoint.
	 * @param p another polygon
	 * @return the separation point
	 */
	public Point separationPoint(Polygon p)
	{
		return separationPoint(p,p.npoints);
	}
	/**
	 * Find the last same vertex of this polygon and another polygon and
	 * this vertex is belonged to last n vertices.
	 * @param p another polygon
	 * @param n int number, number of last vertices
	 * @return the separation point
	 */
	public Point separationPoint(Polygon p,int n)
	{
		Point point=new Point();
		int i=0;
		while (i<p.npoints-n && !containVertex(p.xpoints[i],p.ypoints[i]))
			i++;
		if (i<p.npoints-n)
			point.setLocation(p.xpoints[i],p.ypoints[i]);
		else
			point=null;
		return point;
	}
	
}
/**
 * AutoKeyVertex is a countable point, which help to determine whether 
 * a vertex satisfies the conditions to be a key vertex.
 * @author LIU Xinchang
 *
 */
class AutoKeyVertex{
	private int x;
	private int y;
	private int count; 
	private int maxCount=5;
	public AutoKeyVertex(Point p)
	{
		this(p.x,p.y);
	}
	public AutoKeyVertex(int x,int y)
	{
		this.x=x;
		this.y=y;
		count=0;
	}
	/**
	 * Count the appear times
	 * @return true if more than max appear times
	 */
	public boolean  count()
	{
		this.count++;
		return (count>maxCount);
	}
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
}

/**
 * Class for control one track of scissor tool from begin to end.
 * This class provides method to add, remove points, auto generation
 * of key points, auto remove recent point and so on. Paint method
 * can be call if user want to show this track. 
 * @author LIU Xinchang
 *
 */
public class ScissorLine{
	//the operation is determined by current state 
	private SCISSOR_STATE state;
	
	private ScissorPolygon currentScissorLine;
	private ArrayList<ScissorPolygon> scissorLine; //this array list stores polygons from one key point to next key point
	
	private Color currentLineColor;
	private Color lineColor;
	private double magnification;	//scale parameter
	private Rectangle srcRect;		//translation parameter
	private Scissor scissor;		//each polygon is obtained from the result of algorithm in class Scissor 
	
	/*
	 * interactive operation variable, used detect mouse motion 
	 */
	private int mouseCount;			 
	private int mouseCountMax;
	private int endCounter;
	private ArrayList<AutoKeyVertex> autoKeyVertexList;
	private int removeCounter;

	/**
	 * Constructor of ScissorLine, initial parameters
	 * @param s Scissor associated to current graph
	 */
	public ScissorLine(Scissor s){
		scissor=s;
		lineColor=Color.yellow;
		currentLineColor=Color.red;
		srcRect=new Rectangle(0,0,0,0);
		magnification=1;
		scissorLine=new ArrayList<ScissorPolygon>();
		state=SCISSOR_STATE.HOLD;
		mouseCount=0;
		mouseCountMax=4;
		autoKeyVertexList=new ArrayList<AutoKeyVertex>();
		
	}
	/**
	 *Add a new key point in scissor track.
	 * @param x	x coordinate
	 * @param y y coordinate
	 */
	public void addNewKeyPoint(int x,int y)
	{
        x = offScreenX(x);
        y = offScreenY(y);
        
		if (state==SCISSOR_STATE.BEGIN)	//just after the scissor tool begins 	
    	{
			reset();
			scissor.setBegin(x, y);
    		currentScissorLine=scissor.getPathsList(x, y);

    		state=SCISSOR_STATE.DOING;
    		this.autoKeyVertexList.clear();
    		removeCounter=0;
    		endCounter=0;
        	
    	}else if(state ==SCISSOR_STATE.DOING)
    	{
    		ScissorPolygon tempPath=scissor.getPathsList(x, y);
			scissorLine.add(tempPath);
			scissor.setBegin(x, y);
    		currentScissorLine=scissor.getPathsList(x, y);
    		this.autoKeyVertexList.clear();
    		removeCounter=0;
    		endCounter=0;
    	}
	}
	/**
	 * When mouse move, call this method for generating changeable track
	 * and handle the interactive function.
	 * @param x	x coordinate
	 * @param y y coordinate
	 */
	public void setMovePoint(int x,int y)
	{
        x = offScreenX(x);
        y = offScreenY(y);
        //Check current state
		if (state==SCISSOR_STATE.DOING)
		{	
			//Backup current changeable scissor line
           	ScissorPolygon tempSp=currentScissorLine;
           	//update current changeable scissor line
        	currentScissorLine=scissor.getPathsList(x, y);
        	//Auto generate key point
        	if(mouseCount++>mouseCountMax)
        	{
        		mouseCount=0;
        		Point p=currentScissorLine.separationPoint(tempSp, 10);
        		if (p!=null)
        		{
        			AutoKeyVertex v=new AutoKeyVertex(p);
        			this.autoKeyVertexList.add(v);
        		}
        		int i=0;
        		//Check whether ancient key points are still on the track
        		while(i<autoKeyVertexList.size())
        		{
	        		AutoKeyVertex v =autoKeyVertexList.get(i);
	        		if (currentScissorLine.containVertex(v.getX(), v.getY(),10))
	    			{
	    				if (v.count())
	    				{
	    					Rectangle r=currentScissorLine.getBounds();
	    					this.addNewKeyPoint(reOffScreenX(v.getX()), reOffScreenY(v.getY()));
	    					scissor.setActiveRegion(r.x, r.y);
	    					scissor.setActiveRegion(r.x+r.width, r.y+r.height);
	    					autoKeyVertexList.remove(v);
	    				}
	    				i++;
	    			}
	    			else
	    				autoKeyVertexList.remove(v);	
        		}
        	}
        	//Auto remove last key point if the cursor is close to it
    		if (removeCounter<10)
    		{
        		if (currentScissorLine.npoints<6)
        			removeCounter++;
    		}
    		else
    		{
    			this.removeRecentKeyPoint();
    			removeCounter=0;
    		}
    		//Auto end the scissor track if the cursor is close to begin point
    		if (scissorLine.size()>1)
    		{
	    		if(endCounter<10)
	    		{
	    			ScissorPolygon sp=scissorLine.get(0);
	        		if (x-sp.getBeginX()<5 && sp.getBeginX()-x<5 && sp.getBeginY()-y<5 && y-sp.getBeginY()<5)
	        			endCounter++;
	    		}
	    		else
	    		{
	    			this.endScissor();
	    			endCounter=0;
	    		}
    		}
		}
	}
	/**
	 * End scissor process and connect last point to begin point
	 */
	public void endScissor()
	{
		if (state==SCISSOR_STATE.DOING)
		{
			currentScissorLine=null;
			ScissorPolygon pg=scissorLine.get(0);
			scissorLine.add(scissor.getPathsList(pg.getBeginX(), pg.getBeginY()));
			state=SCISSOR_STATE.HOLD;
		}
	}	
	// Getter for scissorLine
	public ArrayList<ScissorPolygon> getScissorLine() {
		return scissorLine;
	}
	/**
	 * If current line is not from begin point, Remove recent key point.
	 */
	public void removeRecentKeyPoint()
	{
		if (scissorLine.size()>1)
		{
			scissorLine.remove(scissorLine.size()-1);
			ScissorPolygon pg=scissorLine.get(scissorLine.size()-1);
			scissor.setBegin(pg.xpoints[0],pg.ypoints[0]);
		}
		else if (scissorLine.size()==1)
		{
			ScissorPolygon pg=scissorLine.get(0);
			scissor.setBegin(pg.getBeginX(),pg.getBeginY());
			scissorLine.remove(scissorLine.size()-1);
		}
	}
	/**
	 * Reset scissorLine, clear all tracks.
	 */
	public void reset()
	{
		this.scissorLine.clear();
		currentScissorLine=null;
		this.state=SCISSOR_STATE.HOLD;
	}
	/**
	 * Paint all tracks,  default color is yellow for tracks and
	 * red for changeable part. A square is also painted at every
	 * key point.
	 * @param g Of type Graphics
	 */
	public void paint(Graphics g)
	{

	    Graphics2D g2 = (Graphics2D)g;
	    g2.setColor(lineColor);
	    g2.setStroke(new BasicStroke(1f));
	    
	    Iterator<ScissorPolygon> i=scissorLine.iterator();
	    while (i.hasNext())
	    {
	    	Polygon pg=(Polygon) i.next();
	    	pg=newReOffScreenPoly(pg);
	    	g2.drawRect(pg.xpoints[0]-2, pg.ypoints[0]-2,4,4);
	    	g2.drawPolyline(pg.xpoints, pg.ypoints,pg.npoints);
	    }
	    g2.setColor(currentLineColor);
	    if (currentScissorLine!=null)
	    {
	    	Polygon pg=newReOffScreenPoly(currentScissorLine);
	    	g2.drawPolyline(pg.xpoints, pg.ypoints,pg.npoints);
	    }
	    
	}
    public Polygon newReOffScreenPoly(Polygon poly)
    {
    	Polygon poly2=new Polygon();
    	for(int i=0;i<poly.npoints;i++)
    		poly2.addPoint(reOffScreenX(poly.xpoints[i]), reOffScreenY(poly.ypoints[i]));
    	return poly2;
    }
    /**
     * Translation from screen view coordinate to image coordinate 
     * @param x x coordinate in screen view
     * @return x coordinate in image 
     */
    public int offScreenX(int x)
    {
		return (int) (x/magnification +srcRect.x);
    }
    /**
     * Translation from screen view coordinate to image coordinate 
     * @param y y coordinate in screen view
     * @return y coordinate in image 
     */
    public int offScreenY(int y)
    {
		return (int) (y/magnification +srcRect.y);
    }
    /**
     * Translation from image coordinate to screen view coordinate
     * @param x x coordinate in image
     * @return  x coordinate in screen view
     */
    public int reOffScreenX(int x)
    {
		return (int) ( (x-srcRect.x)*magnification);
    }
    /**
     * Translation from image coordinate to screen view coordinate
     * @param y y coordinate in image
     * @return  y coordinate in screen view
     */
    public int reOffScreenY(int y)
    {
		return (int) ((y-srcRect.y)*magnification);
    }  
    /**
     * Set zoom parameter.
     * @param m double number of magnification
     */
    public void setMagnification(double m)
    {
    	this.magnification=m;
    }
    /**
     * Set screen rectangle which defines translation parameter.
     * @param r Rectangle
     */
    public void setSrcRect(Rectangle r)
    {
    	this.srcRect=r;
    } 
	/**
	 * Set current state to a user defined state.
	 * @param s state of type SCISSOR_STATE
	 */
	public void setState(SCISSOR_STATE s)
	{
		state=s;
	}
	/**
	 * Set current state to BEGIN.
	 */
	public void setActive()
	{
		state=SCISSOR_STATE.BEGIN;
	}
	/**
	 * Set current state to HOLD.
	 */
	public void setHold()
	{
		state=SCISSOR_STATE.HOLD;
	}
	/**
	 * Check current state
	 * @return current state
	 */
	public SCISSOR_STATE getState()
	{
		return state;
	}
	/**
	 * Check current state
	 * @return true if surrent state is isDoing
	 */
	public boolean isDoing()
	{
		return (state==SCISSOR_STATE.DOING);
	}
	/**
	 * Convert scissor track to a free ROI of ImageJ
	 * @return A PolygonRoi 
	 */
	public PolygonRoi toRoi()
	{
		ScissorPolygon pg=new ScissorPolygon();
		for (int i=0;i<this.scissorLine.size();i++)
			pg.appends(scissorLine.get(i));
		PolygonRoi pr=new PolygonRoi(pg, PolygonRoi.FREEROI);
		return pr;
	}
	/**
	 * Get the area in the scissor track
	 * @return int area
	 */
	public int getSurface()
	{
		int surface=0;
		byte[] b=(byte[]) toRoi().getMask().getPixels();
		for (int i=0;i<b.length;i++)
			if (b[i]!=0)
				surface++;
		return surface;
	}
	/**
	 * Set color of scissor track
	 * @param c A color
	 */
	public void setLineColor(Color c)
	{
		this.lineColor=c;
	}
	/**
	 * Set color of changeable part of track line
	 * @param c A color
	 */
	public void setCurrentLineColor(Color c)
	{
		this.currentLineColor=c;
	}
}
