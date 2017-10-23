package Model;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.TreeMap;

import api.ripley.Incident;

/**
 * returns the number of incidents taking place in non-US locations
 * @author Raihan & Rewaz
 *
 */
public class StatsNonUS implements Statistics {
	
	// type of statistic
	private static final String statType = "non-US incidents";

	private IncidentFetcher incidentFetcher;
	
	// the statistic
	String statistic;

	// US only states
	private String[] UsStates = { "AK", "AL", "AR", "AZ", "CA", "CO", "CT", "DC", "DE", "FL", "GA", "HI", "IA", "ID",
			"IL", "IN", "KS", "KY", "LA", "MA", "MD", "ME", "MI", "MN", "MO", "MS", "MT", "NC", "ND", "NE", "NH", "NJ",
			"NM", "NV", "NY", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VA", "VT", "WA", "WI", "WV",
			"WY" };
	
	
	
	/**
	 * uses incidentFetcher to process statistic
	 * passed incidentFetcher should be the one used in main method/baseFrame
	 * @param incidentFetcher
	 */
	public StatsNonUS(IncidentFetcher incidentFetcher) 
	{
		this.incidentFetcher = incidentFetcher;
	}

	/**
	 * processes the data from IncidentFetcher to number of incidents in non-US states
	 */
	@Override
	public String processStat() 
	{
		
		// uses tempRef and incidentTree in order to avoid referencing the incidentsByState
		TreeMap<String, ArrayList<Incident>> tempRef = incidentFetcher.getIncidentsByState();
		
		TreeMap<String, ArrayList<Incident>> incidentTree = new TreeMap<String, ArrayList<Incident>>(tempRef);
		
		// removes any state that is not a US state from incidentFetcher
		for(String stateName : UsStates)
		{
			incidentTree.remove(stateName);
		}
		
		// number of total incidents
		int numberOfIncidents = 0;
		
		// sets the numberOfIncidents value
		for (Entry<String, ArrayList<Incident>> entry : incidentTree.entrySet())
		{
			numberOfIncidents += entry.getValue().size();
		}
	
		// returns the number of incidents as a string
		statistic = Integer.toString(numberOfIncidents);
		return statistic;
	}

	/**
	 * return the type of statistic
	 */
	@Override
	public String getStatType() {
		return statType;
	}
	
	/**
	 * returns the actual statistic
	 */
	@Override
	public String getStatistic() 
	{
		statistic = processStat();
		return statistic;
	}
}
