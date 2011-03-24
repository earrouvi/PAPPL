package fr.irstv.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComboBox;

import fr.irstv.dataModel.ImageModel;

public class MenuBarScaleComboBoxController implements ActionListener{

	private ImageModel imageModel;
	private JComboBox scaleComboBox;
	
	public MenuBarScaleComboBoxController(ImageModel imageModel, JComboBox scaleComboBox){
		this.imageModel = imageModel;
		this.scaleComboBox = scaleComboBox;
	}

	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
