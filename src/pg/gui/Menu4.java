package pg.gui;

import java.awt.*;
import pg.modules.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class Menu4 extends JFrame implements ActionListener, ChangeListener{
	 JPanel boutons=new JPanel();
	 JButton a=new JButton("add a segment");
	 JButton vp=new JButton("compute VP");
	 JButton r=new JButton("compute rectangles");

	 JSlider nb_g=new JSlider(2,5,2);
	 NbGroupeDisplay display=new NbGroupeDisplay(2);
	 VPandRectangles t;

	public Menu4(VPandRectangles t){
		this.t=t;
		  this.setTitle("Saisie");
          this.setSize(100, 300);
          this.setLocation(900,0);
          this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          this.setResizable(false);
          boutons.setLayout(new GridLayout(5,1));
          vp.addActionListener(this);
          r.addActionListener(this);
          //a.addActionListener(this);
          nb_g.addChangeListener(this);
          nb_g.setMajorTickSpacing(3);

          //boutons.add(a);
          boutons.add(vp);
          boutons.add(r);
          boutons.add(nb_g);
          boutons.add(display);

          this.setContentPane(boutons);
          this.setVisible(true);
	}

	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent arg0) {

		if(arg0.getSource() == vp)
			t.computeVP();
		if(arg0.getSource() == r)
			t.computeRectangles();


     }

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		JSlider source = (JSlider)e.getSource();

			t.setNbGroups(source.getValue());
			display.setNb(source.getValue());
			repaint();




	}


}


