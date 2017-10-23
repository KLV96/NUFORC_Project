package View;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

import Controller.LeaderboardButtonListener;
import Controller.LeaderboardListener;
import Model.IncidentFetcher;
import Model.QuizModel;
/**
 * A class which creates a panel to ask the user questions about the incidents fetched, plus some trivia.
 * 
 * @author Colakovic Laptop
 *
 */
public class QuizPanel extends JPanel implements ActionListener, Observer
{
	private static final long serialVersionUID = 2155458621159213473L;
	
	//For passing to the model, to keep use of only 1 ripley
	private IncidentFetcher incidentFetcher;
	
	//Model
	private QuizModel quizModel;
	
	//Label for the question
	private JLabel northLabel;
	
	//Radio buttons for the user's answer
	private JPanel buttonPanel;
	private JRadioButton option1;
	private JRadioButton option2;
	private JRadioButton option3;
	private JRadioButton option4;
	private ButtonGroup group;

	//South panel for buttons to get leaderboard, next question, and submit answers
	private JPanel southPanel;
	private JButton leaderboardButton;
	private JButton nextQuestionButton;
	private JButton submitAnswerButton;
	private JLabel scoreLabel;
	
	//Fields to contain the Leaderboard frame when opened
	private Leaderboard leaderboard;
	private LeaderboardListener leaderboardListener;
	private LeaderboardButtonListener leaderboardButtonListener;
	
	/**
	 * Instantiates all fields.
	 * Takes an IncidentFetcher during construction to pass to the Model; 
	 * with no IncidentFetcher, the questions will be only trivia.
	 * 
	 * @param incidentFetcher
	 */
	public QuizPanel(IncidentFetcher incidentFetcher) 
	{
		//Instantiate JPanel and layout
		super();
		this.setLayout(new BorderLayout(30, 100));
		
		//Pass incidentFetcher from argument to field
		this.incidentFetcher = incidentFetcher;
		
		//Instantiate model using incidentFetcher
		quizModel = new QuizModel(this.incidentFetcher);
		
		//Instantiate question label
		northLabel = new JLabel();
		
		//Instantiate radio buttons
		buttonPanel = new JPanel(new GridLayout(4, 1));
		option1 = new JRadioButton();
		option2 = new JRadioButton();
		option3 = new JRadioButton();
		option4 = new JRadioButton();
		group = new ButtonGroup();
		
		//Instantiate South panel with buttons and score
		southPanel = new JPanel(new GridLayout(1,4));
		leaderboardButton = new JButton("Leaderboard");
		nextQuestionButton = new JButton("Next Question");
		submitAnswerButton = new JButton("Sumbit Answer");
		scoreLabel = new JLabel("<html><div style='text-align: center;'>Score = 0</div></html>", SwingConstants.CENTER);
		
		//Instantiate leaderboard as null until button is pressed
		leaderboard = null;
		leaderboardListener = null;
		leaderboardButtonListener = new LeaderboardButtonListener();
		
		//Method to separate instantiation from allocation
		initWidgets();
	}
	
