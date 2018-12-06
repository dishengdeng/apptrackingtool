package portal.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import portal.entity.Desktop;
import portal.service.DesktopService;



@Controller
public class DesktopController {
	@Autowired
	private DesktopService desktopService;
	
	
    @GetMapping("/desktops")
    public String desktoptable(ModelMap model) {
    	model.addAttribute("desktops", desktopService.getAll());
    	model.addAttribute("desktopModel", new Desktop());
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
        return "redirect:/desktops";
    }
    
    @GetMapping("/addDesktop")
    public String CreateDesktop(ModelMap model) {
    	model.addAttribute("desktop", new Desktop());
        return "addDesktop";
    }
    
    @GetMapping("/deleteDesktop")
    public String deleteDesktop(@RequestParam(name="id", required=true) String id,@RequestParam(name="desktopName", required=true) String desktopName) {
    	Desktop desktop = new Desktop();
    	desktop.setId(Long.valueOf(id));
    	desktop.setDesktopName(desktopName);
    	

    	
    	desktopService.delete(desktop);
    	return "redirect:/desktops";
    }


}
