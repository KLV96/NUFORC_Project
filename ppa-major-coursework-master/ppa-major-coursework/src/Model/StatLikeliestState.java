package Model;

/**
 * Gets the state likliest to have the next incident 
 * @author Sharif
 *
 */
public class StatLikeliestState implements Statistics
{
	// the type of statistic
	private static final String statType = "Likliest State";
	
	// the actual statistic
	private String statistic;
	
	private IncidentFetcher incidentFetcher;

	/**
	 * uses incident Fetcher to process statistic
	 * @param incidentFetcher
	 */
	public StatLikeliestState(IncidentFetcher incidentFetcher) {
		this.incidentFetcher = incidentFetcher;
	}
	
	/**
	 * processes the statistic
	 * returns the state with the most incidents
	 */
	@Override
	public String processStat() 
	{
		String likliestState = "";
		int incidentCount = 0;
		
		// Iteratively compare the incident count for each state 
		// save the state with the biggest number
		for (String state : incidentFetcher.getIncidentsByState().keySet()) {
			if (incidentFetcher.getIncidentsByState().get(state).size() > incidentCount) {
				incidentCount = incidentFetcher.getIncidentsByState().get(state).size();
				likliestState = state;
			}
		}
		
		// save statistic, return
		statistic = likliestState;
		return likliestState;
	}

	/**
	 * returns the statistic
	 */
	@Override
	public String getStatistic() {
		processStat();
		return statistic;
	}

	/**
	 * returns the statistic type
	 */
	@Override
	public String getStatType() {
		return statType;
	}
}
