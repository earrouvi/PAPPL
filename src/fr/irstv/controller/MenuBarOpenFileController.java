package fr.irstv.controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;

import fr.irstv.dataModel.ImageModel;
import fr.irstv.view.ImageFilter;
import fr.irstv.view.VanishingPointAppView;

public class MenuBarOpenFileController implements Observer, ActionListener{
	
	private ImageModel imageModel;
	private JMenuItem fileOpen;
	private Component component;
	private ResourceBundle stringValues;
	
	public MenuBarOpenFileController(ImageModel imageModel, JMenuItem fileOpen, Component component, ResourceBundle stringValues){
		this.imageModel = imageModel;
		this.fileOpen = fileOpen;
		
		this.component = component;
		this.stringValues = stringValues;

	}
	
	public void update(Observable arg0, Object arg1) {
		if(!((ImageModel)arg0).isEmpty()){
			this.fileOpen.setEnabled(false);
		}else{
			this.fileOpen.setEnabled(true);
		}
		
	}

	public void actionPerformed(ActionEvent arg0) {
		//create a file chooser
		JFileChooser fileChooser = new JFileChooser();
		//use an image file filter
		ImageFilter imageFilter = new ImageFilter();
		fileChooser.addChoosableFileFilter(imageFilter);
		//Show it.
        int returnVal = fileChooser.showDialog(this.component,"Open");

        //Process the results.
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            this.imageModel.processOpenFile(file, this.component, this.stringValues);
            
            //show internal frames
            ((VanishingPointAppView) this.component).openInternalFrame(this.stringValues, this.imageModel);
            
            /*String fileName = ImageFilter.getFileNameWithoutExtension(file.getName());
            try {
            	this.imageModel.setImageName(fileName);
            	this.imageModel.setImageURI(file.toURI());
            	File xmlFile = new File(ImageFilter.getFileNameWithoutExtension(file.getPath())+".xml");
            	this.imageModel.readXMLResultFile(xmlFile, stringValues);
            	this.imageModel.setPlanarImage(JAI.create("fileload", file.getPath()));
			} catch (IOException e) {
				new XMLFileNotFoundExceptionDialog(fileName, component, stringValues);
				e.printStackTrace();
			}catch (SAXException esax){
				new XMLFileNotReadException(fileName, component, stringValues);
				esax.printStackTrace();
			}*/
        }        
        
        //Reset the file chooser for the next time it's shown.
        fileChooser.setSelectedFile(null);
		
	}
	
	
}
