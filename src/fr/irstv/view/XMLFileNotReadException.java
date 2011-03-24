package fr.irstv.view;

import java.awt.Component;
import java.io.File;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;


public class XMLFileNotReadException {
	
	public XMLFileNotReadException(String fileName, Component component, ResourceBundle param){
		JOptionPane.showMessageDialog(component,
				fileName+".xml"+" "+param.getString(Constants.XML_FILE_NOT_READ_MESSAGE),
				"Inane warning",
				JOptionPane.WARNING_MESSAGE);

	}

}
