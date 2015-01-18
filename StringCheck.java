package seat_administration;

/**I klasi tha periexei methodous pou
 * exoun na kanoun me Strings
 */
public final class StringCheck 
{
	
	/** Pernei ena String ke elenxei an exei 
	 * mesa keimeno i arithmous
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
