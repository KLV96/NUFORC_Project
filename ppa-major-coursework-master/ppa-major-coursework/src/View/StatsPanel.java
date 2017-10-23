package View;

import java.awt.GridLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.TreeMap;

import javax.swing.JPanel;

import Controller.StatsNavLeft;
import Controller.StatsNavRight;
import Controller.TickerChangeListener;
import Model.FileReadNWrite;
import Model.IncidentFetcher;
import Model.StatLikeliestState;
import Model.Statistics;
import Model.StatsCityWithMostIncidents;
import Model.StatsHoaxes;
import Model.StatsLikeliestDay;
import Model.StatsLikliestShape;
import Model.StatsNUFORCInvestigation;
import Model.StatsNonUS;
import Model.StatsVideoCount;

/**
 * This class extends JPanel and implements Observer. This class is like a stats master where all the stats are displayed on the gui depending on the buttons that the user presses.
 * @author Rewaz 
 *
 */
public class StatsPanel extends JPanel implements Observer{
	
	 
	 private static final long serialVersionUID = 1L;
	// making objects of statsPanel for each panel ( there are 4 panels )
	 private StatsBoxView statsPanel1;
	 private StatsBoxView statsPanel2;
	 private StatsBoxView statsPanel3;
	 private StatsBoxView statsPanel4;
	 
	 private FileReadNWrite fileReadNWrite;
	
	 /*
	  *  making objects of StatsNavLeft & StatsNavRight which both implements Action Listener. 
	  *  Each object is for a different button. Each panel has 2 buttons ( Right and Left ).
	  */
	 private StatsNavLeft panel1NavLeft;
	 private StatsNavRight panel1NavRight;
	 
	 private StatsNavLeft panel2NavLeft;
	 private StatsNavRight panel2NavRight;
	 
	 private StatsNavLeft panel3NavLeft;
	 private StatsNavRight panel3NavRight;
	 
	 private StatsNavLeft panel4NavLeft;
	 private StatsNavRight panel4NavRight;
	 
	 // making objects of all the statistics
	 private StatsNonUS statsNonUS;
	 private StatsHoaxes statsHoaxes;
	 private StatLikeliestState statLikeliestState;
	 private StatsLikliestShape statsLikliestShape;
	 private StatsNUFORCInvestigation statsNUFORCInvestigation;
	 private StatsCityWithMostIncidents statsCityWithMostIncidents;
	 private StatsLikeliestDay statsLikeliestDay;
	 private StatsVideoCount statsVideoCount;
	 
	 // this has title of all the stats  
	 private ArrayList<String> allStatsList;
	 
	 // This has titles of all the shown stats on the stats panel
	 private ArrayList<String> shownStatsList;
	 
	 // This has titles of all the shown stats on the stats panel 
	 private ArrayList<String> nonShownStatsList;
	 
	 // The key which is of type string is the same as the title of statistics which are used to get the statistics objects
	 private Map<String, Statistics> allStatsMap;

	 private File confFile = new File("config.properties");
	
	 // This is used to ensure that the user selects dates
	 private TickerChangeListener tickerChangeListener;
	 
	 
	 /**
	  * This is the constructor where all the fields are initialised
	  * @param incidentFetcher
	  */
	
