package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

/**
 * This class implements ActionListener 
 * @author Dan, Stefan
 *
 */
public class OrganiseMapStatsAL extends Observable implements ActionListener
{
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		setChanged();
		notifyObservers();
	}
}