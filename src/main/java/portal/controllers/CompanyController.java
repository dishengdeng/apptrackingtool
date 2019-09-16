package portal.controllers;




import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;



import portal.entity.Application;
import portal.entity.Company;
import portal.service.AppService;
import portal.service.CompanyService;




@Controller
public class CompanyController {
	@Autowired
	private CompanyService companyService;
	

	
	@Autowired
	private AppService appService;	
	
    @GetMapping("/companys")
    public String companytable(ModelMap model) {
    	model.addAttribute("companys", companyService.getAll());
        return "companys";
    }
    
    @PostMapping("/addCompany")
    public String addCompany(@ModelAttribute("company") Company company) {

    	companyService.addCompany(company);
        return "redirect:/companys";
    }
 
    @PostMapping("/updateCompany")
    public String updateCompany(@ModelAttribute("companyModel") Company company) {


    	companyService.updateCompany(company);
    	return "redirect:/companydetail?company="+company.getId();
    }
    
    @GetMapping("/addCompany")
    public String CreateCompany(ModelMap model) {
    	model.addAttribute("company", new Company());
        return "addCompany";
    }
    
    @GetMapping("/deleteCompany")
    public String deleteCompany(@ModelAttribute("company") Company company) {

    	
    	company.removeAllDependence();
    	companyService.delete(company);
    	return "redirect:/companys";
    }
    
    @GetMapping("/companydetail")
    public String companydetail(@ModelAttribute("company") Company company,ModelMap model) {
    	model.addAttribute("company", company);
    	model.addAttribute("appAssginedInstances",appService.getAll().stream().sorted().collect(Collectors.toList()));

        return "companydetail";
    }
    //--application--    
    @GetMapping("/deleteCompanyApplication")
    public String deleteCompanyApplication(@ModelAttribute("company") Company company,@ModelAttribute("application") Application application) {
    	company.removeApplication(application);
    	companyService.updateCompany(company);

    	return "redirect:/companydetail?company="+company.getId();
    }    
    
    @PostMapping("/addCompanyApplication")
    public String addCompanyApplication(@ModelAttribute("company") Company company) {

    	companyService.updateCompany(company);

    	return "redirect:/companydetail?company="+company.getId();
    }
 
    

}