	 public StatsPanel(IncidentFetcher incidentFetcher, TickerChangeListener tickerChangeListener) 
	 {
		
		this.tickerChangeListener = tickerChangeListener;	
		
		// initialising the compulsory statistics 
		statsHoaxes = new StatsHoaxes(incidentFetcher);
		statsNonUS = new StatsNonUS(incidentFetcher);
		statLikeliestState = new StatLikeliestState(incidentFetcher);
		statsVideoCount = new StatsVideoCount();
		
		// initialising the optional statistics
		statsNUFORCInvestigation = new StatsNUFORCInvestigation(incidentFetcher);
		statsLikliestShape = new StatsLikliestShape(incidentFetcher);
		statsCityWithMostIncidents = new StatsCityWithMostIncidents(incidentFetcher);
		statsLikeliestDay = new StatsLikeliestDay(incidentFetcher);
		
		allStatsList = new ArrayList<String>();
		shownStatsList = new ArrayList<String>(3);
		nonShownStatsList = new ArrayList<String>(3);
		
		// This is object is used to set the isUsed boolean to false and check if it is false or true.
		allStatsMap = new TreeMap<String, Statistics>();
		fileReadNWrite = new FileReadNWrite(confFile);
		
		
		initialiseLeftButtons();
		initialiseRightButtons();
		
		allStatsList.add("Hoaxes");
		allStatsList.add("non-US incidents");
		allStatsList.add("Likliest State");
		allStatsList.add("US UFO sighting videos last month");
		
		allStatsList.add("Under NUFORC investigation");
		allStatsList.add("Likliest Shape");
		allStatsList.add("City With most Incidents");
		allStatsList.add("Likilest Day");
		
		shownStatsList.add(fileReadNWrite.configReader("panel1"));
		shownStatsList.add(fileReadNWrite.configReader("panel2"));
		shownStatsList.add(fileReadNWrite.configReader("panel3"));
		shownStatsList.add(fileReadNWrite.configReader("panel4"));
		
		
		allStatsMap.put("non-US incidents", statsNonUS);
		allStatsMap.put("Hoaxes", statsHoaxes);
		allStatsMap.put("Under NUFORC investigation", statsNUFORCInvestigation);
		allStatsMap.put("Likliest Shape", statsLikliestShape);
		
		allStatsMap.put("City With most Incidents", statsCityWithMostIncidents);
		allStatsMap.put("Likliest State", statLikeliestState);
		allStatsMap.put("US UFO sighting videos last month", statsVideoCount);
		allStatsMap.put("Likilest Day", statsLikeliestDay);
		
		
		addnotShown();
		
		statsPanel1 = new StatsBoxView(panel1NavLeft,panel1NavRight);
		
		/*
		 *  These lines below are setting the Action command name which is used in the ActionEvent in StatsNavLefet and StatsNavRight.
		 *  This is done to use the same action Listener for different buttons.
		 *  RB stands for Right Button, LB stands for Left Button
		 */
		
		statsPanel1.getRightButton().setActionCommand(Actions.statsPanel1RB.name());
		statsPanel1.getLeftButton().setActionCommand(Actions.statsPanel1LB.name());

		panel1NavLeft.addObserver(this);
		panel1NavRight.addObserver(this);
		
		statsPanel2 = new StatsBoxView(panel2NavLeft,panel2NavRight);
		statsPanel2.getRightButton().setActionCommand(Actions.statsPanel2RB.name());
		statsPanel2.getLeftButton().setActionCommand(Actions.statsPanel2LB.name());
		
		panel2NavLeft.addObserver(this);
		panel2NavRight.addObserver(this);
		
		statsPanel3 = new StatsBoxView(panel3NavLeft,panel3NavRight);
		statsPanel3.getRightButton().setActionCommand(Actions.statsPanel3RB.name());
		statsPanel3.getLeftButton().setActionCommand(Actions.statsPanel3LB.name());
		
		panel3NavLeft.addObserver(this);
		panel3NavRight.addObserver(this);
		
		statsPanel4 = new StatsBoxView(panel4NavLeft,panel4NavRight);
		statsPanel4.getRightButton().setActionCommand(Actions.statsPanel4RB.name());
		statsPanel4.getLeftButton().setActionCommand(Actions.statsPanel4LB.name());
		
		panel4NavLeft.addObserver(this);
		panel4NavRight.addObserver(this);
		
		
		initWidgets();
		
	}
	
	/**
	 * This method sets the layout of the panel and adds the objects of the StatsBoxView to the panel.
	 */
	private void initWidgets() 
	{
		
		setLayout(new GridLayout(2, 2));
		
		this.add(statsPanel1);
		this.add(statsPanel2);
		this.add(statsPanel3);
		this.add(statsPanel4);

	}
	
