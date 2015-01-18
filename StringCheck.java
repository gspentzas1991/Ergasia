package seat_administration;


public class StringCheck 
{
	
	/** Pernei ena String ke elenxei an exei 
	 * mesa keimeno i arithmous
	 */
	public static boolean isNumeric(String str)  
	{  
	  try  
	  {  
	    double d = Double.parseDouble(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}

}
