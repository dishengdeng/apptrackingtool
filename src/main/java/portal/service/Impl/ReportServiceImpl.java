package portal.service.Impl;


import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import portal.entity.Report;

import portal.repository.ReportRepository;
import portal.service.AppInstanceService;
import portal.service.AppService;
import portal.service.CompanyService;
import portal.service.ContractService;
import portal.service.DepartmentService;
import portal.service.FileService;
import portal.service.JsonWriter;
import portal.service.LicenseService;
import portal.service.ReportService;
import portal.service.ServerService;
import portal.service.SiteService;
import portal.service.StakeholderService;
import portal.service.SupportService;
import portal.service.ZacService;
import portal.service.ZoneService;
import portal.utility.JSONObjectWithEmpty;
import portal.utility.ReportLevelType;

@Service
public class ReportServiceImpl implements ReportService{
	@Autowired
	private ReportRepository reportRepository;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private JsonWriter jsonWriter;
	
	@Autowired
	private AppService appService;
	
	@Autowired
	private SupportService supportService;
	
	@Autowired
	private ContractService contractService;
	
	@Autowired
	private ZoneService zoneService;
	
	@Autowired
	private ServerService serverService;
	
	@Autowired
	private LicenseService licenseService;
	
	@Autowired
	private ZacService zacService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private SiteService siteService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private AppInstanceService instanceService;
	
	@Autowired
	private StakeholderService stakeholderService;

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
	public JSONObjectWithEmpty getReportModel(Report report) throws Exception{
		JSONObjectWithEmpty reportObj= new JSONObjectWithEmpty();
		reportObj.put("id",report.getId());
		reportObj.put("ReportName",report.getReportName());
		reportObj.put("Description", report.getDescription());
		
		if(report.isContainReportLevel(ReportLevelType.STAKEHOLDER)) reportObj.put("Stakeholders", jsonWriter.getStakeholders(new HashSet<>(stakeholderService.getAll())));
		if(report.isContainReportLevel(ReportLevelType.APPLICATION)) reportObj.put("Applications", jsonWriter.getApplications(new HashSet<>(appService.getAll())));
		if(report.isContainReportLevel(ReportLevelType.APPINSTANCE)) reportObj.put("AppInstances", jsonWriter.getInstances(new HashSet<>(instanceService.getAll())));
		if(report.isContainReportLevel(ReportLevelType.DEPARTMENT)) reportObj.put("Departments", jsonWriter.getDepartment(new HashSet<>(departmentService.getAll())));
		if(report.isContainReportLevel(ReportLevelType.ZONE)) reportObj.put("Zones", jsonWriter.getZones(new HashSet<>(zoneService.getAll())));
		if(report.isContainReportLevel(ReportLevelType.SITE)) reportObj.put("Sites", jsonWriter.getSites(new HashSet<>(siteService.getAll())));
		if(report.isContainReportLevel(ReportLevelType.VENDOR)) reportObj.put("Vendors", jsonWriter.getVendors(new HashSet<>(companyService.getAll())));
		if(report.isContainReportLevel(ReportLevelType.CONTRACT)) reportObj.put("Contracts", jsonWriter.getContracts(new HashSet<>(contractService.getAll())));
		if(report.isContainReportLevel(ReportLevelType.LICENSE)) reportObj.put("Licenses", jsonWriter.getLicense(new HashSet<>(licenseService.getAll())));
		if(report.isContainReportLevel(ReportLevelType.SERVER)) reportObj.put("Servers", jsonWriter.getServers(new HashSet<>(serverService.getAll())));
		if(report.isContainReportLevel(ReportLevelType.SUPPORT)) reportObj.put("Supports", jsonWriter.getSupports(new HashSet<>(supportService.getAll())));
		if(report.isContainReportLevel(ReportLevelType.ZAC)) reportObj.put("Zacs", jsonWriter.getZacs(new HashSet<>(zacService.getAll())));
		return reportObj;
		
		

	}

	@Override
	public Report getById(Long id) {

		return reportRepository.findOne(id);
	}


	
	
}