	/**
	 * This method is called whenever one of the buttons is pressed. It checks which first checks which of the button is pressed ( right or left )
	 * then checks which one of the button is pressed ( there are 6 buttons ) then checks which one of the statistics is not used and based on that it 
	 * updates the file then settings the isUsed field to false ( as the button is pressed so that statistics is no longer used in the panel ) 
	 * Then it write to the file the n
	 */
	@Override
	public void update(Observable o, Object arg) 
	{
		
		// The buttons will not work until the user selects dates
		if (tickerChangeListener.getDateSelected())
		{
		
		
		// note that only the first if statement for the left navigation is commented as all other are similar to the other ones.
		
		if (o instanceof StatsNavLeft)
		{
			// if the argument has statsPanel1LB as name ( This button is the left button in the first panel )
			if ((String)arg == "statsPanel1LB")
			{
						String temp;
						// storing the title of the statistics that is displayed in the first panel into a temporary variable
						temp = fileReadNWrite.configReader("panel1");
						// removes the temp ( the statistics that is displayed in the first panel ) from the Shown Statistics List
						shownStatsList.remove(temp);
						// writes to the file into the panel1 the string title of the first statistics in the non Shown Stats List
						fileReadNWrite.configWriter("panel1", nonShownStatsList.get(0));
						// adds the string title of the first statistics in the non Shown Stats List 
						shownStatsList.add(nonShownStatsList.get(0));
						// removes the first statistic title from non Shown Stats List
						nonShownStatsList.remove(0);
						// adds temp ( the statistics that is displayed in the first panel ) to the non Shown StatsList as the last element of the list
						nonShownStatsList.add(3,temp);
						
			}
					
				
	
			else if ((String)arg == "statsPanel2LB") 
			{
				
				String temp;
				temp = fileReadNWrite.configReader("panel2");
				shownStatsList.remove(temp);
				fileReadNWrite.configWriter("panel2", nonShownStatsList.get(0));
				shownStatsList.add(nonShownStatsList.get(0));
				nonShownStatsList.remove(0);
				nonShownStatsList.add(3,temp);

			}
			
			else if ((String)arg == "statsPanel3LB")
			{
				
				String temp;
				temp = fileReadNWrite.configReader("panel3");
				shownStatsList.remove(temp);
				fileReadNWrite.configWriter("panel3", nonShownStatsList.get(0));
				shownStatsList.add(nonShownStatsList.get(0));
				nonShownStatsList.remove(0);
				nonShownStatsList.add(3,temp);

			}
		
			
			else if ((String)arg == "statsPanel4LB")
			{
				
				
				String temp;
				temp = fileReadNWrite.configReader("panel4");
				shownStatsList.remove(temp);
				fileReadNWrite.configWriter("panel4", nonShownStatsList.get(0));
				shownStatsList.add(nonShownStatsList.get(0));
				nonShownStatsList.remove(0);
				nonShownStatsList.add(3,temp);

			
			}
		
		}
		
		// note that only the first else if statement for the right navigatin is commented as all other are similar to the other ones.
		else if (o instanceof StatsNavRight)
		{
	
			
			if ((String)arg == "statsPanel1RB")
			{
				// storing the title of the statistics that is displayed in the first panel into a temporary variable
				String temp;
				// removes the temp ( the statistics that is displayed in the first panel ) from the Shown Statistics List
				temp = fileReadNWrite.configReader("panel1");
				// removes the temp ( the statistics that is displayed in the first panel ) from the Shown Statistics List
				shownStatsList.remove(temp);
				// writes to the file into the panel1 the string title of the last statistics in the non Shown Stats List
				fileReadNWrite.configWriter("panel1", nonShownStatsList.get(3));
				// adds the string title of the last statistics in the non Shown Stats List 
				shownStatsList.add(nonShownStatsList.get(3));
				// removes the last statistic title from non Shown Stats List
				nonShownStatsList.remove(3);
				// adds temp ( the statistics that is displayed in the first panel ) to the non Shown StatsList as the first element of the list
				nonShownStatsList.add(0,temp);
				
				
			}
			
			else if ((String)arg == "statsPanel2RB")
			{
				
				String temp;
				temp = fileReadNWrite.configReader("panel2");
				shownStatsList.remove(temp);
				fileReadNWrite.configWriter("panel2", nonShownStatsList.get(3));
				shownStatsList.add(nonShownStatsList.get(3));
				nonShownStatsList.remove(3);
				nonShownStatsList.add(0,temp);

			}
			
			else if ((String)arg == "statsPanel3RB")
			{
				
				String temp;
				temp = fileReadNWrite.configReader("panel3");
				shownStatsList.remove(temp);
				fileReadNWrite.configWriter("panel3", nonShownStatsList.get(3));
				shownStatsList.add(nonShownStatsList.get(3));
				nonShownStatsList.remove(3);
				nonShownStatsList.add(0,temp);

			}
			
			else if ((String)arg == "statsPanel4RB")
			{
				
				String temp;
				temp = fileReadNWrite.configReader("panel4");
				shownStatsList.remove(temp);
				fileReadNWrite.configWriter("panel4", nonShownStatsList.get(3));
				shownStatsList.add(nonShownStatsList.get(3));
				nonShownStatsList.remove(3);
				nonShownStatsList.add(0,temp);
				
			}
		

	}	
		
			// Theses lines updates the statistics panels based on what is writen in the file 
			updatePanel1(fileReadNWrite.configReader("panel1"));
			updatePanel2(fileReadNWrite.configReader("panel2"));
			updatePanel3(fileReadNWrite.configReader("panel3"));
			updatePanel4(fileReadNWrite.configReader("panel4"));
		}
	}
	
	
	/**
	 * This updates panel 1 based on the value in the confFile panel 1.
	 * Then it uses that string to get the key of that string value ( the Key is of type statistics ).
	 * It passes the statistics ( the key ) to the updateStats method in statsPanel1 object
	 * @param statsTitle
	 */
	public void updatePanel1(String statsTitle)
	{
		statsPanel1.updateStats(allStatsMap.get(statsTitle));
	}
	
