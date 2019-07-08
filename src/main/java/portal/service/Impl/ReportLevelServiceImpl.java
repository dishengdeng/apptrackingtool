package portal.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import portal.entity.ReportLevel;
import portal.repository.ReportLevelRepository;
import portal.service.ReportLevelService;

@Service
public class ReportLevelServiceImpl implements ReportLevelService{
	@Autowired
	private ReportLevelRepository reportLevelRepository;
	
	@Override
	public List<ReportLevel> getReportLevels() {
		
		return reportLevelRepository.findAll();
	}

	@Override
	public ReportLevel save(ReportLevel reportLevel) {
		
		return reportLevelRepository.save(reportLevel);
	}

	@Override
	public ReportLevel update(ReportLevel reportLevel) {
		
		return reportLevelRepository.saveAndFlush(reportLevel);
	}

	@Override
	public void delete(ReportLevel reportLevel) {
		
		reportLevelRepository.delete(reportLevel);
	}

	@Override
	public ReportLevel findReportLevel(ReportLevel reportLevel) {
		
		return reportLevelRepository.findOne(reportLevel.getId());
	}

}
