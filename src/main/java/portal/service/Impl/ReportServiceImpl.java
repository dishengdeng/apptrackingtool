package portal.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import portal.entity.Report;
import portal.repository.ReportRepository;
import portal.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService{
	@Autowired
	private ReportRepository reportRepository;

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
	
	
}
