package portal.report;



import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;



import net.sf.jasperreports.engine.JRException;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JsonQLDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

import portal.entity.File;
import portal.entity.Report;
import portal.models.ParameterModel;
import portal.models.RunReportModel;
import portal.service.JsonWriter;
import portal.service.ReportService;
import portal.utility.Convertor;
import portal.utility.JSONObjectWithEmpty;
import portal.utility.ParameterType;
import portal.utility.ReportFormat;
import portal.utility.ReportSourceType;

@Service
public class ReportManagerImpl implements ReportManager{
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private JsonWriter jsonWriter;
	
	@Autowired
	private DataSource sqlDataSource;

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
	public void runreport(Report report, ReportFormat reportFormat,String templatepath,HttpServletResponse response,Optional<HashMap<String, Object>> parmeters) {
		
		try {

			
			if(reportFormat.equals(ReportFormat.EXCEL)) exportToExcel(report,templatepath,response,parmeters);
			
			if(reportFormat.equals(ReportFormat.PDF)) exportToPDF(report,templatepath,response,parmeters);

			

		}
		catch(Exception ex)
		{
			Logger.getLogger(ReportManager.class.getName()).log(Level.SEVERE, null, ex);
		}


	}
	

	private void exportToExcel(Report report, String templatepath,HttpServletResponse response,Optional<HashMap<String, Object>> parmeters) throws Exception
	{
		JRXlsxExporter exporter = new JRXlsxExporter();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename="+report.getReportName()+".xlsx");
		SimpleOutputStreamExporterOutput output=new SimpleOutputStreamExporterOutput(response.getOutputStream());

		exporter.setExporterInput(new SimpleExporterInput(getJasperPrinter(report,templatepath,parmeters.isPresent()?parmeters.get():null)));
		exporter.setExporterOutput(output);

		exporter.exportReport();
		

		response.flushBuffer();
		output.close();
		
		
	}
	
	private void exportToPDF(Report report, String templatepath,HttpServletResponse response,Optional<HashMap<String, Object>> parmeters) throws Exception
	{
		ByteArrayOutputStream output=new ByteArrayOutputStream();
		response.setContentType("application/pdf");
		 JasperExportManager.exportReportToPdfStream(getJasperPrinter(report,templatepath,parmeters.isPresent()?parmeters.get():null),output);		 
			output.writeTo(response.getOutputStream());
			
			response.flushBuffer();
	}
	
	private JasperPrint getJasperPrinter(Report report, String templatepath,HashMap<String, Object> parmeters) throws Exception
	{
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(new java.io.File(templatepath+Long.valueOf(report.getId())+"_"+report.getTempate().getAttachment()+".jasper"));

		if(report.getSourceType()==ReportSourceType.SQL) return JasperFillManager.fillReport(jasperReport, parmeters,sqlDataSource.getConnection());
		
		InputStream inputStream =jsonWriter.writeJsonWithNoNullIn(reportService.getReportModel(report));
		
		JsonQLDataSource jsonDataSource = new JsonQLDataSource(inputStream);
		
		return JasperFillManager.fillReport(jasperReport, parmeters,jsonDataSource);
	}

	@Override
	public HashMap<String, Object> getParameters(List<ParameterModel> parameters) {
    	HashMap<String, Object> params = new HashMap<String, Object>();
    	for(ParameterModel model:parameters)
    	{
    		if(model.getType().equals(ParameterType.String)) params.put(model.getName(), model.getValue());
    		if(model.getType().equals(ParameterType.Number)) params.put(model.getName(), Double.valueOf(model.getValue()));
    		if(model.getType().equals(ParameterType.Boolean)) params.put(model.getName(), Boolean.valueOf(model.getValue()));
    		if(model.getType().equals(ParameterType.Date)) params.put(model.getName(), Convertor.JavaDate(model.getValue()));
    	}
		return params;
	}

	@Override
	public RunReportModel<List<ParameterModel>> getRunReportModel(String jsonString) {
		
    	JSONObjectWithEmpty reportObj= new JSONObjectWithEmpty(jsonString);
    	
    	RunReportModel<List<ParameterModel>> reportModel=new RunReportModel<List<ParameterModel>>();
    	
    	reportModel.setId(reportObj.getLong("report"));
    	reportModel.setReportFormat(ReportFormat.valueOf(reportObj.getString("reportFormat")));
    	JSONArray parameters= reportObj.getJSONArray("parameters");
    	List<ParameterModel> parameterList = new ArrayList<ParameterModel>();
    	
    	for(Object obj:parameters)
    	{
    		
    		parameterList.add(new ParameterModel((JSONObject) obj)) ;
    		
    	}
    	reportModel.setParameters(parameterList);
		return reportModel;
	}

}
