package pg.gui;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import pg.data.Drawable;

import ij.ImagePlus;
import ij.gui.ImageCanvas;


public class ImageCanvasPG extends ImageCanvas{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private List<Drawable> drawables;

	public ImageCanvasPG(ImagePlus imp) {

		super(imp);
		drawables=new ArrayList<Drawable>();
		// TODO Auto-generated constructor stub
	}

	public void paint(Graphics g){
		super.paint(g);
		if(drawables.size()>0)
		{
			for(int i=0;i<drawables.size();i++)
			{
				if(drawables.get(i)!=null)
					drawables.get(i).paint(g,magnification);

			}

		}
	}

	public void add(Drawable d){
		drawables.add(d);
	}

	public void remove(Drawable d){
		drawables.remove(d);
	}

	public void clean(){
		drawables=new ArrayList<Drawable>();
	}









}
