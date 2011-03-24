package fr.irstv.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import fr.irstv.view.SegmentListInternalFrame;


public class MenuBarSegmentListShowController implements ActionListener{

	private JMenuItem segmentListShow;
	private JMenuItem segmentListHide;
	private SegmentListInternalFrame segmentListInternalFrame;

	public MenuBarSegmentListShowController(JMenuItem segmentListShow, JMenuItem segmentListHide, SegmentListInternalFrame segmentListInternalFrame){
		this.segmentListShow = segmentListShow;
		this.segmentListHide = segmentListHide;
		this.segmentListInternalFrame = segmentListInternalFrame;
	}

	public void actionPerformed(ActionEvent e) {
		this.segmentListShow.setEnabled(false);
		this.segmentListHide.setEnabled(true);
		this.segmentListInternalFrame.setVisible(true);
	}

}
