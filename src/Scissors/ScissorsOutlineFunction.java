package Scissors;

import java.util.*;
import Scissors.algo.*;
import ij.*;

public class ScissorsOutlineFunction {

	private VerticesList vl;
	public ScissorsOutlineFunction() {
		@SuppressWarnings("unused")
		ImageJ imagej = new ImageJ();
		Scissor_Frame sf = new Scissor_Frame();
		this.vl = new VerticesList(sf);
		this.vl.run();	
	}
	
	public void scissorsOutlineDisplay(){
		for (int i=0;i<vl.getPoints().size();i++) {
			System.out.println(vl.getPoints().get(i));
		}
	}

	public VerticesList getVl() {
		return vl;
	}
}
