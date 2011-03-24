package Scissors.GUI;
import ij.CompositeImage;
import ij.IJ;
import ij.ImagePlus;
import ij.ImageStack;
import ij.WindowManager;
import ij.gui.ImageWindow;
import ij.gui.StackWindow;
import ij.process.ImageProcessor;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ListIterator;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

import Scissors.algo.*;
import Scissors.GUI.*;
import java.awt.Color;

/**
 * An interface for scissor
 * @author CAO
 *
 */
@SuppressWarnings("serial")
public class ScissorFrame extends JFrame implements ActionListener, ItemListener, KeyListener{
	private static final String ADD = "Add";
	private static final String REMOVECURRENTSCISSOR = "Remove";
	private static final String INITIALIZE = "Initialize";
	private static final String DELETE = "Delete";
	//  private static final String SHOWNUMBERS = "Show Numbers";
	private static final String RESET = "Reset";
	private static final String EXPORTIMG = "Export Image";
	private static final String MEASURE = "Measure...";
	private static final String VALIDATE = "Validate";
	private static final String DRAW="Draw";
	private static final String RUN="Run";
	private static final String REMOVECURRENTPOINT="RemoveP";
	private static final String EREASE="Erease";
	//	private static final String TWOPOINTS="Two Points";


	private Vector typeVector;
	private Vector dynRadioVector;
	private Vector txtFieldVector;
	private ScissorMarkerVector markerVector;

	private JPanel dynPanel;
	private JPanel dynButtonPanel;
	private JPanel statButtonPanel;
	private JPanel dynTxtPanel;
	private ButtonGroup radioGrp;
	private JSeparator separator;
	private JButton addButton;
	private JButton removeSButton;
	private JButton initializeButton;
	private JButton drawButton;
	private JButton ereaseButton;
	private JButton deleteButton;
	private JButton resetButton;
	private JButton measureButton;
	private JButton validateButton;
	//  private JButton twopointsButton;

	private boolean keepOriginal=false;

	private ScissorImageCanvas ic;

	private ImagePlus img;
	private ImagePlus counterImg;

	static Vector images = new Vector();

	private GridLayout dynGrid;

	private boolean isJava14;
	ScissorController scissor;
	public boolean validCanvas = false;


	final int SCISSOR=1;
	final int FREE=0;
	int state;
	public int click;

