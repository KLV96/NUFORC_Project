package View;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import Controller.StatsNavLeft;
import Controller.StatsNavRight;
import Model.Statistics;

/**
 * the statsBoxView class contains two text fields for title and statistic
 * and two buttons to navigate.
 * 
 * can use a statistics model to populate data
 * @author Rewaz & Raihan
 *
 */
public class StatsBoxView extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	// East and West for buttons. North and Center for JLabels 
	private JPanel jpEast;
	private JPanel jpWest;
	private JPanel jpNorth;
	private JPanel jpCenter;
	private JButton jbLeft;
	private JButton jbRight;
	
	// This is for the labels ( The North is for the title of the statistics )
	private JLabel jlNorth;
	// This is for Number that is getting fetched ( a number from the 8 possible )
	private JLabel jlCenter;
	
	private StatsNavLeft statsNavLeft;
	private StatsNavRight statsNavRight;
	
	
	/**
	 * supplies a statistic to the statsBoxView
	 * @param statModel
	 */
	public StatsBoxView(Statistics statModel) {
		
		setLayout(new BorderLayout());
		initWidgets();
	}
	
	/**
	 * supplies controllers to the view
	 * @param statsNavLeft
	 * @param statsNavRight
	 */
	public StatsBoxView(StatsNavLeft statsNavLeft, StatsNavRight statsNavRight) {
		this.statsNavLeft = statsNavLeft;
		this.statsNavRight = statsNavRight;
		setLayout(new BorderLayout());
		initWidgets();
	}
	
	/**
	 * initialise the widgets
	 */
	private void initWidgets() {
		
		// dimension used to give size to the buttons
		Dimension buttonDim = new Dimension(45, 250);
		
		jpEast = new JPanel();
		jpWest = new JPanel();
		jpNorth = new JPanel();
		jpCenter = new JPanel();
		jlNorth = new JLabel();
		jlCenter = new JLabel();
		
		jpCenter.setLayout(new GridLayout(1, 2));
			
		jpCenter.setPreferredSize(new Dimension(400,250));
		
		jbLeft = new JButton("<");
		
		jbLeft.setPreferredSize(buttonDim);
		jbLeft.setMaximumSize(getSize());
		
		jbRight = new JButton(">");
		jbRight.setPreferredSize(buttonDim);
		jbRight.setMaximumSize(getSize());
		jpNorth.add(jlNorth);
	
		jbLeft.setCursor(new Cursor(Cursor.HAND_CURSOR));
		jbRight.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		jpCenter.add(new JLabel("  "));
		jpCenter.add(jlCenter);
		jpEast.add(jbRight);
		jpWest.add(jbLeft);
		
		
		// This is to set the layout of the statsBox where East & West have buttons. North & Centre have JLabel
		add(jpEast, BorderLayout.EAST);
		add(jpWest, BorderLayout.WEST);
		add(jpNorth, BorderLayout.NORTH);
		add(jpCenter, BorderLayout.CENTER);
		
		// each of the buttons has the right action listener
		jbRight.addActionListener(statsNavRight);
		jbLeft.addActionListener(statsNavLeft);
	}
	
	/**
	 * sets the title/name field of the statistic
	 * @param title
	 */
	public void setStatName(String title) {
		jlNorth.setText(title);
	}
	
	/**
	 * returns the title of the statistic
	 * @return statistic title/name
	 */
	public String getStatName() {
		return jlNorth.getText();
	}
	
	/**
	 * sets the statistics text
	 * @param statistic
	 */
	public void setStat(String statistic) {
		jlCenter.setText(statistic);
	}
	
	/**
	 * gets the statistics from the body of the statics box
	 * @return
	 */
	public String getStat() {
		return jlCenter.getText();
	}

	/**
	 * updates all the text of the statistics box 
	 * @param statModel
	 */
	public void updateStats(Statistics statModel) {
		
		//statModel.processStat();
		setStatName(statModel.getStatType());
		setStat(statModel.getStatistic());
//		System.out.println(statModel + " is " + getStat());
	}
	
	/**
	 * returns the right button
	 * @return
	 */
	public JButton getRightButton()
	{
		return jbRight;
	}
	
	/**
	 * returns the left button
	 * @return
	 */
	public JButton getLeftButton()
	{
		return jbLeft;
	}
}