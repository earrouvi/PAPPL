package Scissors;

import java.util.*;
import Scissors.algo.*;
import ij.*;

public class Main {

	/**
	 * entry of program
	 * @param args system parameter
	 */
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		ImageJ imagej=new ImageJ();
		Scissor_Frame sf = new Scissor_Frame();
		VerticesList vl = new VerticesList(sf);
		
		vl.run();
		for (int i=0;i<vl.points.size();i++) {
			System.out.println(vl.points.get(i));
		}
	}
}
