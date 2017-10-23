package Model;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

/**
 * Model for the quiz panel.
 * 
 * @author Colakovic Laptop
 *
 */
public class QuizModel implements Observer
{
	//Fields for incident fetcher and randomiser
	private IncidentFetcher incidentFetcher;
	private Random random;
	
	//Fields for question functionality
	private ArrayList<String[]> questions;
	private int currentQuestion;
	private boolean questionAlreadyAnswered;
	private int score;
	
	/**
	 * Takes an IncidentFetcher to make use of the ripley implemented in parent classes.
	 * Uses this to generate random questions based on any loaded incidents.
	 * 
	 * @param incidentFetcher
	 */
	public QuizModel(IncidentFetcher incidentFetcher) 
	{
		//Allocating field for incident fetcher, and adding this as an observer to it...
		//...in order to make sure that new questions are generated when new incidents are fetched.
		this.incidentFetcher = incidentFetcher;
		this.incidentFetcher.addObserver(this);
		
		//Instantiating random generator
		random = new Random();
		
		//Instantiating score and index
		score = 0;
		currentQuestion = -1;
		
		//Calling question generation method
		initQuestions();
	}
	
	/**
	 * Generates questions to be asked of the user. 
	 * Positioned in a specific format and scrambled later to increase usability.
	 */
	private void initQuestions()
	{
		//Instantiate questions array - done within this method rather than constructor so that...
		//...when the update method is called due to a new set of incidents being fetched, the array...
		//...is completely re-populated rather than added upon.
		questions = new ArrayList<String[]>();
		
		//In String[] questions, [0] = question, [1] = correct answer, [2-4] = wrong answers
		String[] question1 = {"Which of the following is a registered UFO Shape?", "Light", "Sound", "Smell", "Taste"};
		String[] question2 = {"Which of these is one of the symbols used on the map?", "Facehugger", "Alien Goop", "Shadow-walker", "Headcrab"};
		String[] question3 = {"What is the surname of the protagonist of the movie 'Alien'?", "Ripley", "Ian", "Weaver", "Weyland-Utani"};
		String[] question4 = {"The U.S. Air Force investigated UFO sightings under Project Blue Book. Of the 12,618 sightings reported between 1947 and 1969, how many are still 'unidentified' ?", "701", "2", "10000", "None"};
		String[] question5 = {"In 1947, a man in Roswell, New Mexico, found foil reflectors, metallic sticks and other odd material in his ...", "His sheep pasture", "Outdoor swimming pool", "Bedroom", "Garage"};
		String[] question6 = {"Mysterious red lights hovering in formation over Phoenix, Ariz., in April 2008, turned out to be ...", "Flares attached to helium balloons", "Four flying angels holding candles", "The glow from alein spaceships", "none of the above"};
		String[] question7 = {" What is the earliest record that related to unusual aerial phenomena in the world?", "1450 BC", "99 BC", "1561", "1235"};
		String[] question8 = {"What percentage of Americans believe that the government is hiding information about UFOs?", "80%", "20%", "40%", "60%"};
		String[] question9 = {"'Area 51' is located near which Air Force base?", "Nellis", "Davis Monthan", "Luke", "Martin"};
		String[] question10 = {"Who found the UFO that crashed near Roswell New Mexico in 1947?", "Mac Brazel", "Sam Jackson", "Martin Chapman", "Ronnie Walker"};
		String[] question11 = {"Which company produces a plastic model kit of the Roswell UFO?", "Testors", "Air America", "Tesco", "Bandai"};
		String[] question12 = {"In most movies, pictures, paintings, and sightings UFOs are depicted as what?", "Plate-like shapes", "Square-like shapes", "cubical-like shapes", "sphere-like shapes"};
		
		//Adding questions to array
		questions.add(question1);
		questions.add(question2);
		questions.add(question3);
		questions.add(question4);
		questions.add(question5);
		questions.add(question6);
		questions.add(question7);
		questions.add(question8);
		questions.add(question9);
		questions.add(question10);
		questions.add(question11);
		questions.add(question12);
		
		//Generating questions randomly based on incidents.
		initRandomQuestions();
	}
	
