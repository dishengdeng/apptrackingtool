package portal.controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import portal.entity.Report;
import portal.entity.ReportLevel;
import portal.service.ReportLevelService;

@Controller
public class ReportLevelController {
	@Autowired
	private ReportLevelService reportLevelService;
	
    @GetMapping("/reportlevels")
    public String reportlevelstable(ModelMap model) {

    	model.addAttribute("reportlevels", reportLevelService.getReportLevels());

        return "reportlevels";
    }
    
    @PostMapping("/addreportlevel")
    public String addreportlevel(@ModelAttribute("reportlevel") ReportLevel reportLevel) {

    	reportLevelService.save(reportLevel);
        return "redirect:/reportlevels";
    }
    
    @GetMapping("/addreportlevel")
    public String createreportlevel(ModelMap model) {
    	model.addAttribute("reportlevel", new Report());
        return "addreportlevel";
    }
    
    @GetMapping("/deletereportlevel")
    public String deletereportlevel(@ModelAttribute("reportlevel") ReportLevel reportLevel) {
    	reportLevel.removeAlldependence();
    	reportLevelService.delete(reportLevel);
    	return "redirect:/reportlevels";
    }
    

}
