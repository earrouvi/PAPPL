package Scissors.GUI;

import ij.IJ;
import ij.ImagePlus;
import ij.gui.ImageCanvas;
import ij.gui.Toolbar;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import Scissors.algo.ScissorController;
/**
 * An Image Canvas for Scissor, on which we can run scissor
 * @author CAO
 *
 */
@SuppressWarnings("serial")
public class ScissorImageCanvas extends ImageCanvas{

    private ScissorController scissor;
    public final int SCISSOR_ACTIVE=1,HOLD=0,SCISSOR_ACTIVE2=2;

    /** Constructor of ScissorImageCanvas, Creates a new instance of ScissorImageCanvas */
    public ScissorImageCanvas(ImagePlus img,ScissorController scissor){//, Vector typeVector, Vector displayList) {
    	super(img);
        this.scissor=scissor;
        scissor.updateMagnification(this.magnification,this.srcRect);
    }
    /**
     * Action performed when mouse is pressed on the canvas
     */
    public void mousePressed(MouseEvent e) {

    }
    /**
     * Action performed when mouse is released on the canvas
     */
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        scissor.updateMagnification(this.magnification,this.srcRect);
        int x= e.getX();
        int y = e.getY();
 
        if (e.getClickCount()==2)
        	scissor.endScissor();
        else if (e.getClickCount()==1)
           	scissor.addNewKeyPoint(x, y);
        this.repaint();
    }
    /**
     * Action performed when mouse is moved on the canvas
     */
    public void mouseMoved(MouseEvent e) {
        super.mouseMoved(e);
        int x = e.getX();
        int y = e.getY();
        if (scissor.isDoing())
        {
        	scissor.setMovePoint(x, y);
        	this.repaint();
        }
    }
    /**
     * Action performed when mouse exits the canvas
     */
    public void mouseExited(MouseEvent e) {
        super.mouseExited(e);
    }
    /**
     * Action performed when mouse enters the canvas
     */
    public void mouseEntered(MouseEvent e) {
        super.mouseEntered(e);
        if (!IJ.spaceBarDown() | Toolbar.getToolId()!=Toolbar.MAGNIFIER | Toolbar.getToolId()!=Toolbar.HAND)
            setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    }
    /**
     * Action performed when mouse is dragged on  the canvas
     */
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);
    }
    /**
     * Action performed when mouse is clicked on  the canvas
     */
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
    }
    /**
     * Function for enlarging or reducing a polygon when we use the magnifying glass
     * @param poly Polygon
     */
    public void reOffScreenPoly(Polygon poly)
    {
    	for(int i=0;i<poly.npoints;i++)
    	{
    		poly.xpoints[i]=(int) ((poly.xpoints[i]-srcRect.x)*magnification);
    		poly.ypoints[i]=(int) ((poly.ypoints[i]-srcRect.y)*magnification);

    	}
    }
    /**
     * Function for enlarging or reducing a polygon when we use the magnifying glass
     * @param poly  Polygon
     * @return  polygon
     */
    public Polygon newReOffScreenPoly(Polygon poly)
    {
    	Polygon poly2=new Polygon();
    	for(int i=0;i<poly.npoints;i++)
    		poly2.addPoint((int) ((poly.xpoints[i]-srcRect.x)*magnification), (int) ((poly.ypoints[i]-srcRect.y)*magnification));
    	return poly2;
    }
    private Rectangle srcRect = new Rectangle(0, 0, 0, 0);
    /**
     *  Paint scissorline on the canvas
     */
    public void paint(Graphics g){	
        super.paint(g);
        scissor.paint(g);
    }
    
    public ScissorController getScissor() {
    	return scissor;
    }
}

