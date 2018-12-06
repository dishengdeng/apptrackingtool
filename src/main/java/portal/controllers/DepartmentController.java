package portal.controllers;






import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import portal.entity.Department;
import portal.service.AppInstanceService;
import portal.service.DepartmentService;
import portal.service.StakeholderService;





@Controller
public class DepartmentController {
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private StakeholderService stakholderService;
	
	@Autowired
	private AppInstanceService appInstanceService;	
	
    @GetMapping("/departments")
    public String departmenttable(ModelMap model) {
    	model.addAttribute("departments", departmentService.getAll());
    	model.addAttribute("departmentModel", new Department());
        return "departments";
    }
    
    @PostMapping("/addDepartment")
    public String addDepartment(@ModelAttribute("department") Department department) {

    	departmentService.addDepartment(department);
        return "redirect:/departments";
    }
 
    @PostMapping("/updateDepartment")
    public String updateDepartment(@ModelAttribute("departmentModel") Department department) {
    	
    	department.setStakeholders(stakholderService.findbyDepartment(department));

    	departmentService.updateDepartment(department);
        return "redirect:/departments";
    }
    
    @GetMapping("/addDepartment")
    public String CreateDepartment(ModelMap model) {
    	model.addAttribute("department", new Department());
        return "addDepartment";
    }
    
    @GetMapping("/deleteDepartment")
    public String deleteDepartment(@RequestParam(name="id", required=true) String id,@RequestParam(name="departmentName", required=true) String departmentName) {
    	Department department = departmentService.getById(Long.valueOf(id));	

    	appInstanceService.removeDeparment(department);   	
    	stakholderService.removeDepartment(department);   	

    	
    	departmentService.delete(department);
    	return "redirect:/departments";
    }
    




}
