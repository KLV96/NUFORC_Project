package Controller;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SwingWorker;

import Model.IncidentFetcher;

/**
 * A class used to listen for a press of the 'go' button and cause the incidentFetcher to get the dates.
 * 
 * @author JavaToJava
 *
 */
public class DateUpdateAL extends Observable implements ActionListener, KeyListener 
{
	//Holds the JSpinnder and JButton components from the BaseFrame 
	private JSpinner startSpin;
	private JSpinner endSpin;
	private JButton executeButton;
	//Model of incidents fetched from the ripley API
	private IncidentFetcher incidentFetcher;
	
	/**
	 * Constructor for fields.
	 * 
	 * @param startSpin
	 * @param endSpin
	 * @param incidentFetcher
	 * @param executeButton
	 */
	public DateUpdateAL(JSpinner startSpin, JSpinner endSpin, IncidentFetcher incidentFetcher, JButton executeButton)
	{
		this.startSpin = startSpin;
		this.endSpin = endSpin;
		this.incidentFetcher = incidentFetcher;
		this.executeButton = executeButton;
	}
	
	/**
	 * Override of the ActionListener interface, used to get the incidentFetcher to fetch upon button press.
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		
		//local fields to assess if the number of years of incidents requested is greater than 10
		int start = (Integer) startSpin.getValue();
		int end = (Integer) endSpin.getValue();
		Boolean runme = false;
		//If equal to or greater than 10, play sound and provide warning message in a new option pane and change boolean value
		if(end-start >= 10)
		{
			Toolkit.getDefaultToolkit().beep();
			int input = JOptionPane.showOptionDialog(null, "This might take long time, Do you want to continue ?", "Large data set warning", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
			if(input == JOptionPane.OK_OPTION)
			{
			   
				incidentFetcher.setIsFetching(true);
			    runme = true;
			}
		
					
		}
		 
		if (runme || end-start < 10 ) 
			{
			//Set loading screen and mouse icon in the welcome panel
			incidentFetcher.setIsFetching(true);
			//disable search button
			executeButton.setEnabled(false);
			//Creates a new Inner class SwingWorker each time a action is performed
			class ComputationSW extends SwingWorker<Void, Void>
			{
				//Executes the process to be handled in a separate thread to allow continuous functionality to the UI
				@Override
				protected Void doInBackground() throws Exception 
				{
					incidentFetcher.setStartString(startSpin.getValue() + "-01-01 00:00:00");
					incidentFetcher.setEndString(endSpin.getValue() + "-01-01 00:00:00");
					incidentFetcher.getDates();
					
					// when go is pressed refersh all the stats
					
					// startSpin.filterStates(incidentFetcher.passAllStates());
					setChanged();
					notifyObservers("notifyStatsPanel");
					return null;
				}
				
				//Executes when the doInBackground has finished			
				@Override
				protected void done()
				{
						executeButton.setEnabled(true);
				}
			}
			ComputationSW sw = new ComputationSW();
			//Executes/Activates the swing worker
			sw.execute();
			
			
		}
		
	}

	/**
	 * Method to allow a button press upon pressing 'ENTER'.
	 */
	@Override
	public void keyPressed(KeyEvent arg0) {
		//When the enter button is pressed, simulate a button click event
		if (arg0.getKeyCode() == 10) 
		{
			executeButton.doClick();
		}
		
		
	}

	/**
	 * Unused Interface method.
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Unused Interface method.
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
