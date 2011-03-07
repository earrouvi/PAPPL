package fr.irstv.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import fr.irstv.view.VanishingPointListInternalFrame;


public class MenuBarVanishingPointListShowController implements ActionListener{

	private JMenuItem vanishingPointListShow;
	private JMenuItem vanishingPointListHide;
	private VanishingPointListInternalFrame vanishingPointListInternalFrame;

	public MenuBarVanishingPointListShowController(JMenuItem vanishingPointListShow, JMenuItem vanishingPointListHide, VanishingPointListInternalFrame vanishingPointListInternalFrame){
		this.vanishingPointListShow = vanishingPointListShow;
		this.vanishingPointListHide = vanishingPointListHide;
		this.vanishingPointListInternalFrame = vanishingPointListInternalFrame;
	}

	public void actionPerformed(ActionEvent e) {
		this.vanishingPointListShow.setEnabled(false);
		this.vanishingPointListHide.setEnabled(true);
		this.vanishingPointListInternalFrame.setVisible(true);
	}

}
