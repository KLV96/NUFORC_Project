package View;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 * Leaderboard class to display the names and top scores of previous players.
 * 
 * @author Colakovic Laptop
 *
 */
public class Leaderboard extends JFrame 
{
	//ID for serialisation
	private static final long serialVersionUID = 6178319692511643354L;
	
	//Field for list of entries
	private JList<String> list;
	private DefaultListModel<String> listModel;
	private JScrollPane leaderboardScrollPane;
	
	//Fields for panel with text input and submission button.
	private JPanel southPanel;
	private JTextField textField;
	private JButton submitButton;
	
	//Field for finding the score within previous entries, to know where to place the current submission.
	private static final Pattern scorePattern = Pattern.compile("\\[\\d*\\]");
	private Matcher scoreMatcher;
	
	/**
	 * Constructs the leaderboard.
	 */
	public Leaderboard()
	{
		//Call super constructor and instantiate list
		super();
		list = new JList<String>();
		
		//Instantiate ListModel
		listModel = new DefaultListModel<String>();
		
		//Attempt fetching of ListModel from storage file
		try 
		{
			retrieveFromFile();
		} 
		catch (ClassNotFoundException | IOException e) 
		{
			e.printStackTrace();
		}
		
		//Allocation of model to list
		list.setModel(listModel);
		
		//Allocation of layout
		this.setLayout(new BorderLayout());
		
		//Instantiation of southern panel
		southPanel = new JPanel(new GridLayout(2, 1));
		
		//Instantiation of southern text field and button
		textField = new JTextField("Enter your Name");
		submitButton = new JButton("Submit");
		
		//Instantiation and setup of scrolling pane in which the list is contained
		leaderboardScrollPane = new JScrollPane();
		leaderboardScrollPane.setViewportView(list);
		leaderboardScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		//Add list to frame
		this.add(leaderboardScrollPane, BorderLayout.CENTER);
		
		//Add widgets to south panel and add south panel to frame
		southPanel.add(textField);
		southPanel.add(submitButton);
		this.add(southPanel, BorderLayout.SOUTH);
		
		//Make frame usable
		this.pack();
		this.setSize(300, 300);
		this.setVisible(true);
	}
	
	/**
	 * Save list to file.
	 */
	private void saveToFile()
	{
		//catch exceptions
		try 
		{
			//Open output stream to file
			FileOutputStream fileOutputStream = new FileOutputStream("src/storage.txt");
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			
			//Write ListModel to file
			objectOutputStream.writeObject(listModel);
			
			//Close Output streams
			objectOutputStream.close();
			fileOutputStream.close();
		} 
		//Catch clauses
		catch (FileNotFoundException e1) 
		{
			e1.printStackTrace();
		} 
		catch (IOException e1) 
		{
			e1.printStackTrace();
		}
	}
	
	/**
	 * Fetch list from file.
	 */
	@SuppressWarnings("unchecked")
	private void retrieveFromFile() throws IOException, ClassNotFoundException
	{
		//Open input stream to file
		FileInputStream fileInputStream = new FileInputStream("src/storage.txt");
		ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
		
		//Fetch ListModel from file
		listModel = (DefaultListModel<String>) objectInputStream.readObject();
		
		//Close input streams
		objectInputStream.close();
		fileInputStream.close();
	}

	/**
	 * Method referenced by listener. 
	 * Causes the leaderboard to call another method which handles adding a player.
	 * Input score is handled by listener to avoid passing IncidentFetcher unnecessarily.
	 * 
	 * @param score
	 */
	public void appendNewEntry(int score)
	{
		this.addElement(textField.getText(), score);
		saveToFile();
	}
	
	/**
	 * Setter for listener.
	 * 
	 * @param listener
	 */
	public void setListener(ActionListener listener)
	{
		submitButton.addActionListener(listener);
	}
	
	/**
	 * Method that adds a name and score to the leaderboard in the right order, highest score first.
	 * 
	 * @param playerName
	 * @param score
	 */
	public void addElement(String playerName, int score) 
	{
		//Boolean to check for placement.
		boolean placed = false;
		
		//Preventing null pointer exceptions
		if (!listModel.isEmpty())
		{
			//For each element in the list...
			for (int i = 0; i < listModel.size(); i++)
			{
				//...extract the score from the entry...
				scoreMatcher = scorePattern.matcher(listModel.getElementAt(i));
				scoreMatcher.find();
				
				//...then check if this score is as big as or bigger than that score, and submit the...
				//...entry in this position if it is. Break after submission.
				if (score >= Integer.parseInt( listModel.getElementAt(i).substring(scoreMatcher.start() + 1, scoreMatcher.end() - 1) ))
				{
					listModel.add(i, "Player: (" + playerName + ") Score: [" + score + "]");
					placed = true;
					break;
				}
			}
		}
		
		//If the entry was smaller than all other entries, append it to the end of the list.
		if (placed == false)
		{
			listModel.addElement("Player: (" + playerName + ") Score: [" + score + "]");
		}
	}
}
