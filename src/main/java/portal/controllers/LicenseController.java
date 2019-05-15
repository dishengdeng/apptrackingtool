package portal.controllers;




import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import portal.entity.AppInstance;
import portal.entity.Application;
import portal.entity.License;
import portal.service.AppInstanceService;
import portal.service.AppService;
import portal.service.LicenseService;




@Controller
public class LicenseController {
	@Autowired
	private LicenseService licenseService;
	
	@Autowired
	private AppService appService;
	
	@Autowired
	private AppInstanceService appInstanceService;
	
    @GetMapping("/licenses")
    public String licensetable(ModelMap model) {
    	model.addAttribute("licenses", licenseService.getAll());

        return "licenses";
    }
    
    @PostMapping("/addLicense")
    public String addLicense(@ModelAttribute("license") License license) {

    	licenseService.addLicense(license);
        return "redirect:/licenses";
    }
 
    @PostMapping("/updateLicense")
    public String updateLicense(@ModelAttribute("licenseModel") License license) {

    	licenseService.updateLicense(license);
    	return "redirect:/licensedetail?license="+license.getId();
    }
    
    @GetMapping("/addLicense")
    public String CreateLicense(ModelMap model) {
    	model.addAttribute("license", new License());
        return "addLicense";
    }
    
    @GetMapping("/deleteLicense")
    public String deleteLicense(@ModelAttribute("license") License license) {

    	license.removeAllDependence();
    	licenseService.delete(license);
    	return "redirect:/licenses";
    }

    @GetMapping("/licensedetail")
    public String licensedetail(@ModelAttribute("license") License license,ModelMap model) {
    	model.addAttribute("license", license);
    	model.addAttribute("appUnassginedInstances",appInstanceService.getUnassginedAppInstances());
    	model.addAttribute("appAssginedInstances",appService.getAll().stream().sorted().collect(Collectors.toList()));
        return "licensedetail";
    }

    //------Application---------------    
    @GetMapping("/deleteLicenseApplication")
    public String deleteLicenseApplication(@ModelAttribute("app") Application application,@ModelAttribute("license") License license) {

    	license.removeApplication(application);
    	licenseService.updateLicense(license);

    	return "redirect:/licensedetail?license="+license.getId();
    }
    
    @PostMapping("/addLicenseApplication")
    public String addLicenseApplication(@ModelAttribute("license") License license) {


    	licenseService.updateLicense(license);
    	return "redirect:/licensedetail?license="+license.getId();
    }    
    
    //--Instance--    
    @GetMapping("/deleteLicenseInstance")
    public String deleteLicenseInstance(@ModelAttribute("license") License license,@ModelAttribute("appinstance") AppInstance appinstance) {
    	license.removeInstance(appinstance);
    	licenseService.updateLicense(license);

    	return "redirect:/licensedetail?license="+license.getId();
    }    
    
    @PostMapping("/addLicenseInstance")
    public String addLicenseInstance(ModelMap model,@ModelAttribute("license") License license) {

    	licenseService.updateLicense(license);

    	return "redirect:/licensedetail?license="+license.getId();
    }      
}
