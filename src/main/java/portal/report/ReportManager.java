package portal.report;



import javax.servlet.http.HttpServletResponse;

import portal.entity.File;
import portal.entity.Report;


public interface ReportManager {
	public void compileTemplate(File template,String templatepath);
	public void runreport(Report report, String templatepath,HttpServletResponse response);
}