	/**
	 * Allocates widgets to their needed locations.
	 */
	private void initWidgets()
	{
		//Set string to be put into question at start
		String labelText = "Click 'Next Question' button to get a new question, or 'Submit Answer' to check your answer.";
		
		//Add radio buttons to button group to only allow 1 selection at a time
		group.add(option1);
		group.add(option2);
		group.add(option3);
		group.add(option4);
		
		//Add text to labels and format it
		northLabel.setText("<html><div style='text-align: center;'>" + labelText + "</div></html>");
		northLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		//Add text to radio buttons and format them
		option1.setText("These are the buttons with which");
		option2.setText("you select your choice.");
		option3.setText("There is only one correct answer, and");
		option4.setText("the quesion will be shown above.");
		option1.setHorizontalAlignment(SwingConstants.CENTER);
		option2.setHorizontalAlignment(SwingConstants.CENTER);
		option3.setHorizontalAlignment(SwingConstants.CENTER);
		option4.setHorizontalAlignment(SwingConstants.CENTER);
		//Set default selected button
		option1.setSelected(true);
		
		//Add this as action listener to buttons
		nextQuestionButton.addActionListener(this);
		submitAnswerButton.addActionListener(this);
		leaderboardButtonListener.addObserver(this);
		
		//Add action listener for leaderboard button
		leaderboardButton.addActionListener(leaderboardButtonListener);
		
		//Add question label to north of panel
		this.add(northLabel, BorderLayout.NORTH);
		
		//Add radio buttons to central panel
		buttonPanel.add(option1);
		buttonPanel.add(option2);
		buttonPanel.add(option3);
		buttonPanel.add(option4);
		
		//Add central panel to centre of panel
		this.add(buttonPanel, BorderLayout.CENTER);
		
		//Add buttons to southern panel
		southPanel.add(leaderboardButton);
		southPanel.add(nextQuestionButton);
		southPanel.add(submitAnswerButton);
		southPanel.add(scoreLabel);
		
		//Add southern panel to south of panel
		this.add(southPanel, BorderLayout.SOUTH);
	}

	/**
	 * Override of the ActionListener interface. Used to provide functionality to the next 
	 * question button and the submit answer button.
	 * 
	 * @Override
	 */
	public void actionPerformed(ActionEvent e) 
	{
		//Checks which button is pressed and calls the relevant method
		if (e.getSource() == nextQuestionButton)
		{
			fetchNewQuestion();
		}
		else if (e.getSource() == submitAnswerButton)
		{
			checkAnswer();
		}
	}
	
	/**
	 * Communicates with the model to fetch a random new question from its collection. 
	 * Scrambles the answers so as to prevent cheating.
	 */
	private void fetchNewQuestion()
	{
		//Gets question and answers from model
		String[] question = quizModel.getNewQuestion();
		
		//Allocates answers to a new ArrayList then shuffles them randomly
		ArrayList<String> answers = new ArrayList<String>();
		answers.add(question[1]);
		answers.add(question[2]);
		answers.add(question[3]);
		answers.add(question[4]);
		Collections.shuffle(answers);
		
		//Allocates north label to the question
		northLabel.setText("<html><div style='text-align: center;'>" + question[0] + "</div></html>");
		
		//Allocates each radio button to a possible answer. Based on strings, so randomly generated questions...
		//...which by chance repeat a correct answer in two slots, either will award the user a point.
		option1.setText(answers.get(0));
		option2.setText(answers.get(1));
		option3.setText(answers.get(2));
		option4.setText(answers.get(3));
		option1.setActionCommand(answers.get(0));
		option2.setActionCommand(answers.get(1));
		option3.setActionCommand(answers.get(2));
		option4.setActionCommand(answers.get(3));
		option1.setSelected(true);
		
		//Update score
		scoreLabel.setText("<html><div style='text-align: center;'>" +  "Score = " + quizModel.getScore() + "</div></html>");
	}
	
	/**
	 * Method to submit answer to model for checking if the answer is correct.
	 */
	private void checkAnswer()
	{
		//Submission to the model and getting 'true' if correct and 'false' otherwise
		boolean response = quizModel.isCorrect(group.getSelection().getActionCommand());
		
		//Updating score label based on result
		if (response == true)
		{
			scoreLabel.setText("<html><div style='text-align: center;'>" + "Score = " + quizModel.getScore() + "<br>Correct answer!" + "</div></html>");
		}
		else
		{
			scoreLabel.setText("<html><div style='text-align: center;'>" + "Score = " + quizModel.getScore() + "<br>Incorrect answer!" + "</div></html>");
		}
	}

	/**
	 * Observer method override to utilise Leaderboard Button Listener
	 * 
	 *	@Override
	 */
	public void update(Observable o, Object arg)
	{
		//Instantiates leaderboard object and its listeners, so that the user can utilise them. These dispose on close.
		leaderboard = new Leaderboard();
		leaderboardListener = new LeaderboardListener(quizModel, leaderboard);
		leaderboard.setListener(leaderboardListener);
	}
}