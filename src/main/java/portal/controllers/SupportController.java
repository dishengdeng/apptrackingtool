package portal.controllers;



import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import portal.entity.AppInstance;
import portal.entity.Application;
import portal.entity.Support;
import portal.service.AppInstanceService;
import portal.service.AppService;
import portal.service.SupportService;
import portal.validator.SupportValidator;




@Controller
public class SupportController {
	@Autowired
	private SupportService supportService;
	
	@Autowired
	private AppInstanceService appInstanceService;
	
	@Autowired
	private AppService appService;
	
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
    	getUpdatedSupport(support);
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

    	support.removeAllApp();
    	support.removeAllInstance();

    	supportService.delete(support);
    	return "redirect:/supports";
    }
    
    @GetMapping("/supportdetail")
    public String supportdetail(@ModelAttribute("support") Support support,ModelMap model) {
    	setModel(support,model);
        return "supportdetail";
    }
    
    //--application--    
    @GetMapping("/deletesupportapplication")
    public String deletesupportapplication(@ModelAttribute("application") Application application,@ModelAttribute("support") Support support,ModelMap model) {
    	support.removeApplication(application);
    	supportService.updateSupport(support);

    	return "redirect:/supportdetail?support="+support.getId();
    }    
    
    @PostMapping("/addsupportapplication")
    public String addDepartmentapplication(ModelMap model,@ModelAttribute("support") Support support) {

    	supportService.updateSupport(support);

    	return "redirect:/supportdetail?support="+support.getId();
    }    
    
  //--Instance--    
    @GetMapping("/deleteSupportInstance")
    public String deleteSupportInstance(@ModelAttribute("instance") AppInstance appInstance,@ModelAttribute("support") Support support,ModelMap model) {
    	support.removeInstance(appInstance);
    	supportService.updateSupport(support);

    	return "redirect:/supportdetail?support="+support.getId();
    }    
    
    @PostMapping("/addSupportInstance")
    public String addSupportInstance(ModelMap model,@ModelAttribute("support") Support support) {

    	supportService.updateSupport(support);

    	return "redirect:/supportdetail?support="+support.getId();
    }

    private void getUpdatedSupport(Support support)
    {
    	Support supportEntity=supportService.getById(support.getId());
    	support.setAppInstances(supportEntity.getAppInstances());
    	support.setApplications(supportEntity.getApplications());
    }
    
    private void setModel(Support support,ModelMap model)
    {
    	model.addAttribute("support",support);
    	model.addAttribute("supportModel",support);
    	model.addAttribute("appUnassginedInstances",appInstanceService.getUnassginedAppInstances());
    	model.addAttribute("appAssginedInstances",appService.getAll().stream().sorted().collect(Collectors.toList()));
    }
}