	/**
	 * This updates panel 2 based on the value in the confFile panel 2.
	 * Then it uses that string to get the key of that string value ( the Key is of type statistics ).
	 * It passes the statistics ( the key ) to the updateStats method in statsPanel2 object
	 * @param statsTitle
	 */
	public void updatePanel2(String statsTitle)
	{
		statsPanel2.updateStats(allStatsMap.get(statsTitle));
	}
	
	/**
	 * This updates panel 3 based on the value in the confFile panel 3.
	 * Then it uses that string to get the key of that string value ( the Key is of type statistics ).
	 * It passes the statistics ( the key ) to the updateStats method in statsPanel3 object
	 * @param statsTitle
	 */
	public void updatePanel3(String statsTitle)
	{
		statsPanel3.updateStats(allStatsMap.get(statsTitle));
	}
	
	/**
	 * This updates panel 4 based on the value in the confFile panel 4.
	 * Then it uses that string to get the key of that string value ( the Key is of type statistics ).
	 * It passes the statistics ( the key ) to the updateStats method in statsPanel4 object
	 * @param statsTitle
	 */
	public void updatePanel4(String statsTitle)
	{
		statsPanel4.updateStats(allStatsMap.get(statsTitle));
	}

	
	
	/**
	 * Initialising the Left buttons
	 */
	public void initialiseLeftButtons()
	{
		panel1NavLeft = new StatsNavLeft();
		panel2NavLeft = new StatsNavLeft();
		panel3NavLeft = new StatsNavLeft();
		panel4NavLeft = new StatsNavLeft();
	}
	
	/**
	 * Initialising the Right buttons
	 */
	public void initialiseRightButtons()
	{
		panel1NavRight = new StatsNavRight();
		panel2NavRight = new StatsNavRight();
		panel3NavRight = new StatsNavRight();
		panel4NavRight = new StatsNavRight();
		
	}
	
	/**
	 * This stores Enumerations variables ( for the right buttons and the left buttons ) which are constants that are used to define separate buttons 
	 * @author rewaz
	 *
	 */
	private enum Actions 
	{
		statsPanel1RB,
		statsPanel1LB,
		
		statsPanel2RB,
		statsPanel2LB,
		
		statsPanel3RB,
		statsPanel3LB,
		
		statsPanel4RB,
		statsPanel4LB,
	 }
	
	/**
	 * This method adds elements to the nonShownStatsList based on the allStats
	 * So non-Shown Stats List has all the elements that are in the all Stats List but not in the shown Stats List
	 */
	public void addnotShown()
	{
		for(int i=0; i < allStatsList.size()  ; i++)
		{
			
			if (!(shownStatsList.contains(allStatsList.get(i))))
			{
				
				nonShownStatsList.add(allStatsList.get(i));
				
				
			}
			
		}
	}
	
}
