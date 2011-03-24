package Scissors;

import Scissors.GUI.ScissorFrame;
import ij.plugin.frame.PlugInFrame;


@SuppressWarnings("serial")
public class Scissor_Frame extends PlugInFrame{
	
	protected ScissorFrame scissorFrame;
    
    /** Creates a new instance of Cell_Counter */
    public Scissor_Frame() {
         super("Scissor Frame");
         scissorFrame = new ScissorFrame();
    }
    
    public void run(String arg){
    }
    
    public ScissorFrame getScissorFrame() {
    	return scissorFrame;
    }
  
}
