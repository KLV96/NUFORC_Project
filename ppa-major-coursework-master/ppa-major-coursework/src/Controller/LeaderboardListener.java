package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Model.QuizModel;
import View.Leaderboard;

/**
 * Listener for a leaderboard to allow it to submit a new entry to the leaderboard with
 * the current session's score and the name input.
 * 
 * @author Colakovic Laptop
 *
 */
public class LeaderboardListener implements ActionListener 
{
	//Fields used upon button press
	private QuizModel quizModel;
	private Leaderboard leaderboard;
	
	/**
	 * Constructor based on field allocation.
	 * 
	 * @param quizModel
	 * @param leaderboard
	 */
	public LeaderboardListener(QuizModel quizModel, Leaderboard leaderboard) 
	{
		this.quizModel = quizModel;
		this.leaderboard = leaderboard;
	}

	/**
	 * Override for ActionListener interface to append a new entry to the leaderboard based on current session.
	 * 
	 * @Override
	 */
	public void actionPerformed(ActionEvent e) 
	{
		leaderboard.appendNewEntry(quizModel.getScore());
	}
}