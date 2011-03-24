package fr.irstv.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.util.ResourceBundle;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import fr.irstv.controller.MenuBarHPointListHideController;
import fr.irstv.controller.MenuBarHPointListShowController;
import fr.irstv.controller.MenuBarSegmentListHideController;
import fr.irstv.controller.MenuBarSegmentListShowController;
import fr.irstv.controller.MenuBarVanishingPointListHideController;
import fr.irstv.controller.MenuBarVanishingPointListShowController;
import fr.irstv.controller.MenuBarWorkingAreaHideController;
import fr.irstv.controller.MenuBarWorkingAreaShowController;
import fr.irstv.dataModel.ImageModel;

@SuppressWarnings("serial")
public class VanishingPointAppView extends JFrame{

	private JDesktopPane desktop;
	private MenuBarView menuBarView;
	private SegmentListInternalFrame segmentListInternalFrame;
	private VanishingPointListInternalFrame vanishingPointListInternalFrame;
	private WorkingAreaInternalFrame workingAreaInternalFrame;
	private HPointListInternalFrame hPointListInternalFrame;

	/**
	 * @uml.property   name="fileInfoTextField"
	 */
	private JTextField fileInfoText;

	public VanishingPointAppView(ResourceBundle stringValues)
	{
		//create a JDesktopPane
		this.desktop = new  JDesktopPane();
		this.desktop.setBackground(Color.lightGray);
		//desktop.add(new JScrollPane());
		//Create a control panel
		JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		//with a menuBar
		this.menuBarView = new MenuBarView(stringValues);
		controlPanel.add(menuBarView);

		//fileInfoText
		this.fileInfoText = new JTextField();
		this.fileInfoText.setBackground(Color.lightGray);
		this.fileInfoText.setEditable(false);

		// Get to the screen size
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		//Manadge ContentPane
		this.getContentPane().add(controlPanel,BorderLayout.NORTH);
		this.getContentPane().add(desktop,BorderLayout.CENTER);
		this.getContentPane().add(fileInfoText, BorderLayout.SOUTH);

		this.setSize(dim.width,dim.height);
		// Set the closing operation so the application is finished.
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//show the frame
		//this.setBackground(Color.lightGray);
		this.setVisible(true);
	}

	public JDesktopPane getDesktop() {
		return desktop;
	}

	public void setDesktop(JDesktopPane desktop) {
		this.desktop = desktop;
	}

	public MenuBarView getMenuBarView() {
		return menuBarView;
	}

	public void setMenuBarView(MenuBarView menuBarView) {
		this.menuBarView = menuBarView;
	}

	/**
	 * Getter of the property <tt>fileInfoTextField</tt>
	 * @return  Returns the fileInfoTextField.
	 * @uml.property  name="fileInfoTextField"
	 */
	public JTextField getFileInfoTextField() {
		return fileInfoText;
	}

	/**
	 * Setter of the property <tt>fileInfoTextField</tt>
	 * @param fileInfoTextField  The fileInfoTextField to set.
	 * @uml.property  name="fileInfoTextField"
	 */
	public void setFileInfoTextField(JTextField fileInfoTextField) {
		this.fileInfoText = fileInfoTextField;
	}

	public SegmentListInternalFrame getSegmentListInternalFrame() {
		return segmentListInternalFrame;
	}

	public void setSegmentListInternalFrame(
			SegmentListInternalFrame segmentListInternalFrame) {
		this.segmentListInternalFrame = segmentListInternalFrame;
	}

	public VanishingPointListInternalFrame getVanishingPointListInternalFrame() {
		return vanishingPointListInternalFrame;
	}

	public void setVanishingPointListInternalFrame(
			VanishingPointListInternalFrame vanishingPointListInternalFrame) {
		this.vanishingPointListInternalFrame = vanishingPointListInternalFrame;
	}


	public JTextField getFileInfoText() {
		return fileInfoText;
	}

	public void setFileInfoText(JTextField fileInfoText) {
		this.fileInfoText = fileInfoText;
	}

	public HPointListInternalFrame getHPointListInternalFrame() {
		return hPointListInternalFrame;
	}

	public void setHPointListInternalFrame(
			HPointListInternalFrame pointListInternalFrame) {
		hPointListInternalFrame = pointListInternalFrame;
	}

	public WorkingAreaInternalFrame getWorkingAreaInternalFrame() {
		return workingAreaInternalFrame;
	}

	public void setWorkingAreaInternalFrame(
			WorkingAreaInternalFrame workingAreaInternalFrame) {
		this.workingAreaInternalFrame = workingAreaInternalFrame;
	}

