package View;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import Controller.OrganiseMapStatsAL;
import api.ripley.Incident;
import edu.emory.mathcs.backport.java.util.Collections;

public class StateListPopupFrame extends JFrame implements Observer
{
	private static final long serialVersionUID = 6084966249660666292L;
	private ArrayList<Incident> stateIncidents;
	private JList<IncidentText> incidentList;
	private DefaultListModel<IncidentText> incidentListModel;
	private JScrollPane scrollingPane;
	private JComboBox<String> jcbSortIncidents;
	private OrganiseMapStatsAL cbPopupAL;
	private ArrayList<IncidentText> incidentTextList;

	public StateListPopupFrame(ArrayList<Incident> stateIncidents)
	{
		super();
		
		this.stateIncidents = stateIncidents;
		incidentList = new JList<IncidentText>();
		incidentListModel = new DefaultListModel<IncidentText>();
		jcbSortIncidents = new JComboBox<String>();
		
		initWidgets();
	}
	
	private void initWidgets()
	{
		jcbSortIncidents.setEditable(false);
		jcbSortIncidents.addItem("Date");
		jcbSortIncidents.addItem("City");
		jcbSortIncidents.addItem("Shape");
		jcbSortIncidents.addItem("Duration");
		jcbSortIncidents.addItem("Posted");
		jcbSortIncidents.setEditable(true);
		cbPopupAL = new OrganiseMapStatsAL();
		jcbSortIncidents.addActionListener(cbPopupAL);
		cbPopupAL.addObserver(this);
		
		incidentList.setModel(incidentListModel);
		
		incidentTextList = new ArrayList<IncidentText>();
		
		for (Incident incident : stateIncidents)
		{
			incidentTextList.add(new IncidentText(incident));
		}
		
		populateIncidentListModel();
		
		incidentList.addMouseListener(new ListClickListener());

		this.setLayout(new BorderLayout());
		scrollingPane = new JScrollPane(incidentList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		this.add(scrollingPane, BorderLayout.CENTER);
		this.add(jcbSortIncidents, BorderLayout.NORTH);
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		this.pack();
		this.setVisible(true);
	}
	
	private void populateIncidentListModel() 
	{
		for (IncidentText incidentText : incidentTextList)
		{
			incidentListModel.addElement(incidentText);
		}
		
		incidentList.setModel(incidentListModel);
	}

	private class ListClickListener extends MouseAdapter
	{
		@Override
	// invoked when the mouse has been clicked
		public void mouseClicked(MouseEvent e) 
		{
			//checks for double-click on list
			if (e.getClickCount() == 2 && (e.getSource() instanceof JList<?>))
			{
				//gets index of list and incident at that location, then throws a dialog box with the incident's summary
				int indexOfClick = ((JList<?>)(e.getSource())).locationToIndex(e.getPoint());
				
				JOptionPane.showMessageDialog(null, 
						((IncidentText)((JList<?>)(e.getSource())).getModel().getElementAt(indexOfClick)).toString()
							+ "\n\n" 
								+((IncidentText)((JList<?>)(e.getSource())).getModel().getElementAt(indexOfClick)).getIncidentSummary(), 
						"Incident Details",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	
	private class IncidentText
	{
		private Incident incident;
		
		public IncidentText(Incident incident)
		{
			this.incident = incident;
		}

		public String toString()
		{
			return "[Date: " + incident.getDateAndTime()
			+ "]\t[City: " + incident.getCity() 
			+ "]\t[Shape: " + incident.getShape()
			+ "]\t[Duration: " + getIncidentDurationComparable() + " seconds"
			+ "]\t[Posted: " + incident.getPosted() + "]";
		}
		
		public String getIncidentCity()
		{
			return incident.getCity();
		}
		
		public String getIncidentShape()
		{
			return incident.getShape();
		}
		
		public String getIncidentSummary()
		{
			return incident.getSummary();
		}
		
		public Calendar getIncidentDateComparable()
		{
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar calendar = Calendar.getInstance();
			try 
			{
				calendar.setTime(dateFormat.parse(incident.getDateAndTime()));
			} 
			catch (ParseException e) 
			{
				e.printStackTrace();
				return null;
			}
			return calendar;
		}
		
		public Calendar getIncidentPostedComparable()
		{
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			try 
			{
				calendar.setTime(dateFormat.parse(incident.getPosted()));
			} 
			catch (ParseException e) 
			{
				e.printStackTrace();
				return null;
			}
			return calendar;
		}
		
		public int getIncidentDurationComparable()
		{
			String durationString = incident.getDuration();
			
			Parser parser = new Parser();
			List<DateGroup> groups = parser.parse(durationString);
			
			int durationInSeconds = -2;
			
			for (DateGroup group : groups) 
			{
			  List<Date> dates = group.getDates();
			  Calendar now = Calendar.getInstance();
			  Calendar then = Calendar.getInstance();
			  then.setTime(dates.get(0));
			  
			  if (now.getTimeInMillis() >= then.getTimeInMillis())
			  {
				  durationInSeconds = -1;
			  }
			  else
			  {
				  long durationInMillis = then.getTimeInMillis() - now.getTimeInMillis();
				  durationInSeconds = (int)durationInMillis /1000;
			  }
			}
			
			return durationInSeconds;
		}
	}

	@Override
	public void update(Observable o, Object arg) 
	{
		switch ((String)jcbSortIncidents.getSelectedItem())
		{
			case "Date":
			{
				Comparator<IncidentText> comparator = 
						new Comparator<IncidentText>()
						{
							public int compare(IncidentText incident1, IncidentText incident2)
							{
								return incident1.getIncidentDateComparable().compareTo(incident2.getIncidentDateComparable());
							}
					    };
					    
	    		Collections.sort(incidentTextList, comparator);
	    		
	    		incidentListModel = new DefaultListModel<IncidentText>();
	    		populateIncidentListModel();
	    		
	    		Toolkit.getDefaultToolkit().beep();
	    		
				break;
			}
			
			case "City":
			{
				Comparator<IncidentText> comparator = 
						new Comparator<IncidentText>()
						{
							public int compare(IncidentText incident1, IncidentText incident2)
							{
								return incident1.getIncidentCity().compareTo(incident2.getIncidentCity());
							}
					    };
					    
	    		Collections.sort(incidentTextList, comparator);
	    		
	    		incidentListModel = new DefaultListModel<IncidentText>();
	    		populateIncidentListModel();
	    		
	    		Toolkit.getDefaultToolkit().beep();
	    		
				break;
			}
			
			case "Shape":
			{
				Comparator<IncidentText> comparator = 
						new Comparator<IncidentText>()
						{
							public int compare(IncidentText incident1, IncidentText incident2)
							{
								return incident1.getIncidentShape().compareTo(incident2.getIncidentShape());
							}
					    };
					    
	    		Collections.sort(incidentTextList, comparator);
	    		
	    		incidentListModel = new DefaultListModel<IncidentText>();
	    		populateIncidentListModel();
	    		
	    		Toolkit.getDefaultToolkit().beep();
	    		
				break;
			}
			
			case "Duration":
			{
				Comparator<IncidentText> comparator = 
						new Comparator<IncidentText>()
						{
							public int compare(IncidentText incident1, IncidentText incident2)
							{
								if (incident1.getIncidentDurationComparable() == incident2.getIncidentDurationComparable())
								{
									return 0;
								}
								else if (incident1.getIncidentDurationComparable() > incident2.getIncidentDurationComparable())
								{
									return 1;
								}
								else
								{
									return -1;
								}
							}
					    };
					    
	    		Collections.sort(incidentTextList, comparator);
	    		
	    		incidentListModel = new DefaultListModel<IncidentText>();
	    		populateIncidentListModel();
	    		
	    		Toolkit.getDefaultToolkit().beep();
	    		
				break;
			}
			
			case "Posted":
			{
				Comparator<IncidentText> comparator = 
						new Comparator<IncidentText>()
						{
							public int compare(IncidentText incident1, IncidentText incident2)
							{
								return incident1.getIncidentPostedComparable().compareTo(incident2.getIncidentPostedComparable());
							}
					    };
					    
	    		Collections.sort(incidentTextList, comparator);
	    		
	    		incidentListModel = new DefaultListModel<IncidentText>();
	    		populateIncidentListModel();
	    		
	    		Toolkit.getDefaultToolkit().beep();
	    		
				break;
			}
		}
	}
}