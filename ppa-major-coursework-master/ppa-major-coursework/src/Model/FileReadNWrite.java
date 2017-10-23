package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

/**
 * 
 * Class to read and write to the config file.
 * 
 * @author JavaToJava
 *
 */
public class FileReadNWrite 
{
	//Fields for file & settings
	private File confFile ;
	private String settingData;
	
	/**
	 * Constructor for fields.
	 * 
	 * @param confFile
	 */
	public FileReadNWrite(File confFile)
	{
		this.confFile = confFile;
	}
	
	/**
	 * Reads from file and returns as string.
	 * 
	 * @param configPanel
	 * @return
	 */
	public String configReader(String configPanel) {
		try {
			FileReader reader = new FileReader(confFile);
			Properties statProperties = new Properties();
			statProperties.load(reader);
			
			settingData = statProperties.getProperty(configPanel);					
			reader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("file not found: " + ex);
		} catch (IOException ex) {
			System.out.println("Some IOExeption: " + ex);
		}
		
		return settingData;
	}
	/**
	 * Writes to file.
	 * 
	 * @param configPanel
	 * @return
	 */
	public void configWriter(String PanelToWrite, String whatToWrite) {
		try {
			FileReader reader = new FileReader(confFile);
			
		    Properties statProperties = new Properties();
		    statProperties.load(reader);
		    statProperties.setProperty(PanelToWrite, whatToWrite);
		    FileWriter writer = new FileWriter(confFile);
		    // second param is just a comment
		    statProperties.store(writer, "Test test");
		    writer.close();
		} catch (FileNotFoundException ex) {
		    // file does not exist
		} catch (IOException ex) {
		    // I/O error
		}
	}
}
