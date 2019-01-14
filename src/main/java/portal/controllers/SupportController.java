package portal.controllers;


import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import portal.entity.Support;
import portal.service.AppInstanceService;
import portal.service.AppService;
import portal.service.SupportService;




@Controller
public class SupportController {
	@Autowired
	private SupportService supportService;
	
	@Autowired
	private AppInstanceService appInstanceService;
	
	@Autowired
	private AppService appService;
	
    @GetMapping("/supports")
    public String supporttable(ModelMap model) {
    	model.addAttribute("supports", supportService.getAll());
    	model.addAttribute("appUnassginedInstances",appInstanceService.getUnassginedAppInstances());
    	model.addAttribute("appAssginedInstances",appService.getAll().stream().sorted().collect(Collectors.toList()));
    	model.addAttribute("supportModel", new Support());
        return "supports";
    }
    
    @PostMapping("/addSupport")
    public String addSupport(@ModelAttribute("support") Support support) {

    	supportService.addSupport(support);
        return "redirect:/supports";
    }
 
    @PostMapping("/updateSupport")
    public String updateSupport(@ModelAttribute("supportModel") Support support) {
    	supportService.updateAppInstanceSupport(support.getAppInstances(), support);
    	supportService.updateSupport(support);
        return "redirect:/supports";
    }
    
    @GetMapping("/addSupport")
    public String CreateSupport(ModelMap model) {
    	model.addAttribute("support", new Support());
        return "addSupport";
    }
    
    @GetMapping("/deleteSupport")
    public String deleteSupport(@RequestParam(name="id", required=true) String id,@RequestParam(name="supportName", required=true) String supportName) {
    	Support support = new Support();
    	support.setId(Long.valueOf(id));
    	support.setSupportName(supportName);
    	
    	appInstanceService.removeSupport(support);
    	
    	supportService.delete(support);
    	return "redirect:/supports";
    }


}
