package fr.irstv.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import fr.irstv.view.WorkingAreaInternalFrame;


public class MenuBarWorkingAreaHideController implements ActionListener{

	private JMenuItem workingAreaShow;
	private JMenuItem workingAreaHide;
	private WorkingAreaInternalFrame workingAreaInternalFrame;

	public MenuBarWorkingAreaHideController(JMenuItem workingAreaShow, JMenuItem workingAreaHide, WorkingAreaInternalFrame workingAreaInternalFrame){
		this.workingAreaShow = workingAreaShow;
		this.workingAreaHide = workingAreaHide;
		this.workingAreaInternalFrame = workingAreaInternalFrame;
	}

	public void actionPerformed(ActionEvent e) {
		this.workingAreaHide.setEnabled(false);
		this.workingAreaShow.setEnabled(true);
		this.workingAreaInternalFrame.setVisible(false);
	}

}
