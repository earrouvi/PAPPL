package Apps;

import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import pg.modules.VPandRectangles;


import fr.irstv.view.ImageFilter;
import fr.irstv.view.VanishingPointAppView;


public class VPandRectangleApp {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		final JFileChooser fc = new JFileChooser();

		//In response to a button click:
		int returnVal = fc.showOpenDialog(null);


            File file = fc.getSelectedFile();
            //This is where a real application would open the file.




		String imageName=ImageFilter.getFileNameWithoutExtension(file.getName());



		//Do we load segments from xml?
		Boolean fromXml = true;
		VPandRectangles test=new VPandRectangles(imageName,fromXml);


	}

}
