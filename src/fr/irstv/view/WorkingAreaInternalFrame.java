package fr.irstv.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.DataBuffer;
import java.awt.image.WritableRaster;
import java.awt.image.renderable.ParameterBlock;
import java.util.ResourceBundle;

import javax.media.jai.RasterFactory;
import javax.media.jai.TiledImage;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.sun.media.jai.widget.DisplayJAI;

import fr.irstv.dataModel.DrawableDataPoint;
import fr.irstv.dataModel.DrawableSegment;
import fr.irstv.dataModel.ImageModel;
import fr.irstv.dataModel.Segment;
import fr.irstv.dataModel.VanishingPoint;
import fr.irstv.dataModel.WorkingArea;

@SuppressWarnings("serial")
public class WorkingAreaInternalFrame extends JInternalFrame {

		private ImageModel image;
		private TiledImage tiledImage;
		private WorkingArea workingArea;	
		private DisplayJAI displayJAIworkingImage;
		private ParameterBlock pb;
		private WritableRaster workingAreaRaster;
		private DisplayJAIWorkingAreaImage displayJAIWorkingAreaImage;
		
		private int[] lightGray; 
		
	
		public WorkingAreaInternalFrame(ResourceBundle param, ImageModel image){
			
			//create the internal Frame
			super(param.getString(Constants.WORKING_AREA_INTERNAL_FRAME_NAME),true, false, true, true);
			
			//set the imageModel
			this.image = image;
			
			//create a Tiled image from the working area size and the 
			this.workingArea = this.image.getWorkingArea();
			
			int width = this.workingArea.getXSize();
			int height = this.workingArea.getYSize();
			
			//workingarea info
			System.out.println("working area "+this.workingArea.getXSize()+"x"+this.workingArea.getYSize());
			System.out.println("Zero point : ("+this.workingArea.getZeroPoint().get(0)+","+this.workingArea.getZeroPoint().get(1)+")");
			
			
			//define the light gray RGB pixel
			lightGray = new int[3];
			lightGray [0] = Color.lightGray.getRed();
			lightGray [1] = Color.lightGray.getGreen();
			lightGray [2] = Color.lightGray.getBlue();
			
			//create the tiled Image
			tiledImage = new TiledImage(0,
										0,
										this.workingArea.getXSize(),
										this.workingArea.getYSize(),
										0,
										0,
										this.image.getPlanarImage().getSampleModel(),
										this.image.getPlanarImage().getColorModel());
			
			//create a raster this the working area size
			workingAreaRaster = RasterFactory.createWritableRaster(RasterFactory.createPixelInterleavedSampleModel(DataBuffer.TYPE_INT, this.workingArea.getXSize(),this.workingArea.getYSize(),3), null);
			
			//fill this raster with lightGray RGB Color
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					workingAreaRaster.setPixel(j, i, lightGray);
				}
			}
			
			//set the image in the loaded file in the working area size raster at the rigth place defined by the ZeroPoint of the working area
			workingAreaRaster.setRect((int)workingArea.getZeroPoint().get(0), (int)workingArea.getZeroPoint().get(1), image.getPlanarImage().getData());
			
			//set the raster filed with the data in the tiled image
			tiledImage.setData(workingAreaRaster);		
			
			this.displayJAIWorkingAreaImage = new DisplayJAIWorkingAreaImage(tiledImage);
			
			//add the Drawables segments
			for(Segment segment : image.getSegmentList()){
				this.displayJAIWorkingAreaImage.addDrawableSegment(new DrawableSegment(segment, Color.red, 3, this.workingArea));
				this.displayJAIWorkingAreaImage.addDrawableHPoint(new DrawableDataPoint(segment.getHPoint(), Color.orange, 3, this.workingArea, 5));
			}
			
			//add the drawable VanishingPoint
			for(VanishingPoint vanishing : image.getVanishingPointList()){
				this.displayJAIWorkingAreaImage.addDrawableVanishingPoint(new DrawableDataPoint(vanishing, Color.blue, 3, this.workingArea, 7));

			}
			
			this.getContentPane().add(new JScrollPane(this.displayJAIWorkingAreaImage), BorderLayout.CENTER);
		    this.getContentPane().add(new JTextArea(width+"x"+height), BorderLayout.SOUTH);
		
		}

		
		public TiledImage getTiledImage() {
			return tiledImage;
		}

		public void setTiledImage(TiledImage tiledImage) {
			this.tiledImage = tiledImage;
		}

		public WorkingArea getWorkingArea() {
			return workingArea;
		}

		public void setWorkingArea(WorkingArea workingArea) {
			this.workingArea = workingArea;
		}

		public DisplayJAI getDisplayJAIworkingImage() {
			return displayJAIworkingImage;
		}

		public void setDisplayJAIworkingImage(DisplayJAI displayJAIworkingImage) {
			this.displayJAIworkingImage = displayJAIworkingImage;
		}

		public ParameterBlock getPb() {
			return pb;
		}

		public void setPb(ParameterBlock pb) {
			this.pb = pb;
		}
}
