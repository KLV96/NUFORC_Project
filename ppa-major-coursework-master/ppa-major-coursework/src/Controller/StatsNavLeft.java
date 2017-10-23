package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

/**
 * This class is the action listener for 4 left buttons. The name that is set to the button is used to recogonise which button is pressed. 
 * @author Rewaz & Raihan
 *
 */
public class StatsNavLeft extends Observable implements ActionListener{
	
	
	/**
	 * This class checks the name of the button and depending on certain name the observer is notified 
	 * and passes that name to the update method in the observer as argument
	 */
	@Override
	public void actionPerformed(ActionEvent evt) 
	{
		
		if (evt.getActionCommand() == Actions.statsPanel1LB.name())
		{
			setChanged();
			notifyObservers("statsPanel1LB");
		}
		else if (evt.getActionCommand() == Actions.statsPanel2LB.name())
		{
			setChanged();
			notifyObservers("statsPanel2LB");
		}
		
		else if (evt.getActionCommand() == Actions.statsPanel3LB.name())
		{
			setChanged();
			notifyObservers("statsPanel3LB");
		}
		else if (evt.getActionCommand() == Actions.statsPanel4LB.name())
		{
			setChanged();
			notifyObservers("statsPanel4LB");
		}
		
	}
	
	/**
	 * This stores Enumerations variables for the left buttons which are constants that are used to define separate buttons 
	 * @author Rewaz & Raihan
	 *
	 */
	private enum Actions 
	{
		statsPanel1LB,
		statsPanel2LB,
		statsPanel3LB,
		statsPanel4LB,
	}

}
