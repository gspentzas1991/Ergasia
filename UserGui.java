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

/** I klasi i opia dimiourgei to frame tou
 * parathirou gia ton xristi*/
public class UserGui 
{

	/**defines*/
	
	/** dixnei oti ta eisitiria pou epileksame
	 * eine A seiras */
	private static final int A_SEIRA= 0;
	
	/** dixnei oti ta eisitiria pou epileksame
	 * eine B seiras */
	private static final int B_SEIRA= 1;
	
	
	
	/** variables */
	
	/**to frame tou programmatos*/
	public transient JFrame frame;
	
	/**i sindesi me tin vasi dedomenon*/
	private transient Database_connector connector;
	
	/**to comboBox to opio exei mesa tou tis sinavlies tis vasis dedomenon*/
	private transient  JComboBox<String> concertBox;
	
	/**to comboBox to opio exei mesa tou tis seires gia tis sinavlies*/
	private transient  JComboBox<String> seatBox;
	
	/**to label pou leei poses diathesimes theseis exoun apominei*/
	private transient JLabel availableTicketsLabel;
	
	/**to editorPane sto opio boroume na valoume ton arithmo 
	 * ton eisitirion pou theloume na agorasoume*/
	private transient JEditorPane ticketsPane;
	
	/**to label to opio mas leei poso tha eine to teliko kostos ton eisitirion*/
	private transient JLabel ticketsCostLabel;

	/**ena int pou tha apothikevei ton arithmo ton diathesimon
	 *  isitirion tis sinavlias/seiras pou exei epilextei*/
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
		
		frame.setTitle("Σύστημα έκδοσης εισιτηρίων για συναυλίες");
		frame.setResizable(false);
		frame.setAlwaysOnTop(true);
		frame.setBounds(0, 0, 500, 300);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
				
		/**to koubi pou se pigenei sto parathiro tou diaxiristi*/
		final JButton adminBtn = new JButton("Διαχειριστής");
		adminBtn.setBounds(10, 10, 150, 30);
		frame.getContentPane().add(adminBtn);
		
		final JLabel concertLabel = new JLabel("Επιλογή Συναυλίας:");
		concertLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		concertLabel.setBounds(10, 50, 150, 20);
		frame.getContentPane().add(concertLabel);

		/**sindeete me tin vasi dedomenon*/
		connector= new Database_connector();
		connector.startConn();
		
		
		final JLabel seatLabel = new JLabel("Επιλογή Θέσης:");
		seatLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		seatLabel.setBounds(10, 100, 150, 20);
		frame.getContentPane().add(seatLabel);
		
		
		/**to combobox me to opio dialegeis sinavlia
		*dinoume tous titlous ton sinavlion apo tin vasi dedomenon 
		*sto concertBox me to function title_select() tou Database_connector*/
		concertBox = new JComboBox<>(connector.titleSelect());
		concertBox.setBounds(170, 50, 320, 20);
		frame.getContentPane().add(concertBox);
		
		/**to combobox me to opio dialegeis tin zoni eisitirion*/
		seatBox = new JComboBox<>();
		seatBox.setMaximumRowCount(20);
		seatBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Α Ζώνη", "Β Ζώνη", "Γ Ζώνη"}));
		seatBox.setBounds(170, 100, 110, 20);
		frame.getContentPane().add(seatBox);
		
		/**to label to opio dixnei poses eleftheres theseis 
		 * iparxoun gia tin sigekrimeni sinavlia-zoni*/
		availableTicketsLabel = new JLabel("Διαθέσιμες Θέσεις: "+connector.availableSeatsSelect ("available_expensive_tickets", concertBox.getSelectedItem().toString()));
		availableTicketsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		availableTicketsLabel.setBounds(290, 100, 200, 20);
		frame.getContentPane().add(availableTicketsLabel);
		
		/**arxikopoioume tin metavliti availableTickets*/
		availableTickets=Integer.parseInt(connector.availableSeatsSelect ("available_expensive_tickets", concertBox.getSelectedItem().toString()));
		
		final JLabel ticketsLabel = new JLabel("Αριθμός Εισιτηρίων:");
		ticketsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		ticketsLabel.setBounds(10, 150, 150, 20);
		frame.getContentPane().add(ticketsLabel);
		
