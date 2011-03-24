package pg.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

import pg.modules.RemovePerspectiveDistortion;


public class Menu2 extends JFrame implements ActionListener{
	 JPanel boutons=new JPanel();
	 JButton x1=new JButton("x1");
	 JButton x2=new JButton("x2");
	 JButton x3=new JButton("x3");
	 JButton x4=new JButton("x4");
	 JButton compute=new JButton("Compute");
	 JButton reset=new JButton("Reset");
	 RemovePerspectiveDistortion r;

	public Menu2(RemovePerspectiveDistortion r){
		this.r=r;
		  this.setTitle("Saisie");
          this.setSize(150, 300);
          this.setLocation(900,0);
          this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          this.setResizable(false);
          boutons.setLayout(new GridLayout(6,1));
          x1.addActionListener(this);
          x2.addActionListener(this);
          x3.addActionListener(this);
          x4.addActionListener(this);
          reset.addActionListener(this);
          compute.addActionListener(this);
          boutons.add(x1);
          boutons.add(x2);
          boutons.add(x3);
          boutons.add(x4);
          boutons.add(compute);
          boutons.add(reset);

          this.setContentPane(boutons);
          this.setVisible(true);
	}

	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent arg0) {

		if(arg0.getSource() == x1)
			r.setActivatedButton(1);

		if(arg0.getSource() == x2)
			r.setActivatedButton(2);
		if(arg0.getSource() == x3)
			r.setActivatedButton(3);
		if(arg0.getSource() == x4)
			r.setActivatedButton(4);
		if(arg0.getSource() == compute)
			r.compute();
		if(arg0.getSource() == reset)
			r.reset();



     }


}
