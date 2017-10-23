package Model;

public interface Statistics{
	
	/**
	 * processes the statistic
	 * @return statistic
	 */
	public abstract String processStat();
	
	/**
	 * returns the statistic
	 * @return
	 */
	public abstract String getStatistic();
	
	/**
	 * returns the type of statistic
	 * @return
	 */
	public abstract String getStatType();
}
