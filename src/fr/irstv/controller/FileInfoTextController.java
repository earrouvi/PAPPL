package fr.irstv.controller;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JTextField;

import fr.irstv.dataModel.ImageModel;


public class FileInfoTextController implements Observer{
	
	private JTextField textPane;
	
	/**
	 */
	public FileInfoTextController(JTextField textPane){
		this.textPane = textPane;
	}


	public void update(Observable o, Object arg) {
		if (!((ImageModel)o).isEmpty()) {
			this.textPane.setText(((ImageModel)o).getImageURI().toString());
		}	
	}


}
