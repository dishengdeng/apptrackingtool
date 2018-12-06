package portal.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import portal.entity.Application;
import portal.models.App;
import portal.service.AppService;

@Controller
public class HomeController {
	@Autowired
	AppService appService;
    @GetMapping("/")
    public String home(ModelMap model) {
    	List<App> apps = appService.getAllApp();
    	model.addAttribute("Applications",apps);
    	model.addAttribute("app", new App());
        return "index";
    }
    
    @PostMapping("/addApplication")
    public String addApplication(@ModelAttribute("app") App app) {
    	Application application = new Application();
    	application.setAppName(app.getAppName());
    	application.setAppDecomminsionDate(app.getAppDecomminsionDate());
    	appService.addApplication(application);
        return "redirect:/";
    }
    
    @GetMapping("/addApplication")
    public String CreateApplication(ModelMap model) {
    	model.addAttribute("app", new App());
        return "addApplication";
    }
    
    @GetMapping("/deleteApplication")
    public String DeleteApplication(@RequestParam(name="id", required=true) String id,@RequestParam(name="name", required=true) String name) {
    	Application application = new Application();
    	application.setId(Long.valueOf(id));
    	application.setAppName(name);
    	//application.setAppDecomminsionDate(Convertor.SQLDate(app.getAppDecomminsionDate()));
    	appService.delete(application);
    	return "redirect:/";
    }
     

}
