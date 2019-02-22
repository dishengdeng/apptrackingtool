package portal.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import portal.entity.File;
import portal.service.AppInstanceService;
import portal.service.AppService;
import portal.service.CompanyService;
import portal.service.DepartmentService;
import portal.service.FileService;
import portal.service.SupportService;
import portal.utility.FileType;

@Controller
public class HomeController {
	
	private final String UPLOADED_FOLDER="files//application//";
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private AppService appService;
	
	@Autowired
	private SupportService supportService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private AppInstanceService appInstanceService;
	
	@Autowired
	private CompanyService companyService;
	
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
    public String DeleteApplication(@ModelAttribute("application") Application application) {
    	application.setDepartment(null);
    	application.setSupport(null);
    	application.setManufacturer(null);
    	application.removeAllInstance();
    	//appInstanceService.removeApplication(application);
    	//companyService.removeApplication(application);
    	appService.delete(application);
    	return "redirect:/";
    }
    @PostMapping("/updateApplication")
    public String updateApplication(@ModelAttribute("application") Application application) {
    	
    	appService.updateApp(application);
        return "redirect:/applicationdetail?id="+application.getId();
    }
    
    @GetMapping("/applicationdetail")
    public String ApplicationDetauk(@ModelAttribute("app") Application application,ModelMap model) {
    	//Application application = appService.findbyId(Long.valueOf(id));
    	
    	model.addAttribute("app",application);
    	
    	//--appInstance--
    	model.addAttribute("appinstances",application.getAppInstances());

    	model.addAttribute("instances",appInstanceService.findNotAssgined(application));
    	
    	//--Manufacturer-----
    	model.addAttribute("company",application.getManufacturer());
    	model.addAttribute("companys",companyService.findApplicationByNotAssigned(application));
    	
    	//--Support-----
    	model.addAttribute("support",application.getSupport());
    	model.addAttribute("supports",supportService.getAll());    	
    	
    	//--Department-----
    	model.addAttribute("department",application.getDepartment());
    	model.addAttribute("departments",departmentService.getAll());

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
    	
        return "redirect:/applicationdetail?id="+applicationEntity.getId();
        
    }   
    
    @GetMapping("/deleteApplicationInstance")
    public String deleteApplicationInstance(@RequestParam(name="instanceid", required=true) String instanceId,@RequestParam(name="applicationid", required=true) String applicationId) {
    	AppInstance appInstance=appInstanceService.getById(Long.valueOf(instanceId));
    	appInstance.setApplication(null);
    	
    	appInstanceService.updateAppInstance(appInstance);


    	return "redirect:/applicationdetail?id="+applicationId;
    }     
    
    //------Manufacturer---------------    
    @GetMapping("/deleteApplicationCompany")
    public String deleteApplicationCompany(@RequestParam(name="id", required=false) String id) {
    	Company company = companyService.getById(Long.valueOf(id));
    	Long applicationId = company.getApplication().getId();
    	company.setApplication(null);
    	companyService.updateCompany(company);
 
    	return "redirect:/applicationdetail?id="+applicationId;

    }
    
    @PostMapping("/addApplicationCompany")
    public String addOrupdateApplicationCompany(@ModelAttribute("company") Company company) {


    	companyService.removeApplication(company.getApplication());//remove application from previous company
    	companyService.updateApplication(company.getApplication(), company.getId());//assign it to a new company
        return "redirect:/applicationdetail?id="+company.getApplication().getId();
    }
  //-----------------------   
    //------Support---------------    
    @GetMapping("/deleteApplicationSupport")
    public String deleteApplicationSupport(@ModelAttribute("application") Application application) {

    	application.setSupport(null);
    	appService.updateApp(application);

    	return "redirect:/applicationdetail?id="+application.getId();
    }
    
    @PostMapping("/addApplicationSupport")
    public String addApplicationSupport(@ModelAttribute("app") Application application) {


    	appService.updateApp(application);
    	return "redirect:/applicationdetail?id="+application.getId();
    }    
    //------Department---------------    
    @GetMapping("/deleteApplicationDepartment")
    public String deleteApplicationDepartment(@ModelAttribute("application") Application application) {

    	application.setDepartment(null);
		appService.updateApp(application);
    	return "redirect:/applicationdetail?id="+application.getId();
    }
    
    @PostMapping("/addApplicationDepartment")
    public String addOrupdateInstanceDepartment(@ModelAttribute("application") Application application) {

		appService.updateApp(application);
        return "redirect:/applicationdetail?id="+application.getId();
    }
    //------file management----
    @PostMapping("/applicationupload")
    public String uploadapplication(@RequestParam("file") MultipartFile file,@ModelAttribute("application") Application application) {


    	File fileEntity = new File();
    	fileEntity.setFiletype(FileType.APPLICATION);
    	fileEntity.setAttachment(fileService.getFileName(file.getOriginalFilename()));
    	fileEntity.setApplication(application);

		fileService.uploadFile(file, UPLOADED_FOLDER,application.getId().toString(),fileEntity);
		
		return "redirect:/applicationdetail?id="+application.getId();
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
		return "redirect:/applicationdetail?id="+application.getId();
    	
    }    

}
