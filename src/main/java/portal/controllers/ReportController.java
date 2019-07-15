package portal.controllers;







import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.multipart.MultipartFile;



import portal.entity.File;
import portal.entity.Report;
import portal.entity.Stakeholder;

import portal.report.ReportManager;
import portal.service.FileService;
import portal.service.ReportLevelService;
import portal.service.ReportService;
import portal.service.JsonWriter;
import portal.service.StakeholderService;
import portal.utility.FileType;
import portal.utility.ReportFormat;

@Controller
public class ReportController {
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private ReportLevelService reportLevelService;
	
	@Autowired
	private JsonWriter jsonWriter;
	
	@Autowired
	private StakeholderService stakeholderService;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private ReportManager reportManager;
	
	
	private final String UPLOADED_FOLDER="reports//";
	
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
    	reportService.removeFile(UPLOADED_FOLDER, report);
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
    	model.addAttribute("stakeholders", stakeholderService.getAll());
        return "reportdetail";
    }
    
    //---get report model
    @GetMapping(value="/reportmodel")
    public void addQeustionDepartmentByJQuery(@ModelAttribute("report") Report report,HttpServletResponse response) throws Exception {


    	jsonWriter.writeJsonWithNoNull(reportService.getReportModel(report)).writeTo(response.getOutputStream());
    	response.setContentType("application/json");
    	response.setHeader("Content-Disposition", "attachment; filename="+report.getReportName()+".json");
    	response.flushBuffer();
   
    }    
    
    //-----stakeholder------
	@GetMapping("/deletereportstakeholder")
	public String deletereportstakeholder(@ModelAttribute("stakeholder") Stakeholder stakeholder,@ModelAttribute("report") Report report)
	{
		report.removeStakeholder(stakeholder);
		reportService.update(report);
		return "redirect:/reportdetail?report="+report.getId();
	}
	
	@PostMapping("/addreportstakeholder")
	public String addreportstakeholder(@ModelAttribute("report") Report report)
	{
		reportService.update(report);
		return "redirect:/reportdetail?report="+report.getId();
	}
	
	//------report template management----
    @PostMapping("/reporttemplateupload")
    public String departmentupload(@RequestParam("file") MultipartFile file,ModelMap model,@ModelAttribute("report") Report report) {

    	

    	File fileEntity = new File();
    	fileEntity.setFiletype(FileType.REPORT);
    	fileEntity.setAttachment(fileService.getFileName(file.getOriginalFilename()));
    	fileEntity.setReport(report);

    	
    	reportManager.compileTemplate(fileService.uploadFile(file, UPLOADED_FOLDER,report.getId().toString(),fileEntity), UPLOADED_FOLDER);

    	return "redirect:/reportdetail?report="+report.getId();
    }
    
    @GetMapping("/downloadreporttemplate")
    public ResponseEntity<Resource> downloadfile(@ModelAttribute("file") File file,HttpServletRequest request)
    {
    	

    	return fileService.downloadFile(UPLOADED_FOLDER,file.getReport().getId().toString(),file , request);
    }

    @GetMapping("/deletereporttemplate")
    public String deletefile(@ModelAttribute("file") File file,ModelMap model,@ModelAttribute("report") Report report)
    {
    	

		fileService.removeFile(UPLOADED_FOLDER,file.getReport().getId().toString(), file);
		
		

    	
    	return "redirect:/reportdetail?report="+report.getId();
    	
    }	
 //--Run report   
    @GetMapping("/getreport")
    public void downloadfile(@ModelAttribute("report") Report report,HttpServletResponse response,@ModelAttribute("reportformat") String reportFormat)
    {
    	
    	reportManager.runreport(report, UPLOADED_FOLDER,response,ReportFormat.valueOf(reportFormat));
    	
    }
}
