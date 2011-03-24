package fr.irstv.view;

import java.util.ResourceBundle;

import javax.swing.JInternalFrame;


public class SegmentListInternalFrame extends JInternalFrame {

		
		/**
		 */
		public SegmentListInternalFrame(ResourceBundle param){
			super(param.getString(Constants.SEGMENT_LIST_INTERNAL_FRAME_NAME),true, false, true, true);
		
		}
	

}
