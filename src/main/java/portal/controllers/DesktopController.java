package portal.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import portal.entity.Desktop;
import portal.service.AppInstanceService;
import portal.service.DesktopService;



@Controller
public class DesktopController {
	@Autowired
	private DesktopService desktopService;
	
	@Autowired
	private AppInstanceService appInstanceService;
	
	
    @GetMapping("/desktops")
    public String desktoptable(ModelMap model) {
    	model.addAttribute("desktops", desktopService.getAll());
        return "desktops";
    }
    
    @PostMapping("/addDesktop")
    public String addDesktop(@ModelAttribute("desktop") Desktop desktop) {

    	desktopService.addDesktop(desktop);
        return "redirect:/desktops";
    }
 
    @PostMapping("/updateDesktop")
    public String updateDesktop(@ModelAttribute("desktopModel") Desktop desktop) {

    	desktopService.updateDesktop(desktop);
        return "redirect:/desktopdetail?desktop="+desktop.getId();
    }
    
    @GetMapping("/addDesktop")
    public String CreateDesktop(ModelMap model) {
    	model.addAttribute("desktop", new Desktop());
        return "addDesktop";
    }
    
    @GetMapping("/deleteDesktop")
    public String deleteDesktop(@ModelAttribute("desktop") Desktop desktop) {

    	desktop.getAppInstance().setDesktop(null);
    	desktop.setAppInstance(null);
    	
    	desktopService.delete(desktop);
    	return "redirect:/desktops";
    }
    
    @GetMapping("/desktopdetail")
    public String CreateDesktop(@ModelAttribute("desktop") Desktop desktop,ModelMap model) {
    	model.addAttribute("desktop",desktop);
    	model.addAttribute("appInstances",appInstanceService.getAll());
        return "desktopdetail";
    }    
//---App instance---
    @GetMapping("/deleteDesktopInstance")
    public String deleteDesktopInstance(@ModelAttribute("desktop") Desktop desktop) {
    	desktop.setAppInstance(null);
    	desktopService.updateDesktop(desktop);
        return "redirect:/desktopdetail?desktop="+desktop.getId();
    } 
    
    @PostMapping("/addDesktopInstance")
    public String addDesktopInstance(@ModelAttribute("desktop") Desktop desktop) {

    	desktopService.updateDesktop(desktop);
        return "redirect:/desktopdetail?desktop="+desktop.getId();
    }   

}
