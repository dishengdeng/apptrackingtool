package portal.report;



import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import portal.entity.File;
import portal.entity.Report;
import portal.models.ParameterModel;
import portal.models.RunReportModel;
import portal.utility.ReportFormat;


public interface ReportManager {
	public void compileTemplate(File template,String templatepath);
	public void runreport(Report report, ReportFormat reportFormat,String templatepath,HttpServletResponse response,Optional<HashMap<String, Object>> parmeters);
	public HashMap<String, Object> getParameters(List<ParameterModel> parameters);
	public RunReportModel<List<ParameterModel>> getRunReportModel(String jsonString);
}
