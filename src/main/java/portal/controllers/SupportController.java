package portal.controllers;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import portal.entity.Support;
import portal.service.SupportService;
import portal.validator.SupportValidator;




@Controller
public class SupportController {
	@Autowired
	private SupportService supportService;
	

	
	@Autowired
	private SupportValidator supportValidator;
	
    @GetMapping("/supports")
    public String supporttable(ModelMap model) {
    	model.addAttribute("supports", supportService.getAll());
        return "supports";
    }
    
    @PostMapping("/addSupport")
    public String addSupport(@ModelAttribute("support") Support support) {

    	supportService.addSupport(support);
        return "redirect:/supports";
    }
 
    @PostMapping("/updateSupport")
    public String updateSupport(@ModelAttribute("supportModel") Support support,BindingResult bindingResult,ModelMap model) {

    	supportValidator.validate(support, bindingResult);
    	if (bindingResult.hasErrors())
    	{
    		setModel(support,model);
    		return "supportdetail";
    	}
    	
    	supportService.updateDetail(support);
    	return "redirect:/supportdetail?support="+support.getId();
    }
    
    @GetMapping("/addSupport")
    public String CreateSupport(ModelMap model) {
    	model.addAttribute("support", new Support());
        return "addSupport";
    }
    
    @GetMapping("/deleteSupport")
    public String deleteSupport(@ModelAttribute("support") Support support) {

    	support.removeAllDependence();


    	supportService.delete(support);
    	return "redirect:/supports";
    }
    
    @GetMapping("/supportdetail")
    public String supportdetail(@ModelAttribute("support") Support support,ModelMap model) {
    	setModel(support,model);
        return "supportdetail";
    }
    
    private void setModel(Support support,ModelMap model)
    {
    	model.addAttribute("support",support);
    	model.addAttribute("supportModel",support);

    }
}
