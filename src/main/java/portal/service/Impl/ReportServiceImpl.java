package portal.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import portal.entity.Report;
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
	
	
}
