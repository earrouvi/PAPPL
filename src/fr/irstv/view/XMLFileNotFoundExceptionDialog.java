package fr.irstv.view;

import java.awt.Component;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;

public class XMLFileNotFoundExceptionDialog {


	/**
	 */
	public XMLFileNotFoundExceptionDialog(String fileBaseName, Component component, ResourceBundle param){
		JOptionPane.showMessageDialog(component,
				fileBaseName+".xml" +" "+param.getString(Constants.XML_FILE_NOT_FOUND_MESSAGE),
				"Inane warning",
				JOptionPane.WARNING_MESSAGE);

	}


}
