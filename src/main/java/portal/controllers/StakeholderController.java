package portal.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import portal.entity.Stakeholder;
import portal.service.DepartmentService;
import portal.service.SLARoleService;
import portal.service.StakeholderService;




@Controller
public class StakeholderController {
	@Autowired
	private StakeholderService stakeholderService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private SLARoleService slaRoleService;
	
    @GetMapping("/stakeholders")
    public String stakeholdertable(ModelMap model) {
    	model.addAttribute("stakeholders", stakeholderService.getAll());
    	model.addAttribute("stakeholderModel", new Stakeholder());
    	model.addAttribute("departments", departmentService.getAll());
    	model.addAttribute("roles", slaRoleService.getAll());
        return "stakeholders";
    }
    
    @PostMapping("/addStakeholder")
    public String addStakeholder(@ModelAttribute("stakeholder") Stakeholder stakeholder) {

    	stakeholderService.addStakeholder(stakeholder);
        return "redirect:/stakeholders";
    }
 
    @PostMapping("/updateStakeholder")
    public String updateStakeholder(@ModelAttribute("stakeholderModel") Stakeholder stakeholder) {

    	stakeholderService.updateStakeholder(stakeholder);
        return "redirect:/stakeholders";
    }
    
    @GetMapping("/addStakeholder")
    public String CreateStakeholder(ModelMap model) {
    	model.addAttribute("stakeholder", new Stakeholder());
    	model.addAttribute("departments", departmentService.getAll());
    	model.addAttribute("roles", slaRoleService.getAll());
        return "addStakeholder";
    }
    
    @GetMapping("/deleteStakeholder")
    public String deleteStakeholder(@RequestParam(name="id", required=true) String id,@RequestParam(name="stakeholderName", required=true) String stakeholderName) {
    	Stakeholder stakeholder = new Stakeholder();
    	stakeholder.setId(Long.valueOf(id));
    	stakeholder.setStakeholderName(stakeholderName);
    	stakeholderService.updateStakeholder(stakeholder);

    	
    	stakeholderService.delete(stakeholder);
    	return "redirect:/stakeholders";
    }
    
    @GetMapping("/stakeholderdetail")
    public String stakeholderdetail(@ModelAttribute("stakeholder") Stakeholder stakeholder,ModelMap model) {
    	model.addAttribute("stakeholder", stakeholder);
    	model.addAttribute("departments", departmentService.getAll());
    	model.addAttribute("roles", slaRoleService.getAll());
        return "stakeholderdetail";
    }


}
