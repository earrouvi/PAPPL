package fr.irstv.view;

import java.util.ResourceBundle;

import javax.swing.JInternalFrame;


public class VanishingPointListInternalFrame extends JInternalFrame {
	
	/**
	 */
	public VanishingPointListInternalFrame(ResourceBundle param){
		super(param.getString(Constants.VANISHING_POINT_LIST_INTERNAL_FRAME_NAME),true, false, true, true);
	
	}


}
