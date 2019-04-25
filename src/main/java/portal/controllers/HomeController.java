package portal.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import portal.entity.AppInstance;
import portal.entity.Application;
import portal.entity.Company;
import portal.entity.Contract;
import portal.entity.File;
import portal.entity.Project;
import portal.entity.Site;
import portal.entity.Support;
import portal.entity.Zone;
import portal.service.AppInstanceService;
import portal.service.AppService;
import portal.service.CompanyService;
import portal.service.ContractService;
import portal.service.DepartmentService;
import portal.service.FileService;
import portal.service.MessageSourceService;
import portal.service.ProjectService;
import portal.service.SiteService;
import portal.service.SupportService;
import portal.service.ZoneService;
import portal.utility.Action;
import portal.utility.FileType;
import portal.utility.Status;
import portal.utility.messages;
import portal.validator.AppValidator;

@Controller
public class HomeController {
	
	private final String UPLOADED_FOLDER="files//application//";
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private AppValidator appValidator;	
	
	@Autowired
	private AppService appService;
	
	@Autowired
	private SupportService supportService;
	
	@Autowired
	private ContractService contractService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private ZoneService zoneService;
	
	@Autowired
	private SiteService siteService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private AppInstanceService appInstanceService;
	
	@Autowired
	private CompanyService companyService;
	
    @Autowired
    private MessageSourceService messageSourceService;
	
    @GetMapping("/")
    public String home(ModelMap model) {
    	List<Application> apps = appService.getAll();
    	model.addAttribute("Applications",apps);
        return "index";
    }
    
    @PostMapping("/addApplication")
    public String addApplication(@ModelAttribute("application") Application application) {

    	appService.addApplication(application);
        return "redirect:/";
    }
    
    @GetMapping("/addApplication")
    public String CreateApplication(ModelMap model) {
    	model.addAttribute("application", new Application());
        return "addApplication";
    }
    
    @GetMapping("/deleteApplication")
    public String DeleteApplication(@ModelAttribute("application") Application application,ModelMap model) {
    	
    	for(AppInstance app:application.getAppInstances())
    	{
    		if(app.getAppStatus()==Status.Active)
    		{
            	model.addAttribute("message",messageSourceService.getMessage(messages.APP_DELETE_ERROR.toString(),new Object[]{application.getAppName()},LocaleContextHolder.getLocale()));
            	model.addAttribute("Applications",appService.getAll());
                return "index";
    		}
    	}

    	application.removeAllDependence();

    	appService.removFiles(UPLOADED_FOLDER, application);
    	appService.delete(application);
    	return "redirect:/";
    }
    
    @PostMapping("/updateApplication")
    public String updateApplication(@ModelAttribute("appmodel") Application application,BindingResult bindingResult,ModelMap model) {

        getUpdatedApp(application);
    	
    	appValidator.validateStatus(application, bindingResult, Action.UPDATE);
        if (bindingResult.hasErrors()) {
        	setModel(model,application);
            return "applicationdetail";
        }
        appService.saveDetails(application);
        return "redirect:/applicationdetail?app="+application.getId();
    }
    

    
    @GetMapping("/applicationdetail")
    public String ApplicationDetauk(@ModelAttribute("app") Application application,ModelMap model) {

    	setModel(model,application);

    	return "applicationdetail";
    } 

    //--appInstance--
    @PostMapping("/addApplicationInstance")
    public String addApplicationInstance(@ModelAttribute("app") Application application) {
    	Application applicationEntity = appService.findbyId(application.getId());
    	List<AppInstance> appInstances = new ArrayList<>(application.getAppInstances());
    	
    	for(AppInstance appInstance:appInstances)
    	{
    		appInstance.setApplication(applicationEntity);
    		appInstanceService.updateAppInstance(appInstance);
    	}
    	
    	return "redirect:/applicationdetail?app="+application.getId();
        
    }   
    
    @GetMapping("/deleteApplicationInstance")
    public String deleteApplicationInstance(@RequestParam(name="instanceid", required=true) String instanceId,@RequestParam(name="applicationid", required=true) String applicationId) {
    	AppInstance appInstance=appInstanceService.getById(Long.valueOf(instanceId));
    	appInstance.setApplication(null);
    	
    	appInstanceService.updateAppInstance(appInstance);


    	return "redirect:/applicationdetail?app="+applicationId;
    } 
    
    //----project------
	@GetMapping("/deleteApplicationProject")
	public String deleteApplicationProject(@ModelAttribute("application") Application application,@ModelAttribute("project") Project project)
	{
		project.removeApplication(application);
		projectService.updateProject(project);
		return "redirect:/applicationdetail?app="+application.getId();
	}
	
	@PostMapping("/addApplicationProject")
	public String addProjectApplication(@ModelAttribute("app") Application application)
	{
		appService.updateApp(application);
		return "redirect:/applicationdetail?app="+application.getId();
	}
	
    //------Sites---------------    
    @GetMapping("/deleteAppSite")
    public String deleteAppSite(@ModelAttribute("site") Site site,@ModelAttribute("app") Application application) {


    	
    	site.removeApplication(application);
    	
    	siteService.updateSite(site);
    	
    	
    	return "redirect:/applicationdetail?app="+application.getId();
    }
    
    @PostMapping("/addAppSite")
    public String addAppSite(@ModelAttribute("app") Application application) {


    	
    	appService.updateApp(application);
    	
    	return "redirect:/applicationdetail?app="+application.getId();
    }
    
    //------Zones---------------    
    @GetMapping("/deleteappzone")
    public String deleteappzone(@ModelAttribute("zone") Zone zone,@ModelAttribute("app") Application application) {


    	
    	zone.removeApplication(application);
    	zoneService.updateZone(zone);
    	
    	
    	return "redirect:/applicationdetail?app="+application.getId();
    }
    
