package Model;

import java.util.ArrayList;
import java.util.Map;

import api.ripley.Incident;

/**
 * returns the number of hoaxes in the current date range
 * @author Rewaz & Raihan
 *
 */
public class StatsHoaxes implements Statistics {
	
	private IncidentFetcher incidentFetcher;
	private String statistic;
	private static final String statType = "Hoaxes";
	
	/**
	 * constructor requires the IncidentFetcher object used in the overall project
	 * not a new instance
	 * @param incidentFetcher
	 */
	public StatsHoaxes(IncidentFetcher incidentFetcher) 
	{
		super();
		this.incidentFetcher = incidentFetcher;
		statistic = "0";
	}
	
	/**
	 * processes hoaxes in the given date range
	 * returns a string representation of the integer value
	 * looks for specific strings in the incident summary
	 */
	@Override
	public String processStat() {
		
		int hoaxCount = 0;
		
		// for all incidents in the incidentByState treemap
		// if an incident contains the strings below, increment hoax counter
		for (Map.Entry<String, ArrayList<Incident>> entry : incidentFetcher.getIncidentsByState().entrySet()) {
			String x = "";
			int incidentsInState = entry.getValue().size();

			for (int i = 0; i < incidentsInState; i++) {
				x = entry.getValue().get(i).getSummary();
				
				if (x.contains("((HOAX??))") || x.contains("((HOAX))")) {
					hoaxCount++;
				}
			}
		}
		
		// set statistic and return
		statistic  = Integer.toString(hoaxCount);
		return statistic;
	}

	/**
	 * returns the type of statistic
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
		processStat();
		return statistic;
	}
}
