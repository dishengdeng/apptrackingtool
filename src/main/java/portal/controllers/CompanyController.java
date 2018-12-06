package portal.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import portal.entity.Company;

import portal.service.CompanyService;




@Controller
public class CompanyController {
	@Autowired
	private CompanyService companyService;
	
	
    @GetMapping("/companys")
    public String companytable(ModelMap model) {
    	model.addAttribute("companys", companyService.getAll());
    	model.addAttribute("companyModel", new Company());
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
        return "redirect:/companys";
    }
    
    @GetMapping("/addCompany")
    public String CreateCompany(ModelMap model) {
    	model.addAttribute("company", new Company());
        return "addCompany";
    }
    
    @GetMapping("/deleteCompany")
    public String deleteCompany(@RequestParam(name="id", required=true) String id,@RequestParam(name="companyName", required=true) String companyName) {
    	Company company = new Company();
    	company.setId(Long.valueOf(id));
    	company.setCompanyName(companyName);
    	

    	
    	companyService.delete(company);
    	return "redirect:/companys";
    }


}
