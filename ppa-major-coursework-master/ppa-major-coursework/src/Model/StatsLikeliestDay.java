package Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

import api.ripley.Incident;

/**
 * returns the day of week most likely to have the next incident
 * @author Rewaz
 *
 */
public class StatsLikeliestDay implements Statistics {

	// displayed at the top of the stats box to which this is attached
	private static final String statType = "Likliest Day";
	// the actual statstic
	private String statistic;
	
	private IncidentFetcher incidentFetcher;

	// this has 7 days, and stores the number of incidents
	// found for each state, get the index with the largest value
	// get the index of the largest value to get likliest day
	private ArrayList<Integer> numIncidentsPerDay;

	// formatting ripley dateTime string to a SimpleDateFormat object
	SimpleDateFormat dateFormatter;
	Calendar ripleyToCal;

	/**
	 * Consutrctor initializes data structures and fields 
	 * @param incidentFetcher
	 */
	public StatsLikeliestDay(IncidentFetcher incidentFetcher) {
		this.incidentFetcher = incidentFetcher;
		dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
		ripleyToCal = Calendar.getInstance();

		// a new arrayList with 7 elements for the 7 days of the week
		// index 0 = sunday, 1 = monday etc
		// value = num of incidents
		numIncidentsPerDay = new ArrayList<Integer>();
		numIncidentsPerDay.add(0);
		numIncidentsPerDay.add(0);
		numIncidentsPerDay.add(0);
		numIncidentsPerDay.add(0);
		numIncidentsPerDay.add(0);
		numIncidentsPerDay.add(0);
		numIncidentsPerDay.add(0);

	}

	/**
	 * Processes the incidents in the given dateRange 
	 * returns a string with the full name of day of week
	 *  most likely to have the next incident. 
	 * This is just the day with the most incidents in the
	 * current date range.
	 */
	@Override
	public String processStat() {
		// ripley dateTime format example: 2017-02-15 00:00:00
		// convert this to a day using Calender.
		
		// temporarily store all the incidents
		ArrayList<Incident> tempIncidents = incidentFetcher.getIncidents();
		
		// used to process the ripley date input
		String tempDateString = "";
		
		// the final result;
		String dayString = null;

		// day of the week as an integer
		int dayInt = 0;

		// loop through each incident and parse the date of incident
		for (Incident incident : tempIncidents) {
			tempDateString = incident.getDateAndTime();
			// formatter needs a try block
			try {
				// parse the string dateTime from ripley into a calendar object
				// this can then be used to get the day of week
				ripleyToCal.setTime(dateFormatter.parse(tempDateString));

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// day of week of the current incident as an integer
			// 1 - 7, 1 = sunday, 2 = monday etc, no 0 index
			dayInt = ripleyToCal.get(Calendar.DAY_OF_WEEK);

			// for every incident in day x, increment the array position of that day
			numIncidentsPerDay.set(dayInt - 1, numIncidentsPerDay.get(dayInt - 1) + 1);

			// gets the day with the most incidents (as an integer, a.k.a the array index) 
			int indexOfDay = numIncidentsPerDay.indexOf(Collections.max(numIncidentsPerDay));
		
			
			// returns a string day by "converting the arrayList index into the appropriate day of week
			switch (indexOfDay) {

			case 0:
				dayString = "Sunday";
				break;
			case 1:
				dayString = "Monday";
				break;
			case 2:
				dayString = "Tuesday";
				break;
			case 3:
				dayString = "Wednesday";
				break;
			case 4:
				dayString = "Thursday";
				break;
			case 5:
				dayString = "Friday";
				break;
			case 6:
				dayString = "Saturday";
				break;
			default:
				dayString = "invalid day";
				break;
			}
		}
		
		// sets the statistic field and returns it
		statistic = dayString;
		return statistic;
	}

	/**
	 * returns the statistic field
	 * returns a string
	 */
	@Override
	public String getStatistic() {
		processStat();
		return statistic;
	}

	/**
	 * returns the type of statistic
	 */
	@Override
	public String getStatType() {
		return statType;
	}
}	