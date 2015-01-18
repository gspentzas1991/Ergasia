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

/** I klasi i opia dimiourgei to frame tou
 * parathirou gia ton diaxiristi*/
public class AdminGui 
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
	
	/**to comboBox to opio exei mesa tou tis seires gia tis sinavlies*/
	private transient JComboBox<String> seatBox;
	
	/**to label pou leei poses sinolikes theseis iparxoun*/
	private transient JLabel totalTicketsLabel;
	
	/**to comboBox to opio exei mesa tou tis sinavlies tis vasis dedomenon*/
	private transient JComboBox<String> concertBox;
	
	/**to label to opio mas leei poso tha eine to teliko kostos ton eisitirion*/
	private transient JLabel ticketsCostLabel;
	
	/**to label pou leei poses diathesimes theseis exoun apominei*/
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
		frame.setTitle("Σύστημα Διαχειριστή εισιτηρίων για συναυλίες");
		frame.setResizable(false);
		frame.setAlwaysOnTop(true);
		frame.setBounds(0, 0, 621, 706);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);
		
		
		final JLabel concertLabel = new JLabel("Επιλογή Συναυλίας:");
		concertLabel.setHorizontalAlignment(SwingConstants.LEFT);
		concertLabel.setBounds(10, 80, 120, 20);
		frame.getContentPane().add(concertLabel);

		/**sindeomaste me tin vasi dedomenon*/
		connector= new Database_connector();
		connector.startConn();
		
		/**to comboBox pou periexei tis sinavlies
		*dinoume tous titlous ton sinavlion apo tin vasi dedomenon 
		*sto concertBox me to function titleSelect() tou Database_connector*/
		concertBox = new JComboBox<>(connector.titleSelect());
		concertBox.setBounds(140, 80, 220, 20);
		frame.getContentPane().add(concertBox);
		
		/**to comboBox pou periexei tis zones eisitirion*/
		seatBox = new JComboBox<>();
		seatBox.setMaximumRowCount(20);
		seatBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Α Ζώνη", "Β Ζώνη", "Γ Ζώνη"}));
		seatBox.setBounds(370, 80, 70, 20);
		frame.getContentPane().add(seatBox);
		
		/**to label pou leei poses theseis iparxoun 
		 * sinolika stin sigekrimeni sinavlia-zoni*/
		totalTicketsLabel = new JLabel("Σύνολο Θέσεων: "+connector.availableSeatsSelect ("total_expensive_tickets", concertBox.getSelectedItem().toString()));
		totalTicketsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		totalTicketsLabel.setBounds(140, 130, 200, 20);
		frame.getContentPane().add(totalTicketsLabel);
		

		/**to label pou dixnei poses diathesimes 
		 * theseis iparxoun tin epilegmeni sinavlia-zoni*/
		availableTicketsLabel = new JLabel("Διαθέσιμες Θέσεις: "+connector.availableSeatsSelect ("available_expensive_tickets", concertBox.getSelectedItem().toString()));
		availableTicketsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		availableTicketsLabel.setBounds(350, 130, 200, 20);
		frame.getContentPane().add(availableTicketsLabel);
		
		/**to label pou leei poso kanei to kathe isitirio stin sigekrimeni sinavlia-zoni*/
		ticketsCostLabel = new JLabel("Κόστος εισιτηρίων: "+ connector.availableSeatsSelect ("expensive_ticket_price", concertBox.getSelectedItem().toString()) +" €");
		ticketsCostLabel.setHorizontalAlignment(SwingConstants.LEFT);
		ticketsCostLabel.setBounds(10, 130, 200, 20);
		frame.getContentPane().add(ticketsCostLabel);
		
		final JLabel newCostLabel = new JLabel("Νέο Κόστος:");
		newCostLabel.setHorizontalAlignment(SwingConstants.LEFT);
		newCostLabel.setBounds(22, 186, 70, 20);
		frame.getContentPane().add(newCostLabel);
		
		/**to editor pane sto opio o diaxiristis vazei to 
		 * neo kostos eisitirion tis sinavlias-seiras*/
		final JEditorPane newCostPane = new JEditorPane();
		newCostPane.setBounds(102, 186, 50, 20);
		frame.getContentPane().add(newCostPane);
		
		/**to editor pane sto opio o diaxiristis vazei to 
		 * neo arithmo diathesimon theseon tis sinavlias-seiras*/
		final JEditorPane newSeatsPane = new JEditorPane();
		newSeatsPane.setBounds(543, 186, 50, 20);
		frame.getContentPane().add(newSeatsPane);
		
		final JLabel newSeats = new JLabel("Αλλαγή αριθμού θέσεων :");
		newSeats.setBounds(162, 186, 150, 20);
		frame.getContentPane().add(newSeats);
		
		/**to editor pane sto opio o diaxiristis vazei ton
		 *  neo arithmo ton sinolikon theseon tis sinavlias-seiras*/
		final JEditorPane newTotalSeatsPane = new JEditorPane();
		newTotalSeatsPane.setBounds(308, 186, 50, 20);
		frame.getContentPane().add(newTotalSeatsPane);
		
		/**to koubi pou tha pragmatopoiei tis allages pou evale o diaxiristis*/
		final JButton changeBtn = new JButton("Αλλαγή");
		changeBtn.setBounds(10, 230, 240, 30);
		frame.getContentPane().add(changeBtn);
		
		/**to koubi pou tha diagrafei mia iparxousa sinavlia*/
		final JButton deleteBtn = new JButton("Διαγραφή");
		deleteBtn.setBounds(260, 230, 100, 30);
		frame.getContentPane().add(deleteBtn);
		
		final JLabel newConcertLabel = new JLabel("Όνομα Συναυλίας:");
		newConcertLabel.setHorizontalAlignment(SwingConstants.LEFT);
		newConcertLabel.setBounds(10, 320, 120, 20);
		frame.getContentPane().add(newConcertLabel);
		
		/**to editor pane sto opio o diaxiristis vazei ton titlo mias neas sinavlias*/
		final JEditorPane newConcertPane = new JEditorPane();
		newConcertPane.setBounds(140, 320, 220, 20);
		frame.getContentPane().add(newConcertPane);
		
		final JLabel aZoneSeatsLabel = new JLabel("Α Ζώνη:   Θέσεις:");
		aZoneSeatsLabel.setHorizontalAlignment(SwingConstants.LEFT);
		aZoneSeatsLabel.setBounds(10, 370, 120, 20);
		frame.getContentPane().add(aZoneSeatsLabel);
		
		/**to editor pane sto opio o diaxiristis vazei 
		 * tis sinolikes theseis tis A zonis mias sinavlias*/
		final JEditorPane aZoneSeatsPane = new JEditorPane();
		aZoneSeatsPane.setBounds(140, 370, 50, 20);
		frame.getContentPane().add(aZoneSeatsPane);
		
		final JLabel aZoneCostLabel = new JLabel("Κόστος:");
		aZoneCostLabel.setHorizontalAlignment(SwingConstants.LEFT);
		aZoneCostLabel.setBounds(200, 370, 70, 20);
		frame.getContentPane().add(aZoneCostLabel);
		
		/**to editor pane sto opio o diaxiristis vazei
		 *  to kostos ton theseon tis A zonis mias sinavlias*/
		final JEditorPane aZoneCostPane = new JEditorPane();
		aZoneCostPane.setBounds(280, 370, 50, 20);
		frame.getContentPane().add(aZoneCostPane);
		
		final JLabel bZoneSeatsLabel = new JLabel("Β Ζώνη:   Θέσεις:");
		bZoneSeatsLabel.setHorizontalAlignment(SwingConstants.LEFT);
		bZoneSeatsLabel.setBounds(10, 420, 120, 20);
		frame.getContentPane().add(bZoneSeatsLabel);
		
		/**to editor pane sto opio o diaxiristis vazei tis 
		 * sinolikes theseis tis B zonis mias sinavlias*/
		final JEditorPane bZoneSeatsPane = new JEditorPane();
		bZoneSeatsPane.setBounds(140, 420, 50, 20);
		frame.getContentPane().add(bZoneSeatsPane);
		
		final JLabel bZoneCostLabel = new JLabel("Κόστος:");
		bZoneCostLabel.setHorizontalAlignment(SwingConstants.LEFT);
		bZoneCostLabel.setBounds(200, 420, 70, 20);
		frame.getContentPane().add(bZoneCostLabel);
		
		/**to editor pane sto opio o diaxiristis vazei 
		 * to kostos ton theseon tis B zonis mias sinavlias*/
		final JEditorPane bZoneCostPane = new JEditorPane();
		bZoneCostPane.setBounds(280, 420, 50, 20);
		frame.getContentPane().add(bZoneCostPane);
		
		final JLabel cZoneSeatsLabel = new JLabel("Γ Ζώνη:   Θέσεις:");
		cZoneSeatsLabel.setHorizontalAlignment(SwingConstants.LEFT);
		cZoneSeatsLabel.setBounds(10, 470, 120, 20);
		frame.getContentPane().add(cZoneSeatsLabel);
		
		/**to editor pane sto opio o diaxiristis vazei tis
		 *  sinolikes theseis tis Γ zonis mias sinavlias*/
		final JEditorPane cZoneSeatsPane = new JEditorPane();
		cZoneSeatsPane.setBounds(140, 470, 50, 20);
		frame.getContentPane().add(cZoneSeatsPane);
		
		final JLabel cZoneCostLabel = new JLabel("Κόστος:");
		cZoneCostLabel.setHorizontalAlignment(SwingConstants.LEFT);
		cZoneCostLabel.setBounds(200, 470, 70, 20);
		frame.getContentPane().add(cZoneCostLabel);
		
		/**to editor pane sto opio o diaxiristis vazei 
		 * to kostos ton theseon tis Γ zonis mias sinavlias*/
		final JEditorPane cZoneCostPane = new JEditorPane();
		cZoneCostPane.setBounds(280, 470, 50, 20);
		frame.getContentPane().add(cZoneCostPane);
		
		/**to button pou tha eisagei tin nea sinavlia stin vasi dedomenon*/
		final JButton newBtn = new JButton("Εισαγωγή");
		newBtn.setBounds(10, 530, 240, 30);
		frame.getContentPane().add(newBtn);
		
		/**to button pou mas pigenei sto parathiro tou xristi*/
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
		
		/**to button pou anigei to parathiro me ta statistika*/
		final JButton btnShowStats = new JButton("\u0395\u03BC\u03C6\u03AC\u03BD\u03B9\u03C3\u03B7 \u03A3\u03C4\u03B1\u03C4\u03B9\u03C3\u03C4\u03B9\u03BA\u03CE\u03BD");
		btnShowStats.setBounds(8, 624, 240, 30);
		frame.getContentPane().add(btnShowStats);
		
		final JLabel label4 = new JLabel("\u0391\u03BB\u03BB\u03B1\u03B3\u03AE \u03B4\u03B9\u03B1\u03B8\u03AD\u03C3\u03B9\u03BC\u03C9\u03BD \u03B8\u03AD\u03C3\u03B5\u03C9\u03BD :");
		label4.setBounds(370, 186, 180, 20);
		frame.getContentPane().add(label4);
		
		
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
		
		
		/**otan patame to koubi btnShowStats tha klinei to 
		 * parathiro, ke tha anigei ena parathiro StatsGui*/
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
		
		
		/**otan dialegoume mia nea sinavlia apo to comboBox, tha ananeonoume ta labels*/
		concertBox.addActionListener(
		new ActionListener()
        {
			/**to action ginete otan dialegoume kapio item apo to combobox*/
			public void actionPerformed(final ActionEvent itemSelected)
            {
	            refreshLabels();
            }
        });
		
		
		/**otan dialegoume mia nea seira apo to comboBox, tha ananeonoume ta labels*/
		seatBox.addActionListener(
        new ActionListener()
        {
        	/**to action ginete otan dialegoume kapio item apo to combobox*/
        	public void actionPerformed(final ActionEvent itemSelected)
            {
        		refreshLabels();
            }
        });
		
		
		/**otan patame to changeBtn tha pername tis times pou dosame sta panes
		 * stin vasi dedomenon xrisimopoiontas to update function tou Database_connector*/
		changeBtn.addActionListener(
		new ActionListener() 
		{
			public void actionPerformed(final ActionEvent buttonPressed) 
			{
				/** elenxoume an o diaxiristis 
				 * afise kapio field adeio
				 */
				if((newSeatsPane.getText().isEmpty())||(newTotalSeatsPane.getText().isEmpty())||(newCostPane.getText().isEmpty()))
				{
					JOptionPane.showMessageDialog(frame, "Κάποιο Field Είναι Αδειο");
				}
				
				/** elenxoume an o diaxiristis evale keimeno 
				 * se kapio field pou perimenei arithmo
				 */
				else if(!(StringCheck.isNumeric(newSeatsPane.getText()))||!(StringCheck.isNumeric(newTotalSeatsPane.getText())) ||!(StringCheck.isNumeric(newCostPane.getText())))
				{
					JOptionPane.showMessageDialog(frame, "Κάποιο Field Εχει Κείμενο Αντί Για Αριθμό");
				}
				else
				{
					/**vriskoume tin seira eisitirion tis sinavlias pou theloume na epeksergastoume*/
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
	
		/**otan patame to koubi deleteBtn, diagrafoume tin epilegmeni sinavlia apo 
		 * tin vasi dedomenon, me to function delete tou Database_connector*/
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
		
		/**otan patame to newBtn, prosthetoume mia sinavlia stin
		 *  vasi dedomenon me ta values pou valame sta panes*/
		newBtn.addActionListener(new ActionListener() 
		{
			public void actionPerformed(final ActionEvent buttonPressed)
			{
				/** elenxoume an o diaxiristis 
				 * afise kapio field adeio
				 */
				if((newConcertPane.getText().isEmpty())||(aZoneSeatsPane.getText().isEmpty())||(aZoneCostPane.getText().isEmpty())
						||(bZoneSeatsPane.getText().isEmpty())||(bZoneCostPane.getText().isEmpty())
						||(cZoneSeatsPane.getText().isEmpty())||(cZoneCostPane.getText().isEmpty()))
				{
					JOptionPane.showMessageDialog(frame, "Κάποιο Field Είναι Αδειο");
				}
				
				/** elenxoume an o diaxiristis evale keimeno 
				 * se kapio field pou perimenei arithmo
				 */
				else if(!(StringCheck.isNumeric(aZoneSeatsPane.getText()))||!(StringCheck.isNumeric(aZoneCostPane.getText()))
						||!(StringCheck.isNumeric(bZoneSeatsPane.getText()))||!(StringCheck.isNumeric(bZoneCostPane.getText()))
						||!(StringCheck.isNumeric(cZoneSeatsPane.getText()))||!(StringCheck.isNumeric(cZoneCostPane.getText())))
				{
					JOptionPane.showMessageDialog(frame, "Κάποιο Field Εχει Κείμενο Αντί Για Αριθμό");
				}
				else
				{
					connector.create(newConcertPane.getText(), aZoneSeatsPane.getText(),aZoneCostPane.getText() , bZoneSeatsPane.getText(),bZoneCostPane.getText() , cZoneCostPane.getText(), cZoneSeatsPane.getText());
					/**dinoume tous titlous ton sinavlion apo tin vasi dedomenon 
					 * sto concertBox me to function titleSelect() tou Database_connector*/
					concertBox.setModel(connector.titleSelect());
					seatBox.setSelectedIndex(0);
				}
			}
		});
		
		/**otan patame to btnUser, tha klinei afto 
		 * to parathiro ke anigei ena parathiro Gui*/
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
	
	/**ananeonei ta labels tin kaloume kathe fora 
	 * pou kanoume kapia allagi stin vasi dedomenon*/
	public void refreshLabels()
	{
		if ( seatBox.getSelectedIndex() == A_SEIRA )
        {
			totalTicketsLabel.setText("Σύνολο Θέσεων: "+connector.availableSeatsSelect ("total_expensive_tickets", concertBox.getSelectedItem().toString()));
            availableTicketsLabel.setText("Διαθέσιμες Θέσεις: "+connector.availableSeatsSelect ("available_expensive_tickets", concertBox.getSelectedItem().toString()));
            ticketsCostLabel.setText("Κόστος εισιτηρίων: "+ Integer.parseInt(connector.availableSeatsSelect ("expensive_ticket_price", concertBox.getSelectedItem().toString())));
        } 
        else if ( seatBox.getSelectedIndex() == B_SEIRA )
        {
        	totalTicketsLabel.setText("Σύνολο Θέσεων: "+connector.availableSeatsSelect ("total_normal_tickets", concertBox.getSelectedItem().toString()));
            availableTicketsLabel.setText("Διαθέσιμες Θέσεις: "+connector.availableSeatsSelect ("available_normal_tickets", concertBox.getSelectedItem().toString()));
            ticketsCostLabel.setText("Κόστος εισιτηρίων: "+ Integer.parseInt(connector.availableSeatsSelect ("normal_ticket_price", concertBox.getSelectedItem().toString())));
        } 
        else 
        {
        	totalTicketsLabel.setText("Σύνολο Θέσεων: "+connector.availableSeatsSelect ("total_cheap_tickets", concertBox.getSelectedItem().toString()));
            availableTicketsLabel.setText("Διαθέσιμες Θέσεις: "+connector.availableSeatsSelect ("available_cheap_tickets", concertBox.getSelectedItem().toString()));
            ticketsCostLabel.setText("Κόστος εισιτηρίων: "+ Integer.parseInt(connector.availableSeatsSelect ("cheap_ticket_price", concertBox.getSelectedItem().toString())));
        }
	}
}