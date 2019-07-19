package portal.controllers;







import java.util.stream.Collectors;

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

import portal.entity.AppInstance;
import portal.entity.Application;
import portal.entity.Company;
import portal.entity.Contract;
import portal.entity.Department;
import portal.entity.File;
import portal.entity.License;
import portal.entity.Report;
import portal.entity.Server;
import portal.entity.Site;
import portal.entity.Stakeholder;
import portal.entity.Support;
import portal.entity.Zac;
import portal.entity.Zone;
import portal.report.ReportManager;
import portal.service.AppInstanceService;
import portal.service.AppService;
import portal.service.CompanyService;
import portal.service.ContractService;
import portal.service.DepartmentService;
import portal.service.FileService;
import portal.service.ReportLevelService;
import portal.service.ReportService;
import portal.service.ServerService;
import portal.service.SiteService;
import portal.service.JsonWriter;
import portal.service.LicenseService;
import portal.service.StakeholderService;
import portal.service.SupportService;
import portal.service.ZacService;
import portal.service.ZoneService;
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
    	model.addAttribute("departments", departmentService.getAll());
    	model.addAttribute("apps", appService.getAll());
    	model.addAttribute("appUnassginedInstances",instanceService.getUnassginedAppInstances());
    	model.addAttribute("appAssginedInstances",appService.getAll().stream().sorted().collect(Collectors.toList()));
    	model.addAttribute("zones", zoneService.getAll());
    	model.addAttribute("sites", siteService.getAll());
    	model.addAttribute("vendors", companyService.getAll());
    	model.addAttribute("contracts", contractService.getAll());
    	model.addAttribute("licenses", licenseService.getAll());
    	model.addAttribute("servers", serverService.getAll());
    	model.addAttribute("supports", supportService.getAll());
    	model.addAttribute("zacs", zacService.getAll());
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
	
	
	 //-----application------
	@GetMapping("/deletereportapplication")
	public String deletereportapplication(@ModelAttribute("app") Application application,@ModelAttribute("report") Report report)
	{
		report.removeApplication(application);
		reportService.update(report);
		return "redirect:/reportdetail?report="+report.getId();
	}
	
	@PostMapping("/addreportapplication")
	public String addreportapplication(@ModelAttribute("report") Report report)
	{
		reportService.update(report);
		return "redirect:/reportdetail?report="+report.getId();
	}
	
	 //----- appinstance------
	@GetMapping("/deletereportappinstance")
	public String deletereportappinstance(@ModelAttribute("appinstance")  AppInstance  appinstance,@ModelAttribute("report") Report report)
	{
		report.removeAppInstance(appinstance);
		reportService.update(report);
		return "redirect:/reportdetail?report="+report.getId();
	}
	
	@PostMapping("/addreportappinstance")
	public String addreportappinstance(@ModelAttribute("report") Report report)
	{
		reportService.update(report);
		return "redirect:/reportdetail?report="+report.getId();
	}
	
	 //----- department------
	@GetMapping("/deletereportdepartment")
	public String deletereportdepartment(@ModelAttribute("department")  Department  department,@ModelAttribute("report") Report report)
	{
		report.removeDepartment(department);
		reportService.update(report);
		return "redirect:/reportdetail?report="+report.getId();
	}
	
	@PostMapping("/addreportdepartment")
	public String addreportdepartment(@ModelAttribute("report") Report report)
	{
		reportService.update(report);
		return "redirect:/reportdetail?report="+report.getId();
	}
	
	 //----- zone------
	@GetMapping("/deletereportzone")
	public String deletereportzone(@ModelAttribute("zone")  Zone  zone,@ModelAttribute("report") Report report)
	{
		report.removeZone(zone);
		reportService.update(report);
		return "redirect:/reportdetail?report="+report.getId();
	}
	
	@PostMapping("/addreportzone")
	public String addreportzone(@ModelAttribute("report") Report report)
	{
		reportService.update(report);
		return "redirect:/reportdetail?report="+report.getId();
	}
	
	 //----- site------
	@GetMapping("/deletereportsite")
	public String deletereportsite(@ModelAttribute("site")  Site  site,@ModelAttribute("report") Report report)
	{
		report.removeSite(site);
		reportService.update(report);
		return "redirect:/reportdetail?report="+report.getId();
	}
	
	@PostMapping("/addreportsite")
	public String addreportsite(@ModelAttribute("report") Report report)
	{
		reportService.update(report);
		return "redirect:/reportdetail?report="+report.getId();
	}
	
	 //----- company------
	@GetMapping("/deletereportcompany")
	public String deletereportcompany(@ModelAttribute("company")  Company  company,@ModelAttribute("report") Report report)
	{
		report.removeCompany(company);
		reportService.update(report);
		return "redirect:/reportdetail?report="+report.getId();
	}
	
	@PostMapping("/addreportcompany")
	public String addreportcompany(@ModelAttribute("report") Report report)
	{
		reportService.update(report);
		return "redirect:/reportdetail?report="+report.getId();
	}
	
	 //----- contract------
	@GetMapping("/deletereportcontract")
	public String deletereportcontract(@ModelAttribute("contract")  Contract  contract,@ModelAttribute("report") Report report)
	{
		report.removeContract(contract);
		reportService.update(report);
		return "redirect:/reportdetail?report="+report.getId();
	}
	
	@PostMapping("/addreportcontract")
	public String addreportcontract(@ModelAttribute("report") Report report)
	{
		reportService.update(report);
		return "redirect:/reportdetail?report="+report.getId();
	}
	
	 //----- license------
	@GetMapping("/deletereportlicense")
	public String deletereportlicense(@ModelAttribute("license")  License  license,@ModelAttribute("report") Report report)
	{
		report.removeLicense(license);
		reportService.update(report);
		return "redirect:/reportdetail?report="+report.getId();
	}
	
	@PostMapping("/addreportlicense")
	public String addreportlicense(@ModelAttribute("report") Report report)
	{
		reportService.update(report);
		return "redirect:/reportdetail?report="+report.getId();
	}
	
	 //----- server------
	@GetMapping("/deletereportserver")
	public String deletereportserver(@ModelAttribute("server")  Server  server,@ModelAttribute("report") Report report)
	{
		report.removeServer(server);
		reportService.update(report);
		return "redirect:/reportdetail?report="+report.getId();
	}
	
	@PostMapping("/addreportserver")
	public String addreportserver(@ModelAttribute("report") Report report)
	{
		reportService.update(report);
		return "redirect:/reportdetail?report="+report.getId();
	}
	
	 //----- support------
	@GetMapping("/deletereportsupport")
	public String deletereportsupport(@ModelAttribute("support")  Support  support,@ModelAttribute("report") Report report)
	{
		report.removeSupport(support);
		reportService.update(report);
		return "redirect:/reportdetail?report="+report.getId();
	}
	
	@PostMapping("/addreportsupport")
	public String addreportsupport(@ModelAttribute("report") Report report)
	{
		reportService.update(report);
		return "redirect:/reportdetail?report="+report.getId();
	}
	 //----- zac------
	@GetMapping("/deletereportzac")
	public String deletereportzac(@ModelAttribute("zac")  Zac  zac,@ModelAttribute("report") Report report)
	{
		report.removeZac(zac);
		reportService.update(report);
		return "redirect:/reportdetail?report="+report.getId();
	}
	
	@PostMapping("/addreportzac")
	public String addreportzac(@ModelAttribute("report") Report report)
	{
		reportService.update(report);
		return "redirect:/reportdetail?report="+report.getId();
	}	
	
	//------report template management----
    @PostMapping("/reporttemplateupload")
    public String departmentupload(@RequestParam("file") MultipartFile file,ModelMap model,@ModelAttribute("report") Report report) {

    	//remove the original one, in case of upload a file with different name
    	reportService.removeFile(UPLOADED_FOLDER, report);

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
