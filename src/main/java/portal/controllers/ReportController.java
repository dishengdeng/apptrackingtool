package portal.controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import portal.entity.Report;
import portal.service.ReportLevelService;
import portal.service.ReportService;

@Controller
public class ReportController {
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private ReportLevelService reportLevelService;
	
    @GetMapping("/reports")
    public String ReportsTable(ModelMap model) {

    	model.addAttribute("reports", reportService.getReports());

        return "reports";
    }
    
    @PostMapping("/addreport")
    public String addreport(@ModelAttribute("report") Report report) {

    	reportService.Save(report);
        return "redirect:/reports";
    }
    
    @GetMapping("/addreport")
    public String createZac(ModelMap model) {
    	model.addAttribute("report", new Report());
    	model.addAttribute("reportlevels", reportLevelService.getReportLevels());
        return "addreport";
    }
    
    @GetMapping("/deletereport")
    public String deleteZac(@ModelAttribute("report") Report report) {

    	report.removeAlldependence();
    	reportService.delete(report);
    	return "redirect:/reports";
    }
    
    @PostMapping("/updatereport")
    public String updatereport(@ModelAttribute("report") Report report) {

    	reportService.update(report);
    	return "redirect:/reportdetail?report="+report.getId();
    }
    
    @GetMapping("/reportdetail")
    public String reportdetail(@ModelAttribute("report") Report report,ModelMap model) {
    	model.addAttribute("report",report);
 	
        return "reportdetail";
    }
}
