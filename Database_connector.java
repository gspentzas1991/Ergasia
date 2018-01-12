package seat_administration;

import java.sql.*;

import javax.swing.DefaultComboBoxModel;

/**The class that makes the connection to the DB*/
public class Database_connector 
{
	/** */
	private  static Connection conn;
	/** The url of the DB */
    private final transient String url;
    /** The DB's name */
    private final transient String dbName;
    /** JDBC's driver*/
    private final transient String driver;
    /** The username for the DB connection */
    private final transient String userName;
    /** The password for the DB connection */
    private final transient String password;
    
    
    /**Constructor
    */
    public Database_connector( ) 
    {
    	this.url = "jdbc:mysql://localhost:3306/";
    	this.dbName = "ticketsdb";
    	this.driver = "com.mysql.jdbc.Driver";
        this.userName = "root";
        this.password = "J5316826";
    }
    
    /**Creates a connection with the DB*/
    public void startConn () 
    {
        try 
        {
            Class.forName(driver).newInstance();
            try 
            {
                conn = DriverManager.getConnection(url + dbName, userName, password);
            }
            catch (SQLException ex) 
            {
                ex.printStackTrace();
            }
        } 
        catch (InstantiationException ex) 
        {
            ex.printStackTrace();
        } 
        catch (IllegalAccessException ex) 
        {
            ex.printStackTrace();
        }
        catch (ClassNotFoundException ex)
        {
        	
            ex.printStackTrace();
        }
    }
    
    /**Closes the connection to the DB*/
    
    public void stopConn() 
    {
        try 
        {
            conn.close();
        } 
        catch (SQLException ex) 
        {
            ex.printStackTrace();
        }
    }

    /**Select query for the concert titles
    *Returns a comboboxmodel that includes all the concert titles*/
    public DefaultComboBoxModel<String> titleSelect ()
    {

    	/**The comboboxModel that will include all the concert titles*/
        final DefaultComboBoxModel<String> concertBoxModel = new DefaultComboBoxModel<>();
        try 
        {
            final Statement select = conn.createStatement();
            /**The query that returns all the concert titles from the DB*/
            final ResultSet result = select.executeQuery("SELECT title FROM concert;");
            try
            {
    			while(result.next())
    			{
    				concertBoxModel.addElement(result.getString("title"));
    			}
    		}
            
            catch(SQLException e)
            {
    			e.printStackTrace();
    		}
            /**closes the select and result statements*/
            select.close();
            result.close();
        } 
        catch (SQLException ex) 
        {
            ex.printStackTrace();
        }

        return concertBoxModel;
    }

    /**Select query that returns the number or cost of tickets
	*category: the column of the DB we want to get data from
	*title: the title of the concert*/
    
    public String availableSeatsSelect (final String category, final String title)
    {
    	String returnString = null;
        try 
        {
            final Statement select = conn.createStatement();
            final ResultSet result = select.executeQuery("SELECT "+ category +" FROM concert WHERE title = \""+ title +"\";");
            
            if(result.next())
            {
            	/**The String returnString takes the value of the first row of the "category" column (where category is the String we called the method with)*/
	        returnString = result.getString(category);
           	/**closes the select and result statements*/
	        select.close();
	        result.close();
            }
        }
        catch (SQLException ex) 
        {
            ex.printStackTrace();
        }
        return returnString ;
    }
  
    
    /**Update query that decreses the available tickets on the DB after a purchase
    *category: the column of the DB we want to get data from
    *title: the title of the concert
    *seatcount: the number of tickets that will be reducted*/
    public void updateAvailableSeats(final String category,final String title,final String seatcount) 
    {
        try 
        {
            final Statement update = conn.createStatement();
            update.executeUpdate("UPDATE concert SET "+category+" = "+category+"-"+seatcount+" WHERE title = \""+ title +"\"");
            update.close();
        }
        catch (SQLException ex) 
        {
            ex.printStackTrace();
        }
    }
    
    /**update querty that changes the amount of total tickets, available tickets and cost of each ticket
    *title: the title of the concert
    *seat_category: the name of the column on the DB that has the appropriate type of tickets (A B or C) that we want to change
    *seatcount: the new number of available tickets
    *total_seat_category:the name of the column on the DB that has the number of available tickets that we want to change
    *total_seat_count: the new number of total tickets
    *price_category: the name of the column on the DB that has the price of the tickets */
    public void update(final String title,final String seatCategory,final String seatcount,final String totalSeatCategory,final String totalSeatCount,final String priceCategory,final String price) 
    {
        try 
        {
            final Statement update = conn.createStatement();
            update.executeUpdate("UPDATE concert SET "+seatCategory+" = "+seatcount+" WHERE title = \""+ title +"\"");
            update.executeUpdate("UPDATE concert SET "+totalSeatCategory+" = "+totalSeatCount+" WHERE title = \""+ title +"\"");
            update.executeUpdate("UPDATE concert SET "+priceCategory+" = "+price+" WHERE title = \""+ title +"\"");
            update.close();
        } 
        catch (SQLException ex) 
        {
            ex.printStackTrace();
        }
    }
    
    /**The query that adds a new concert to the DB
    *title:The concert title
    *expSeatsNo: The total number of type A tickets
    *expSeatsCost: the cost for type A tickets
    *normSeatsNo: The total number of type B tickets
    *normSeatsCost: the cost for type B tickets
    *cheapSteasNo:  The total number of type C tickets
    *cheapSeatsCost: the cost for type C tickets*/
    public void create(final String title,final String expSeatsNo,final String expSeatsCost,final String normSeatsNo,final String normSeatsCost,final String cheapSeatsNo,final String cheapSeatsCost)
    {
    	final String quary="INSERT INTO concert (`title`, `cheap_ticket_price`, `available_cheap_tickets`, `"
    			+ "normal_ticket_price`, `available_normal_tickets`, `"
    			+ "expensive_ticket_price`, `available_expensive_tickets`, `"
    			+ "total_cheap_tickets`, `total_normal_tickets`, `total_expensive_tickets`)"
    			+ " VALUES ('"+title+"','"+cheapSeatsCost+"','"+cheapSeatsNo+"','"
    			+normSeatsCost+"','"+normSeatsNo+"','"
    			+expSeatsCost+"','"+expSeatsNo+"','"
    			+cheapSeatsNo+"','"+normSeatsNo+"','"+expSeatsNo+"')";
    	try 
    	{
            final Statement insert = conn.createStatement();
            insert.executeUpdate(quary);
            insert.close();
        } 
    	catch (SQLException ex) 
    	{
            ex.printStackTrace();
        }
    }
    
    /**The query that deletes a concert from the DB
    *title: The title of the concert to be deleted*/
    public void delete(final String title) 
    {
        try 
        {
            final Statement delete = conn.createStatement();
            delete.executeUpdate("DELETE FROM concert WHERE title = \""+title+"\"");
            delete.close();
        } 
        catch (SQLException ex) 
        {
            ex.printStackTrace();
        }
    }
}
