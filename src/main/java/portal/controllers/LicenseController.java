package portal.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import portal.entity.License;
import portal.service.LicenseService;




@Controller
public class LicenseController {
	@Autowired
	private LicenseService licenseService;
	
	
    @GetMapping("/licenses")
    public String licensetable(ModelMap model) {
    	model.addAttribute("licenses", licenseService.getAll());
    	model.addAttribute("licenseModel", new License());
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
        return "redirect:/licenses";
    }
    
    @GetMapping("/addLicense")
    public String CreateLicense(ModelMap model) {
    	model.addAttribute("license", new License());
        return "addLicense";
    }
    
    @GetMapping("/deleteLicense")
    public String deleteLicense(@RequestParam(name="id", required=true) String id,@RequestParam(name="licenseNumber", required=true) String licenseNumber) {
    	License license = new License();
    	license.setId(Long.valueOf(id));
    	license.setLicenseNumber(licenseNumber);
    	
    	licenseService.updateLicense(license);
    	
    	licenseService.delete(license);
    	return "redirect:/licenses";
    }


}
