package portal.utility;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Convertor {

		@SuppressWarnings("finally")
		static public Date SQLDate(String dateString)
		{
			Date sqlStartDate = Date.valueOf("9999-12-30");
			
			try
			{
				
				SimpleDateFormat dateFormatStr= new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date date = dateFormatStr.parse(dateString);
				sqlStartDate = new Date(date.getTime()); 
			}
			catch(ParseException ex)
			{
				ex.printStackTrace();
			}
			finally
			{
				return sqlStartDate;
			}
			

		}
		@SuppressWarnings("finally")
		static public java.util.Date JavaDate(String dateString)
		{
			java.util.Date date=null;
			
			try
			{
				
				SimpleDateFormat dateFormatStr= new SimpleDateFormat("yyyy-MM-dd");
				date = dateFormatStr.parse(dateString);
				
			}
			catch(ParseException ex)
			{
				//ex.printStackTrace();
			}
			finally
			{
				return date;
			}
			

		}
		
		@SuppressWarnings("finally")
		static public java.util.Date JavaDateTime(String dateString)
		{
			java.util.Date date=null;
			
			try
			{
				
				SimpleDateFormat dateFormatStr= new SimpleDateFormat("yyyy-MM-dd HH:mm");
				date = dateFormatStr.parse(dateString);
				
			}
			catch(ParseException ex)
			{
				//ex.printStackTrace();
			}
			finally
			{
				return date;
			}
			

		}
		
		@SuppressWarnings("finally")
		static public int JavaInt(String intString)
		{
			int number = 0;
			
			try
			{
				
				number = Integer.parseInt(intString);
				
			}
			catch(NumberFormatException ex)
			{
				//ex.printStackTrace();
			}
			finally
			{
				return number;
			}
			

		}
		

		static public String JavaCurrentDate()
		{
				java.util.Date date=new java.util.Date();
				SimpleDateFormat dateFormatStr= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
				return dateFormatStr.format(date);
	
		}
		




}
