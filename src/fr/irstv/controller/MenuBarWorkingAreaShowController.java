package fr.irstv.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import fr.irstv.view.WorkingAreaInternalFrame;


public class MenuBarWorkingAreaShowController implements ActionListener{

	private JMenuItem workingAreaShow;
	private JMenuItem workingAreaHide;
	private WorkingAreaInternalFrame workingAreaInternalFrame;

	public MenuBarWorkingAreaShowController(JMenuItem workingAreaShow, JMenuItem workingAreaHide, WorkingAreaInternalFrame workingAreaInternalFrame){
		this.workingAreaShow = workingAreaShow;
		this.workingAreaHide = workingAreaHide;
		this.workingAreaInternalFrame = workingAreaInternalFrame;
	}

	public void actionPerformed(ActionEvent e) {
		this.workingAreaHide.setEnabled(true);
		this.workingAreaShow.setEnabled(false);
		this.workingAreaInternalFrame.setVisible(true);
	}

}
