package portal.validator;



import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.json.JSONArray;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import portal.utility.AppInventoryDataMap;
import portal.utility.AppinventoryMap;
import portal.utility.Convertor;
import portal.utility.InvalidDataFormatException;
import portal.utility.InvalidTemplateFormatException;



public class AppInventoryImportValidator<T> {
	
	private final DataFormatter dataFormatter = new DataFormatter();
	
	private final int siteNameLength=250;

	private final int compnayNameLength=250;
	
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
				return (T) AppInventoryDataMap.valueOf(StringUtils.upperCase(dataFormatter.formatCellValue(cell).replaceAll("[\\\\/]", "").trim())).getValue();
			}
			catch(Exception ex)
			{
				throw new InvalidDataFormatException("Invalid Data Format at cell "+cell.getAddress()+". Should be Yes, No, N/A, Partial or Empty");
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
		
		if(elment.equals(AppinventoryMap.Site))
		{
			List<String> sites=Arrays.asList(StringUtils.split(dataFormatter.formatCellValue(cell),";"));
			
			List<String> duplicates=sites.stream()
				.collect(Collectors.groupingBy(Function.identity(),Collectors.counting()))
				.entrySet()
				.stream()
				.filter(e->e.getValue()>1L)
				.map(e->e.getKey())
				.collect(Collectors.toList());
			
			if(duplicates.size()>0) throw new InvalidDataFormatException("Invalid Data Format at cell "+cell.getAddress()+". Duplicate Sites: "+duplicates.toString());
			
			JSONArray sitesArray= new JSONArray();
			for(String site:sites)
			{
				if(site.length()>siteNameLength) throw new InvalidDataFormatException("Invalid Data Format at cell "+cell.getAddress()+". Site name cannot more than "+siteNameLength+" characters");
				sitesArray.put(site);				
			}
			
			return (T) sitesArray;

		}
		
		if(elment.equals(AppinventoryMap.Vendor))
		{
			List<String> vendors=Arrays.asList(StringUtils.split(dataFormatter.formatCellValue(cell),";"));
			JSONArray vendorArray= new JSONArray();
			for(String vendor:vendors)
			{
				if(vendor.length()>compnayNameLength) throw new InvalidDataFormatException("Invalid Data Format at cell "+cell.getAddress()+". Vendor name cannot more than "+compnayNameLength+" characters");
				vendorArray.put(vendor);				
			}
			
			return (T) vendorArray;

		}
		
		
		return (T) dataFormatter.formatCellValue(cell);
	}
	
	public void TemplateFormatValidate(Row columnHeader) throws InvalidTemplateFormatException
	{
		Assert.notNull(columnHeader);
		
		
		
		int numberOfCell=columnHeader.getLastCellNum();
		
		
		List<AppinventoryMap> elements=Arrays.asList(AppinventoryMap.values());
		
		if(numberOfCell!=elements.size())
		{
			throw new InvalidTemplateFormatException("Invalid Template Format. Number of Columns are not matched up");
		}
		
		
		
		for(Cell cell:columnHeader)
		{

			AppinventoryMap el=elements.stream()
					.filter(obj->obj.getColumnName().equalsIgnoreCase(dataFormatter.formatCellValue(cell)))
					.findAny()
					.orElse(null);
			
			if(ObjectUtils.isEmpty(el)) throw new InvalidTemplateFormatException("Invalid Template Format. Wrong Column Name: "+dataFormatter.formatCellValue(cell));			
			
			if(el.getColumnIndex()!=cell.getColumnIndex()) throw new InvalidTemplateFormatException("Invalid Template Format. Wrong Column Position for "+dataFormatter.formatCellValue(cell)+" .Should be in column "+el.getColumnAddress());
			

			
	
		}
		
		

	}
}