		/**to editor pane sto opio vazoume ton arithmo ton 
		 * eisitirion pou theloume na agorasoume*/
		ticketsPane = new JEditorPane();
		ticketsPane.setText("0");
		ticketsPane.setBounds(170, 150, 50, 20);
		frame.getContentPane().add(ticketsPane);
		
		
		/**polaplasiazoume tin timi tou enos 
		 * eisitiriou (to pernoume apo tin vasi dedomenon), me ton 
		 * arithmo eisitirion pou theloume na  tiposoume
		 * (to pernoume apo to editor pane "ticketsPane")*/
		final int ticketCost = Integer.parseInt(connector.availableSeatsSelect ("expensive_ticket_price", concertBox.getSelectedItem().toString())) * Integer.parseInt(ticketsPane.getText());
		
		/**to label to opio tiponei to oliko kostos ton eisitirion*/
		ticketsCostLabel = new JLabel("Κόστος εισιτηρίων: "+ ticketCost +" €");
		ticketsCostLabel.setHorizontalAlignment(SwingConstants.CENTER);
		ticketsCostLabel.setBounds(290, 150, 200, 20);
		frame.getContentPane().add(ticketsCostLabel);
		
		/**to koubi gia tin agora ton eisitirion*/
		final JButton purchaseBtn = new JButton("Αγορά εισιτηρίων");
		purchaseBtn.setBounds(10, 200, 240, 30);
		frame.getContentPane().add(purchaseBtn);
		

		/**
		 * Action Listeners.
		 */
		
		/**an paei o xristeis na klisei to parathiro
		 * prin klisei to frame, klinei ton connector
		 */
		frame.addWindowListener(new WindowAdapter() 
		{
			@Override
			public void windowClosing(WindowEvent arg0) 
			{

				 connector.stopConn();
			}
		});
		
		
		/**ananeonoume ta labels kathe fora pou o xristis dialegei alli sinavlia*/
		concertBox.addActionListener(
        new ActionListener()
        {
        	/**to action ginete otan dialegoume kapio item apo to combobox*/
        	public void actionPerformed(final ActionEvent itemSelected)
        	{
        		refreshLabels(true);
            }
         });
		
		
		/**ananeonoume ta labels kathe fora pou o xristis dialegei alli seira*/
		seatBox.addActionListener(
        new ActionListener()
        {
        	/**to action ginete otan dialegoume kapio item apo to combobox*/
        	public void actionPerformed(final ActionEvent itemSelected)
        	{
        		refreshLabels(true);
        	}
        } );
		
