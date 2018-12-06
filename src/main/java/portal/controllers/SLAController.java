package portal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import portal.entity.SLA;
import portal.service.SLAService;




@Controller
public class SLAController {
	

	
	@Autowired
	private SLAService slaService;
	
    @GetMapping("/slas")
    public String slatable(ModelMap model) {
    	model.addAttribute("slas", slaService.getAll());
    	model.addAttribute("updateSLA", new SLA());    	
        return "slas";
    }
    
    @PostMapping("/addSLA")
    public String addSLA(@ModelAttribute("sla") SLA sla) {
    	slaService.addSLA(sla);
        return "redirect:/slas";
    }
 
    @PostMapping("/updateSLA")
    public String updateSLA(@ModelAttribute("updateSLA") SLA sla) {

    	slaService.updateSLA(sla);
        return "redirect:/slas";
    }
    
    @GetMapping("/addSLA")
    public String CreateSLA(ModelMap model) {
    	model.addAttribute("sla", new SLA());

        return "addSLA";
    }
    
    @GetMapping("/deleteSLA")
    public String deleteSLA(@RequestParam(name="id", required=true) String id,@RequestParam(name="slaName", required=true) String slaName) {
    	SLA sla = new SLA();
    	sla.setId(Long.valueOf(id));
    	sla.setSlaName(slaName);
    	slaService.delete(sla);
    	return "redirect:/slas";
    }

}
