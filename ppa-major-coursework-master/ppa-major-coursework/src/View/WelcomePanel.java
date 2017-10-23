package View;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SwingConstants;

import Model.IncidentFetcher;

/**
 * This class has the welcome panel which is displayed when the program runs
 * @author JavaToJava
 *
 */
public class WelcomePanel extends JPanel 
{
	
	private static final long serialVersionUID = 143861526521545864L;
	
	private JLabel text;
	private String labelInternal;
	private ImageIcon loading;
	private ImageIcon loadingUFO;
	private JLabel imageHolder;
	private JLabel imageHolderUFO;
	private IncidentFetcher incidentFetcher;
	
	public WelcomePanel(IncidentFetcher incidentFetcher) 
	{	
		this.incidentFetcher = incidentFetcher;
		
		loading = new ImageIcon("src/Images/SpinnerNoBackground.gif");
		loadingUFO = new ImageIcon("src/Images/ufo-flying.gif");
		imageHolder = new JLabel(loading);
		imageHolderUFO = new JLabel(loadingUFO);
		// need to move to a model.
		
		labelInternal = 
				"<html><p><div align=\"center\"><font size=\"5\"> Ripley Version: " 
				+ this.incidentFetcher.getVersion() + 
				"<br><br>Please insert which years to search in order to begin analysing UFO sighting data."
				+ "<br><br>The year of the first sighted incident is " 
				+ this.incidentFetcher.getStartYear() 
				+ "<br><br>The year of the latest sighting is displayed at the bottom of the window."
				+ "<font color=\"red\"><br><br>Please enter dates between these years" 
				+ "<br><br>"
				+ "Acknowledgement String: <br>" 
				+ this.incidentFetcher.getAcknowledgementString().substring(0, 72)
				+ "<br>" + this.incidentFetcher.getAcknowledgementString().substring(72, this.incidentFetcher.getAcknowledgementString().length() - 1)
				+"</font></p></html>";
		
		text = new JLabel(labelInternal, SwingConstants.CENTER);
		
		text.setHorizontalAlignment(SwingConstants.CENTER);
		text.setVerticalAlignment(SwingConstants.CENTER);
		add(text);
	}
	
	/**
	 * Retrieve the amount of time taken to execute the search, the date range, and display within the Welcome Panel
	 * @param timeToFetch
	 * @param startDateSpinner
	 * @param endDateSpinner
	 */
	public void ripleyHasFetched(long timeToFetch, JSpinner startDateSpinner, JSpinner endDateSpinner) 
	{
		System.out.println("ripley has fetched");
		this.remove(imageHolder);
	
		
		labelInternal = "<html><b><div align=\"center\"><font size=\"5\">" + "Date range selected, " 
				+ startDateSpinner.getValue() + "-" + endDateSpinner.getValue()
				+ "<br><br> Data grabbed in " 
				+ java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes(timeToFetch)
				+ " minutes, "
				+ (java.util.concurrent.TimeUnit.MILLISECONDS.toSeconds(timeToFetch) - (java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes(timeToFetch)*60))
				+ " seconds, "
				+ (timeToFetch - (java.util.concurrent.TimeUnit.MILLISECONDS.toSeconds(timeToFetch)*1000 + (java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes(timeToFetch)*60000)))
				+ "ms</font></p></html>";
		
		text.setText(labelInternal);
		
		add(imageHolderUFO);
		add(text);
		revalidate();
		repaint();
		
	}
	
	/**
	 * Remove all contents within the JPanel and display loading icon
	 */
	public void DisplayLoadingIcon() 
	{
		remove(text);
		remove(imageHolderUFO);
		add(imageHolder);
		revalidate();
		repaint();
	}
	
}