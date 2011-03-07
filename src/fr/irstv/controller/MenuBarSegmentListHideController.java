package fr.irstv.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import fr.irstv.view.SegmentListInternalFrame;


public class MenuBarSegmentListHideController implements ActionListener{

	private JMenuItem segmentListShow;
	private JMenuItem segmentListHide;
	private SegmentListInternalFrame segmentListInternalFrame;

	public MenuBarSegmentListHideController(JMenuItem segmentListShow, JMenuItem segmentListHide, SegmentListInternalFrame segmentListInternalFrame){
		this.segmentListShow = segmentListShow;
		this.segmentListHide = segmentListHide;
		this.segmentListInternalFrame = segmentListInternalFrame;
	}

	public void actionPerformed(ActionEvent e) {
		this.segmentListShow.setEnabled(true);
		this.segmentListHide.setEnabled(false);
		this.segmentListInternalFrame.setVisible(false);
	}

}