	/**
		 */
		/*	public VanishingPointView(String file){
			//Load the image which file name was passed as the first argument to the
			// application.
			PlanarImage image = JAI.create("fileload", file);
			// Get some information about the image
			String imageInfo = "Dimensions: "+image.getWidth()+"x"+image.getHeight()+
			" Bands:"+image.getNumBands();
			// Create a frame for display.
			JFrame frame = new JFrame();
			frame.setTitle("VanishingPointApplication: "+file);
			// Get the JFrame's ContentPane.
			Container contentPane = frame.getContentPane();
			contentPane.setLayout(new BorderLayout());
			// Create an instance of DisplayJAI.
			DisplayJAI dj = new DisplayJAI(image);
			// Add to the JFrame's ContentPane an instance of JScrollPane containing the
			// DisplayJAI instance.
			contentPane.add(new JScrollPane(dj),BorderLayout.CENTER);
			// Add a text label with the image information.
			contentPane.add(new JLabel(imageInfo),BorderLayout.SOUTH);
			// Set the closing operation so the application is finished.
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(1280,800); // adjust the frame size.
			frame.setVisible(true); // show the frame.
		}*/
		public void openInternalFrame(ResourceBundle stringValues, ImageModel image){

			//JInternalFrame segmentList vanishingPointList

			//get desktop dimension
			Dimension desktopDim = new Dimension(this.desktop.getWidth(), this.desktop.getHeight());

			int waWidth = desktopDim.width*4/5;
			int listWidth = desktopDim.width - waWidth;
			int listHeight = desktopDim.height*2/5;

			//set the working Area Internal Frame
			this.workingAreaInternalFrame = new WorkingAreaInternalFrame(stringValues, image);
			this.workingAreaInternalFrame.setSize(waWidth , desktopDim.height);
			this.desktop.add(this.workingAreaInternalFrame);
			this.workingAreaInternalFrame.setLocation(0, 0);
			this.workingAreaInternalFrame.setVisible(true);
			this.menuBarView.getWorkingAreaShow().setEnabled(false);
			this.menuBarView.getWorkingAreaHide().setEnabled(true);
			//add listeners
			MenuBarWorkingAreaShowController menuBarWorkingAreaShowController =
				new MenuBarWorkingAreaShowController(this.menuBarView.getWorkingAreaShow(),
						this.menuBarView.getWorkingAreaHide(), this.workingAreaInternalFrame);
			this.menuBarView.getWorkingAreaShow().addActionListener(menuBarWorkingAreaShowController);
			MenuBarWorkingAreaHideController menuBarWorkingAreaHideController =
				new MenuBarWorkingAreaHideController(this.menuBarView.getWorkingAreaShow(),
						this.menuBarView.getWorkingAreaHide(), this.workingAreaInternalFrame);
			this.menuBarView.getWorkingAreaHide().addActionListener(menuBarWorkingAreaHideController);


			//set the Segment List Internal Frame
			this.segmentListInternalFrame = new SegmentListInternalFrame(stringValues);
			this.segmentListInternalFrame.setSize(listWidth, listHeight);
			this.desktop.add(this.segmentListInternalFrame);
			this.segmentListInternalFrame.setLocation(waWidth, 0);
			this.segmentListInternalFrame.setVisible(true);
			this.menuBarView.getSegmentsListShow().setEnabled(false);
			this.menuBarView.getSegmentsListHide().setEnabled(true);
			//add listeners
			MenuBarSegmentListShowController menuBarSegmentListShowController =
				new MenuBarSegmentListShowController(this.menuBarView.getSegmentsListShow(),
						this.menuBarView.getSegmentsListHide(), this.segmentListInternalFrame);
			this.menuBarView.getSegmentsListShow().addActionListener(menuBarSegmentListShowController);
			MenuBarSegmentListHideController menuBarSegmentListHideController =
				new MenuBarSegmentListHideController(this.menuBarView.getSegmentsListShow(),
						this.menuBarView.getSegmentsListHide(), this.segmentListInternalFrame);
			this.menuBarView.getSegmentsListHide().addActionListener(menuBarSegmentListHideController);

			//set the H Point Internal frame
			this.hPointListInternalFrame = new HPointListInternalFrame(stringValues);
			this.hPointListInternalFrame.setSize(listWidth, listHeight);
			this.desktop.add(this.hPointListInternalFrame);
			this.hPointListInternalFrame.setLocation(waWidth, listHeight);
			this.hPointListInternalFrame.setVisible(true);
			this.menuBarView.getHPointsListShow().setEnabled(false);
			this.menuBarView.getHPointsListHide().setEnabled(true);
			//add listeners
			MenuBarHPointListShowController menuBarHPointListShowController =
				new MenuBarHPointListShowController(this.menuBarView.getHPointsListShow(),
						this.menuBarView.getHPointsListHide(), this.hPointListInternalFrame);
			this.menuBarView.getHPointsListShow().addActionListener(menuBarHPointListShowController);
			MenuBarHPointListHideController menuBarHPointListHideController =
				new MenuBarHPointListHideController(this.menuBarView.getHPointsListShow(),
						this.menuBarView.getHPointsListHide(), this.hPointListInternalFrame);
			this.menuBarView.getHPointsListHide().addActionListener(menuBarHPointListHideController);

			//set the Vanishing Point List Internal Frame
			this.vanishingPointListInternalFrame = new VanishingPointListInternalFrame(stringValues);
			this.vanishingPointListInternalFrame.setSize(listWidth, desktopDim.height - 2*listHeight);
			this.desktop.add(this.vanishingPointListInternalFrame);
			this.vanishingPointListInternalFrame.setLocation(waWidth, 2*listHeight);
			this.vanishingPointListInternalFrame.setVisible(true);
			this.menuBarView.getVanishingPointListShow().setEnabled(false);
			this.menuBarView.getVanishingPointListHide().setEnabled(true);
			//add listeners
			MenuBarVanishingPointListShowController menuBarVanishingPointListShowController =
				new MenuBarVanishingPointListShowController(this.menuBarView.getVanishingPointListShow(),
						this.menuBarView.getVanishingPointListHide(), this.vanishingPointListInternalFrame);
			this.menuBarView.getVanishingPointListShow().addActionListener(menuBarVanishingPointListShowController);
			MenuBarVanishingPointListHideController menuBarVanishingPointListHideController =
				new MenuBarVanishingPointListHideController(this.menuBarView.getVanishingPointListShow(),
						this.menuBarView.getVanishingPointListHide(), this.vanishingPointListInternalFrame);
			this.menuBarView.getVanishingPointListHide().addActionListener(menuBarVanishingPointListHideController);

			this.setVisible(true);
		}

		public void closeinternalFrame(){
			this.workingAreaInternalFrame.dispose();
			this.segmentListInternalFrame.dispose();
			this.hPointListInternalFrame.dispose();
			this.vanishingPointListInternalFrame.dispose();
		}

}
