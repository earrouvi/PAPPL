package hough;
import java.util.Arrays;

/* W. Burger, M. J. Burge: "Digitale Bildverarbeitung" 
 * © Springer-Verlag, 2005
 * www.imagingbook.com
*/

public class HoughSet {
	protected int size;
	protected HoughNode[] nodes;
	
	HoughSet(int size){
		this.size = size;
		nodes = new HoughNode[size];
		//create an empty Hough set
		for (int i=0; i<nodes.length; i++){
			nodes[i] = new HoughNode(0,0,-1);
		}
	}
	
	
	void add(int angle, int radius, int count){
		HoughNode last = nodes[nodes.length-1];
		if (count > last.count){
			last.angle = angle;
			last.radius = radius;
			last.count = count;
		}
		Arrays.sort(nodes);
	}


	public HoughNode[] getNodes() {
		return nodes;
	}


	public void setNodes(HoughNode[] nodes) {
		this.nodes = nodes;
	}


	public int getSize() {
		return size;
	}


	public void setSize(int size) {
		this.size = size;
	}
}
