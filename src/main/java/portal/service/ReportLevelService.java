package portal.service;

import java.util.List;

import portal.entity.ReportLevel;

public interface ReportLevelService {
	public List<ReportLevel> getReportLevels();
	public ReportLevel save(ReportLevel reportLevel);
	public ReportLevel update(ReportLevel reportLevel);
	public void delete(ReportLevel reportLevel);
	public ReportLevel findReportLevel(ReportLevel reportLevel);
}
