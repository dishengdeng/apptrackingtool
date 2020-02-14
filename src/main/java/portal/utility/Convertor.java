package portal.utility;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;




public class Convertor {
		private static final Logger LOGGER = LoggerFactory.getLogger(Convertor.class);
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

		static public java.util.Date JavaDate(String dateString) throws ParseException
		{
			
			

				if(ObjectUtils.isEmpty(dateString)) return null;
				SimpleDateFormat dateFormatStr= new SimpleDateFormat("yyyy-MM-dd");
				return dateFormatStr.parse(dateString);

	
			

		}

		@SuppressWarnings("finally")
		static public java.util.Date StringToDate(String dateString) 
		{
			
			
				java.util.Date date=null;
				try
				{
				if(ObjectUtils.isEmpty(dateString)) return date;
				SimpleDateFormat dateFormatStr= new SimpleDateFormat("yyyy-MM-dd");
				date= dateFormatStr.parse(dateString);
				}
				catch(ParseException ex)
				{
					LOGGER.info(ex.getMessage());
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
				
				//SimpleDateFormat dateFormatStr= new SimpleDateFormat("yyyy-MM-dd HH:mm");
				SimpleDateFormat dateFormatStr= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
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
				SimpleDateFormat dateFormatStr= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				return dateFormatStr.format(date);
	
		}
		
		
		static public String setEmptyIfNull(String str)
		{
			if(StringUtils.isEmpty(str)) str="";
			return str;
		}
		
		static public Object getEmpty(Object obj)
		{
			if(obj==null) return "";
			return obj;
		}



}
