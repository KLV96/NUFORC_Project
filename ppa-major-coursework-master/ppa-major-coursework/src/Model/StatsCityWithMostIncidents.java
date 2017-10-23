package Model;

import java.util.Map.Entry;
import java.util.TreeMap;

import api.ripley.Incident;

/**
 * Returns the city most likely to have the next incident
 * @author Raihan
 *
 */
public class StatsCityWithMostIncidents implements Statistics {

	// type of statistic
	private static final String statType = "City With most Incidents";
	
	// The actual statistic
	private String statistic;

	private IncidentFetcher incidentFetcher;
	
	/**
	 * initialises the incidentFetcher object
	 * which is used to process the statistic
	 * @param incidentFetcher
	 */
	public StatsCityWithMostIncidents(IncidentFetcher incidentFetcher) 
	{
		this.incidentFetcher = incidentFetcher;
		statistic = "";
	}
	
	/**
	 * actual statistic processing
	 */
	@Override
	public String processStat() {
		
		// A treemap with each city and the number of incidents
		TreeMap<String, Integer> City = new TreeMap<String, Integer>();
		
		// populates the city treemap
		for (Incident incident: incidentFetcher.getIncidents()) {
			if (City.containsKey(incident.getCity())){
				City.put(incident.getCity(), new Integer(City.get(incident.getCity()).intValue() + 1));
			}
			else {
				City.put(incident.getCity(), new Integer(1));
			}
		}
		
		
		// Iteratively compare the values of the treemap and get the key of the biggest value
		String biggestCityName = "";
		int highestNum = 0;
		for (Entry<String, Integer> entry : City.entrySet())
		{
			if (highestNum < entry.getValue()) {
				highestNum = entry.getValue();
				biggestCityName = entry.getKey();
			}
			
		}
		
		statistic = biggestCityName;
		return biggestCityName;
	}

	
	/**
	 * returns the statistic
	 */
	@Override
	public String getStatistic() {
		statistic = processStat();
		return statistic;
	}

	/**
	 * returns the type of statistic
	 */
	@Override
	public String getStatType() 
	{

		return statType;
	}

}