package portal.validator;



import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.json.JSONArray;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;


import portal.utility.InvalidDataFormatException;
import portal.utility.InvalidTemplateFormatException;
import portal.utility.StakeholderDataMap;
import portal.utility.StakeholderMap;



public class StakeholderImportValidator<T> {
	
	private final DataFormatter dataFormatter = new DataFormatter();
	
	
	private final String emailPattern="^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
	
	private final String phonenumPattern="^\\d{10}$";
	
	@SuppressWarnings("unchecked")
	public T StakeholderDataValidate(StakeholderMap elment,Cell cell) throws InvalidDataFormatException,Exception
	{
		Assert.notNull(elment);
		if(elment.equals(StakeholderMap.Name))
		{
			if(ObjectUtils.isEmpty(dataFormatter.formatCellValue(cell).trim()))
			{
				throw new InvalidDataFormatException("Invalid Data Format at cell "+cell.getAddress()+". "+StakeholderMap.Name.getColumnName()+" cannot be empty.");
			}
		}
		if(elment.equals(StakeholderMap.Email))
		{
			if(!Pattern.compile(emailPattern, Pattern.CASE_INSENSITIVE).matcher(dataFormatter.formatCellValue(cell)).matches())
			{
				throw new InvalidDataFormatException("Invalid Email Format at cell "+cell.getAddress());
			}
		}
		
		if(elment.equals(StakeholderMap.Phone))
		{
			if(!Pattern.compile(phonenumPattern, Pattern.CASE_INSENSITIVE).matcher(dataFormatter.formatCellValue(cell)).matches())
			{
				throw new InvalidDataFormatException("Invalid Phone Format at cell "+cell.getAddress()+". Phone number should be 10 digital number.");
			}
		}
		
		if(elment.equals(StakeholderMap.Influence) || elment.equals(StakeholderMap.Interest))
		{
			returnNull(cell);
			
			try
			{
				switch(StakeholderDataMap.valueOf(dataFormatter.formatCellValue(cell))) {
				case L:
					return (T) StakeholderDataMap.L.getValue();
				case H:
					return (T) StakeholderDataMap.H.getValue();
				case M:
					return (T) StakeholderDataMap.M.getValue();
				default:
					throw new InvalidDataFormatException("Invalid Data Format at cell "+cell.getAddress()+". Should be L,M,H or Empty");					
				}
			}
			catch(Exception ex)
			{
				throw new InvalidDataFormatException("Invalid Data Format at cell "+cell.getAddress()+". Should be L,M,H or Empty");
			}
		}
		
		if(elment.equals(StakeholderMap.RACI))
		{
			returnNull(cell);
			try
			{
				char[] arr=dataFormatter.formatCellValue(cell).toCharArray();
				JSONArray jarray= new JSONArray();
				for(char c:arr)
				{
					switch(StakeholderDataMap.valueOf(String.valueOf(c))) {
					case R:
						jarray.put(StakeholderDataMap.R.getValue());
						break;
					case A:
						jarray.put(StakeholderDataMap.A.getValue());
						break;
					case C:
						jarray.put(StakeholderDataMap.C.getValue());
						break;
					case I:
						jarray.put(StakeholderDataMap.I.getValue());
						break;					
					default:
						throw new InvalidDataFormatException("Invalid Data Format at cell "+cell.getAddress()+". Should be R,A,C,I or Empty");					
					}
				}
				
				return (T) jarray;

			}
			catch(Exception ex)
			{
				throw new InvalidDataFormatException("Invalid Data Format at cell "+cell.getAddress()+". Should be R,A,C,I or Empty");
			}			
		}
		
		return (T) dataFormatter.formatCellValue(cell);
	}
	
	public void TemplateFormatValidate(Row columnHeader) throws InvalidTemplateFormatException
	{
		Assert.notNull(columnHeader);
		
		
		
		int numberOfCell=columnHeader.getLastCellNum();
		
		
		List<StakeholderMap> elements=Arrays.asList(StakeholderMap.values());
		
		if(numberOfCell!=elements.size())
		{
			throw new InvalidTemplateFormatException("Invalid Template Format. Number of Columns are not matched up");
		}
		
		
		
		for(Cell cell:columnHeader)
		{

			StakeholderMap el=elements.stream()
					.filter(obj->obj.getColumnName().equalsIgnoreCase(dataFormatter.formatCellValue(cell)))
					.findAny()
					.orElse(null);
			
			if(ObjectUtils.isEmpty(el)) throw new InvalidTemplateFormatException("Invalid Template Format. Wrong Column Name: "+dataFormatter.formatCellValue(cell));			
			
			if(el.getColumnIndex()!=cell.getColumnIndex()) throw new InvalidTemplateFormatException("Invalid Template Format. Wrong Column Position for "+dataFormatter.formatCellValue(cell)+" .Should be in column "+el.getColumnAddress());
			

			
	
		}
		
		

	}
	
	private Object returnNull(Cell cell)
	{
		if(ObjectUtils.isEmpty(dataFormatter.formatCellValue(cell).trim()))
		{
			return null;
		}
		else
		{
			return StringUtils.EMPTY;
		}
	}
}
