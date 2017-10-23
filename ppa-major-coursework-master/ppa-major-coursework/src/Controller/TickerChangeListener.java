package Controller;

import java.util.Observable;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
/**
 * ActionListener class to restrict value of two JSpinners
 * @author JavaToJava Group
 *
 */
public class TickerChangeListener extends Observable implements ChangeListener {

	//Holds the JSpinners present within the BaseFrame
	private JSpinner startSpin;
	private JSpinner endSpin;
	private boolean dateSelected;
	
	/**
	 * Constructor based on two JSpinners
	 * @param startSpin
	 * @param endSpin
	 */
	public TickerChangeListener(JSpinner startSpin, JSpinner endSpin) 
	{
		this.startSpin = startSpin;
		this.endSpin = endSpin;
		
	}
	
	public TickerChangeListener()
	{
	}
	
	/**
	 * Override of stateChanged to restrict values of JSpinners to not exceed start date, end date, and a start date being greater than end date
	 */
	@Override
	public void stateChanged(ChangeEvent arg0) 
	{
		//Loads the default minimum and maximum values of the JSpinners, casted as an integer
		final int globalStartDate = (int) ((SpinnerNumberModel) endSpin.getModel()).getMinimum();
		final int globalEndDate = (int) ((SpinnerNumberModel) endSpin.getModel()).getMaximum();
		
		//Conditional to set startSpin within bounds
		if ((int) startSpin.getValue() < globalStartDate)
		{
			startSpin.setValue(globalStartDate);
		}
		else if ((int) startSpin.getValue() > globalEndDate)
		{
			startSpin.setValue(globalEndDate);
		}
		
		//Conditional to set endSpin within bounds
		if ((int) endSpin.getValue() < globalStartDate)
		{
			endSpin.setValue(globalStartDate);
		}
		else if ((int) endSpin.getValue() > globalEndDate)
		{
			endSpin.setValue(globalEndDate);
		}
		
		//Conditional to set the end date to be start date + 1 if the startSpin JSpinner value is greater than endSpin JSpinner value
		if ((int) startSpin.getValue() > (int) endSpin.getValue())
		{
			endSpin.setValue((int) startSpin.getValue() + 1);
		}
		
		dateSelected = true;
	}
	
	public boolean getDateSelected()
	{
		return dateSelected;
	}
}