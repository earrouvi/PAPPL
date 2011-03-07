package Apps;

import java.io.File;

import javax.swing.JFileChooser;

import pg.modules.RemovePerspectiveDistortion;

import fr.irstv.view.ImageFilter;


public class RemovePerspectiveApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub



		final JFileChooser fc = new JFileChooser();

		//In response to a button click:
		int returnVal = fc.showOpenDialog(null);


          File file = fc.getSelectedFile();
            //This is where a real application would open the file.




		String pathToFile=file.getPath();


		RemovePerspectiveDistortion r=new RemovePerspectiveDistortion(pathToFile);





	}

}
