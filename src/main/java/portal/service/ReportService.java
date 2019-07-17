package portal.service;

import java.util.List;

import org.json.JSONObject;

import portal.entity.Report;


public interface ReportService {
	public List<Report> getReports();
	public Report Save(Report report);
	public Report update(Report report);
	public void delete(Report report);
	public Report getReport(Report report);
	public void removeFile(String upload_foler, Report report);
	public JSONObject getReportModel(Report report) throws Exception;
}
