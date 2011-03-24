package fr.irstv.view;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ResourceBundle;

import javax.swing.JComboBox;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 * Contain the View and menu bar and items.
 * @author mservier
 *
 */
@SuppressWarnings("serial")
public class MenuBarView extends JMenuBar{

	private JMenu vanishingPointApp;
	private JMenuItem vanishingPointAppClose;
	
	private JMenu file;
	private JMenuItem fileOpen;
	private JMenuItem urlOpen;
	private JMenuItem fileClose;
	
	private JMenu workingArea;
	private JMenuItem workingAreaShow;
	private JMenuItem workingAreaHide;
	
	private JMenu vanishingPoint;	
	private JMenuItem vanishingPointShow;
	private JMenuItem vanishingPointHide;
	private JMenuItem vanishingPointShowCircles;
	private JMenuItem vanishingPointHideCircles;
	private JMenuItem vanishingPointListShow;
	private JMenuItem vanishingPointListHide;
	
	private JMenu segments;
	private JMenuItem segmentsShow;
	private JMenuItem segmentsHide;
	private JMenuItem segmentsShowVanishingPointColors;
	private JMenuItem segmentsHideVanishingPointColors;
	private JMenuItem segmentsListShow;
	private JMenuItem segmentsListHide;

	private JMenu hPoints;
	private JMenuItem hPointsShow;
	private JMenuItem hPointsHide;
	private JMenuItem hPointsListShow;
	private JMenuItem hPointsListHide;
	
	private JComboBox scaleComboBox;
	private String[] scalePattern = {"0.1", "0.2", "0.5", "1", "1.2", "1.5", "2"};

