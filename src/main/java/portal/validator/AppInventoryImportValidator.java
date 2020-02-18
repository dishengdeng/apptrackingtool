package portal.validator;



import java.text.ParseException;


import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;


import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import portal.utility.AppInventoryDataMap;
import portal.utility.AppinventoryMap;
import portal.utility.Convertor;
import portal.utility.InvalidDataFormatException;



public class AppInventoryImportValidator<T> {
	
	private final DataFormatter dataFormatter = new DataFormatter();
	
	

	
	@SuppressWarnings("unchecked")
	public T AppinventoryDataValidate(AppinventoryMap elment,Cell cell) throws InvalidDataFormatException,Exception
	{
		Assert.notNull(elment);
		//Assert.notNull(cell);
		
		if(elment.equals(AppinventoryMap.ApplicationName))
		{
			if(ObjectUtils.isEmpty(dataFormatter.formatCellValue(cell).trim()))
			{
				throw new InvalidDataFormatException("Invalid Data Format at cell "+cell.getAddress()+". "+AppinventoryMap.ApplicationName.getColumnName()+" cannot be empty.");
			}
		}
		
		if(	elment.equals(AppinventoryMap.Calgary) || 
			elment.equals(AppinventoryMap.South) ||
			elment.equals(AppinventoryMap.Central) ||
			elment.equals(AppinventoryMap.North) ||
			elment.equals(AppinventoryMap.Edmonton) ||
			elment.equals(AppinventoryMap.Goverinplace) ||
			elment.equals(AppinventoryMap.Contractinplace) ||
			elment.equals(AppinventoryMap.Cshrecimit) ||
			elment.equals(AppinventoryMap.AhsItSla) ||
			elment.equals(AppinventoryMap.IMP) ||
			elment.equals(AppinventoryMap.VendorSla))
		{
			if(ObjectUtils.isEmpty(dataFormatter.formatCellValue(cell).trim()))
			{
				return null;
			}
			try
			{
				return (T) AppInventoryDataMap.valueOf(StringUtils.upperCase(dataFormatter.formatCellValue(cell).replaceAll("[\\\\/]", ""))).getValue();
			}
			catch(Exception ex)
			{
				throw new InvalidDataFormatException("Invalid Data Format at cell "+cell.getAddress()+". Should be Yes, No, N/A, or Empty");
			}
			
		}
		
		if(elment.equals(AppinventoryMap.ExpirationDate))
		{
			if(ObjectUtils.isEmpty(dataFormatter.formatCellValue(cell).trim()))
			{
				return null;
			}
			
			try
			{
				Convertor.JavaDate(dataFormatter.formatCellValue(cell).trim());
		
			}
			catch(ParseException ex)
			{
				throw new InvalidDataFormatException("Invalid Date Format at cell "+cell.getAddress()+". Format should be yyyy-MM-dd");
			}			
			
		}		
		return (T) dataFormatter.formatCellValue(cell);
	}
}
