package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import Controller.DateUpdateAL;
import Controller.NavLeftButton;
import Controller.NavRightButton;
import Controller.TickerChangeListener;
import Model.IncidentFetcher;
import Model.SoundUtils;

public class BaseFrame extends JFrame implements Observer {
	private static final long serialVersionUID = 7215803232734759333L;

	// Base Frame panels
	private JPanel northPanel;
	private JPanel centerPanel;
	private JPanel southPanel;
	// Labels for JSpinners and holding the last version of the ripley API
	private JLabel startDateLabel;
	private JLabel endDateLabel;
	private JLabel lastUpdate;
	// Components used for fetching incident data
	private DateUpdateAL dateFetcher;
	private JSpinner startDateSpinner;
	private JSpinner endDateSpinner;
	private TickerChangeListener tickerListener;
	private JButton jbExecuteSearch;
	// Panel navigation buttons
	private JButton scrollBack;
	private JButton scrollForward;

	// model class holding incident data
	private IncidentFetcher incidentFetcher;

	// Interior panels for each section of coursework
	private WelcomePanel welcomePanel;
	private StatsPanel statsPanel;
	private MapPanel mapPanel;
	private QuizPanel quizPanel;

	// Array to store panel and integer to update index
	private int panelIndex;
	private ArrayList<JPanel> jpPanels;

	// Navigation components
	private NavLeftButton navleft;
	private NavRightButton navRight;

	// Toolkit for notification sounds
	private Toolkit toolkit;

	// Components for adjusting the cursor
	private Image welcomePanel_Alien_Image;
	private Cursor welcomePanel_Alien_Cursor;
	
	public BaseFrame(IncidentFetcher incidentFetcher) {

		// Call superclass, pass in title
		super("NUFORC UFO Sighting Tracker");
		this.incidentFetcher = incidentFetcher;

		// Create new panels for base frame layout
		northPanel = new JPanel();
		centerPanel = new JPanel();
		southPanel = new JPanel();

		// Labels to be displayed next to spinner widgets and display latest
		// version of the ripley API
		startDateLabel = new JLabel("<html><font size=\"4\">From: </font></html>");
		endDateLabel = new JLabel("<html><font size=\"4\">To: </font></html>");
		lastUpdate = new JLabel(incidentFetcher.getLastUpdated(), SwingConstants.CENTER);

		// Create spinner widgets, loading in sipper number models & range based
		// from information from ripley
		startDateSpinner = new JSpinner(new SpinnerNumberModel((incidentFetcher.getStartYear()),
				incidentFetcher.getStartYear(), (incidentFetcher.getLatestYear() + 1), 1));
		startDateSpinner.setFont(new Font("Arial Bold", Font.PLAIN, 15));
		startDateSpinner.setPreferredSize(new Dimension(65, 30));
		JSpinner.NumberEditor StartDateeditor = new JSpinner.NumberEditor(startDateSpinner, "#");
		startDateSpinner.setEditor(StartDateeditor);

		endDateSpinner = new JSpinner(new SpinnerNumberModel((incidentFetcher.getLatestYear() + 1),
				incidentFetcher.getStartYear(), (incidentFetcher.getLatestYear() + 1), 1));
		endDateSpinner.setPreferredSize(new Dimension(65, 30));
		endDateSpinner.setFont(new Font("Arial Bold", Font.PLAIN, 15));
		tickerListener = new TickerChangeListener(startDateSpinner, endDateSpinner);
		JSpinner.NumberEditor EndDateeditor = new JSpinner.NumberEditor(endDateSpinner, "#");
		endDateSpinner.setEditor(EndDateeditor);

		// Search button for fetching incident data
		jbExecuteSearch = new JButton("<html><b>Go</b></html>");
		jbExecuteSearch.setPreferredSize(new Dimension(60, 30));
		jbExecuteSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
		// Action listener for button click when fetching incident data
		dateFetcher = new DateUpdateAL(startDateSpinner, endDateSpinner, incidentFetcher, jbExecuteSearch);

		// Panel navigation buttons
		scrollBack = new JButton("<");
		scrollBack.setName("NavLeft");
		scrollBack.setPreferredSize(new Dimension(60, 30));
		scrollBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
		scrollForward = new JButton(">");
		scrollForward.setName("NavRight");
		scrollForward.setPreferredSize(new Dimension(60, 30));
		scrollForward.setCursor(new Cursor(Cursor.HAND_CURSOR));

		// Panels to hold content
		// TODO replace placeholder panels
		welcomePanel = new WelcomePanel(incidentFetcher);
		statsPanel = new StatsPanel(incidentFetcher, tickerListener);
		mapPanel = new MapPanel(incidentFetcher);
		quizPanel = new QuizPanel(incidentFetcher);

		panelIndex = 0;

		// Populate arraylist of panels
		jpPanels = new ArrayList<JPanel>();
		jpPanels.add(welcomePanel);
		jpPanels.add(mapPanel);
		jpPanels.add(statsPanel);
		jpPanels.add(quizPanel);

		// Create navigation buttons
		navleft = new NavLeftButton();
		navRight = new NavRightButton();

		toolkit = Toolkit.getDefaultToolkit();
		// Set courser to image icon of an alien
		welcomePanel_Alien_Image = toolkit.getImage("src/Images/alien_cursor_32x32.png");
		welcomePanel_Alien_Cursor = toolkit.createCustomCursor(welcomePanel_Alien_Image, new Point(0, 0), "img");

		// Call method to initialise wigits
		initWidgets();

		// Executes for all the panels where we want it to be executed for only
		// the welcome panel
		welcomePanel.getRootPane().setDefaultButton(jbExecuteSearch);
		

	}

