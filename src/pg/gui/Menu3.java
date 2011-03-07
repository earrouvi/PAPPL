package pg.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

import pg.modules.SegmentFromVanishingPoint;


public class Menu3 extends JFrame implements ActionListener{
	 JPanel boutons=new JPanel();
	 JButton b=new JButton("saisie");
	 JButton compute=new JButton("Compute");
	 SegmentFromVanishingPoint s;

	public Menu3(SegmentFromVanishingPoint s){
		this.s=s;
		  this.setTitle("Saisie");
          this.setSize(100, 300);
          this.setLocation(900,0);
          this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          this.setResizable(false);
          boutons.setLayout(new GridLayout(5,1));
          b.addActionListener(this);

          compute.addActionListener(this);
          boutons.add(b);

          boutons.add(compute);

          this.setContentPane(boutons);
          this.setVisible(true);
	}

	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent arg0) {

		if(arg0.getSource() == b)
			s.setActivatedButton(1);

		if(arg0.getSource() == compute)
			s.compute();



     }


}
