package portal.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import portal.entity.SLARole;
import portal.entity.Stakeholder;
import portal.service.SLARoleService;
import portal.service.StakeholderService;





@Controller
public class SLARoleController {
	@Autowired
	private SLARoleService slaroleService;
	
	@Autowired
	private StakeholderService stakeholderService;
	
    @GetMapping("/slaroles")
    public String slaroletable(ModelMap model) {
    	model.addAttribute("slaroles", slaroleService.getAll());
    	model.addAttribute("slaroleModel", new SLARole());
        return "slaroles";
    }
    
    @PostMapping("/addSLARole")
    public String addSLARole(@ModelAttribute("slarole") SLARole slarole) {

    	slaroleService.addSLARole(slarole);
        return "redirect:/slaroles";
    }
 
    @PostMapping("/updateSLARole")
    public String updateSLARole(@ModelAttribute("slaroleModel") SLARole slarole) {

    	slaroleService.updateSLARole(slarole);
        return "redirect:/slaroles";
    }
    
    @GetMapping("/addSLARole")
    public String CreateSLARole(ModelMap model) {
    	model.addAttribute("slarole", new SLARole());
        return "addSLARole";
    }
    
    @GetMapping("/deleteSLARole")
    public String deleteSLARole(@RequestParam(name="id", required=true) String id,@RequestParam(name="slaroleName", required=true) String slaroleName) {
    	SLARole slarole = slaroleService.getById(Long.valueOf(id));
    	
    	stakeholderService.removeRole(slarole);
    	
    	slaroleService.delete(slarole);
    	return "redirect:/slaroles";
    }

    @GetMapping("/slaroledetail")
    public String DepartmentDetial(@ModelAttribute("slarole") SLARole slarole,ModelMap model) {
    	model.addAttribute("slarole",slarole);
    	model.addAttribute("stakeholders",stakeholderService.getAll());
        return "slaroledetail";
    }
    
    //--Stakeholder--    
    @GetMapping("/deleteSlaroleStakeholder")
    public String deleteSlaroleStakeholder(@ModelAttribute("stakeholder") Stakeholder stakeholder,@ModelAttribute("slarole") SLARole slarole) {
    	stakeholder.setRole(null);
    	stakeholderService.updateStakeholder(stakeholder);

    	return "redirect:/slaroledetail?slarole="+slarole.getId();
    }    
    
    @PostMapping("/addSlaroleStakeholder")
    public String addSlaroleStakeholder(@ModelAttribute("slarole") SLARole slarole) {

    	slaroleService.updateSLARole(slarole);

    	return "redirect:/slaroledetail?slarole="+slarole.getId();
    }    
}
