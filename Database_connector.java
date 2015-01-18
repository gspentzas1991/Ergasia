package seat_administration;

import java.sql.*;

import javax.swing.DefaultComboBoxModel;

/** I klasi i kanei tin sindesi
 * me tin vasi dedomenon*/
public class Database_connector 
{
	/** */
	private  static Connection conn;
	/** to url tis vasis dedomenon */
    private final transient String url;
    /** to onoma tis vasis dedomenon */
    private final transient String dbName;
    /** o driver tou jdbc*/
    private final transient String driver;
    /** to username tis vasis dedomenon */
    private final transient String userName;
    /** to password tis vasis dedomenon */
    private final transient String password;
    
    
    /**Constractor.
    *Edo dinoume ta stoixia tis mysql vasis dedomenon mas*/
    public Database_connector( ) 
    {
    	this.url = "jdbc:mysql://localhost:3306/";
    	this.dbName = "ticketsdb";
    	this.driver = "com.mysql.jdbc.Driver";
        this.userName = "root";
        this.password = "J5316826";
    }
    
    /**Ksekiname mia sindesi me tin Mysql*/
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
    
    /**klinoume tin sindesi me tin mysql*/
    
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

    /**Select query gia tous titlous ton sinavlion
    *epistrefei ena comboboxmodel pou periexei olous tous titlous*/
    public DefaultComboBoxModel<String> titleSelect ()
    {

    	/**dimiourgoume ena ComboBox to opio periexei olous tous titlous mesa*/
        final DefaultComboBoxModel<String> concertBoxModel = new DefaultComboBoxModel<>();
        try 
        {
            final Statement select = conn.createStatement();
            /**to query pou mas epistrefei olous tous
             *  titlous ton sinavlion apo tin vasi dedomenon*/
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
            /**klinoume ta select ke result*/
            select.close();
            result.close();
        } 
        catch (SQLException ex) 
        {
            ex.printStackTrace();
        }

        return concertBoxModel;
    }

    /**Select query pou mas epistrefei ton arithmo i to kostos theseon
	*category: i stili tis vasis dedomenon pou theloume
	*title: o titlos tis sinavlias pou theloume
	*epistrefei ena String me ton arithmo theseon, i kostos theseon*/
    
    public String availableSeatsSelect (final String category, final String title)
    {
    	String returnString = null;
        try 
        {
            final Statement select = conn.createStatement();
            final ResultSet result = select.executeQuery("SELECT "+ category +" FROM concert WHERE title = \""+ title +"\";");
            /**to resultSet ksekinaei prin to proto row, gia afto 
             * prepei na kanoume ena .next() gia na diksei sto proto row*/
            if(result.next())
            {
            	/**to string returnString pernei tin timi pou exei to proto 
            	 * row tis stilis "category" (opou category, to string pou dosame otan 
            	 * kalesame tin sinartisi)*/
	            returnString = result.getString(category);
	            /**klinoume ta select ke result*/
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
  
    
    /**Update query gia na aferesoume theseis apo ton arithmo ton diathesimon theseon, meta apo mia agora eisitirion
    *category: i stili tis mysql pou theloume na alaksoume
    *title: o titlos tis sinavlias pou epileksame
    *seatcount: o arithmos theseon pou tha aferethoun apo ton arithmo diathesimon theseon*/
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
    
    /**update query pou alazei ton arithmo ton sinolikon theseon, ton diathesimon theseon ke tis timis tis kathe thesis
    *title: o titlos tis sinavlias pou epileksame
    *seat_category: i stili tis mysql pou exei ton arithmo ton diathesimon theseon tis seiras
    *seatcount: o neos arithmos diathesimon theseon tis sinavlias
    *total_seat_category:  i stili tis mysql pou exei ton arithmo ton sinolikon theseon tis seiras
    *total_seat_count: o neos arithmos sinolikon theseon tis sinavlias
    *price_category: i stili tis mysql pou exei to kostos ton eisitirion gia tin sigekrimeni seira*/
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
    
    /**to query to opio tha prosthetei mia nea sinavlia stin vasi dedomenon
    *title: o titlos tis neas sinavlias
    *expSeatsNo: o sinolikos arithmos theseon A seiras
    *expSeatsCost: to kostos ton eisitirion tis A seiras
    *normSeatsNo:o sinolikos arithmos theseon B seiras
    *normSeatsCost: to kostos ton eisitirion tis B seiras
    *cheapSteasNo: o sinolikos arithmos theseon Ã seiras
    *cheapSeatsCost: to kostos ton eisitirion tis Ã seiras*/
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
    
    /**to query pou diagrafei mia sinavlia apo tin vasi dedomenon
    *title: o titlos tis sinavlias pou tha diagrafei*/
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