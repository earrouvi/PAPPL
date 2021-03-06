package ij.io;
import ij.gui.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.net.*;
import java.awt.geom.*;


/** Saves an ROI to a file or stream. RoiDecoder.java has a description of the file format.
	@see ij.io.RoiDecoder
	@see ij.plugin.RoiReader
*/
public class RoiEncoder {
	static final int HEADER_SIZE = 64;
	static final int VERSION = 218; // changed to 218 in v1.43i
	private String path;
	private OutputStream f;
	private final int polygon=0, rect=1, oval=2, line=3, freeline=4, polyline=5, noRoi=6, freehand=7, 
		traced=8, angle=9, point=10;
	private byte[] data;
	
	/** Creates an RoiEncoder using the specified path. */
	public RoiEncoder(String path) {
		this.path = path;
	}

	/** Creates an RoiEncoder using the specified OutputStream. */
	public RoiEncoder(OutputStream f) {
		this.f = f;
	}

	/** Save the Roi to the file of stream. */
	public void write(Roi roi) throws IOException {
		if (f!=null) {
			write(roi, f);
		} else {
			f = new FileOutputStream(path);
			write(roi, f);
			f.close();
		}
	}

	void write(Roi roi, OutputStream f) throws IOException {
		int roiType = roi.getType();
		int type = rect;
		switch (roiType) {
			case Roi.POLYGON: type=polygon; break;
			case Roi.FREEROI: type=freehand; break;
			case Roi.TRACED_ROI: type=traced; break;
			case Roi.OVAL: type=oval; break;
			case Roi.LINE: type=line; break;
			case Roi.POLYLINE: type=polyline; break;
			case Roi.FREELINE: type=freeline; break;
			case Roi.ANGLE: type=angle; break;
			case Roi.COMPOSITE: type=rect; break; // shape array size (36-39) will be >0 to indicate composite type
			case Roi.POINT: type=point; break;
			default: type = rect; break;
		}
		
		if (roiType==Roi.COMPOSITE) {
			saveShapeRoi(roi, type, f);
			return;
		}

		int n=0;
		int[] x=null,y=null;
		if (roi instanceof PolygonRoi) {
			Polygon p = ((PolygonRoi)roi).getNonSplineCoordinates();
			n = p.npoints;
			x = p.xpoints;
			y = p.ypoints;
		}
		data = new byte[HEADER_SIZE+n*4];
		
		Rectangle r = roi.getBounds();
		
		data[0]=73; data[1]=111; data[2]=117; data[3]=116; // "Iout"
		putShort(4, VERSION);
		data[6] = (byte)type;
		putShort(8, r.y);			//top
		putShort(10, r.x);			//left
		putShort(12, r.y+r.height);	//bottom
		putShort(14, r.x+r.width);	//right
		putShort(16, n);
		
		if (roi instanceof Line) {
			Line l = (Line)roi;
			putFloat(18, l.x1);
			putFloat(22, l.y1);
			putFloat(26, l.x2);
			putFloat(30, l.y2);
		}
		
		// save stroke width, stroke color and fill color (1.43i or later)
		if (VERSION>=218) {
			BasicStroke stroke = roi.getStroke();
			if (stroke!=null)
				putShort(RoiDecoder.STROKE_WIDTH, (int)stroke.getLineWidth());
			Color strokeColor = roi.getStrokeColor();
			if (strokeColor!=null)
				putInt(RoiDecoder.STROKE_COLOR, strokeColor.getRGB());
			Color fillColor = roi.getFillColor();
			if (fillColor!=null)
				putInt(RoiDecoder.FILL_COLOR, fillColor.getRGB());
			if ((roi instanceof PolygonRoi) && ((PolygonRoi)roi).isSplineFit()) {
				int options = 0;
				options |= RoiDecoder.SPLINE_FIT;
				putShort(RoiDecoder.OPTIONS, options);
			}
		}
		
		// save TextRoi
		if (n==0 && roi instanceof TextRoi)
			saveTextRoi((TextRoi)roi);
			
		if (n>0) {
			int base1 = 64;
			int base2 = base1+2*n;
			for (int i=0; i<n; i++) {
				putShort(base1+i*2, x[i]);
				putShort(base2+i*2, y[i]);
			}
		}
		
		f.write(data);
	}

	void saveShapeRoi(Roi roi, int type, OutputStream f) throws IOException {
		float[] shapeArray = ((ShapeRoi)roi).getShapeAsArray();
		if (shapeArray==null) return;
		BufferedOutputStream bout = new BufferedOutputStream(f);
		Rectangle r = roi.getBounds();
		data  = new byte[HEADER_SIZE + shapeArray.length*4];
		data[0]=73; data[1]=111; data[2]=117; data[3]=116; // "Iout"
		putShort(4, VERSION);
		data[6] = (byte)type;
		putShort(8, r.y);			//top
		putShort(10, r.x);			//left
		putShort(12, r.y+r.height);	//bottom
		putShort(14, r.x+r.width);	//right
		//putShort(16, n);
		putInt(36, shapeArray.length); // non-zero segment count indicate composite type

		// handle the actual data: data are stored segment-wise, i.e.,
		// the type of the segment followed by 0-6 control point coordinates.
		int base = 64;
		for (int i=0; i<shapeArray.length; i++) {
			putFloat(base, shapeArray[i]);
			base += 4;
		}
		bout.write(data,0,data.length);
		bout.flush();
	}
	
	void saveTextRoi(TextRoi roi) {
		Font font = roi.getCurrentFont();
		String name = font.getName();
		int size = font.getSize();
		int style = font.getStyle();
		String text = roi.getText();
		int nameLength = name.length();
		int textLength = text.length();
		byte[] data2 = new byte[HEADER_SIZE+16+nameLength*2+textLength*2];
		System.arraycopy(data, 0, data2, 0, HEADER_SIZE);
		data = data2;
		putShort(RoiDecoder.SUBTYPE, RoiDecoder.TEXT);
		putInt(HEADER_SIZE, size);
		putInt(HEADER_SIZE+4, style);
		putInt(HEADER_SIZE+8, nameLength);
		putInt(HEADER_SIZE+12, textLength);
		for (int i=0; i<nameLength; i++)
			putShort(HEADER_SIZE+16+i*2, name.charAt(i));
		for (int i=0; i<textLength; i++)
			putShort(HEADER_SIZE+16+nameLength*2+i*2, text.charAt(i));
	}

    void putShort(int base, int v) {
		data[base] = (byte)(v>>>8);
		data[base+1] = (byte)v;
    }

	void putFloat(int base, float v) {
		int tmp = Float.floatToIntBits(v);
		data[base]   = (byte)(tmp>>24);
		data[base+1] = (byte)(tmp>>16);
		data[base+2] = (byte)(tmp>>8);
		data[base+3] = (byte)tmp;
	}

	void putInt(int base, int i) {
		data[base]   = (byte)(i>>24);
		data[base+1] = (byte)(i>>16);
		data[base+2] = (byte)(i>>8);
		data[base+3] = (byte)i;
	}
	
	
}
