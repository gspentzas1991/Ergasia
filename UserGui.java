package seat_administration;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JEditorPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

/*Creates the User frame*/
public class UserGui 
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
	
	/**Stores all the concerts*/
	private transient  JComboBox<String> concertBox;
	
	/**Stores the ticket types for the concert*/
	private transient  JComboBox<String> seatBox;
	
	/**Label to show the number of available tickets for the concert*/
	private transient JLabel availableTicketsLabel;
	
	/**The editorPane where we input how many tickets we want to purchase*/
	private transient JEditorPane ticketsPane;
	
	/**Label that shows the final cost of the tickets*/
	private transient JLabel ticketsCostLabel;

	/**Has the available tickets for the type of tickets of the cohncert we selected*/
	private transient int availableTickets;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(final String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					final UserGui window = new UserGui();
					window.frame.setVisible(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UserGui() 
	{
		initialize();
	}
	
	
	/**
	 * Initialize the contents of the frame.
	 */
	
	private void initialize()
	{
		frame = new JFrame();
		
		frame.setTitle("Óýóôçìá Ýêäïóçò åéóéôçñßùí ãéá óõíáõëßåò");
		frame.setResizable(false);
		frame.setAlwaysOnTop(true);
		frame.setBounds(0, 0, 500, 300);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
				
		/**The button that takes you to the admin frame*/
		final JButton adminBtn = new JButton("Äéá÷åéñéóôÞò");
		adminBtn.setBounds(10, 10, 150, 30);
		frame.getContentPane().add(adminBtn);
		
		final JLabel concertLabel = new JLabel("ÅðéëïãÞ Óõíáõëßáò:");
		concertLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		concertLabel.setBounds(10, 50, 150, 20);
		frame.getContentPane().add(concertLabel);

		/**Connects to the DB*/
		connector= new Database_connector();
		connector.startConn();
		
		
		final JLabel seatLabel = new JLabel("ÅðéëïãÞ ÈÝóçò:");
		seatLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		seatLabel.setBounds(10, 100, 150, 20);
		frame.getContentPane().add(seatLabel);
		
		
		/**ComboBox to select the concert we want to buy tickets for
		*Gets the concert titles from the DB through the title_select() method of the connector*/
		concertBox = new JComboBox<>(connector.titleSelect());
		concertBox.setBounds(170, 50, 320, 20);
		frame.getContentPane().add(concertBox);
		
		/**ComboBox to sellect the type of tickets*/
		seatBox = new JComboBox<>();
		seatBox.setMaximumRowCount(20);
		seatBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Á Æþíç", "Â Æþíç", "Ã Æþíç"}));
		seatBox.setBounds(170, 100, 110, 20);
		frame.getContentPane().add(seatBox);
		
		/**Label that shows how many available tickets there are for the selected concert and ticket type*/
		availableTicketsLabel = new JLabel("ÄéáèÝóéìåò ÈÝóåéò: "+connector.availableSeatsSelect ("available_expensive_tickets", concertBox.getSelectedItem().toString()));
		availableTicketsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		availableTicketsLabel.setBounds(290, 100, 200, 20);
		frame.getContentPane().add(availableTicketsLabel);
		
		/**initialization of availableTickets*/
		availableTickets=Integer.parseInt(connector.availableSeatsSelect ("available_expensive_tickets", concertBox.getSelectedItem().toString()));
		
		final JLabel ticketsLabel = new JLabel("Áñéèìüò Åéóéôçñßùí:");
		ticketsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		ticketsLabel.setBounds(10, 150, 150, 20);
		frame.getContentPane().add(ticketsLabel);
		
		/**EditorPane where we input the number of tickets we want to purchase*/
		ticketsPane = new JEditorPane();
		ticketsPane.setText("0");
		ticketsPane.setBounds(170, 150, 50, 20);
		frame.getContentPane().add(ticketsPane);
		
		
		/**calculates the total cost of the tickets*/
		final int ticketCost = Integer.parseInt(connector.availableSeatsSelect ("expensive_ticket_price", concertBox.getSelectedItem().toString())) * Integer.parseInt(ticketsPane.getText());
		
		/**Label that shows the total cost of the tickets*/
		ticketsCostLabel = new JLabel("Êüóôïò åéóéôçñßùí: "+ ticketCost +" €");
		ticketsCostLabel.setHorizontalAlignment(SwingConstants.CENTER);
		ticketsCostLabel.setBounds(290, 150, 200, 20);
		frame.getContentPane().add(ticketsCostLabel);
		
		/**The button to purchase the tickets*/
		final JButton purchaseBtn = new JButton("ÁãïñÜ åéóéôçñßùí");
		purchaseBtn.setBounds(10, 200, 240, 30);
		frame.getContentPane().add(purchaseBtn);
		

		/**
		 * Action Listeners.
		 */
		
		/**If we close the window, closes the DB connector
		 */
		frame.addWindowListener(new WindowAdapter() 
		{
			@Override
			public void windowClosing(WindowEvent arg0) 
			{

				 connector.stopConn();
			}
		});
		
		
		/**refreshes the labels when we select a different concert*/
		concertBox.addActionListener(
        new ActionListener()
        {
        	public void actionPerformed(final ActionEvent itemSelected)
        	{
        		refreshLabels(true);
            }
         });
		
		
		/**refreshes the labels when we select a different ticket type*/
		seatBox.addActionListener(
        new ActionListener()
        {
        	public void actionPerformed(final ActionEvent itemSelected)
        	{
        		refreshLabels(true);
        	}
        } );
		
		/**refreshes the labels when we input a different number of tickets to buy*/
		ticketsPane.getDocument().addDocumentListener(new DocumentListener() 
		{
			
			  /**Action for when we change the number of the Pane*/
			  public void changedUpdate(final DocumentEvent change) 
			  {
				  
						updatePrice();
			  }
			  /**Action for when we delete the number of the Pane*/
			  public void removeUpdate(final DocumentEvent removal) 
			  {
				  updatePrice();
			  }
			  /**Action for when we insert a number on the Pane*/
			  public void insertUpdate(final DocumentEvent insert) 
			  {
				  updatePrice();
			  }
			  
			  /**refreshes the labels with the ticket price*/
			  public void updatePrice() 
			  {
				  	  try 
					  {
						  refreshLabels(false);
					  }
				  	  catch(NumberFormatException ex) 
				  	  {
				  		  ex.printStackTrace();
				  	  }
			  }
				
		});
		
		/**purchaseBtn listener*/
		purchaseBtn.addActionListener(new ActionListener() 
		{
			public void actionPerformed(final ActionEvent buttonPress) 
			{
				if(ticketsPane.getText().isEmpty())
				{
					JOptionPane.showMessageDialog(frame, "Ôo Field Åßíáé Áäåéï");
				}
				else if (!StringCheck.isNumeric(ticketsPane.getText()))
				{
					JOptionPane.showMessageDialog(frame, "Ôo Field Å÷åé ÃñÜììáôá áíôß ãéá Áñéèìü");
				}
				else
				{
					/**Checks if the number of tickets we want to purchase is bigger than the number of available tickets
					 *If there are enough tickets, detracts the purchased tickets from the total tickets, updates the DB and prints a message*/
					if(Integer.parseInt(ticketsPane.getText())<=availableTickets)
					{
						if ( seatBox.getSelectedIndex() ==A_SEIRA)
						{
							connector.updateAvailableSeats("available_expensive_tickets", concertBox.getSelectedItem().toString(), ticketsPane.getText());
						} 
						else if ( seatBox.getSelectedIndex() == B_SEIRA)
	                  	{
							connector.updateAvailableSeats("available_normal_tickets", concertBox.getSelectedItem().toString(), ticketsPane.getText());
	                  	} 
	                  	else 
	                  	{
							connector.updateAvailableSeats("available_cheap_tickets", concertBox.getSelectedItem().toString(), ticketsPane.getText());
	                  	}
						/** Print the message that the tickets are purchased if there are enough tickets, otherwise print error message*/
						if(Integer.parseInt(ticketsPane.getText())>0)
						{
							JOptionPane.showMessageDialog(frame, "Ôá ÅéóéôÞñéá Åêôõðþíïíôáé");
						}
						refreshLabels(true);
					}
					else
					{
						JOptionPane.showMessageDialog(frame, "ÅðéëÝîôå Ìéêñüôåñï Áñéèìü Åéóéôçñßùí");
					}
				}
				
			}
		});
		
		
		/**When we click the adminBtn closes the frame and opens an AdminGui*/
		adminBtn.addActionListener(new ActionListener() 
		{
			public void actionPerformed(final ActionEvent buttonPress)
			{
				try
				{
					final AdminGui window = new AdminGui();
					window.frame.setVisible(true);
					
					frame.dispose();
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
		
	}
	
	/**refteshes the labels when we pick a different concert or ticket type
	*if we call the method with false, it refreshes the ticketsCostLabel
	*if we call the method with true, it refreshes both ticketsCostLabel and availableTicketsLabel*/
	public void refreshLabels(final boolean refreshAll)
	{
		/**if the seatBox has ticket type A selected gives labels the appropriate values*/
    	if ( seatBox.getSelectedIndex() ==A_SEIRA)
    	{
    		if(refreshAll)
    		{
	    		availableTickets=Integer.parseInt(connector.availableSeatsSelect ("available_expensive_tickets", concertBox.getSelectedItem().toString()));
	    		availableTicketsLabel.setText("ÄéáèÝóéìåò ÈÝóåéò: "+availableTickets);
    		}
    		
    		/** checks if we put characters or numbers on the Pane*/
    		if(ticketsPane.getText().isEmpty()||!StringCheck.isNumeric(ticketsPane.getText()))
	    	{
	    		ticketsCostLabel.setText("Êüóôïò åéóéôçñßùí: ");
	    	}
    		/**Calculates the total cost of the tickets*/
	    	else 
	    	{
	    		ticketsCostLabel.setText("Êüóôïò åéóéôçñßùí: "+ Integer.parseInt(connector.availableSeatsSelect ("expensive_ticket_price", concertBox.getSelectedItem().toString())) * Integer.parseInt(ticketsPane.getText()) +" €");
	    	}
    	}
    	/**if the seatBox has ticket type B selected gives labels the appropriate values*/
    	else if ( seatBox.getSelectedIndex() == B_SEIRA)
    	{
    		if(refreshAll)
    		{
	    		availableTickets=Integer.parseInt(connector.availableSeatsSelect ("available_normal_tickets", concertBox.getSelectedItem().toString()));
	        	availableTicketsLabel.setText("ÄéáèÝóéìåò ÈÝóåéò: "+availableTickets);
    		}
    		/** checks if we put characters or numbers on the Pane*/
	        if(ticketsPane.getText().isEmpty()||!StringCheck.isNumeric(ticketsPane.getText()))
	       	{
	        	ticketsCostLabel.setText("Êüóôïò åéóéôçñßùí: ");
	       	}
	       	else 
	       	{
	       		ticketsCostLabel.setText("Êüóôïò åéóéôçñßùí: "+ Integer.parseInt(connector.availableSeatsSelect ("normal_ticket_price", concertBox.getSelectedItem().toString())) * Integer.parseInt(ticketsPane.getText()) +" €");
	       	}	
        } 
    	else 
    	{                    	
    		/**if the seatBox has ticket type C selected gives labels the appropriate values*/
    		if(refreshAll)
    		{
	   			availableTickets=Integer.parseInt(connector.availableSeatsSelect ("available_cheap_tickets", concertBox.getSelectedItem().toString()));
	       		availableTicketsLabel.setText("ÄéáèÝóéìåò ÈÝóåéò: "+availableTickets);
    		}
    		/** checks if we put characters or numbers on the Pane*/
    		if(ticketsPane.getText().isEmpty()||!StringCheck.isNumeric(ticketsPane.getText()))
	       	{
	       		ticketsCostLabel.setText("Êüóôïò åéóéôçñßùí: ");
	       	}
	       	else
	       	{
	       		ticketsCostLabel.setText("Êüóôïò åéóéôçñßùí: "+ Integer.parseInt(connector.availableSeatsSelect ("cheap_ticket_price", concertBox.getSelectedItem().toString())) * Integer.parseInt(ticketsPane.getText()) +" €");	
        
	       	}
    	}
	}
}
