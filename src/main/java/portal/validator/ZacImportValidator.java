package portal.validator;




import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;

import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import portal.entity.Department;
import portal.entity.Zacfield;
import portal.service.ZacfieldService;

import portal.utility.InvalidDataFormatException;
import portal.utility.InvalidTemplateFormatException;

import portal.utility.ZacMap;



public class ZacImportValidator<T> {
	
	private final DataFormatter dataFormatter = new DataFormatter();
	
	
	private final String zacPattern="^[0-5]|N\\/A$";
	


	

	private ZacfieldService zacfieldService;
	
	public ZacImportValidator()
	{}
	
	public ZacImportValidator(ZacfieldService _zacfieldService)
	{
		this.zacfieldService=_zacfieldService;
	}	
	
	@SuppressWarnings("unchecked")
	public T ZacDataValidate(ZacMap elements,Cell cell) throws InvalidDataFormatException,Exception
	{

		if(elements.equals(ZacMap.APPLICATION))
		{
			if(ObjectUtils.isEmpty(dataFormatter.formatCellValue(cell).trim()))
			{
				throw new InvalidDataFormatException("Invalid Data Format at cell "+cell.getAddress()+". "+ZacMap.APPLICATION.getValue()+" cannot be empty.");
			}
		}
		
		if(elements.equals(ZacMap.ZACFIELDS) && !isNull(cell))
		{

			if(!Pattern.compile(zacPattern, Pattern.CASE_INSENSITIVE).matcher(dataFormatter.formatCellValue(cell)).matches())
			{
				throw new InvalidDataFormatException("Invalid Phone Format at cell "+cell.getAddress()+". Phone number should be 0 to 5, N/A or empty.");
			}
		}
		

		
		return (T) dataFormatter.formatCellValue(cell);
	}
	
	public Map<String,Integer> TemplateFormatValidate(Row columnHeader,Department department) throws InvalidTemplateFormatException
	{
		Assert.notNull(columnHeader);
		
		Map<String,Integer> zacFieldIndex=new HashMap<String,Integer>();
		
		int numberOfCell=columnHeader.getLastCellNum();
		
		
		List<Zacfield> elements=zacfieldService.findbyDepartment(department);
		elements.add(new Zacfield(ZacMap.APPLICATION.getValue()));
		elements.add(new Zacfield(ZacMap.DETAIL.getValue()));
		
		if(numberOfCell!=elements.size())
		{
			throw new InvalidTemplateFormatException("Invalid Template Format. Number of Columns are not matched up");
		}
		
		
		
		for(Cell cell:columnHeader)
		{

			Zacfield el=elements.stream()
					.filter(obj->obj.getFieldName().equalsIgnoreCase(dataFormatter.formatCellValue(cell)))
					.findAny()
					.orElse(null);
			
			if(ObjectUtils.isEmpty(el)) throw new InvalidTemplateFormatException("Invalid Template Format. Wrong Column Name: "+dataFormatter.formatCellValue(cell));
			
			zacFieldIndex.put(el.getFieldName(), cell.getColumnIndex());

		}
		
		
		return zacFieldIndex;
		
		

	}
	
	private boolean isNull(Cell cell)
	{
		if(ObjectUtils.isEmpty(dataFormatter.formatCellValue(cell).trim()))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
