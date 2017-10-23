package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

/**
 * Class to listen to the button for opening the leaderboard.
 * 
 * @author Colakovic Laptop
 *
 */
public class LeaderboardButtonListener extends Observable implements ActionListener {

	/**
	 * Override of the ActionListener interface to notify QuizModel upon button press
	 * 
	 * @Override
	 */
	public void actionPerformed(ActionEvent e) 
	{
		setChanged();
		notifyObservers();
	}
}