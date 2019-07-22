package portal.report;



import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;



import net.sf.jasperreports.engine.JRException;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JsonDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

import portal.entity.File;
import portal.entity.Report;
import portal.service.JsonWriter;
import portal.service.ReportService;
import portal.utility.ReportFormat;

@Service
public class ReportManagerImpl implements ReportManager{
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private JsonWriter jsonWriter;

	@Override
	public void compileTemplate(File template, String templatepath) {
		try {

			
			JasperReport jasperReport=JasperCompileManager.compileReport(templatepath+Long.valueOf(template.getReport().getId())+"_"+template.getAttachment());
			
			JRSaver.saveObject(jasperReport, templatepath+Long.valueOf(template.getReport().getId())+"_"+template.getAttachment()+".jasper");
		}
		catch(JRException ex)
		{
			Logger.getLogger(ReportManager.class.getName()).log(Level.SEVERE, null, ex);
		}
		
	}

	@Override
	public void runreport(Report report, String templatepath,HttpServletResponse response) {
		
		try {

			
			if(report.getReportformat().equals(ReportFormat.EXCEL)) exportToExcel(report,templatepath,response);
			
			if(report.getReportformat().equals(ReportFormat.PDF)) exportToPDF(report,templatepath,response);

			

		}
		catch(Exception ex)
		{
			Logger.getLogger(ReportManager.class.getName()).log(Level.SEVERE, null, ex);
		}


	}
	

	private void exportToExcel(Report report, String templatepath,HttpServletResponse response) throws Exception
	{
		JRXlsxExporter exporter = new JRXlsxExporter();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename="+report.getReportName()+".xlsx");
		SimpleOutputStreamExporterOutput output=new SimpleOutputStreamExporterOutput(response.getOutputStream());

		exporter.setExporterInput(new SimpleExporterInput(getJasperPrinter(report,templatepath)));
		exporter.setExporterOutput(output);

		exporter.exportReport();
		

		response.flushBuffer();
		output.close();
		
		
	}
	
	private void exportToPDF(Report report, String templatepath,HttpServletResponse response) throws Exception
	{
		ByteArrayOutputStream output=new ByteArrayOutputStream();
		 JasperExportManager.exportReportToPdfStream(getJasperPrinter(report,templatepath),output);
			output.writeTo(response.getOutputStream());
			response.setContentType("application/pdf");
			response.flushBuffer();
	}
	
	private JasperPrint getJasperPrinter(Report report, String templatepath) throws Exception
	{
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(new java.io.File(templatepath+Long.valueOf(report.getId())+"_"+report.getTempate().getAttachment()+".jasper"));

		InputStream inputStream =jsonWriter.writeJsonWithNoNullIn(reportService.getReportModel(report));


		JsonDataSource jsonDataSource = new JsonDataSource(inputStream);
		JasperPrint jasperPrinter = JasperFillManager.fillReport(jasperReport, null, jsonDataSource);
		
		return jasperPrinter;
	}

}
