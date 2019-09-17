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

import portal.entity.File;




import portal.service.AppInstanceService;
import portal.service.AppService;
import portal.service.CompanyService;

import portal.service.FileService;

import portal.service.MessageSourceService;

import portal.service.ZacService;

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
	private ZacService zacService;
	

	
	@Autowired
	private AppInstanceService appInstanceService;
	
	@Autowired
	private CompanyService companyService;
	

    @Autowired
    private MessageSourceService messageSourceService;
	
    @GetMapping("/applications")
    public String home(ModelMap model) {
    	List<Application> apps = appService.getAll();
    	model.addAttribute("Applications",apps);
        return "index";
    }
    
    @PostMapping("/addApplication")
    public String addApplication(@ModelAttribute("application") Application application) {

    	appService.addApplication(application);
        return "redirect:/applications";
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
    	return "redirect:/applications";
    }
    
    @PostMapping("/updateApplication")
    public String updateApplication(@ModelAttribute("appmodel") Application application,BindingResult bindingResult,ModelMap model) {
    	

    	
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
    

    
    private void setModel(ModelMap model,Application application)
    {
    	Application appEntity=appService.findbyId(application.getId());
    	model.addAttribute("appmodel",application);
    	model.addAttribute("app",appEntity);

    	//--appInstance--
    	model.addAttribute("appinstances",appEntity.getAppInstances());

    	model.addAttribute("instances",appInstanceService.findNotAssgined());
    	

    	
    	//--zac---

    	model.addAttribute("zacs",zacService.getAll());
    	

    	
    	//--Manufacturer-----
    	model.addAttribute("companies",appEntity.getManufacturers());
    	model.addAttribute("companys",companyService.findAllManufacturer());
    	


    }

}
