package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;


/**
 * This class is the action listener for 4 right buttons. The name that is set to the button is used to recogonise which button is pressed. 
 * @author Rewaz 
 *
 */
public class StatsNavRight extends Observable implements ActionListener{


	/**
	 * This class checks the name of the button and depending on certain name the observer is notified 
	 * and passes that name to the update method in the observer as argument
	 */
	@Override
	public void actionPerformed(ActionEvent evt) 
	{
		
		if (evt.getActionCommand() == Actions.statsPanel1RB.name())
		{
			setChanged();
			notifyObservers("statsPanel1RB");
		}
		else if (evt.getActionCommand() == Actions.statsPanel2RB.name())
		{
			setChanged();
			notifyObservers("statsPanel2RB");
		}
		
		else if (evt.getActionCommand() == Actions.statsPanel3RB.name())
		{
			setChanged();
			notifyObservers("statsPanel3RB");
		}
		else if (evt.getActionCommand() == Actions.statsPanel4RB.name())
		{
			setChanged();
			notifyObservers("statsPanel4RB");
		}
		

	}
	
	/**
	 * This stores Enumerations variables for the right buttons which are constants that are used to define separate buttons 
	 * @author rewaz
	 *
	 */
	private enum Actions {

		statsPanel1RB,
		
		statsPanel2RB,
		
		statsPanel3RB,
		
		statsPanel4RB,
	  }


}
