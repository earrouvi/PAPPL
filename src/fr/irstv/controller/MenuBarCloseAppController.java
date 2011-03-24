package fr.irstv.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;


public class MenuBarCloseAppController implements ActionListener{

	private JFrame component;

	/**
	 */
	public MenuBarCloseAppController(JFrame component){
		this.component = component;
	}

	public void actionPerformed(ActionEvent arg0) {
		this.component.dispose();
	}

}
