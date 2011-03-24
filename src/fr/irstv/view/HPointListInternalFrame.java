package fr.irstv.view;

import java.util.ResourceBundle;

import javax.swing.JInternalFrame;


@SuppressWarnings("serial")
public class HPointListInternalFrame extends JInternalFrame{
	public HPointListInternalFrame(ResourceBundle param){
		super(param.getString(Constants.H_POINT_LIST_INTERNAL_FRAME_NAME),true, false, true, true);
	
	}
}
