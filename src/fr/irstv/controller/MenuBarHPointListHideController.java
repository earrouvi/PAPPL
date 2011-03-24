package fr.irstv.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import fr.irstv.view.HPointListInternalFrame;


public class MenuBarHPointListHideController implements ActionListener{

	private JMenuItem hPointsListShow;
	private JMenuItem hPointsListHide;
	private HPointListInternalFrame hPointListInternalFrame;

	public MenuBarHPointListHideController(JMenuItem workingAreaShow, JMenuItem workingAreaHide, HPointListInternalFrame hPointListInternalFrame){
		this.hPointsListShow = workingAreaShow;
		this.hPointsListHide = workingAreaHide;
		this.hPointListInternalFrame = hPointListInternalFrame;
	}

	public void actionPerformed(ActionEvent e) {
		this.hPointsListHide.setEnabled(false);
		this.hPointsListShow.setEnabled(true);
		this.hPointListInternalFrame.setVisible(false);
	}

}
