package Model;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;

/**
 *  Number of UFO related videos posted on YouTube in the US,
 *  in the last month
 * @author Raihan
 *
 */
public class StatsVideoCount implements Statistics {
	
	// Fields to get today's date and a month ago 
	DateFormat dateFormat;
	Calendar calStartDate;
	Calendar calEndDate;
	
	// the string representation of those dates
	// to be plugged into the the API call URL
	String startDate;
	String endDate;
	
	private URL url;
	// raw api url with place holders for publishedAfter and publishedBefore
	private String url_raw = "https://www.googleapis.com/youtube/v3/search?part=snippet&publishedAfter={0}T00%3A00%3A00Z&publishedBefore={1}T00%3A00%3A00Z&q=UFO+seen+in+the+US+-debunked&regionCode=US&key=AIzaSyDmH-g5_gkndthCqgCpBc0A4KQgMKqGPq8";
	private String finalURL;
	private HttpsURLConnection httpConn;

	// this tells the attached view what kind of statistic this is
	private static final String statType = "US UFO sighting videos last month";
	
	// the final statistic
	private String statistic;
	
	/**
	 * constructor initialises fields
	 * and processes API URL
	 */
	public StatsVideoCount() {
		
		// formats the date to fit the YT URL requirement
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		// instantiate calander, today's date by default
		calEndDate = Calendar.getInstance();
		calStartDate = Calendar.getInstance();
		
		// sets end date by setting the month to be 1 less than startDate's month
		calStartDate.set(Calendar.MONTH, (calEndDate.get(Calendar.MONTH)-2));
		
		// save as Strings in the correct format
		startDate = (dateFormat.format(calStartDate.getTime()));
		endDate = (dateFormat.format(calEndDate.getTime()));
		
		// get the final URL string by replacing the existing date range 
		// with program generated dates
		finalURL = MessageFormat.format(url_raw, startDate, endDate);
		
		// calls the processStat at the start as this caches the data 
		// does not need to call the api every time user wants to see the data
		processStat();
	}

	/**
	 * processes the YouTube API URL and calls it
	 * returns the number of videos in the last month
	 * about UFOs in the US
	 */
	@Override
	public String processStat() {
		
		// used a private method as it produces cleaner code than
		// adding try clauses to everything
		try {
			ytProcessor();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return statistic;
	}
	
	/**
	 * processes the YT api call
	 * and returns a String of the number of videos found
	 * @return String number of videos
	 * @throws Exception when casting to HttpURLConnection
	 */
	private String ytProcessor() throws Exception {
		
		// save final URL string into an URL object
		url = new URL(finalURL);
		
		// pass the URL to the HttpURLConnection object
		httpConn = (HttpsURLConnection)url.openConnection();
		httpConn.setRequestMethod("GET");
		httpConn.connect();
		
		// save the input from the API as inputStream
		InputStream httpInput = httpConn.getInputStream();
		InputStreamReader httpInStreamReader = new InputStreamReader(httpInput);
	   
		// read from the inputStreamReader using a BufferedReader
		BufferedReader rd = new BufferedReader(httpInStreamReader);
		String line;
		StringBuffer response = new StringBuffer();
		
		// read the input line by line
		while((line=rd.readLine()) != null) {
			response.append(line);
		}
		// close reader
		rd.close();
		
		// closes connection, not good to leave unused connection open
		// can't use the http connection again anywhere else
		httpConn.disconnect();
		
		// convert to JSON object
		JSONObject j = new JSONObject(response.toString());
		
		// get the JSON object names "pageInfo", get the "totalResults" from that
		int videoCount = (j.getJSONObject("pageInfo").getInt("totalResults"));
		statistic = Integer.toString(videoCount);
		return statistic;
	}

	/**
	 * returns statistics
	 */
	@Override
	public String getStatistic() {
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