package Model;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.TreeMap;

import api.ripley.Incident;
import api.ripley.Ripley;

/** 
 * @author Sharif, Rewaz, Daniel, Stefan
 * a wrapper class for the ripley API which fetches the required data
 * some data is stored in Fields in order to cache and not call API repeatedly
 */
public class IncidentFetcher extends Observable{

	private ArrayList<Incident> incidents;
	private TreeMap<String, ArrayList<Incident>> incidentsByState;
	private Ripley ripley;
	private String startString;
	private String endString;
	private long time;
	
	// called at the start of program and stored locally
	// instead of calling every time a component wants to get this data
	String lastUpdated;
	
	/**
	 * contructor calls ripley, gets acknoeldgement string
	 * last updated
	 */
	public IncidentFetcher()
	{
		startString =  "";
		endString = "";
		incidentsByState = new TreeMap<String, ArrayList<Incident>>();
		ripley = new Ripley("90tLI3CTstuyVD6ql2OMtA==" , "lBgm4pRt9gvVqL46EnH7ew==");
		lastUpdated = ripley.getLastUpdated();
		System.out.println(ripley.getAcknowledgementString());
	
	}
	
	/**
	 * gets the data in the date range provided in the spinners in the view
	 * @throws FileNotFoundException
	 */
	public void getDates() throws FileNotFoundException 
	{		
		long start = System.currentTimeMillis();		
		incidents = ripley.getIncidentsInRange(startString, endString);
		
		incidentsByState = new TreeMap<String, ArrayList<Incident>>();
		
		//FileOutputStream fileOutputStream;
							
		
		for (Incident incident : incidents)
		{
			if (incidentsByState.containsKey((incident.getState())))
			{
				incidentsByState.get(incident.getState()).add(incident);
			}
			else
			{
				incidentsByState.put(incident.getState(), new ArrayList<Incident>());
				incidentsByState.get(incident.getState()).add(incident);
			}
		}
		
		long end = System.currentTimeMillis();	
		time = end - start;

		
		setChanged();
		notifyObservers(false);
	}
	
	/**
	 * returns an arrayList of all the incidents in the date range
	 * for a given state
	 * @param state
	 * @return arrayList of incidents
	 */
	public ArrayList<Incident> getIndividualStateIncidents(String state){

		if (incidentsByState.get(state) != null){
			return incidentsByState.get(state);
		} 
		return null;
	}
	
	/**
	 * returns all incidents in the date range as an arrayList
	 * @return ArrayList of incidents
	 */
	public ArrayList<Incident> getIncidents() {
		return incidents;
	}
	
	/**
	 * returns all the states in the current data  as a string
	 * @return
	 */
	public String passAllStates ()
	{
		return incidentsByState.keySet().toString();
	}

	/**
	 * returns a treemap with the incidents associated with their state
	 * @return TreeMap incidentsByState
	 */
	public TreeMap<String, ArrayList<Incident>> getIncidentsByState() {
		return incidentsByState;
	}

	/**
	 * returns the start date of the incident fetch
	 * @param startString
	 */
	public void setStartString(String startString) {
		this.startString = startString;
	}

	/**
	 * returns the end date of the incident fetch
	 * @param endString
	 */
	public void setEndString(String endString) {
		this.endString = endString;
	}
	
	/**
	 * returns the time field
	 * used to determine how long the data fetching took
	 * @return
	 */
	public long getTime()
	{
		return time;
	}
	
	/**
	 * sets if the data is currently being fetched
	 * @param Fetching
	 */
	public void setIsFetching(Boolean Fetching)
	{
		setChanged();
		notifyObservers(Fetching);
	}


	/**
	 * gets the last time an incident was posted on ripley 
	 * @return String date
	 */
	public String getLastUpdated() {
		return lastUpdated;
	}
	
	/**
	 * returns the first year that an incident took place 
	 * @return integer year
	 */
	public int getStartYear() { 
		return ripley.getStartYear();
	}
	
	/**
	 * returns the latest year where an incident occured 
	 * @return integer year
	 */
	public int getLatestYear() {
		return ripley.getLatestYear();
	}

	/**
	 * returns the total number of incidents
	 * @return int total num of incidents
	 */
	public int getTotalIncidentsSize(){
		return incidents.size();
	}
	
	/**
	 * returns the current version of ripley
	 * @return double type: ripley version
	 */
	public double getVersion() {
		return ripley.getVersion();
		
	}
	
	/**
	 * returns the ripley acknowledgement message
	 * @return String acknowledgement message
	 */
	public String getAcknowledgementString() {
		return ripley.getAcknowledgementString();
	}
}

