package seat_administration;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JEditorPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Font;

/** The class that creates the frame for the admin*/
public class AdminGui 
{

	/**defines*/
	
	/** flag to show that the tickets are of type A (expensive) */
	private static final int A_SEIRA= 0;
	
	/** flag to show that the tickets are of type B (regular) */
	private static final int B_SEIRA= 1;

	
	/** variables */
	
	/**The main frame of the program*/
	public transient JFrame frame;

	/**The connector to the database*/
	private transient Database_connector connector;
	
	/**Stores the ticket type for the concert*/
	private transient JComboBox<String> seatBox;
	
	/**Label to show the total of all tickets for the concert*/
	private transient JLabel totalTicketsLabel;
	
	/**Stores all the concerts*/
	private transient JComboBox<String> concertBox;
	
	/**Label to show the final cost of the tickets*/
	private transient JLabel ticketsCostLabel;
	
	/**Label to show the number of available tickets for the concert*/
	private transient JLabel availableTicketsLabel;
	
	

	
	/**
	 * Create the application.
	 */
	public AdminGui() 
	{
		initialize();
	}

	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		frame = new JFrame();
		frame.setTitle("Óýóôçìá Äéá÷åéñéóôÞ åéóéôçñßùí ãéá óõíáõëßåò");
		frame.setResizable(false);
		frame.setAlwaysOnTop(true);
		frame.setBounds(0, 0, 621, 706);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);
		
		
		final JLabel concertLabel = new JLabel("ÅðéëïãÞ Óõíáõëßáò:");
		concertLabel.setHorizontalAlignment(SwingConstants.LEFT);
		concertLabel.setBounds(10, 80, 120, 20);
		frame.getContentPane().add(concertLabel);

		/**makes a connection to the database*/
		connector= new Database_connector();
		connector.startConn();
		
		/*concertBox takes the titles of the concerts from the DB 
		*through connector's titleSelect method*/
		concertBox = new JComboBox<>(connector.titleSelect());
		concertBox.setBounds(140, 80, 220, 20);
		frame.getContentPane().add(concertBox);
		
		/*initialize seatBox */
		seatBox = new JComboBox<>();
		seatBox.setMaximumRowCount(20);
		seatBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Á Æþíç", "Â Æþíç", "Ã Æþíç"}));
		seatBox.setBounds(370, 80, 70, 20);
		frame.getContentPane().add(seatBox);
		
		/**Gives totalTicketsLabel the number of the total tickets for the concert, for the selected ticket type*/
		totalTicketsLabel = new JLabel("Óýíïëï ÈÝóåùí: "+connector.availableSeatsSelect ("total_expensive_tickets", concertBox.getSelectedItem().toString()));
		totalTicketsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		totalTicketsLabel.setBounds(140, 130, 200, 20);
		frame.getContentPane().add(totalTicketsLabel);
		

		/**Gives availableTicketsLabel the number of the available tickets for the concert, for the selected ticket type*/
		availableTicketsLabel = new JLabel("ÄéáèÝóéìåò ÈÝóåéò: "+connector.availableSeatsSelect ("available_expensive_tickets", concertBox.getSelectedItem().toString()));
		availableTicketsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		availableTicketsLabel.setBounds(350, 130, 200, 20);
		frame.getContentPane().add(availableTicketsLabel);
		
		/**Gives ticketsCostLabel the price of the ticket for the concert and selected ticket type*/
		ticketsCostLabel = new JLabel("Êüóôïò åéóéôçñßùí: "+ connector.availableSeatsSelect ("expensive_ticket_price", concertBox.getSelectedItem().toString()) +" €");
		ticketsCostLabel.setHorizontalAlignment(SwingConstants.LEFT);
		ticketsCostLabel.setBounds(10, 130, 200, 20);
		frame.getContentPane().add(ticketsCostLabel);
		
		final JLabel newCostLabel = new JLabel("ÍÝï Êüóôïò:");
		newCostLabel.setHorizontalAlignment(SwingConstants.LEFT);
		newCostLabel.setBounds(22, 186, 70, 20);
		frame.getContentPane().add(newCostLabel);
		
		/**The EditorPane where the admin can change the ticket cost*/
		final JEditorPane newCostPane = new JEditorPane();
		newCostPane.setBounds(102, 186, 50, 20);
		frame.getContentPane().add(newCostPane);
		
		/**The EditorPane where the admin can change the number of available tickets*/
		final JEditorPane newSeatsPane = new JEditorPane();
		newSeatsPane.setBounds(543, 186, 50, 20);
		frame.getContentPane().add(newSeatsPane);
		
		final JLabel newSeats = new JLabel("ÁëëáãÞ áñéèìïý èÝóåùí :");
		newSeats.setBounds(162, 186, 150, 20);
		frame.getContentPane().add(newSeats);
		
		/**The EditorPane where the admin can change the number of total tickets*/
		final JEditorPane newTotalSeatsPane = new JEditorPane();
		newTotalSeatsPane.setBounds(308, 186, 50, 20);
		frame.getContentPane().add(newTotalSeatsPane);
		
		/**The button that saves the changes*/
		final JButton changeBtn = new JButton("ÁëëáãÞ");
		changeBtn.setBounds(10, 230, 240, 30);
		frame.getContentPane().add(changeBtn);
		
		/**The button that deletes a concert from the DB*/
		final JButton deleteBtn = new JButton("ÄéáãñáöÞ");
		deleteBtn.setBounds(260, 230, 100, 30);
		frame.getContentPane().add(deleteBtn);
		
		final JLabel newConcertLabel = new JLabel("¼íïìá Óõíáõëßáò:");
		newConcertLabel.setHorizontalAlignment(SwingConstants.LEFT);
		newConcertLabel.setBounds(10, 320, 120, 20);
		frame.getContentPane().add(newConcertLabel);
		
		/**The EditorPane on which the admin adds a concert title*/
		final JEditorPane newConcertPane = new JEditorPane();
		newConcertPane.setBounds(140, 320, 220, 20);
		frame.getContentPane().add(newConcertPane);
		
		final JLabel aZoneSeatsLabel = new JLabel("Á Æþíç:   ÈÝóåéò:");
		aZoneSeatsLabel.setHorizontalAlignment(SwingConstants.LEFT);
		aZoneSeatsLabel.setBounds(10, 370, 120, 20);
		frame.getContentPane().add(aZoneSeatsLabel);
		
		
		/**The EditorPane on which the admin adds the total type A tickets of a  concert*/
		final JEditorPane aZoneSeatsPane = new JEditorPane();
		aZoneSeatsPane.setBounds(140, 370, 50, 20);
		frame.getContentPane().add(aZoneSeatsPane);
		
		final JLabel aZoneCostLabel = new JLabel("Êüóôïò:");
		aZoneCostLabel.setHorizontalAlignment(SwingConstants.LEFT);
		aZoneCostLabel.setBounds(200, 370, 70, 20);
		frame.getContentPane().add(aZoneCostLabel);
		
		/**The EditorPane on which the admin adds the cost of type A tickets of a  concert*/
		final JEditorPane aZoneCostPane = new JEditorPane();
		aZoneCostPane.setBounds(280, 370, 50, 20);
		frame.getContentPane().add(aZoneCostPane);
		
		final JLabel bZoneSeatsLabel = new JLabel("Â Æþíç:   ÈÝóåéò:");
		bZoneSeatsLabel.setHorizontalAlignment(SwingConstants.LEFT);
		bZoneSeatsLabel.setBounds(10, 420, 120, 20);
		frame.getContentPane().add(bZoneSeatsLabel);
		
		/**The EditorPane on which the admin adds the total type B tickets of a  concert*/
		final JEditorPane bZoneSeatsPane = new JEditorPane();
		bZoneSeatsPane.setBounds(140, 420, 50, 20);
		frame.getContentPane().add(bZoneSeatsPane);
		
		final JLabel bZoneCostLabel = new JLabel("Êüóôïò:");
		bZoneCostLabel.setHorizontalAlignment(SwingConstants.LEFT);
		bZoneCostLabel.setBounds(200, 420, 70, 20);
		frame.getContentPane().add(bZoneCostLabel);
		
		/**The EditorPane on which the admin adds the cost of type B tickets of a  concert*/
		final JEditorPane bZoneCostPane = new JEditorPane();
		bZoneCostPane.setBounds(280, 420, 50, 20);
		frame.getContentPane().add(bZoneCostPane);
		
		final JLabel cZoneSeatsLabel = new JLabel("Ã Æþíç:   ÈÝóåéò:");
		cZoneSeatsLabel.setHorizontalAlignment(SwingConstants.LEFT);
		cZoneSeatsLabel.setBounds(10, 470, 120, 20);
		frame.getContentPane().add(cZoneSeatsLabel);
		
		/**The EditorPane on which the admin adds the total type C tickets of a  concert*/
		final JEditorPane cZoneSeatsPane = new JEditorPane();
		cZoneSeatsPane.setBounds(140, 470, 50, 20);
		frame.getContentPane().add(cZoneSeatsPane);
		
		final JLabel cZoneCostLabel = new JLabel("Êüóôïò:");
		cZoneCostLabel.setHorizontalAlignment(SwingConstants.LEFT);
		cZoneCostLabel.setBounds(200, 470, 70, 20);
		frame.getContentPane().add(cZoneCostLabel);
		
		/**The EditorPane on which the admin adds the cost of type C tickets of a  concert*/
		final JEditorPane cZoneCostPane = new JEditorPane();
		cZoneCostPane.setBounds(280, 470, 50, 20);
		frame.getContentPane().add(cZoneCostPane);
		
		/**The button to insert the new concert to the db*/
		final JButton newBtn = new JButton("ÅéóáãùãÞ");
		newBtn.setBounds(10, 530, 240, 30);
		frame.getContentPane().add(newBtn);
		
		/**The button that takes us to the User gui*/
		final JButton btnUser = new JButton("\u03A7\u03C1\u03AE\u03C3\u03C4\u03B7\u03C2");
		btnUser.setBounds(10, 11, 89, 23);
		frame.getContentPane().add(btnUser);
		
		final JLabel label = new JLabel("\u03A0\u03C1\u03BF\u03C3\u03B8\u03AE\u03BA\u03B7 \u039D\u03B5\u03B1\u03C2 \u03A3\u03C5\u03BD\u03B1\u03C5\u03BB\u03AF\u03B1\u03C2");
		label.setFont(new Font("Tahoma", Font.PLAIN, 17));
		label.setBounds(10, 278, 260, 21);
		frame.getContentPane().add(label);
		
		final JLabel label1 = new JLabel("\u0395\u03C0\u03B5\u03BE\u03B5\u03C1\u03B3\u03B1\u03C3\u03AF\u03B1 \u03A5\u03C0\u03AC\u03C1\u03C7\u03BF\u03C5\u03C3\u03B1\u03C2 \u03A3\u03C5\u03BD\u03B1\u03C5\u03BB\u03AF\u03B1\u03C2");
		label1.setFont(new Font("Tahoma", Font.PLAIN, 17));
		label1.setBounds(10, 45, 379, 21);
		frame.getContentPane().add(label1);
		
		final JLabel label2 = new JLabel("\u03A3\u03C4\u03B1\u03C4\u03B9\u03C3\u03C4\u03B9\u03BA\u03AC");
		label2.setFont(new Font("Tahoma", Font.PLAIN, 17));
		label2.setBounds(10, 583, 260, 21);
		frame.getContentPane().add(label2);
		
		/**the button that takes us to the stats gui*/
		final JButton btnShowStats = new JButton("\u0395\u03BC\u03C6\u03AC\u03BD\u03B9\u03C3\u03B7 \u03A3\u03C4\u03B1\u03C4\u03B9\u03C3\u03C4\u03B9\u03BA\u03CE\u03BD");
		btnShowStats.setBounds(8, 624, 240, 30);
		frame.getContentPane().add(btnShowStats);
		
		final JLabel label4 = new JLabel("\u0391\u03BB\u03BB\u03B1\u03B3\u03AE \u03B4\u03B9\u03B1\u03B8\u03AD\u03C3\u03B9\u03BC\u03C9\u03BD \u03B8\u03AD\u03C3\u03B5\u03C9\u03BD :");
		label4.setBounds(370, 186, 180, 20);
		frame.getContentPane().add(label4);
		
		
		/**
		 * Action Listeners.
		 */
		
		
		/**If the user tries to close the window, closes the db connector first	 */
		frame.addWindowListener(new WindowAdapter() 
		{
			@Override
			public void windowClosing(WindowEvent arg0) 
			{
				connector.stopConn();
			}
		});
		
		
		/**If we press the button btnShowStats, closes the frame and opens a StatsGui frame*/
		btnShowStats.addActionListener(
		new ActionListener() 
		{
			public void actionPerformed(final ActionEvent buttonPressed) 
			{
				final StatsGui window = new StatsGui();
				window.frame.setVisible(true);
				frame.dispose();
			}
		});
		
		
		/**When we select a different concert from the combobox, refreshes the labels as necessary*/
		concertBox.addActionListener(
		new ActionListener()
        {
			public void actionPerformed(final ActionEvent itemSelected)
            {
	            refreshLabels();
            }
        });
		
		
		/**When we select a different ticket type from the combobox, refreshes the labels as necessary*/
		seatBox.addActionListener(
        new ActionListener()
        {
        	public void actionPerformed(final ActionEvent itemSelected)
            {
        		refreshLabels();
            }
        });
		
		
		/**When we press the changeBtn button, updates the DB with the update method of the connector*/
		changeBtn.addActionListener(
		new ActionListener() 
		{
			public void actionPerformed(final ActionEvent buttonPressed) 
			{
				/** Checks if a required field is empty, and show warning if it is */
				if(newSeatsPane.getText().isEmpty()||newTotalSeatsPane.getText().isEmpty()||newCostPane.getText().isEmpty())
				{
					JOptionPane.showMessageDialog(frame, "ÊÜðïéï Field Åßíáé Áäåéï");
				}
				
				/** Checks if the admin put a String instead of a number and show warning if he did */
				else if(!(StringCheck.isNumeric(newSeatsPane.getText()))||!(StringCheck.isNumeric(newTotalSeatsPane.getText())) ||!(StringCheck.isNumeric(newCostPane.getText())))
				{
					JOptionPane.showMessageDialog(frame, "ÊÜðïéï Field Å÷åé Êåßìåíï Áíôß Ãéá Áñéèìü");
				}
				else
				{
				/**Updates the tickets of the appropriate type on the DB*/
		           	if ( seatBox.getSelectedIndex() == A_SEIRA )
		           	{
		           		connector.update(concertBox.getSelectedItem().toString(), "available_expensive_tickets", newSeatsPane.getText(),"total_expensive_tickets",newTotalSeatsPane.getText(), "expensive_ticket_price", newCostPane.getText());
		           	} 
		           	else if ( seatBox.getSelectedIndex() == B_SEIRA )
		           	{
		           		connector.update(concertBox.getSelectedItem().toString(), "available_normal_tickets", newSeatsPane.getText(),"total_normal_tickets",newTotalSeatsPane.getText(), "normal_ticket_price", newCostPane.getText());
		           	} 
		           	else 
		           	{
		           		connector.update(concertBox.getSelectedItem().toString(), "available_cheap_tickets", newSeatsPane.getText(),"total_normal_tickets",newTotalSeatsPane.getText(), "cheap_ticket_price", newCostPane.getText());
		           	}
		           	
					refreshLabels();
				}
	        }
		});
	
		/**If we press the deleteBtn Button, delete the selected concert from the DB with the delete function of the connector*/
		deleteBtn.addActionListener(
		new ActionListener() 
		{
			public void actionPerformed(final ActionEvent buttonPressed) 
			{
				connector.delete(concertBox.getSelectedItem().toString());
				/**dinoume tous titlous ton sinavlion apo tin vasi dedomenon 
				 * sto concertBox me to function titleSelect() tou Database_connector*/
				concertBox.setModel(connector.titleSelect());
				seatBox.setSelectedIndex(0);
			}
		});
		
		/**If we press the newBtn button, we create a new concert on the DB with the values of the panes*/
		newBtn.addActionListener(new ActionListener() 
		{
			public void actionPerformed(final ActionEvent buttonPressed)
			{
				
				/** Checks if a required field is empty, and show warning if it is */
				if(newConcertPane.getText().isEmpty()||aZoneSeatsPane.getText().isEmpty()||aZoneCostPane.getText().isEmpty()
						||bZoneSeatsPane.getText().isEmpty()||bZoneCostPane.getText().isEmpty()
						||cZoneSeatsPane.getText().isEmpty()||cZoneCostPane.getText().isEmpty())
				{
					JOptionPane.showMessageDialog(frame, "ÊÜðïéï Field Åßíáé Áäåéï");
				}
				
				/** Checks if the admin put a String instead of a number and show warning if he did */
				else if (!StringCheck.isNumeric(aZoneSeatsPane.getText())||!StringCheck.isNumeric(aZoneCostPane.getText())
						||!StringCheck.isNumeric(bZoneSeatsPane.getText())||!StringCheck.isNumeric(bZoneCostPane.getText())
						||!StringCheck.isNumeric(cZoneSeatsPane.getText())||!StringCheck.isNumeric(cZoneCostPane.getText()))
				{
					JOptionPane.showMessageDialog(frame, "ÊÜðïéï Field Å÷åé Êåßìåíï Áíôß Ãéá Áñéèìü");
				}
				else
				{
					connector.create(newConcertPane.getText(), aZoneSeatsPane.getText(),aZoneCostPane.getText() , bZoneSeatsPane.getText(),bZoneCostPane.getText() , cZoneCostPane.getText(), cZoneSeatsPane.getText());
					/**We refresh the concertBox to include the new concert*/
					concertBox.setModel(connector.titleSelect());
					seatBox.setSelectedIndex(0);
				}
			}
		});
		
		/**If we click the btnUser button, closes this frame and opens a user frame*/
		btnUser.addActionListener(new ActionListener() 
		{
			public void actionPerformed(final ActionEvent buttonPressed) 
			{
				final UserGui window = new UserGui();
				window.frame.setVisible(true);
				frame.dispose();
			}
		});
		
	}
	
	/**Refreshes the labels
	 * We call it whenever there is a change in the DB*/
	public void refreshLabels()
	{
		if ( seatBox.getSelectedIndex() == A_SEIRA )
        {
			totalTicketsLabel.setText("Óýíïëï ÈÝóåùí: "+connector.availableSeatsSelect ("total_expensive_tickets", concertBox.getSelectedItem().toString()));
            availableTicketsLabel.setText("ÄéáèÝóéìåò ÈÝóåéò: "+connector.availableSeatsSelect ("available_expensive_tickets", concertBox.getSelectedItem().toString()));
            ticketsCostLabel.setText("Êüóôïò åéóéôçñßùí: "+ Integer.parseInt(connector.availableSeatsSelect ("expensive_ticket_price", concertBox.getSelectedItem().toString())));
        } 
        else if ( seatBox.getSelectedIndex() == B_SEIRA )
        {
        	totalTicketsLabel.setText("Óýíïëï ÈÝóåùí: "+connector.availableSeatsSelect ("total_normal_tickets", concertBox.getSelectedItem().toString()));
            availableTicketsLabel.setText("ÄéáèÝóéìåò ÈÝóåéò: "+connector.availableSeatsSelect ("available_normal_tickets", concertBox.getSelectedItem().toString()));
            ticketsCostLabel.setText("Êüóôïò åéóéôçñßùí: "+ Integer.parseInt(connector.availableSeatsSelect ("normal_ticket_price", concertBox.getSelectedItem().toString())));
        } 
        else 
        {
        	totalTicketsLabel.setText("Óýíïëï ÈÝóåùí: "+connector.availableSeatsSelect ("total_cheap_tickets", concertBox.getSelectedItem().toString()));
            availableTicketsLabel.setText("ÄéáèÝóéìåò ÈÝóåéò: "+connector.availableSeatsSelect ("available_cheap_tickets", concertBox.getSelectedItem().toString()));
            ticketsCostLabel.setText("Êüóôïò åéóéôçñßùí: "+ Integer.parseInt(connector.availableSeatsSelect ("cheap_ticket_price", concertBox.getSelectedItem().toString())));
        }
	}
}
