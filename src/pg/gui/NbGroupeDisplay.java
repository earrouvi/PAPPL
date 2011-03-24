package pg.gui;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.JComponent;

import com.sun.codemodel.internal.JLabel;



public class NbGroupeDisplay extends JComponent{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	protected int nb;


	public NbGroupeDisplay(int value) {

		// TODO Auto-generated constructor stub
		nb=value;
	}




	public void paintComponent(Graphics g) {
		g.setColor(Color.white);
        g.fillRect(0,0, 200, 40);
        g.setColor(Color.black);
        g.drawString("Groupes : "+nb,20,20);

	}

	public void setNb(int value) {
		// TODO Auto-generated method stub
		nb=value;

	}




}
