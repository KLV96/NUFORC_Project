package Model;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import api.ripley.Incident;

/**
 * returns the most likely shape of the next incident
 * @author Stefan
 *
 */
public class StatsLikliestShape implements Statistics {

	private IncidentFetcher incidentFetcher;
	private String statistic;
	private static final String statType = "Likliest Shape";

	public StatsLikliestShape(IncidentFetcher incidentFetcher)
	{
		this.incidentFetcher = incidentFetcher;
	}
	
	/**
	 * returns a string showing the most common shape of object in incident
	 * most common incident in the date range is considered the most likely 
	 * next shape 
	 */
	@Override
	public String processStat() 
	{
		// gets all the incidents from the incidentFetcher and puts into a treemap
		// which tracks the count of each shape found
		ArrayList<Incident> incidents = incidentFetcher.getIncidents();
		TreeMap<String, Integer> count = new TreeMap<String, Integer>();
		
		
		// this populates the treemap
		for (Incident incident : incidents)
		{
			String shape = incident.getShape();
			
			if (count.containsKey(shape))
			{
				count.put(shape, new Integer( (Integer)count.get(shape).intValue() + 1 ));
			}
			else
			{
				count.put(shape, 1);
			}
		}
		
		
		// finds the most common shape by looping through the treemap
		String mostCommonShape = "";
		int numberOfOccurrences = 0;
		
		for (Map.Entry<String, Integer> entry : count.entrySet())
		{
			if (entry.getValue().intValue() > numberOfOccurrences)
			{
				mostCommonShape = entry.getKey();
				numberOfOccurrences = entry.getValue().intValue();
			}
		}
		
		return mostCommonShape;
	}

	/**
	 * returns the statistic
	 */
	@Override
	public String getStatistic() 
	{
		statistic = processStat();
		return statistic;
	}

	/**
	 * returns the type of the statistic
	 */
	@Override
	public String getStatType() 
	{
		return statType;
	}
}
