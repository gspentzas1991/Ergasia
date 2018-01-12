package seat_administration;


import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**The class that creates the Stats frame*/
public class StatsGui {

	

	/**The connector to the DB*/
	private transient Database_connector connector;
	
	public transient JFrame frame;

	
	/**
	 * Create the application.
	 */
	public StatsGui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("\u03A3\u03C4\u03B1\u03C4\u03B9\u03C3\u03C4\u03B9\u03BA\u03AC");
		frame.setResizable(false);
		frame.setBounds(100, 100, 600, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		/**Connects to the DB*/
		connector= new Database_connector();
		connector.startConn();
		
		/**The Combobox concertBox has the concert titles*/
		final JComboBox<String> concertBox = new JComboBox<>();
		concertBox.setBounds(141, 53, 314, 20);
		frame.getContentPane().add(concertBox);
		
		/**We give the concert titles to the concertBox from the DB through the titleSelect() method of the connector*/
		concertBox.setModel(connector.titleSelect());
		
		/**The button that takes us to the admin frame*/
		final JButton btnAdmin = new JButton("Äéá÷åéñéóôÞò");
		btnAdmin.setBounds(10, 11, 89, 23);
		frame.getContentPane().add(btnAdmin);
		
		final JLabel label = new JLabel("\u0395\u03C0\u03B9\u03BB\u03BF\u03B3\u03AE \u03A3\u03C5\u03BD\u03B1\u03C5\u03BB\u03AF\u03B1\u03C2 :");
		label.setBounds(10, 56, 121, 14);
		frame.getContentPane().add(label);
		
		final JLabel label1 = new JLabel("\u03A3\u03C4\u03B1\u03C4\u03B9\u03C3\u03C4\u03B9\u03BA\u03AC");
		label1.setFont(new Font("Tahoma", Font.PLAIN, 17));
		label1.setBounds(10, 93, 89, 30);
		frame.getContentPane().add(label1);
		
		final JLabel label2 = new JLabel("\u0391 \u03B8\u03AD\u03C3\u03B7");
		label2.setBounds(10, 156, 46, 14);
		frame.getContentPane().add(label2);
		
		final JLabel label3 = new JLabel("\u0392 \u03B8\u03AD\u03C3\u03B7");
		label3.setBounds(10, 186, 46, 14);
		frame.getContentPane().add(label3);
		
		final JLabel label4 = new JLabel("\u0393 \u03B8\u03AD\u03C3\u03B7");
		label4.setBounds(10, 216, 46, 14);
		frame.getContentPane().add(label4);
		
		final JLabel label5 = new JLabel("\u03A3\u03CD\u03BD\u03BF\u03BB\u03BF \u03B8\u03AD\u03C3\u03B5\u03C9\u03BD");
		label5.setBounds(90, 134, 121, 14);
		frame.getContentPane().add(label5);
		
		final JLabel label6 = new JLabel("\u03A0\u03BF\u03C5\u03BB\u03B7\u03BC\u03AD\u03BD\u03B5\u03C2 \u0398\u03AD\u03C3\u03B5\u03B9\u03C2");
		label6.setBounds(220, 134, 140, 14);
		frame.getContentPane().add(label6);
		
		final JLabel label7 = new JLabel("\u0394\u03B9\u03B1\u03B8\u03AD\u03C3\u03B9\u03BC\u03B5\u03C2 \u0398\u03B5\u03C3\u03B5\u03B9\u03C2");
		label7.setBounds(370, 134, 158, 14);
		frame.getContentPane().add(label7);
		
		
		/**The label that shows the total tickets of type A for the concert*/
		final JLabel aTotal = new JLabel(connector.availableSeatsSelect ("total_expensive_tickets", concertBox.getSelectedItem().toString()));
		aTotal.setBounds(90, 156, 46, 14);
		frame.getContentPane().add(aTotal);
		
		/**The label that shows the available tickets of type A for the concert*/
		final JLabel aFree = new JLabel(connector.availableSeatsSelect ("available_expensive_tickets", concertBox.getSelectedItem().toString()));
		aFree.setBounds(370, 156, 46, 14);
		frame.getContentPane().add(aFree);

		/**The label that shows the sold tickets of type A for the concert*/
		final JLabel aSold = new JLabel( Integer.toString(Integer.parseInt(aTotal.getText()) - Integer.parseInt(aFree.getText())  ) );
		aSold.setBounds(220, 156, 46, 14);
		frame.getContentPane().add(aSold);
		
		

		/**The label that shows the total tickets of type B for the concert*/
		final JLabel bTotal = new JLabel(connector.availableSeatsSelect ("total_normal_tickets", concertBox.getSelectedItem().toString()));
		bTotal.setBounds(90, 186, 46, 14);
		frame.getContentPane().add(bTotal);

		/**The label that shows the available tickets of type B for the concert*/
		final JLabel bFree = new JLabel(connector.availableSeatsSelect ("available_normal_tickets", concertBox.getSelectedItem().toString()));
		bFree.setBounds(370, 186, 46, 14);
		frame.getContentPane().add(bFree);
		
		/**The label that shows the sold tickets of type B for the concert*/
		final JLabel bSold = new JLabel( Integer.toString( Integer.parseInt(bTotal.getText()) - Integer.parseInt(bFree.getText())  ) );
		bSold.setBounds(220, 186, 46, 14);
		frame.getContentPane().add(bSold);
		
		
		/**The label that shows the total tickets of type C for the concert*/
		final JLabel cTotal = new JLabel(connector.availableSeatsSelect ("total_cheap_tickets", concertBox.getSelectedItem().toString()));
		cTotal.setBounds(90, 216, 46, 14);
		frame.getContentPane().add(cTotal);
		
		/**The label that shows the available tickets of type C for the concert*/
		final JLabel cFree = new JLabel(connector.availableSeatsSelect ("available_cheap_tickets", concertBox.getSelectedItem().toString()));
		cFree.setBounds(370, 216, 46, 14);
		frame.getContentPane().add(cFree);

		/**The label that shows the sold tickets of type C for the concert*/
		final JLabel cSold =new JLabel( Integer.toString( Integer.parseInt(cTotal.getText()) - Integer.parseInt(cFree.getText())  ) );
		cSold.setBounds(220, 216, 46, 14);
		frame.getContentPane().add(cSold);
		
		
		/**
		 * Action Listeners.
		 */
		
		
		/**If the user closes the program, close the DB connection
		 */
		frame.addWindowListener(new WindowAdapter() 
		{
			@Override
			public void windowClosing(WindowEvent arg0) 
			{

				 connector.stopConn();
			}
		});
	
		
		/**When we select a concert, refreshes the labels to the appropriate values*/
		concertBox.addActionListener(
        new ActionListener()
        {
        	public void actionPerformed(final ActionEvent itemSelected)
        	{
        		aTotal.setText(connector.availableSeatsSelect ("total_expensive_tickets", concertBox.getSelectedItem().toString()));
                aFree.setText(connector.availableSeatsSelect ("available_expensive_tickets", concertBox.getSelectedItem().toString()));
                aSold.setText( Integer.toString( Integer.parseInt(aTotal.getText()) - Integer.parseInt(aFree.getText())  ) );
                    	
                bTotal.setText(connector.availableSeatsSelect ("total_normal_tickets", concertBox.getSelectedItem().toString()));
                bFree.setText(connector.availableSeatsSelect ("available_normal_tickets", concertBox.getSelectedItem().toString()));
                bSold.setText( Integer.toString( Integer.parseInt(bTotal.getText()) - Integer.parseInt(bFree.getText())  ) );
                    	
                cTotal.setText(connector.availableSeatsSelect ("total_cheap_tickets", concertBox.getSelectedItem().toString()));
                cFree.setText(connector.availableSeatsSelect ("available_cheap_tickets", concertBox.getSelectedItem().toString()));
                cSold.setText( Integer.toString( Integer.parseInt(cTotal.getText()) - Integer.parseInt(cFree.getText())  ) );
        	}
          });
		
		
		/**When we click the btnAdmin button closes the frame and creates an AdminGui window*/
		btnAdmin.addActionListener(new ActionListener() 
		{
			public void actionPerformed(final ActionEvent buttonPressed) 
			{
				final AdminGui window = new AdminGui();
				window.frame.setVisible(true);
				frame.dispose();
			}
		});
		
	}
	
}
