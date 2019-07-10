package portal.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import portal.entity.Report;
import portal.entity.ReportLevel;
import portal.entity.Stakeholder;
import portal.models.DepartmentModel;
import portal.models.ReportLevelModel;
import portal.models.ReportModel;
import portal.models.SLARoleModel;
import portal.models.StakeholderModel;
import portal.repository.ReportRepository;
import portal.service.FileService;
import portal.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService{
	@Autowired
	private ReportRepository reportRepository;
	
	@Autowired
	private FileService fileService;

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
	public ReportModel getReportModel(Report report) {
		ReportModel reportModel=new ReportModel();
		
		reportModel.setId(report.getId());
		reportModel.setReportname(report.getReportName());
		reportModel.setDescription(report.getDescription());
		reportModel.setStakeholders(getStakeholderModels(report));
		reportModel.setReportlevels(getReportlevelModels(report));
		return reportModel;
	}
	
	private List<StakeholderModel> getStakeholderModels(Report report)
	{
		List<StakeholderModel> stakeholders=new ArrayList<StakeholderModel>();
		
		for(Stakeholder sholder:report.getStakeholders())
		{
			StakeholderModel  stakeholderModel=new StakeholderModel();
			stakeholderModel.setId(sholder.getId());
			stakeholderModel.setStakeholderName(sholder.getStakeholderName());
			stakeholderModel.setFirstname(sholder.getFirstname());
			stakeholderModel.setLastname(sholder.getLastname());
			stakeholderModel.setEmail(sholder.getEmail());
			stakeholderModel.setAddress(sholder.getAddress());
			stakeholderModel.setPosition(sholder.getPosition());
			stakeholderModel.setPhone(sholder.getPhone());
			stakeholderModel.setNote(sholder.getNote());
			stakeholderModel.setInfluence(sholder.getInfluence());
			stakeholderModel.setInterest(sholder.getInterest());
			stakeholderModel.setRaciforsyschanges(sholder.getRaciforsyschanges());
			
			if(!ObjectUtils.isEmpty(sholder.getDepartment()))
			{
				DepartmentModel department=new DepartmentModel();
				department.setId(sholder.getDepartment().getId());
				department.setDepartmentName(sholder.getDepartment().getDepartmentName());
				stakeholderModel.setDepartmentModel(department);
			}
			
			if(!ObjectUtils.isEmpty(sholder.getRole()))
			{
				SLARoleModel slarola=new SLARoleModel();
				slarola.setId(sholder.getRole().getId());
				slarola.setSLARoleName(sholder.getRole().getSLARoleName());
				stakeholderModel.setSlaRole(slarola);
			}

			stakeholders.add(stakeholderModel);
		}
		
		return stakeholders;
	}
	
	private List<ReportLevelModel> getReportlevelModels(Report report)
	{
		List<ReportLevelModel> reportlevels=new ArrayList<ReportLevelModel>();
		
		for(ReportLevel reportlevel:report.getReportlevels())
		{
			ReportLevelModel reportlevelmodel=new ReportLevelModel();
			reportlevelmodel.setId(reportlevel.getId());
			reportlevelmodel.setReportLevelType(reportlevel.getReportLevelType().name());
			reportlevels.add(reportlevelmodel);
		}
		
		return reportlevels;
	}
	
	
}
