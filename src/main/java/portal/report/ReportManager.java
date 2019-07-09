package portal.report;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRSaver;
import portal.entity.File;


@Component
public class ReportManager {

	
	public void compileTemplate(File template,String templatepath) 
	{
		try {
			InputStream employeeReportStream = getClass().getResourceAsStream(templatepath+Long.valueOf(template.getReport().getId())+"_"+template.getAttachment());
			
			JasperReport jasperReport = JasperCompileManager.compileReport(employeeReportStream);
			
			JRSaver.saveObject(jasperReport, templatepath+Long.valueOf(template.getReport().getId())+"_"+template.getReport().getReportName()+".jasper");
		}
		catch(JRException ex)
		{
			Logger.getLogger(ReportManager.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
