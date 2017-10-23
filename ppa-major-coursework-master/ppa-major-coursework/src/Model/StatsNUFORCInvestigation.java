package Model;

import java.util.ArrayList;

import api.ripley.Incident;

/**
 * statstics under or has been under NUFORC investigation
 * @author Daniel
 *
 */
public class StatsNUFORCInvestigation implements Statistics {

	private IncidentFetcher incidentFetcher;

	//String field to hold the information to be displayed
	private String statistic;
	//String field to hold the name of the statistics model
	private static final String statType = "Under NUFORC investigation";

	public StatsNUFORCInvestigation(IncidentFetcher incidentFetcher) {
		super();
		this.incidentFetcher = incidentFetcher;
		statistic = "0";

	}

	/**
	 * Store the number of incidents into an array, then count and return the
	 * total number of incidents that are investigated by the NUFORC Cast and
	 * return total as a string
	 */
	@Override
	public String processStat() {
		ArrayList<Incident> temp = incidentFetcher.getIncidents();
		int freq = 0;
		for (int i = 0; i < temp.size(); i++) {
			if (temp.get(i).getSummary().contains("NUFORC")) {
				freq++;
			}
		}
		statistic = Integer.toString(freq);
		return statistic;
	}

	/**
	 * Get statistics
	 */
	@Override
	public String getStatistic() {
		processStat();
		return statistic;
	}

	/**
	 * Get stat type (name of statistic)
	 */
	@Override
	public String getStatType() {
		return statType;
	}
}
