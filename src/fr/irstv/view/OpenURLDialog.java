package fr.irstv.view;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;


public class OpenURLDialog {
	
	private File file;
	private String value;
	private URL url;
	
	public OpenURLDialog(Component component, ResourceBundle param){
		this.value = JOptionPane.showInputDialog(component, param.getString(Constants.OPEN_URL_DIALOG_TITLE),
				param.getString(Constants.OPEN_URL_DIALOG_MESSAGE), JOptionPane.OK_CANCEL_OPTION);
		
		try {
			this.url = new URL(this.value);
			URLConnection connection = url.openConnection();
			InputStream flux = connection.getInputStream();
			BufferedReader bf = new BufferedReader(new InputStreamReader(flux));

			this.file = (File)(url.getContent());
			System.out.println(file.getName());
		} catch (MalformedURLException e1) {
			JOptionPane.showMessageDialog(component,
					value +" "+param.getString(Constants.MAL_FORMED_URL_MESSAGE),
					"Inane warning",
					JOptionPane.WARNING_MESSAGE);
			e1.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(component,
					value +" "+param.getString(Constants.IO_URL_FILE_MESSAGE),
					"Inane warning",
					JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		} 
		
		
	}
	/**/
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public URL getURL() {
		return url;
	}

	public void setURL(URL url) {
		this.url = url;
	}

}
