package portal.controllers;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;



import portal.entity.SLARole;
import portal.entity.Stakeholderext;
import portal.service.DepartmentService;
import portal.service.SLARoleService;
import portal.service.StakeholderService;
import portal.service.StakeholderextService;





@Controller
public class SLARoleController {
	@Autowired
	private SLARoleService slaroleService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private StakeholderService stakeholderService;
	
	@Autowired
	private StakeholderextService stakeholderextService;
	
    @GetMapping("/slaroles")
    public String slaroletable(ModelMap model) {
    	model.addAttribute("slaroles", slaroleService.getAll());

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
    	return "redirect:/slaroledetail?slarole="+slarole.getId();
    }
    
    @GetMapping("/addSLARole")
    public String CreateSLARole(ModelMap model) {
    	model.addAttribute("slarole", new SLARole());
        return "addSLARole";
    }
    
    @GetMapping("/deleteSLARole")
    public String deleteSLARole(@ModelAttribute("slarole") SLARole slarole) {

    	slarole.removeAllDedenpence();
    	slaroleService.delete(slarole);
    	return "redirect:/slaroles";
    }

    @GetMapping("/slaroledetail")
    public String DepartmentDetial(@ModelAttribute("slarole") SLARole slarole,ModelMap model) {
    	model.addAttribute("slarole",slarole);
    	model.addAttribute("stakeholders",stakeholderService.getAll());
    	model.addAttribute("departments", departmentService.getAll());
        return "slaroledetail";
    }
    
    //--Stakeholder--    
    @GetMapping("/deleteSlaroleStakeholder")
    public String deleteSlaroleStakeholder(@ModelAttribute("stakeholderext") Stakeholderext stakeholderext) {


    	Long id = stakeholderext.getRole().getId();
    	stakeholderext.removeAllDependence();
    	stakeholderextService.delete(stakeholderext);
    	return "redirect:/slaroledetail?slarole="+id;
    }    
    
    @PostMapping("/addSlaroleStakeholder")
    public String addSlaroleStakeholder(@ModelAttribute("stakeholderext") Stakeholderext stakeholderext) {

    	stakeholderextService.save(stakeholderext);
    	return "redirect:/slaroledetail?slarole="+stakeholderext.getRole().getId();
    }
    
    @PostMapping("/updateSlaroleStakeholder")
    public String updateSlaroleStakeholder(@ModelAttribute("stakeholderext") Stakeholderext stakeholderext) {

    	stakeholderextService.update(stakeholderext);

    	return "redirect:/slaroledetail?slarole="+stakeholderext.getRole().getId();
    }   
}