	public void initWidgets() {
		// set application to terminate upon baseframe close
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Set Layout of the BaseFrame to BorderLayout and assign positions to
		// panels
		this.setLayout(new BorderLayout());
		this.add(northPanel, BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(southPanel, BorderLayout.SOUTH);
		centerPanel.setLayout(new BorderLayout());
		// Set FlowLayout and Populate north oanel
		northPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		// Assign ActionListeners to JSpinners
		startDateSpinner.addChangeListener(tickerListener);
		endDateSpinner.addChangeListener(tickerListener);
		northPanel.add(startDateLabel);
		northPanel.add(startDateSpinner);
		northPanel.add(endDateLabel);
		northPanel.add(endDateSpinner);
		jbExecuteSearch.setBackground(Color.green);
		northPanel.add(jbExecuteSearch);

		// Set layout of south panel and add navigation buttons
		southPanel.setLayout(new BorderLayout());
		southPanel.add(scrollBack, BorderLayout.WEST);
		southPanel.add(scrollForward, BorderLayout.EAST);
		southPanel.add(lastUpdate, BorderLayout.CENTER);

		// this fixes map, but breaks stats view
		centerPanel.add(jpPanels.get(panelIndex), BorderLayout.CENTER);

		// Add action and key listener to search button
		jbExecuteSearch.addActionListener(dateFetcher);
		jbExecuteSearch.addKeyListener(dateFetcher);

		incidentFetcher.addObserver(this);
		dateFetcher.addObserver(statsPanel);

		// Add action listener to navigation buttons
		scrollBack.addActionListener(navleft);
		scrollForward.addActionListener(navRight);

		navleft.addObserver(this);
		navRight.addObserver(this);

		// Pack components, and set size of the BaseFrame
		this.pack();
		this.setSize(1100, 720);
	}

	/**
	 * Updates the baseframe upon notification from observable objects
	 */
	@Override
	public void update(Observable o, Object arg) {
		//Check if the obversable object is an incident fetcher model
		if (o instanceof IncidentFetcher) {
			//Checks the argument of the object to determine the boolean value
			if ((Boolean) arg == true) {
				//Change mouse cursor icon
				this.setCursor(welcomePanel_Alien_Cursor);
				//Display loading image
				welcomePanel.DisplayLoadingIcon();
			} else {
				//Restore default mouse icon
				this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				//Populate text field within the welcome panel with appropriate information
				welcomePanel.ripleyHasFetched(incidentFetcher.getTime(), startDateSpinner, endDateSpinner);
				//Notify the user via sound
				try {
					SoundUtils.tone(900, 75, 1.0);
					Thread.sleep(50);
					SoundUtils.tone(900, 75, 1.0);
				} catch (LineUnavailableException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			//Else if the obvervable object is a Navigation Action Listener, change center panels contents
			//Enable looping through an array of JPanels by using a modular operator
		} else if (o instanceof NavRightButton) {
			panelIndex++;
			panelIndex = (panelIndex + 4) % 4;
			centerPanel.removeAll();
			centerPanel.add(jpPanels.get(panelIndex), BorderLayout.CENTER);
			centerPanel.revalidate();
			centerPanel.repaint();
		} else if (o instanceof NavLeftButton) {
			panelIndex--;
			panelIndex = (panelIndex + 4) % 4;
			centerPanel.removeAll();
			centerPanel.add(jpPanels.get(panelIndex), BorderLayout.CENTER);
			centerPanel.revalidate();
			centerPanel.repaint();
		}
	}
}