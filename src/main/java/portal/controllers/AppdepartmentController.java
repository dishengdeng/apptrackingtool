package portal.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import portal.entity.Appdepartment;
import portal.entity.Department;

import portal.service.AppService;
import portal.service.AppdepartmentService;
import portal.service.CompanyService;
import portal.service.ContractService;
import portal.service.LicenseService;
import portal.service.ProjectService;
import portal.service.SiteService;
import portal.service.UtilityService;
import portal.utility.Action;

@Controller
public class AppdepartmentController {
	@Autowired
	private AppService appService;
	
	@Autowired
	private SiteService siteService;
	
	@Autowired
	private CompanyService vendorService;
	
	@Autowired
	private ContractService contractService;
	
	@Autowired
	private LicenseService licenseService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private UtilityService utilityService;
	
	@Autowired
	private AppdepartmentService appDepartmentService;
	
    @GetMapping("/appinventory")
    public String appinventory(@ModelAttribute("appdepartment") Appdepartment appdepartment,
    							@ModelAttribute("department") Department department,
    							@ModelAttribute("actiontype") Action actionType,
    								ModelMap model) {
    	if(actionType==Action.CREATE)
    	{
    		appdepartment.setDepartment(department);
    	}
    	
    	model.addAttribute("appdepartment",appdepartment);
    	model.addAttribute("actiontype",actionType);
    	model.addAttribute("apps",appService.getAll());
    	model.addAttribute("sites",siteService.getAll());
    	model.addAttribute("vendors",vendorService.getAll());
    	model.addAttribute("contracts",contractService.getAll());
    	model.addAttribute("licenses",licenseService.getAll());
    	model.addAttribute("projects",projectService.getAll());
        return "appinventory";
    } 
    
    @PostMapping("/saveappdepartment")
    public void saveappdepartment(HttpServletRequest request,HttpServletResponse response) throws Exception
    {

    	Appdepartment repsponseObj=appDepartmentService.saveAppdepartment(utilityService.getJSONObject(request));
    	response.setContentType("application/json");
    	if(ObjectUtils.isEmpty(repsponseObj)) 
    	{response.setStatus(202);} 
    	else {response.setStatus(200);response.sendRedirect(request.getContextPath()+"/departmentdetail?id="+repsponseObj.getDepartment().getId());} 
    	response.getWriter().write(ObjectUtils.isEmpty(repsponseObj)? "{'status':'202'}":"{'status':'200'}");
    	response.flushBuffer();    	
    }
    
    @GetMapping("/deleteappdepartment")
    public String deleteappdepartment(@ModelAttribute("appdepartment") Appdepartment appdepartment,@ModelAttribute("department") Department department) {
    	
    	appdepartment.removeAlldependence();
    	appDepartmentService.delete(appdepartment);
    	return "redirect:/departmentdetail?id="+department.getId();
    }
}