	/**
	 * Generates questions somewhat randomly based on set patterns and the fetched incidents
	 */
	private void initRandomQuestions()
	{
		//Preventing null pointer exceptions and index out of bounds exceptions
		if (incidentFetcher.getIncidents() != null && incidentFetcher.getIncidents().size() > 0)
		{
			//Getting the number of incidents in the array
			double sizeOfIncidents = (double)(incidentFetcher.getIncidents().size());
			
			//Performing a function to make the number of questions tend towards 30;...
			//...the function makes very large numbers of incidents produce a random 20-40 questions per type
			double function = (10.0d)*(Math.log10(sizeOfIncidents + 1.0d));
			
			//Pruning the function to ensure that there is no index-out-of-bounds exception, as there is...
			//...a point within the function where f(x) > x (e.g. when x = 2, f(x) = 3.01...)
			if (sizeOfIncidents < function)
			{
				function = sizeOfIncidents;
			}

			//Casting to int to be able to iterate through arrays; truncation cannot produce errors...
			//...since it will never cause the index to go above the possible number of incidents
			int numberOfQuestions = (int)function;
			
			//Creates an array of String arrays, since the questions we pass are type String[5]
			String[][] randomQuestions1 = new String[numberOfQuestions][5];

			//For loop to generate the certain number of questions determined above
			for (int i = 0; i < numberOfQuestions; i++)
			{
				//Generates a random index within the incident array
				int index = random.nextInt(incidentFetcher.getIncidents().size());

				//Creates a question based on the incident's summary and shape
				randomQuestions1[i][0] = "Given the following incident's summary, determine its shape:" 
						+ incidentFetcher.getIncidents().get(index).getSummary();

				//Creates an answer from the same incident
				randomQuestions1[i][1] = incidentFetcher.getIncidents().get(index).getShape();

				//Gets random answers from other incidents. MAY be the same the actual answer, but the method for...
				//...awarding score is lenient in this regard; it will reward points for either choice.
				randomQuestions1[i][2] = incidentFetcher.getIncidents().get(random.nextInt(incidentFetcher.getIncidents().size())).getShape();
				randomQuestions1[i][3] = incidentFetcher.getIncidents().get(random.nextInt(incidentFetcher.getIncidents().size())).getShape();
				randomQuestions1[i][4] = incidentFetcher.getIncidents().get(random.nextInt(incidentFetcher.getIncidents().size())).getShape();

				//Add each generated question to the array of questions
				questions.add(randomQuestions1[i]);
			}

			//Repeat of the above for loop generation, except based on an incident's city and state
			String[][] randomQuestions2 = new String[numberOfQuestions][5];

			for (int i = 0; i < numberOfQuestions; i++)
			{
				int index = random.nextInt(incidentFetcher.getIncidents().size());

				randomQuestions2[i][0] = "Given the following incident's city, determine its state:" 
						+ incidentFetcher.getIncidents().get(index).getCity();

				randomQuestions2[i][1] = incidentFetcher.getIncidents().get(index).getState();;

				randomQuestions2[i][2] = incidentFetcher.getIncidents().get(random.nextInt(incidentFetcher.getIncidents().size())).getState();
				randomQuestions2[i][3] = incidentFetcher.getIncidents().get(random.nextInt(incidentFetcher.getIncidents().size())).getState();
				randomQuestions2[i][4] = incidentFetcher.getIncidents().get(random.nextInt(incidentFetcher.getIncidents().size())).getState();

				questions.add(randomQuestions2[i]);
			}

			//Repeat of the above for loop generation, except based on an incident's date posted and date observed
			String[][] randomQuestions3 = new String[numberOfQuestions][5];

			for (int i = 0; i < numberOfQuestions; i++)
			{
				int index = random.nextInt(incidentFetcher.getIncidents().size());

				randomQuestions3[i][0] = "Given the following incident's date posted, determine its date observed:" 
						+ incidentFetcher.getIncidents().get(index).getPosted();

				randomQuestions3[i][1] = incidentFetcher.getIncidents().get(index).getDateAndTime();;

				randomQuestions3[i][2] = incidentFetcher.getIncidents().get(random.nextInt(incidentFetcher.getIncidents().size())).getDateAndTime();
				randomQuestions3[i][3] = incidentFetcher.getIncidents().get(random.nextInt(incidentFetcher.getIncidents().size())).getDateAndTime();
				randomQuestions3[i][4] = incidentFetcher.getIncidents().get(random.nextInt(incidentFetcher.getIncidents().size())).getDateAndTime();

				questions.add(randomQuestions3[i]);
			}
		}
	}
	
	/**
	 * Method that checks whether the answer the user selected (passed via input parameter) 
	 * is the correct answer. Returns true if so, and false otherwise. Only allows one 
	 * submission per question, so the user cannot guess twice.
	 * 
	 * @param input
	 * @return isCorrectBoolean
	 */
	public boolean isCorrect(String input)
	{
		//Preventing submission if a question has not been fetched
		if (currentQuestion >= 0)
		{
			//Checks if the input is correct and the question is being answered for the first time
			if (input.equals(questions.get(currentQuestion)[1]) && questionAlreadyAnswered == false)
			{
				//Increments score and sets question to answered, returns true
				score++;
				questionAlreadyAnswered = true;
				return true;
			}
		}
		//Sets question to answered, returns false. This only runs if the above 'if' was false
		questionAlreadyAnswered = true;
		return false;
	}
	
	public String[] getNewQuestion()
	{
		questionAlreadyAnswered = false;
		currentQuestion = random.nextInt(questions.size());
		return questions.get(currentQuestion);
	}

	public int getScore() {
		return score;
	}

	/**
	 * Observes the IncidentFetcher to make sure that, upon changing the fetched incidents, new questions are generated based on them
	 * 
	 *	@Override
	 */
	public void update(Observable o, Object arg) 
	{
		initQuestions();
	}
}