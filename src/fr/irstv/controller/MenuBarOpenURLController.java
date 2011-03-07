package fr.irstv.controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import javax.swing.JMenuItem;

import fr.irstv.dataModel.ImageModel;
import fr.irstv.view.OpenURLDialog;


public class MenuBarOpenURLController implements Observer, ActionListener{
		
		private ImageModel imageModel;
		private JMenuItem urlOpen;
		private Component component;
		private ResourceBundle stringValues;
		private OpenURLDialog openURIDialog;
		
		public MenuBarOpenURLController(ImageModel imageModel, JMenuItem urlOpen, Component component, ResourceBundle stringValues){
			this.imageModel = imageModel;
			this.urlOpen = urlOpen;
			
			this.component = component;
			this.stringValues = stringValues;

		}
		
		public void update(Observable arg0, Object arg1) {
			if(!((ImageModel)arg0).isEmpty()){
				this.urlOpen.setEnabled(false);
			}else{
				this.urlOpen.setEnabled(true);
			}
			
		}

		public void actionPerformed(ActionEvent arg0) {
			this.openURIDialog = new OpenURLDialog(component, stringValues);
			this.imageModel.processOpenFile(this.openURIDialog.getFile(), component, stringValues);
		}
}
