package fr.irstv.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JMenuItem;

import fr.irstv.dataModel.ImageModel;
import fr.irstv.view.VanishingPointAppView;

public class MenuBarCloseFileController implements Observer, ActionListener{
	
	private ImageModel imageModel;
	private JMenuItem fileClose;
	private VanishingPointAppView frame; 
	
	public MenuBarCloseFileController(ImageModel imageModel, JMenuItem fileClose, VanishingPointAppView frame){
		this.imageModel = imageModel;
		this.fileClose = fileClose;
		this.frame = frame;
		
		this.fileClose.setEnabled(false);
	}
	
	public void update(Observable arg0, Object arg1) {
		if(!((ImageModel)arg0).isEmpty()){
			this.fileClose.setEnabled(true);
		}else{
			this.fileClose.setEnabled(false);
		}
		
	}

	public void actionPerformed(ActionEvent arg0) {
		this.imageModel.reinitializeModel();
		this.frame.closeinternalFrame();
	}
	
}