	static ScissorFrame instance;
	/**
	 * Constructor of ScissorFrame
	 */
	 public ScissorFrame(){
		 super("ScissorFrame");

		 isJava14 = IJ.isJava14(); 
		 if(!isJava14){
			 IJ.showMessage("You are using a pre 1.4 version of java, exporting and loading marker data is disabled");
		 }
		 setResizable(false);
		 typeVector = new Vector();
		 txtFieldVector = new Vector();
		 dynRadioVector = new Vector();
		 initGUI();
		 //populateTxtFields();
		 instance = this;
		 addKeyListener(this);

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
			 jFrame.setLocation(1000, 200);
			 jFrame.setVisible(true);
		 }
	 }
	 /** Initiation of the GUI, buttons and panels are added*/
	 private void initGUI(){

		 setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		 GridBagLayout gb = new GridBagLayout();
		 getContentPane().setLayout(gb);

		 radioGrp = new ButtonGroup();//to group the radiobuttons

		 dynGrid = new GridLayout(4,1);
		 dynGrid.setVgap(2);

		 //this panel will keep the dynamic GUI parts
		 dynPanel = new JPanel();
		 dynPanel.setBorder(BorderFactory.createTitledBorder("Scissor Line"));
		 dynPanel.setLayout(gb);

		 //this panel keeps the radio buttons
		 dynButtonPanel = new JPanel();
		 dynButtonPanel.setLayout(dynGrid);

		 GridBagConstraints gbc = new GridBagConstraints();
		 gbc.anchor = GridBagConstraints.NORTHWEST;
		 gbc.fill = GridBagConstraints.BOTH;
		 gbc.ipadx=5;
		 gb.setConstraints(dynButtonPanel,gbc);
		 dynPanel.add(dynButtonPanel);

		 //this panel keeps the score
		 dynTxtPanel=new JPanel();
		 dynTxtPanel.setLayout(dynGrid);
		 gbc = new GridBagConstraints();
		 gbc.anchor = GridBagConstraints.NORTHWEST;
		 gbc.fill = GridBagConstraints.BOTH;
		 gbc.ipadx=5;
		 gb.setConstraints(dynTxtPanel,gbc);
		 dynPanel.add(dynTxtPanel);

		 gbc = new GridBagConstraints();
		 gbc.anchor = GridBagConstraints.NORTHWEST;
		 gbc.fill = GridBagConstraints.NONE;
		 gbc.ipadx=5;
		 gb.setConstraints(dynPanel,gbc);
		 getContentPane().add(dynPanel);


		 dynButtonPanel.add(makeDynRadioButton(1));
		 dynButtonPanel.add(makeDynRadioButton(2));
		 dynButtonPanel.add(makeDynRadioButton(3));
		 dynButtonPanel.add(makeDynRadioButton(4));


		 //  create a "static" panel to hold control buttons


		 statButtonPanel=new JPanel();
		 statButtonPanel.setBorder(BorderFactory.createTitledBorder("Actions"));
		 statButtonPanel.setLayout(gb);

		 gbc = new GridBagConstraints();
		 gbc.anchor = GridBagConstraints.NORTHWEST;
		 gbc.fill = GridBagConstraints.BOTH;
		 gbc.gridx=0;
		 gbc.gridwidth = GridBagConstraints.REMAINDER;
		 initializeButton = makeButton(INITIALIZE, "Initialize image to count");
		 gb.setConstraints(initializeButton,gbc);
		 statButtonPanel.add(initializeButton);

		 //Button of seed point
		 gbc = new GridBagConstraints();
		 gbc.anchor = GridBagConstraints.NORTHWEST;
		 gbc.fill = GridBagConstraints.BOTH;
		 gbc.gridx=0;
		 gbc.gridwidth = GridBagConstraints.REMAINDER;
		 drawButton = makeButton(DRAW, "Draw seed points line");
		 gb.setConstraints(drawButton,gbc);
		 statButtonPanel.add(drawButton);   

		 gbc = new GridBagConstraints();
		 gbc.anchor = GridBagConstraints.NORTHWEST;
		 gbc.fill = GridBagConstraints.BOTH;
		 gbc.gridx=0;
		 gbc.gridwidth = GridBagConstraints.REMAINDER;
		 gbc.insets = new Insets(3,0,3,0);
		 separator = new JSeparator(SwingConstants.HORIZONTAL);
		 separator.setPreferredSize(new Dimension(1,1));
		 gb.setConstraints(separator,gbc);
		 statButtonPanel.add(separator);

		 gbc = new GridBagConstraints();
		 gbc.anchor = GridBagConstraints.NORTHWEST;
		 gbc.fill = GridBagConstraints.BOTH;
		 gbc.gridx=0;
		 gbc.gridwidth = GridBagConstraints.REMAINDER;
		 deleteButton = makeButton(DELETE, "delete recent key point");
		 //  deleteButton.setEnabled(false);
		 gb.setConstraints(deleteButton,gbc);
		 statButtonPanel.add(deleteButton);

		 gbc = new GridBagConstraints();
		 gbc.anchor = GridBagConstraints.NORTHWEST;
		 gbc.fill = GridBagConstraints.BOTH;
		 gbc.gridx=0;
		 gbc.gridwidth = GridBagConstraints.REMAINDER;
		 ereaseButton = makeButton(EREASE, "Erease Current Scissor");
		 gb.setConstraints(ereaseButton,gbc);
		 statButtonPanel.add(ereaseButton);

		 gbc = new GridBagConstraints();
		 gbc.anchor = GridBagConstraints.NORTHWEST;
		 gbc.fill = GridBagConstraints.BOTH;
		 gbc.gridx=0;
		 gbc.insets = new Insets(3,0,3,0);
		 gbc.gridwidth = GridBagConstraints.REMAINDER;
		 separator = new JSeparator(SwingConstants.HORIZONTAL);
		 separator.setPreferredSize(new Dimension(1,1));
		 gb.setConstraints(separator,gbc);
		 statButtonPanel.add(separator);

		 gbc = new GridBagConstraints();
		 gbc.anchor = GridBagConstraints.NORTHWEST;
		 gbc.fill = GridBagConstraints.BOTH;
		 gbc.gridx=0;
		 gbc.gridwidth = GridBagConstraints.REMAINDER;
		 addButton = makeButton(ADD, "add an object point");
		 gb.setConstraints(addButton,gbc);
		 statButtonPanel.add(addButton);


		 gbc = new GridBagConstraints();
		 gbc.anchor = GridBagConstraints.NORTHWEST;
		 gbc.fill = GridBagConstraints.BOTH;
		 gbc.gridx=0;
		 gbc.gridwidth = GridBagConstraints.REMAINDER;
		 removeSButton = makeButton(REMOVECURRENTSCISSOR, "remove current object type");
		 gb.setConstraints(removeSButton,gbc);
		 statButtonPanel.add(removeSButton);

		 gbc = new GridBagConstraints();
		 gbc.anchor = GridBagConstraints.NORTHWEST;
		 gbc.fill = GridBagConstraints.BOTH;
		 gbc.gridx=0;
		 gbc.gridwidth = GridBagConstraints.REMAINDER;
		 gbc.insets = new Insets(3,0,3,0);
		 separator = new JSeparator(SwingConstants.HORIZONTAL);
		 separator.setPreferredSize(new Dimension(1,1));
		 gb.setConstraints(separator,gbc);
		 statButtonPanel.add(separator);

		 gbc = new GridBagConstraints();
		 gbc.anchor = GridBagConstraints.NORTHWEST;
		 gbc.fill = GridBagConstraints.BOTH;
		 gbc.gridx=0;
		 gbc.gridwidth = GridBagConstraints.REMAINDER;
		 resetButton=makeButton(RESET, "reset scissor");
		 gb.setConstraints(resetButton,gbc);
		 statButtonPanel.add(resetButton);

		 gbc = new GridBagConstraints();
		 gbc.anchor = GridBagConstraints.NORTHWEST;
		 gbc.fill = GridBagConstraints.BOTH;
		 gbc.gridx=0;
		 gbc.gridwidth = GridBagConstraints.REMAINDER;
		 measureButton = makeButton(MEASURE, "Measure pixel intensity of marker points");
		 gb.setConstraints(measureButton,gbc);
		 statButtonPanel.add(measureButton);

		 // validate button ------------------------------------
		 gbc = new GridBagConstraints();
		 gbc.anchor = GridBagConstraints.NORTHWEST;
		 gbc.fill = GridBagConstraints.BOTH;
		 gbc.gridx=0;
		 gbc.gridwidth = GridBagConstraints.REMAINDER;
		 validateButton = makeButton(VALIDATE, "Validate canvas");
		 gb.setConstraints(validateButton,gbc);
		 statButtonPanel.add(validateButton);
		 // end of validate button ------------------------------

		 gbc = new GridBagConstraints();
		 gbc.anchor = GridBagConstraints.NORTHWEST;
		 gbc.fill = GridBagConstraints.NONE;
		 gbc.ipadx=5;
		 gb.setConstraints(statButtonPanel,gbc);
		 getContentPane().add(statButtonPanel);

		 Runnable runner = new GUIShower(this);
		 EventQueue.invokeLater(runner);
	 }

	 /**
	  * 
	  * @param name         the name of the Button
	  * @param tooltip      the explication of button function
	  * @return  jButton
	  * 
	  */
	 private JButton makeButton(String name, String tooltip){
		 JButton jButton = new JButton(name);
		 jButton.setToolTipText(tooltip);
		 jButton.addActionListener(this);
		 return jButton;
	 }


	 /**
	  * Reset the frame
	  */
	 public void reset(){
		 if (typeVector.size()<1){
			 return;
		 }
		 ListIterator mit = typeVector.listIterator();
		 while (mit.hasNext()){
			 ScissorMarkerVector mv = (ScissorMarkerVector)mit.next();
			 mv.clear();
		 }
		 if (ic!=null)
			 ic.repaint();
	 }

	 /**
	  * Function for Initializing Image before we start Scissor
	  */    
	 private void initializeImage(){
		 reset();
		 img = WindowManager.getCurrentImage();
		 scissor=new ScissorController(img.getProcessor().convertToByte(true));
		 boolean v139t = IJ.getVersion().compareTo("1.39t")>=0;
		 if (img!=null && img.getTitle().indexOf("&")!=-1)
			 IJ.showMessage("Scissor", "\"Save Markers" );
		 if (img==null){
			 IJ.noImage();
		 }else if (img.getStackSize() == 1) {
			 ImageProcessor ip = img.getProcessor();
			 ip.resetRoi();
			 ip = ip.crop();
			 counterImg = new ImagePlus("Scissor Window - "+img.getTitle(), ip);
			 Vector displayList = v139t?img.getCanvas().getDisplayList():null;
			 ic = new ScissorImageCanvas(counterImg,scissor);
			 new ImageWindow(counterImg, ic);
		 } else if (img.getStackSize() > 1){
			 ImageStack stack = img.getStack();
			 int size = stack.getSize();
			 ImageStack counterStack = img.createEmptyStack();
			 for (int i = 1; i <= size; i++){
				 ImageProcessor ip = stack.getProcessor(i);
				 ip.resetRoi();
				 ip = ip.crop();
				 counterStack.addSlice(stack.getSliceLabel(i), ip);
			 }
			 counterImg = new ImagePlus("Scissor Window - "+img.getTitle(), counterStack);
			 counterImg.setDimensions(img.getNChannels(), img.getNSlices(), img.getNFrames());
			 if (img.isComposite()) {
				 counterImg = new CompositeImage(counterImg, ((CompositeImage)img).getMode());
				 ((CompositeImage) counterImg).copyLuts(img);
			 }
			 counterImg.setOpenAsHyperStack(img.isHyperStack());
			 Vector displayList = v139t?img.getCanvas().getDisplayList():null;
			 ImageProcessor ip = img.getProcessor();
			 ic = new ScissorImageCanvas(counterImg,scissor);
			 new StackWindow(counterImg, ic);
		 }
		 if (!keepOriginal){
			 img.hide();
		 }

		 addButton.setEnabled(true);
		 removeSButton.setEnabled(true);
		 deleteButton.setEnabled(true);
		 resetButton.setEnabled(true);
		 //if (isJava14) exportButton.setEnabled(true);
		 //exportimgButton.setEnabled(true);
		 measureButton.setEnabled(true);
		 validateButton.setEnabled(true);

		 this.img = img;

		 //scissor =new ScissorLine(img.getProcessor().convertToByte(true));

	 }

	 /**
	  * A JRadioButton for adding radio buttons in the panel
	  * @param id  the type of object, every object have an ID
	  * @return jrButton
	  */
	 private JRadioButton makeDynRadioButton(int id){
		 JRadioButton jrButton = new JRadioButton("Type "+ id);
		 jrButton.addActionListener(this);
		 dynRadioVector.add(jrButton);
		 radioGrp.add(jrButton);
		 markerVector = new ScissorMarkerVector(id);

		 typeVector.add(markerVector);
		 dynTxtPanel.add(makeDynamicTextArea());
		 dynTxtPanel.add(makeDynamicButton("Color"));
		 return jrButton;
	 }



	 private JButton makeDynamicButton(String name){
		 JButton jButton = new JButton(name);
		 jButton.addActionListener(this);
		 return jButton;
	 }
	 /**
	  * Make a text field for counting the area of evety type of object that we have marked
	  * @return txtfld   JTextField
	  */
	 private JTextField makeDynamicTextArea(){
		 JTextField txtFld = new JTextField();
		 txtFld.setHorizontalAlignment(JTextField.CENTER);
		 txtFld.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		 txtFld.setEditable(false);
		 txtFld.setText("0");
		 txtFieldVector.add(txtFld);
		 return txtFld;
	 }

	 //    public KeyEvent1(String title){
	 //    	
	 //    }
	 /**
	  * Generate numbers for counting area of object in the text field
	  */
	 void populateTxtFields(){
		 ListIterator it = typeVector.listIterator();
		 while (it.hasNext()){
			 int index = it.nextIndex();
			 ScissorMarkerVector markerVector = (ScissorMarkerVector)it.next();
			 int count = markerVector.size();
			 if (index< txtFieldVector.size()) {
				 JTextField tArea = (JTextField)txtFieldVector.get(index);
				 tArea.setText(""+scissor.getSurface(index));
			 }
		 }
		 validateLayout();
	 }
	 /**
	  * Validate the frame
	  */
	 void validateLayout(){
		 dynPanel.validate();
		 dynButtonPanel.validate();
		 dynTxtPanel.validate();
		 statButtonPanel.validate();
		 validate();
		 pack();
	 }
	 /**
	  * Action performed for every button
	  */
	 public void actionPerformed(ActionEvent event) {
		 String command = event.getActionCommand();

		 if (command.compareTo(ADD) == 0) {
			 int i = dynRadioVector.size() + 1;
			 dynGrid.setRows(i);
			 dynButtonPanel.add(makeDynRadioButton(i));
			 validateLayout();

			 //ic.clear();
			 //ic.setState(ic.SCISSOR_ACTIVE);
			 //	scissor.newScissorProcess();
			 //            

			 //            if (ic != null)
			 //                ic.setTypeVector(typeVector);
		 } else if (command.compareTo(REMOVECURRENTSCISSOR) == 0) {
			 if (dynRadioVector.size() > 1) {
				 JRadioButton rbutton = (JRadioButton)dynRadioVector.lastElement();
				 dynButtonPanel.remove(rbutton);
				 radioGrp.remove(rbutton);
				 dynRadioVector.removeElementAt(dynRadioVector.size() - 1);
				 dynGrid.setRows(dynRadioVector.size());
			 }
			 if (txtFieldVector.size() > 1) {
				 Component[] components = (Component[])dynTxtPanel.getComponents();
				 dynTxtPanel.remove((JButton)components[components.length-1]);
				 JTextField field = (JTextField)txtFieldVector.lastElement();
				 dynTxtPanel.remove(field);
				 txtFieldVector.removeElementAt(txtFieldVector.size() - 1);
			 }
			 if (typeVector.size() > 1) {
				 typeVector.removeElementAt(typeVector.size() - 1);
			 }
			 validateLayout();

		 } else if (command.compareTo(INITIALIZE) == 0){
			 initializeImage();
			 //        } else if (command.startsWith("Type")){ //COUNT
				 //            if (ic == null){
					 //                IJ.error("Please initialize the image first!");
					 //                return;
			 //            }
			 //            int index = Integer.parseInt(command.substring(command.indexOf(" ")+1,command.length()));
			 //            //ic.setDelmode(false); // just in case
			 //            currentMarkerVector = (ScissorMarkerVector)typeVector.get(index-1);
			 //            ic.setCurrentMarkerVector(currentMarkerVector);
		 } else if (command.compareTo(DELETE) == 0){
			 scissor.removeRecentKeyPoint();
		 } else if (command.compareTo(EREASE) == 0){
			 scissor.removeCurrentScissor();
		 } else if(command.compareTo(DRAW) == 0){
			 scissor.newScissorProcess();
		 } else if (command.compareTo(RESET) == 0){
			 scissor.resetScissorController();
			 if (ic == null)
				 initializeImage();
			 validateLayout();
		 } else if (command.compareTo(EXPORTIMG) == 0){
			 //            ic.imageWithMarkers().show();
			 // 	 scissor.addNewKeyPoint(x, y)
			 //scissor.endScissor();

			 scissor.setTypeColor(0,Color.blue);
			 scissor.setTypeColor(1,Color.red);
			 scissor.setTypeColor(2,Color.white);

		 } else if (command.compareTo(MEASURE) == 0){
			 populateTxtFields();
		 } else if (command.compareTo(VALIDATE) == 0) { // contour validé
			 System.out.println("Contour validé");
			 validCanvas = true;
		 } else if (command.compareTo("Color") == 0){
			 setLinecolor();
			 IJ.log("Color changed");
		 } else if (command.startsWith("Type")){ // Type Number
			 if (ic == null){
				 IJ.error("Please initialize the image first");
				 IJ.log("Please initialize the image first");
				 return;
			 }
			 IJ.log("Before change, CurrentTpye = " + scissor.getCurrentType());

			 int index = Integer.parseInt(command.substring(command.indexOf(" ")+1,command.length()));
			 scissor.setCurrentTpye(index-1);
			 IJ.log("After change, CurrentTpye = " + scissor.getCurrentType());
			 //scissor.setTypeColor(scissor.getCurrentType(), setLinecolor.selectedColor());
			 //	IJ.log("3333333333333333333333");
			 //			if (scissor.getCurrentType()==0){
			 //				IJ.log("0000000000000000");
			 //			}else if (scissor.getCurrentType()==1){
			 //				IJ.log("1111111111111");
			 //			}else if (scissor.getCurrentType()==2){
			 //				IJ.log("22222222222222");
			 //			}
		 } 

		 if (ic!=null)
			 ic.repaint();
		 //populateTxtFields();
	 }

	 /**
	  * Set line color by the palette
	  */
	 private void setLinecolor(){
		 JColorChooser color=new JColorChooser();
		 color.setVisible(true);
		 Color selectedColor=color.showDialog(ScissorFrame.this, "Color", Color.black);
		 scissor.setTypeColor(scissor.getCurrentType(), selectedColor);
	 }

	 /**
	  * Items for check boxs
	  */
	 public void itemStateChanged(ItemEvent e){

	 }

	 @Override
	 /**
	  * Action performed when key is pressed
	  */
	 public void keyPressed (KeyEvent e) {
		 int key=e.getKeyCode();
		 switch(key){
		 case KeyEvent.VK_BACK_SPACE:
			 IJ.log("ddddddddddd");
			 break;
		 case KeyEvent.VK_1:
			 IJ.log("ffffffffffff");
			 break;
		 default:
			 break;
		 }
	 }

	 @Override
	 /**
	  * Action performed when key is typed
	  */
	 public void keyTyped(KeyEvent e) {
		 // TODO Auto-generated method stub

	 }

	 @Override
	 /**
	  * Action performed when key is released
	  */
	 public void keyReleased(KeyEvent arg0) {
		 // TODO Auto-generated method stub

	 }

	 // added getter
	 public ScissorImageCanvas getIc() {
		 return ic;
	 }

}
