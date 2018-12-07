package portal.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import portal.entity.AppInstance;
import portal.entity.Application;
import portal.service.AppInstanceService;
import portal.service.AppService;

@Controller
public class HomeController {
	@Autowired
	AppService appService;
	
	@Autowired
	AppInstanceService appInstanceService;
	
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
    public String DeleteApplication(@RequestParam(name="id", required=true) String id,@RequestParam(name="name", required=true) String name) {
    	Application application = appService.findbyId(Long.valueOf(id));
    	appInstanceService.removeApplication(application);
    	appService.delete(application);
    	return "redirect:/";
    }
    @PostMapping("/updateApplication")
    public String updateApplication(@ModelAttribute("application") Application application) {
    	
    	appService.updateApp(application);
        return "redirect:/applicationdetail?id="+application.getId();
    }
    
    @GetMapping("/applicationdetail")
    public String ApplicationDetauk(@RequestParam(name="id", required=true) String id,ModelMap model) {
    	Application application = appService.findbyId(Long.valueOf(id));
    	
    	model.addAttribute("app",application);
    	
    	//--appInstance--
    	model.addAttribute("appinstances",application.getAppInstances());

    	model.addAttribute("instances",appInstanceService.findNotAssgined(application));

    	return "applicationdetail";
    } 

    //--appInstance--
    @PostMapping("/addApplicationInstance")
    public String addApplicationInstance(@ModelAttribute("app") Application application) {
    	Application applicationEntity = appService.findbyId(application.getId());
    	List<AppInstance> appInstances = application.getAppInstances();
    	
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
    
   
     

}
