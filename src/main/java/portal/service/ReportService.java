package portal.service;

import java.util.List;



import portal.entity.Report;
import portal.utility.JSONObjectWithEmpty;


public interface ReportService {
	public List<Report> getReports();
	public Report Save(Report report);
	public Report getById(Long id);
	public Report update(Report report);
	public void delete(Report report);
	public Report getReport(Report report);
	public void removeFile(String upload_foler, Report report);
	public JSONObjectWithEmpty getReportModel(Report report) throws Exception;
}
