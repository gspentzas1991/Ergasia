package seat_administration;

/**Contains the method that checks if a String has contains a number or characters
 */
public final class StringCheck 
{
	
	/** Checks if the String str contains numbers or characters.
	Returns true if it doesn't contain characters and false if it does
	 */
	public static boolean isNumeric(final String str)  
	{  
		boolean flag=true;
	  try  
	  {  
		 Double.parseDouble(str);  
	  }  
	  catch(NumberFormatException isNotNumber)  
	  {  
	    flag=false;  
	  }  
	  return flag;  
	}

}
