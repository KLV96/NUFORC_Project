package Controller;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Observable;

import javax.swing.JLabel;

import Model.IncidentFetcher;
import api.ripley.Incident;
/**
 * Listener to open a map frame on click.
 * 
 * @author Dan, Stefan
 *
 */
public class MapIconsML extends Observable implements MouseListener {
	//Holds Incidents Model
	private IncidentFetcher fetcher;
	//Holds Incidents relating to the states of the USA
	private ArrayList<Incident> stateIncidents;
	//Holds the name of the targetted state
	private String sourceName;
	

	/**
	 * Constructor for fields.
	 * @param fetcher
	 */
	public MapIconsML(IncidentFetcher fetcher) {
		this.fetcher = fetcher;
		this.sourceName = "";
	}

	/**
	 * Override mouse click event to get events in a specific state.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		//Retrieve the source of the mouse click event and cast as a JLabel
		JLabel source = (JLabel) e.getSource();
		//Retrieve the name of a JLabel stored as a String
		String sourceName = source.getName();
		//Use the source name to query the incidents model to return an array of incidents exclusive to that state
		stateIncidents = fetcher.getIndividualStateIncidents(sourceName);
		//notify observers that an update has occured
		setChanged();
		//provide the state incidents array as an argument to all observers 
		notifyObservers(stateIncidents);
	}

	/**
	 * Used to change cursor icon upon hover.
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		JLabel source = (JLabel) e.getSource();
		source.setCursor(new Cursor(Cursor.HAND_CURSOR));

	}
	
	/**
	 * Used to change cursor icon upon hover.
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		JLabel source = (JLabel) e.getSource();
		source.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

	}

	/**
	 * Unused Interface implementation
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * Unused Interface implementation
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * Getter for state incidents.
	 */
	public ArrayList<Incident> getStateIncidents() {
		return stateIncidents;
	}

	/**
	 * Getter for source name.
	 */
	public String getSourceName() {
		return sourceName;
	}
}
