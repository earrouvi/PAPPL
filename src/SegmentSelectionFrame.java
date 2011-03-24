import ij.IJ;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import SegmentDetection.Segment;

/**
 * 
 * @author Elsa Arrou-Vignod, Florent Buisson
 * adapted from ScissorFrame.java
 */

public class SegmentSelectionFrame extends JFrame implements ActionListener, ItemListener, KeyListener {

	private int width = 500;
	private int height = 300;

	private JPanel statButtonPanel;
	private JButton validateButton;
	private static final String VALID="Validate";
	private JButton closeButton;
	private static final String CLOSE="Close window";
	
	public boolean val = false;

	protected HashMap<Integer, Vector<Segment>> segmentMap = new HashMap<Integer, Vector<Segment>>();
	protected ArrayList<Integer> groupsChosen;

	public SegmentSelectionFrame(HashMap<Integer, Vector<Segment>> segmentMap) {
		super("SegmentSelectionFrame");

		if(!IJ.isJava14()){
			IJ.showMessage("You are using a pre 1.4 version of java, exporting and loading marker data is disabled");
		}
		setResizable(false);

		// attributes initialisation
		this.segmentMap = segmentMap;
		groupsChosen = new ArrayList<Integer>();

		initGUI();
		//populateTxtFields();
		addKeyListener(this);
	}

	public void initGUI() {
		setSize(width, height);
		// creating the color map
		HashMap<Integer,Color> colorMap = new HashMap<Integer,Color>(8);
		colorMap.put(0, Color.RED);
		colorMap.put(1, Color.BLUE);
		colorMap.put(2, Color.GREEN);
		colorMap.put(3, Color.ORANGE);
		colorMap.put(4, Color.YELLOW);
		colorMap.put(5, Color.MAGENTA);
		colorMap.put(6, Color.CYAN);
		colorMap.put(7, Color.WHITE);

		// global frame
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

		// panel containing the check boxes
		statButtonPanel=new JPanel();
		statButtonPanel.setBorder(BorderFactory.createTitledBorder("Choose groups"));
		statButtonPanel.setLayout(new BoxLayout(statButtonPanel, BoxLayout.PAGE_AXIS));
		statButtonPanel.add(Box.createHorizontalGlue());
		getContentPane().add(statButtonPanel);

		// creating the check boxes
		for (int i=0;i<segmentMap.size();i++) {
			createCheckBox(0, colorMap.get(i));
		}

		// validate button
		validateButton = makeButton(VALID, "Validate the chosen groups");
		getContentPane().add(validateButton);

		//		// close button
		//		closeButton = makeButton(CLOSE, "Close window");
		//		getContentPane().add(closeButton);

		// showing the frame
		Runnable runner = new GUIShower(this);
		EventQueue.invokeLater(runner);
	}

	public void createCheckBox(int i, Color color) {
		JCheckBox check = new JCheckBox();
		check.setText("Group "+i);
		check.setOpaque(true);
		check.setBackground(color);
		statButtonPanel.add(check);
	}

	private JButton makeButton(String name, String tooltip){
		JButton jButton = new JButton(name);
		jButton.setToolTipText(tooltip);
		jButton.addActionListener(this);
		return jButton;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key=e.getKeyCode();
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		if (command.compareTo(VALID) == 0) {
			val = true;
			System.out.println("-----\nYou have chosen :");
			for (int i=1;i<statButtonPanel.getComponentCount();i++) {
				if (((JCheckBox) statButtonPanel.getComponent(i)).isSelected()) {
					groupsChosen.add(i-1);
					System.out.println("group "+(i-1));
				}
			}
		}
	}

	/**
	 * Run and set visible of the GUIShower
	 * @author CAO
	 *
	 */
	private static class GUIShower implements Runnable {
		final JFrame jFrame;
		public GUIShower(JFrame jFrame) {
			this.jFrame = jFrame;
		}
		public void run() {
			jFrame.pack();
			jFrame.setLocation(1000, 100);
			jFrame.setVisible(true);
		}
	}

}
