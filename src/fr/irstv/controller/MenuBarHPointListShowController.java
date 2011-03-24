package fr.irstv.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import fr.irstv.view.HPointListInternalFrame;


public class MenuBarHPointListShowController implements ActionListener{

	private JMenuItem hPointsListShow;
	private JMenuItem hPointsListHide;
	private HPointListInternalFrame hPointListInternalFrame;

	public MenuBarHPointListShowController(JMenuItem workingAreaShow, JMenuItem workingAreaHide, HPointListInternalFrame hPointListInternalFrame){
		this.hPointsListShow = workingAreaShow;
		this.hPointsListHide = workingAreaHide;
		this.hPointListInternalFrame = hPointListInternalFrame;
	}

	public void actionPerformed(ActionEvent e) {
		this.hPointsListHide.setEnabled(true);
		this.hPointsListShow.setEnabled(false);
		this.hPointListInternalFrame.setVisible(true);
	}

}