	/**
	 * Constructor.
	 *
	 */
	public MenuBarView(ResourceBundle param){
		
		vanishingPointApp = new JMenu(param.getString(Constants.MENU_VANISHING_POINT_APP_NAME));
		vanishingPointAppClose = new JMenuItem(param.getString(Constants.MENU_VANISHING_POINT_APP_CLOSE));
		
		file = new JMenu(param.getString(Constants.MENU_FILE_NAME));
		urlOpen = new JMenuItem(param.getString(Constants.MENU_URL_OPEN));
		fileOpen = new JMenuItem(param.getString(Constants.MENU_FILE_OPEN));
		fileClose = new JMenuItem(param.getString(Constants.MENU_FILE_CLOSE));
		
		workingArea = new JMenu(param.getString(Constants.MENU_WORKING_AREA_NAME));
		workingAreaShow = new JMenuItem(param.getString(Constants.MENU_WORKING_AREA_SHOW));
		workingAreaHide = new JMenuItem(param.getString(Constants.MENU_WORKING_AREA_HIDE));

		vanishingPoint = new JMenu(param.getString(Constants.MENU_VANISHING_POINT_NAME));	
		vanishingPointShow = new JMenuItem(param.getString(Constants.MENU_VANISHING_POINT_SHOW));
		vanishingPointHide = new JMenuItem(param.getString(Constants.MENU_VANISHING_POINT_HIDE));
		vanishingPointShowCircles = new JMenuItem(param.getString(Constants.MENU_VANISHING_POINT_SHOW_CIRCLES));
		vanishingPointHideCircles = new JMenuItem(param.getString(Constants.MENU_VANISHING_POINT_HIDE_CIRCLES));
		vanishingPointListShow = new JMenuItem(param.getString(Constants.MENU_VANISHING_POINT_SHOW_LIST));
		vanishingPointListHide = new JMenuItem(param.getString(Constants.MENU_VANISHING_POINT_HIDE_LIST));
		
		segments = new JMenu(param.getString(Constants.MENU_SEGMENT_NAME));
		segmentsShow = new JMenuItem(param.getString(Constants.MENU_SEGMENT_SHOW));
		segmentsHide = new JMenuItem(param.getString(Constants.MENU_SEGMENT_HIDE));
		segmentsShowVanishingPointColors = new JMenuItem(param.getString(Constants.MENU_SEGMENT_SHOW_VANISHING_POINT_COLOR));
		segmentsHideVanishingPointColors = new JMenuItem(param.getString(Constants.MENU_SEGMENT_HIDE_VANISHING_POINT_COLOR));
		segmentsListShow = new JMenuItem(param.getString(Constants.MENU_SEGMENT_LIST_SHOW));
		segmentsListHide = new JMenuItem(param.getString(Constants.MENU_SEGMENT_LIST_HIDE));
		
		
		hPoints = new JMenu(param.getString(Constants.MENU_H_POINT_NAME));
		hPointsShow = new JMenuItem(param.getString(Constants.MENU_H_POINT_SHOW));
		hPointsHide = new JMenuItem(param.getString(Constants.MENU_H_POINT_HIDE));
		hPointsListShow = new JMenuItem(param.getString(Constants.MENU_H_POINT_LIST_SHOW));
		hPointsListHide = new JMenuItem(param.getString(Constants.MENU_H_POINT_LIST_HIDE));
		
		scaleComboBox = new JComboBox(scalePattern);
		
		// VannishingPointApp menu
		vanishingPointApp.setMnemonic(KeyEvent.VK_A);
		vanishingPointApp.getAccessibleContext().setAccessibleDescription(param.getString(Constants.MENU_VANISHING_POINT_APP_NAME_DESCRIPTION));
		this.add(vanishingPointApp);
		
		vanishingPointAppClose.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.META_MASK));
		vanishingPointAppClose.getAccessibleContext().setAccessibleDescription(param.getString(Constants.MENU_VANISHING_POINT_APP_CLOSE_DESCRIPTION));
		vanishingPointApp.add(vanishingPointAppClose);
		
		// File menu
		file.setMnemonic(KeyEvent.VK_F);
		file.getAccessibleContext().setAccessibleDescription(param.getString(Constants.MENU_FILE_NAME_DESCRIPTION));
		this.add(file);

		fileOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.META_MASK));
		fileOpen.getAccessibleContext().setAccessibleDescription(param.getString(Constants.MENU_FILE_OPEN_DESCRIPTION));
		file.add(fileOpen);

		urlOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.SHIFT_MASK+ActionEvent.META_MASK));
		urlOpen.getAccessibleContext().setAccessibleDescription(param.getString(Constants.MENU_URL_OPEN_DESCRIPTION));
		file.add(urlOpen);
		
		fileClose.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.META_MASK));
		fileClose.getAccessibleContext().setAccessibleDescription(param.getString(Constants.MENU_FILE_CLOSE_DESCRIPTION));
		file.add(fileClose);

		// Working Area Menu
		workingArea.setMnemonic(KeyEvent.VK_A);
		workingArea.getAccessibleContext().setAccessibleDescription(param.getString(Constants.MENU_WORKING_AREA_NAME_DESCRIPTION));
		this.add(workingArea);
		
		workingAreaShow.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
		workingAreaShow.getAccessibleContext().setAccessibleDescription(param.getString(Constants.MENU_WORKING_AREA_SHOW_DESCRIPTION));
		workingArea.add(workingAreaShow);
		
		workingAreaHide.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.SHIFT_MASK+ActionEvent.CTRL_MASK));
		workingAreaHide.getAccessibleContext().setAccessibleDescription(param.getString(Constants.MENU_WORKING_AREA_HIDE_DESCRIPTION));
		workingArea.add(workingAreaHide);
		
		// Vanishing Point Menu
		vanishingPoint.setMnemonic(KeyEvent.VK_V);
		vanishingPoint.getAccessibleContext().setAccessibleDescription(param.getString(Constants.MENU_VANISHING_POINT_NAME_DESCRIPTION));
		this.add(vanishingPoint);

		vanishingPointShow.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.META_MASK));
		vanishingPointShow.getAccessibleContext().setAccessibleDescription(param.getString(Constants.MENU_VANISHING_POINT_SHOW_DESCRIPTION));
		vanishingPoint.add(vanishingPointShow);

		vanishingPointHide.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.SHIFT_MASK + ActionEvent.META_MASK));
		vanishingPointHide.getAccessibleContext().setAccessibleDescription(param.getString(Constants.MENU_VANISHING_POINT_HIDE_DESCRIPTION));
		vanishingPoint.add(vanishingPointHide);

		vanishingPointShowCircles.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, ActionEvent.META_MASK));
		vanishingPointShowCircles.getAccessibleContext().setAccessibleDescription(param.getString(Constants.MENU_VANISHING_POINT_SHOW_CIRCLES_DESCRIPTION));
		vanishingPoint.add(vanishingPointShowCircles);

		vanishingPointHideCircles.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, ActionEvent.SHIFT_MASK + ActionEvent.META_MASK));
		vanishingPointHideCircles.getAccessibleContext().setAccessibleDescription(param.getString(Constants.MENU_VANISHING_POINT_HIDE_CIRCLES_DESCRIPTION));
		vanishingPoint.add(vanishingPointHideCircles);
		
		vanishingPointListShow.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
		vanishingPointListShow.getAccessibleContext().setAccessibleDescription(param.getString(Constants.MENU_VANISHING_POINT_SHOW_LIST_DESCRIPTION));
		vanishingPoint.add(vanishingPointListShow);
		
		vanishingPointListHide.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.SHIFT_MASK + ActionEvent.CTRL_MASK));
		vanishingPointListHide.getAccessibleContext().setAccessibleDescription(param.getString(Constants.MENU_VANISHING_POINT_HIDE_LIST_DESCRIPTION));
		vanishingPoint.add(vanishingPointListHide);

		// Segments Menu
		segments.setMnemonic(KeyEvent.VK_S);
		segments.getAccessibleContext().setAccessibleDescription(param.getString(Constants.MENU_SEGMENT_NAME_DESCRIPTION));
		this.add(segments);

		segmentsShow.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.META_MASK));
		segmentsShow.getAccessibleContext().setAccessibleDescription(param.getString(Constants.MENU_SEGMENT_SHOW_DESCRIPTION));
		segments.add(segmentsShow);

		segmentsHide.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.SHIFT_MASK + ActionEvent.META_MASK));
		segmentsHide.getAccessibleContext().setAccessibleDescription(param.getString(Constants.MENU_SEGMENT_HIDE_DESCRIPTION));
		segments.add(segmentsHide);

		segmentsShowVanishingPointColors.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.META_MASK));
		segmentsShowVanishingPointColors.getAccessibleContext().setAccessibleDescription(param.getString(Constants.MENU_SEGMENT_SHOW_VANISHING_POINT_COLOR_DESCRIPTION));
		segments.add(segmentsShowVanishingPointColors);

		segmentsHideVanishingPointColors.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.SHIFT_MASK + ActionEvent.META_MASK));
		segmentsHideVanishingPointColors.getAccessibleContext().setAccessibleDescription(param.getString(Constants.MENU_SEGMENT_HIDE_VANISHING_POINT_COLOR_DESCRIPTION));
		segments.add(segmentsHideVanishingPointColors);

		segmentsListShow.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		segmentsListShow.getAccessibleContext().setAccessibleDescription(param.getString(Constants.MENU_SEGMENT_LIST_SHOW_DESCRIPTION));
		segments.add(segmentsListShow);
		
		segmentsListHide.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.SHIFT_MASK + ActionEvent.CTRL_MASK));
		segmentsListHide.getAccessibleContext().setAccessibleDescription(param.getString(Constants.MENU_SEGMENT_HIDE_DESCRIPTION));
		segments.add(segmentsListHide);
		
		// H Point Menu
		hPoints.setMnemonic(KeyEvent.VK_H);
		hPoints.getAccessibleContext().setAccessibleDescription(param.getString(Constants.MENU_H_POINT_NAME_DESCRIPTION));
		this.add(hPoints);

		hPointsShow.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.META_MASK));
		hPointsShow.getAccessibleContext().setAccessibleDescription(param.getString(Constants.MENU_H_POINT_SHOW_DESCRIPTION));
		hPoints.add(hPointsShow);

		hPointsHide.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.SHIFT_MASK + ActionEvent.META_MASK));
		hPointsHide.getAccessibleContext().setAccessibleDescription(param.getString(Constants.MENU_H_POINT_HIDE_DESCRIPTION));
		hPoints.add(hPointsHide);
		
		hPointsListShow.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
		hPointsListShow.getAccessibleContext().setAccessibleDescription(param.getString(Constants.MENU_H_POINT_LIST_SHOW_DESCRIPTION));
		hPoints.add(hPointsListShow);
		
		hPointsListHide.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.SHIFT_MASK + ActionEvent.CTRL_MASK));
		hPointsListHide.getAccessibleContext().setAccessibleDescription(param.getString(Constants.MENU_H_POINT_LIST_HIDE_DESCRIPTION));
		hPoints.add(hPointsListHide);
		
		
		//scale combo box
		this.add(scaleComboBox);
		
		//disables the Unnecessary MenuItem		
		
		fileClose.setEnabled(false);
		
		workingAreaShow.setEnabled(false);
		workingAreaHide.setEnabled(false);
		
		vanishingPointShow.setEnabled(false);
		vanishingPointHide.setEnabled(false);
		vanishingPointShowCircles.setEnabled(false);
		vanishingPointHideCircles.setEnabled(false);
		vanishingPointListShow.setEnabled(false);
		vanishingPointListHide.setEnabled(false);
		
		segmentsShow.setEnabled(false);
		segmentsHide.setEnabled(false);
		segmentsShowVanishingPointColors.setEnabled(false);
		segmentsHideVanishingPointColors.setEnabled(false);
		segmentsListShow.setEnabled(false);
		segmentsListHide.setEnabled(false);

		hPointsShow.setEnabled(false);
		hPointsHide.setEnabled(false);
		hPointsListShow.setEnabled(false);
		hPointsListHide.setEnabled(false);
		
		//set editable the scale combo box
		scaleComboBox.setEditable(true);
		
	}



	public JMenu getFile() {
		return file;
	}



	public void setFile(JMenu file) {
		this.file = file;
	}



	public JMenuItem getFileClose() {
		return fileClose;
	}



	public void setFileClose(JMenuItem fileClose) {
		this.fileClose = fileClose;
	}



	public JMenuItem getFileOpen() {
		return fileOpen;
	}



	public void setFileOpen(JMenuItem fileOpen) {
		this.fileOpen = fileOpen;
	}



	public JMenu getHPoints() {
		return hPoints;
	}



	public void setHPoints(JMenu points) {
		hPoints = points;
	}



	public JMenuItem getHPointsHide() {
		return hPointsHide;
	}



	public void setHPointsHide(JMenuItem pointsHide) {
		hPointsHide = pointsHide;
	}



	public JMenuItem getHPointsShow() {
		return hPointsShow;
	}



	public void setHPointsShow(JMenuItem pointsShow) {
		hPointsShow = pointsShow;
	}



	public JMenu getSegments() {
		return segments;
	}



	public void setSegments(JMenu segments) {
		this.segments = segments;
	}



	public JMenuItem getSegmentsHide() {
		return segmentsHide;
	}



	public void setSegmentsHide(JMenuItem segmentsHide) {
		this.segmentsHide = segmentsHide;
	}



	public JMenuItem getSegmentsHideVanishingPointColors() {
		return segmentsHideVanishingPointColors;
	}



	public void setSegmentsHideVanishingPointColors(
			JMenuItem segmentsHideVanishingPointColors) {
		this.segmentsHideVanishingPointColors = segmentsHideVanishingPointColors;
	}



	public JMenuItem getSegmentsShow() {
		return segmentsShow;
	}



	public void setSegmentsShow(JMenuItem segmentsShow) {
		this.segmentsShow = segmentsShow;
	}



	public JMenuItem getSegmentsShowVanishingPointColors() {
		return segmentsShowVanishingPointColors;
	}



	public void setSegmentsShowVanishingPointColors(
			JMenuItem segmentsShowVanishingPointColors) {
		this.segmentsShowVanishingPointColors = segmentsShowVanishingPointColors;
	}



	public JMenu getVanishingPoint() {
		return vanishingPoint;
	}



	public void setVanishingPoint(JMenu vanishingPoint) {
		this.vanishingPoint = vanishingPoint;
	}



	public JMenuItem getVanishingPointHide() {
		return vanishingPointHide;
	}



	public void setVanishingPointHide(JMenuItem vanishingPointHide) {
		this.vanishingPointHide = vanishingPointHide;
	}



	public JMenuItem getVanishingPointHideCircles() {
		return vanishingPointHideCircles;
	}



	public void setVanishingPointHideCircles(JMenuItem vanishingPointHideCircles) {
		this.vanishingPointHideCircles = vanishingPointHideCircles;
	}



	public JMenuItem getVanishingPointShow() {
		return vanishingPointShow;
	}



	public void setVanishingPointShow(JMenuItem vanishingPointShow) {
		this.vanishingPointShow = vanishingPointShow;
	}



	public JMenuItem getVanishingPointShowCircles() {
		return vanishingPointShowCircles;
	}



	public void setVanishingPointShowCircles(JMenuItem vanishingPointShowCircles) {
		this.vanishingPointShowCircles = vanishingPointShowCircles;
	}



	public JMenu getVanishingPointApp() {
		return vanishingPointApp;
	}



	public void setVanishingPointApp(JMenu vanishingPointApp) {
		this.vanishingPointApp = vanishingPointApp;
	}



	public JMenuItem getVanishingPointAppClose() {
		return vanishingPointAppClose;
	}



	public void setVanishingPointAppClose(JMenuItem vanishingPointAppClose) {
		this.vanishingPointAppClose = vanishingPointAppClose;
	}



	public JMenuItem getURLOpen() {
		return urlOpen;
	}



	public void setURLOpen(JMenuItem urlOpen) {
		this.urlOpen = urlOpen;
	}



	public JMenuItem getHPointsListHide() {
		return hPointsListHide;
	}



	public void setHPointsListHide(JMenuItem pointsListHide) {
		hPointsListHide = pointsListHide;
	}



	public JMenuItem getHPointsListShow() {
		return hPointsListShow;
	}



	public void setHPointsListShow(JMenuItem pointsListShow) {
		hPointsListShow = pointsListShow;
	}



	public JMenuItem getSegmentsListHide() {
		return segmentsListHide;
	}



	public void setSegmentsListHide(JMenuItem segmentsListHide) {
		this.segmentsListHide = segmentsListHide;
	}



	public JMenuItem getSegmentsListShow() {
		return segmentsListShow;
	}



	public void setSegmentsListShow(JMenuItem segmentsListShow) {
		this.segmentsListShow = segmentsListShow;
	}



	public JMenuItem getVanishingPointListHide() {
		return vanishingPointListHide;
	}



	public void setVanishingPointListHide(JMenuItem vanishingPointListHide) {
		this.vanishingPointListHide = vanishingPointListHide;
	}



	public JMenuItem getVanishingPointListShow() {
		return vanishingPointListShow;
	}



	public void setVanishingPointListShow(JMenuItem vanishingPointListShow) {
		this.vanishingPointListShow = vanishingPointListShow;
	}



	public JMenu getWorkingArea() {
		return workingArea;
	}



	public void setWorkingArea(JMenu workingArea) {
		this.workingArea = workingArea;
	}



	public JMenuItem getWorkingAreaHide() {
		return workingAreaHide;
	}



	public void setWorkingAreaHide(JMenuItem workingAreaHide) {
		this.workingAreaHide = workingAreaHide;
	}



	public JMenuItem getWorkingAreaShow() {
		return workingAreaShow;
	}



	public void setWorkingAreaShow(JMenuItem workingAreaShow) {
		this.workingAreaShow = workingAreaShow;
	}



	public JComboBox getScaleComboBox() {
		return scaleComboBox;
	}



	public void setScaleComboBox(JComboBox scale) {
		this.scaleComboBox = scale;
	}



	public JMenuItem getUrlOpen() {
		return urlOpen;
	}



	public void setUrlOpen(JMenuItem urlOpen) {
		this.urlOpen = urlOpen;
	}

}

