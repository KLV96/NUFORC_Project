package Model;

import View.BaseFrame;

/**
 * This class has the main method 
 * @author JavaToJava
 *
 */
public class Main {
	
	public static void main(String[] args) {

		IncidentFetcher mainIncidentFetcher = new IncidentFetcher();
		BaseFrame baseFrame = new BaseFrame(mainIncidentFetcher);
		baseFrame.setVisible(true);
	}

}
