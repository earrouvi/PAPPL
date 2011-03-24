package pg.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

import pg.modules.HeightComputation;


public class Menu extends JFrame implements ActionListener{
	 JPanel boutons=new JPanel();
	 JButton b1=new JButton("b1");
	 JButton b2=new JButton("b2");
	 JButton t1=new JButton("t1");
	 JButton t2=new JButton("t2");
	 JButton compute=new JButton("Compute");
	 HeightComputation h;

	public Menu(HeightComputation h){
		this.h=h;
		  this.setTitle("Saisie");
          this.setSize(100, 300);
          this.setLocation(900,0);
          this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          this.setResizable(false);
          boutons.setLayout(new GridLayout(5,1));
          b1.addActionListener(this);
          b2.addActionListener(this);
          t1.addActionListener(this);
          t2.addActionListener(this);
          compute.addActionListener(this);
          boutons.add(b1);
          boutons.add(b2);
          boutons.add(t1);
          boutons.add(t2);
          boutons.add(compute);

          this.setContentPane(boutons);
          this.setVisible(true);
	}

	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent arg0) {

		if(arg0.getSource() == b1)
			h.setActivatedButton(1);

		if(arg0.getSource() == b2)
			h.setActivatedButton(2);
		if(arg0.getSource() == t1)
			h.setActivatedButton(3);
		if(arg0.getSource() == t2)
			h.setActivatedButton(4);
		if(arg0.getSource() == compute)
			h.process();


     }


}
