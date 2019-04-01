package portal.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import portal.entity.Role;
import portal.service.RoleService;




@Controller
public class RoleController {
	@Autowired
	private RoleService roleService;	

	
    @GetMapping("/roles")
    public String roletable(ModelMap model) {
    	model.addAttribute("roles", roleService.getAll());
    	model.addAttribute("roleModel", new Role());
        return "roles";
    }
    
    @PostMapping("/addRole")
    public String addRole(@ModelAttribute("role") Role role) {

    	roleService.addRole(role);
        return "redirect:/roles";
    }
 
    @PostMapping("/updateRole")
    public String updateRole(@ModelAttribute("roleModel") Role role) {

    	roleService.updateRole(role);
        return "redirect:/roles";
    }
    
    @GetMapping("/addRole")
    public String CreateRole(ModelMap model) {
    	model.addAttribute("role", new Role());
        return "addRole";
    }
    
    @GetMapping("/deleteRole")
    public String deleteRole(@ModelAttribute("role") Role role) {

    	role.removeAllDependence();
    	roleService.delete(role);
    	return "redirect:/roles";
    }


}
