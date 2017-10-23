package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

/**
 * This class implements Action Listener. 
 * This is used to add action Listener to the right button.
 * The right button is used to navigate through the 4 panels
 * @author JavaToJAva
 *
 */
public class NavLeftButton extends Observable implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		setChanged();
		notifyObservers();
	}
	

	
}
