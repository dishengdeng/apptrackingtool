package portal.service.Impl;


import java.util.List;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import portal.entity.Report;

import portal.repository.ReportRepository;
import portal.service.FileService;
import portal.service.JsonWriter;
import portal.service.ReportService;
import portal.utility.ReportLevelType;

@Service
public class ReportServiceImpl implements ReportService{
	@Autowired
	private ReportRepository reportRepository;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private JsonWriter jsonWriter;

	@Override
	public List<Report> getReports() {

		return reportRepository.findAll();
	}

	@Override
	public Report Save(Report report) {

		return reportRepository.save(report);
	}

	@Override
	public Report update(Report report) {

		return reportRepository.saveAndFlush(report);
	}

	@Override
	public void delete(Report report) {

		reportRepository.delete(report);
	}

	@Override
	public Report getReport(Report report) {

		return reportRepository.findOne(report.getId());
	}

	@Override
	public void removeFile(String upload_foler, Report report) {
		if(!ObjectUtils.isEmpty(report.getTempate())) fileService.removeFile(upload_foler,report.getId().toString(), report.getTempate());
		
	}

	@Override
	public JSONObject getReportModel(Report report) throws Exception{
		JSONObject reportObj= new JSONObject();
		reportObj.put("id",report.getId());
		reportObj.put("ReportName",report.getReportName());
		reportObj.put("Description", report.getDescription());
		
		if(report.isContainReportLevel(ReportLevelType.STAKEHOLDER)) reportObj.put("Stakeholders", jsonWriter.getStakeholders(report.getStakeholders()));
		if(report.isContainReportLevel(ReportLevelType.APPLICATION)) reportObj.put("Applications", jsonWriter.getApplications(report.getApplications()));
		if(report.isContainReportLevel(ReportLevelType.APPINSTANCE)) reportObj.put("AppInstances", jsonWriter.getInstances(report.getAppInstances()));
		if(report.isContainReportLevel(ReportLevelType.DEPARTMENT)) reportObj.put("Departments", jsonWriter.getDepartment(report.getDepartments()));
		return reportObj;
		
		

	}


	
	
}
