package portal.utility;

import java.util.Arrays;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
@Component
public class ColumnHeaderValidator {

	private final DataFormatter dataFormatter = new DataFormatter();
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