		/**kathe fora pou o xristis allazei ton arithmo sto ticketsPane
		 *  ananeonoume ta label gia na pernoun tin sosti timi*/
		ticketsPane.getDocument().addDocumentListener(new DocumentListener() 
		{
			  /**to action ginete otan alazoume enan airthmo tou pane*/
			  public void changedUpdate(final DocumentEvent change) 
			  {
				  updatePrice();
			  }
			  /**to action ginete otan svinoume enan arithmo apo to pane*/
			  public void removeUpdate(final DocumentEvent removal) 
			  {
				  updatePrice();
			  }
			  /**to action ginete otan vazoume enan arithmo sto pane*/
			  public void insertUpdate(final DocumentEvent insert) 
			  {
				  updatePrice();
			  }
			  
			  /**ananeonei ta labels me to sosto kostos eisitirion*/
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
		
		/**ti ginete otan patame to purchaseBtn*/
		purchaseBtn.addActionListener(new ActionListener() 
		{
			/**to action eine to patima tou koubiou*/
			public void actionPerformed(final ActionEvent buttonPress) 
			{
				/**elenxoume an o arithmos isitirion pou theloume eine 
				 * megaliteros apo ton arithmo diathesimon eisitirion gia afti tin sinavlia-seira
				 *An theloume ligotera eisitiria apo osa iparxoun petame 
				 *ena minima oti ta eisitiria ektipononte, ke alazoume ton arithmo 
				 *ton diathesimon eisitirion apo tin vasi dedomenon
				 *me to function updateAvailableSeats tou Database_connector*/
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
					/** an o xristeis zitaei pano apo 0 eisitiria
					 * tote petame minima oti ektipononte*/
					if(Integer.parseInt(ticketsPane.getText())>0)
					{
						JOptionPane.showMessageDialog(frame, "Printing...");
					}
					refreshLabels(true);
				}
				/**an theloume perisotera eisitiria apo osa iparxoun, peta ena error message*/
				else
				{
					JOptionPane.showMessageDialog(frame, "Not Enough Tickets Available");
				}
			}
		});
		
		
		/**otan patame to adminBtn, tha dimiourgite 
		 * ena AdminGui parathiro, ke tha katastrefete 
		 * afto to parathiro*/
		adminBtn.addActionListener(new ActionListener() 
		{
			/**to action eine to patima tou koubiou*/
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
	
	/**ananeonei ta label otan dialegoume alli sinavlia i seira
	*an to kalesoume me false, ananeonei mono to ticketsCostLabel
	*an to kalesoume me true, ananeonei ke to availableTicketsLabel*/
	public void refreshLabels(final boolean refreshAll)
	{
		/**an to seatbox exei epilegmeni tin A seira, tote ta 
		 * labels pernoun times gia ta akriva eisitiria*/
    	if ( seatBox.getSelectedIndex() ==A_SEIRA)
    	{
    		if(refreshAll)
    		{
    			/**apothikevoume to arithmo ton diathesimon theseon stin metavliti
    			 * availableTickets ke meta ton vazoume sto availableTicketsLabel*/
	    		availableTickets=Integer.parseInt(connector.availableSeatsSelect ("available_expensive_tickets", concertBox.getSelectedItem().toString()));
	    		availableTicketsLabel.setText("Διαθέσιμες Θέσεις: "+availableTickets);
    		}
    		
    		/**an o xristis den exei kamia timi sto ticketsPane
    		 * tote den kanoume kanenan ipologismo gia to kostos ton isitirion*/
    		if(ticketsPane.getText().equals(""))
	    	{
	    		ticketsCostLabel.setText("Κόστος εισιτηρίων: ");
	    	}
    		/**an o xristis exei valei timi sto ticketsPane
    		 *  tote polaplasiazoume afti tin timi me to kostos 
    		 * enos eisitiriou tis epilegmenis sinavlias-seiras
    		 *  ke vriskoume to sinoliko kostos*/
	    	else 
	    	{
	    		ticketsCostLabel.setText("Κόστος εισιτηρίων: "+ Integer.parseInt(connector.availableSeatsSelect ("expensive_ticket_price", concertBox.getSelectedItem().toString())) * Integer.parseInt(ticketsPane.getText()) +" €");
	    	}
    	}
    	/**an to seatbox exei epilegmeni tin B seira
    	 *  tote ta labels pernoun times gia ta kanonika eisitiria*/
    	else if ( seatBox.getSelectedIndex() == B_SEIRA)
    	{
    		if(refreshAll)
    		{
	    		availableTickets=Integer.parseInt(connector.availableSeatsSelect ("available_normal_tickets", concertBox.getSelectedItem().toString()));
	        	availableTicketsLabel.setText("Διαθέσιμες Θέσεις: "+availableTickets);
    		}
	        if(ticketsPane.getText().equals(""))
	       	{
	        	ticketsCostLabel.setText("Κόστος εισιτηρίων: ");
	       	}
	       	else 
	       	{
	       		ticketsCostLabel.setText("Κόστος εισιτηρίων: "+ Integer.parseInt(connector.availableSeatsSelect ("normal_ticket_price", concertBox.getSelectedItem().toString())) * Integer.parseInt(ticketsPane.getText()) +" €");
	       	}	
        } 
    	else 
    	{                    	
    		/**an to seatbox exei epilegmeni tin Γ seira
    		 *  tote ta labels pernoun times gia ta fthina eisitiria*/
    		if(refreshAll)
    		{
	   			availableTickets=Integer.parseInt(connector.availableSeatsSelect ("available_cheap_tickets", concertBox.getSelectedItem().toString()));
	       		availableTicketsLabel.setText("Διαθέσιμες Θέσεις: "+availableTickets);
    		}
	       	if(ticketsPane.getText().equals(""))
	       	{
	       		ticketsCostLabel.setText("Κόστος εισιτηρίων: ");
	       	}
	       	else
	       	{
	       		ticketsCostLabel.setText("Κόστος εισιτηρίων: "+ Integer.parseInt(connector.availableSeatsSelect ("cheap_ticket_price", concertBox.getSelectedItem().toString())) * Integer.parseInt(ticketsPane.getText()) +" €");	
        
	       	}
    	}
	}
}