    @PostMapping("/addAppZone")
    public String addAppZone(@ModelAttribute("app") Application application) {


    	
    	appService.updateApp(application);
    	
    	return "redirect:/applicationdetail?app="+application.getId();
    }	
	
	
    
    //------Manufacturer---------------    
    @GetMapping("/deleteApplicationCompany")
    public String deleteApplicationCompany(@ModelAttribute("company") Company company,@ModelAttribute("application") Application application) {
    	company.removeApplication(application);
    	companyService.updateCompany(company);
 
    	return "redirect:/applicationdetail?app="+application.getId();

    }
    
    @PostMapping("/addApplicationCompany")
    public String addOrupdateApplicationCompany(@ModelAttribute("application") Application application) {


    	appService.updateApp(application);
        return "redirect:/applicationdetail?app="+application.getId();
    }
  //----------------------- 
    //------Contract---------------    
    @GetMapping("/deleteApplicationContract")
    public String deleteApplicationContract(@ModelAttribute("application") Application application,@ModelAttribute("contract") Contract contract) {

    	contract.removeApplication(application);
    	contractService.updateContract(contract);

    	return "redirect:/applicationdetail?app="+application.getId();
    }
    
    @PostMapping("/addApplicationContract")
    public String addApplicationContract(@ModelAttribute("app") Application application) {


    	appService.updateApp(application);
    	return "redirect:/applicationdetail?app="+application.getId();
    }    
    
    //------Support---------------    
    @GetMapping("/deleteApplicationSupport")
    public String deleteApplicationSupport(@ModelAttribute("application") Application application,@ModelAttribute("support") Support support) {

    	support.removeApplication(application);
    	supportService.updateSupport(support);

    	return "redirect:/applicationdetail?app="+application.getId();
    }
    
    @PostMapping("/addApplicationSupport")
    public String addApplicationSupport(@ModelAttribute("app") Application application) {


    	appService.updateApp(application);
    	return "redirect:/applicationdetail?app="+application.getId();
    }    
    //------Department---------------    
    @GetMapping("/deleteApplicationDepartment")
    public String deleteApplicationDepartment(@ModelAttribute("application") Application application) {

    	application.setDepartment(null);
		appService.updateApp(application);
    	return "redirect:/applicationdetail?app="+application.getId();
    }
    
    @PostMapping("/addApplicationDepartment")
    public String addOrupdateInstanceDepartment(@ModelAttribute("application") Application application) {

		appService.updateApp(application);
        return "redirect:/applicationdetail?app="+application.getId();
    }
    //------file management----
    @PostMapping("/applicationupload")
    public String uploadapplication(@RequestParam("file") MultipartFile file,@ModelAttribute("application") Application application) {


    	File fileEntity = new File();
    	fileEntity.setFiletype(FileType.APPLICATION);
    	fileEntity.setAttachment(fileService.getFileName(file.getOriginalFilename()));
    	fileEntity.setApplication(application);

		fileService.uploadFile(file, UPLOADED_FOLDER,application.getId().toString(),fileEntity);
		
		return "redirect:/applicationdetail?app="+application.getId();
    }
    
    @GetMapping("/downloadapplication")
    public ResponseEntity<Resource> downloadfile(@ModelAttribute("file") File file,HttpServletRequest request)
    {
    	


    	return fileService.downloadFile(UPLOADED_FOLDER,file.getApplication().getId().toString(),file , request);
    }

    @GetMapping("/deleteapplicationfile")
    public String deletefile(@ModelAttribute("file") File file,@ModelAttribute("application") Application application)
    {
    	

    	
		fileService.removeFile(UPLOADED_FOLDER, file.getApplication().getId().toString(),file);
		return "redirect:/applicationdetail?app="+application.getId();
    	
    }
    
    //--private methods--
    private void getUpdatedApp(Application application)
    {
        Application appEntity=appService.findbyId(application.getId());

        application.setAppInstances(appEntity.getAppInstances());
        application.getSupports().forEach(support->{
        	application.addSupport(support);
        });        
        application.setManufacturers(appEntity.getManufacturers());
        application.setDepartment(appEntity.getDepartment());
        application.setFiles(appEntity.getFiles());
 
    }
    
    private void setModel(ModelMap model,Application appEntity)
    {
    	model.addAttribute("appmodel",appEntity);
    	model.addAttribute("app",appEntity);
    	
    	//--appInstance--
    	model.addAttribute("appinstances",appEntity.getAppInstances());

    	model.addAttribute("instances",appInstanceService.findNotAssgined(appEntity));
    	
    	//--project---
    	model.addAttribute("applicationprojects",appEntity.getProjects());
    	model.addAttribute("projects",projectService.getAll());  
    	
    	//--zone---
    	model.addAttribute("appzones",appEntity.getZones());
    	model.addAttribute("zones",zoneService.getAll());
    	
    	//--site---
    	model.addAttribute("appsites",appEntity.getSites());
    	model.addAttribute("sites",siteService.getAll());
    	
    	//--Manufacturer-----
    	model.addAttribute("companies",appEntity.getManufacturers());
    	model.addAttribute("companys",companyService.findAllManufacturer());
    	
    	//--Contract-----
    	model.addAttribute("appcontracts",appEntity.getContracts());
    	model.addAttribute("contracts",contractService.getAll());    	
    	
    	//--Support-----
    	model.addAttribute("appsupports",appEntity.getSupports());
    	model.addAttribute("supports",supportService.getAll());    	
    	
    	//--Department-----
    	model.addAttribute("department",appEntity.getDepartment());
    	model.addAttribute("departments",departmentService.getAll());
    }

}
