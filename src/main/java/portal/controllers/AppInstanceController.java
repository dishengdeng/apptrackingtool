package portal.controllers;



import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import portal.entity.AppInstance;
import portal.entity.Company;
import portal.entity.Contract;
import portal.entity.Desktop;
import portal.entity.License;
import portal.entity.SLA;
import portal.entity.Server;
import portal.entity.Site;
import portal.models.App;
import portal.service.AppInstanceService;
import portal.service.CompanyService;
import portal.service.ContractService;
import portal.service.DesktopService;
import portal.service.LicenseService;
import portal.service.SLAService;
import portal.service.ServerService;
import portal.service.SiteService;
import portal.service.SupportService;
import portal.service.DepartmentService;

@Controller
public class AppInstanceController {
	
	@Autowired
	private AppInstanceService appInstanceService;
	
	@Autowired
	private SLAService slaService;
	
	@Autowired
	private ContractService contractService;
	
	@Autowired
	private LicenseService licenseService;
	
	@Autowired
	private DesktopService desktopService;
	
	@Autowired
	private ServerService serverService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private SupportService supportService;

	@Autowired
	private SiteService siteService;
	
    @GetMapping("/instances")
    public String AppInstance(ModelMap model) {
    	List<AppInstance> appInstances = appInstanceService.getAll();
    	model.addAttribute("appinstances",appInstances);
    	model.addAttribute("appinstance", new AppInstance());
        return "instances";
    }
    
    @PostMapping("/addAppInstance")
    public String addAppInstance(@ModelAttribute("appinstance") AppInstance appInstance) {
    	
    	appInstanceService.addAppInstance(appInstance);
        return "redirect:/instances";
    }
    
    @PostMapping("/updateAppInstance")
    public String updateAppInstance(@ModelAttribute("appinstance") AppInstance appInstance) {
    	AppInstance AppinstanceEntity=appInstanceService.getById(appInstance.getId());
    	appInstance.setApplication(AppinstanceEntity.getApplication());
    	appInstance.setDepartment(AppinstanceEntity.getDepartment());
    	appInstance.setSupport(AppinstanceEntity.getSupport());
    	appInstance.setSites(AppinstanceEntity.getSites());
    	appInstanceService.updateAppInstance(appInstance);
        return "redirect:/instancedetail?id="+appInstance.getId();
    }
    
    @GetMapping("/addAppInstance")
    public String CreateAppInstance(ModelMap model) {
    	model.addAttribute("appinstance", new App());
        return "addAppInstance";
    }
    
    @GetMapping("/deleteAppInstance")
    public String DeleteAppInstance(@RequestParam(name="id", required=true) String id) {
    	AppInstance appInstance = appInstanceService.getById(Long.valueOf(id));
    	slaService.removeAppInstance(appInstance);
    	contractService.removeAppInstance(appInstance);
    	licenseService.removeAppInstance(appInstance);
    	desktopService.removeAppInstance(appInstance);
    	serverService.removeAppInstance(appInstance);
    	companyService.removeAppInstance(appInstance);
    	siteService.removeAppInstance(appInstance);
    	appInstance.setApplication(null);
    	appInstance.setDepartment(null);
    	appInstance.setSupport(null);
    	appInstanceService.delete(appInstance);
    	return "redirect:/instances";
    }
    
    @GetMapping("/instancedetail")
    public String appInstanceDetail(@RequestParam(name="id", required=false) String id,ModelMap model) {
    	AppInstance appInstance = appInstanceService.getById(Long.valueOf(id));
    	model.addAttribute("appinstance", appInstance);
   
    	//--SLA-----
    	model.addAttribute("sla",slaService.findByAppInstance(appInstance));
    	model.addAttribute("slas",slaService.findByNotAssigned(appInstance));
    	
    	//--Contract-----
    	model.addAttribute("contract",contractService.findByAppInstance(appInstance));
    	model.addAttribute("contracts",contractService.findByNotAssigned(appInstance));
    	
    	//--License-----
    	model.addAttribute("license",licenseService.findByAppInstance(appInstance));
    	model.addAttribute("licenses",licenseService.findByNotAssigned(appInstance));
    	
    	//--Desktop-----
    	model.addAttribute("desktop",desktopService.findByAppInstance(appInstance));
    	model.addAttribute("desktops",desktopService.findByNotAssigned(appInstance));
    	
    	//--Server-----
    	model.addAttribute("server",serverService.findByAppInstance(appInstance));
    	model.addAttribute("servers",serverService.findByNotAssigned(appInstance));  
    	
    	//--Vendor-----
    	model.addAttribute("company",companyService.findByAppInstance(appInstance));
    	model.addAttribute("companys",companyService.findByNotAssigned(appInstance));     	

    	//--Department-----
    	model.addAttribute("department",appInstance.getDepartment());
    	model.addAttribute("departments",departmentService.getAll());
    	
    	//--Support-----
    	model.addAttribute("support",appInstance.getSupport());
    	model.addAttribute("supports",supportService.getAll());
    	
    	//--Sites----
    	model.addAttribute("instancelocations",appInstance.getSites());
    	model.addAttribute("sites",siteService.getAll());
    	
    	return "instancedetail";
    }
    
//------SLA---------------    
    @GetMapping("/deleteInstanceSLA")
    public String deleteInstanceSLA(@RequestParam(name="id", required=false) String id) {
    	SLA sla = slaService.getById(Long.valueOf(id));
    	Long appInstanceId = sla.getAppInstance().getId();
    	sla.setAppInstance(null);
    	slaService.updateSLA(sla);


    	return "redirect:/instancedetail?id="+appInstanceId;
    }
    
    @PostMapping("/addInstanceSLA")
    public String addOrupdateInstanceSLA(@ModelAttribute("sla") SLA sla) {


    	slaService.removeAppInstance(sla.getAppInstance());
    	slaService.updateAppInstance(sla.getAppInstance(), sla.getId());
        return "redirect:/instancedetail?id="+sla.getAppInstance().getId();
    }
  //-----------------------
    
  //------Contract---------------    
    @GetMapping("/deleteInstanceContract")
    public String deleteInstanceContract(@RequestParam(name="id", required=false) String id) {
    	Contract contract = contractService.getById(Long.valueOf(id));
    	Long appInstanceId = contract.getAppInstance().getId();
    	contract.setAppInstance(null);
    	contractService.updateContract(contract);


    	return "redirect:/instancedetail?id="+appInstanceId;
    }
    
    @PostMapping("/addInstanceContract")
    public String addOrupdateInstanceContract(@ModelAttribute("contract") Contract contract) {


    	contractService.removeAppInstance(contract.getAppInstance());
    	contractService.updateAppInstance(contract.getAppInstance(), contract.getId());
        return "redirect:/instancedetail?id="+contract.getAppInstance().getId();
    }
  //----------------------- 
    
    
    //------License---------------    
    @GetMapping("/deleteInstanceLicense")
    public String deleteInstanceLicense(@RequestParam(name="id", required=false) String id) {
    	License license = licenseService.getById(Long.valueOf(id));
    	Long appInstanceId = license.getAppInstance().getId();
    	license.setAppInstance(null);
    	licenseService.updateLicense(license);


    	return "redirect:/instancedetail?id="+appInstanceId;
    }
    
    @PostMapping("/addInstanceLicense")
    public String addOrupdateInstanceLicense(@ModelAttribute("license") License license) {


    	licenseService.removeAppInstance(license.getAppInstance());
    	licenseService.updateAppInstance(license.getAppInstance(), license.getId());
        return "redirect:/instancedetail?id="+license.getAppInstance().getId();
    }
  //----------------------- 
    
    
    //------Desktop---------------    
    @GetMapping("/deleteInstanceDesktop")
    public String deleteInstanceDesktop(@RequestParam(name="id", required=false) String id) {
    	Desktop desktop = desktopService.getById(Long.valueOf(id));
    	Long appInstanceId = desktop.getAppInstance().getId();
    	desktop.setAppInstance(null);
    	desktopService.updateDesktop(desktop);


    	return "redirect:/instancedetail?id="+appInstanceId;
    }
    
    @PostMapping("/addInstanceDesktop")
    public String addOrupdateInstanceDesktop(@ModelAttribute("desktop") Desktop desktop) {


    	desktopService.removeAppInstance(desktop.getAppInstance());
    	desktopService.updateAppInstance(desktop.getAppInstance(), desktop.getId());
        return "redirect:/instancedetail?id="+desktop.getAppInstance().getId();
    }
  //----------------------- 
    
    //------Server---------------    
    @GetMapping("/deleteInstanceServer")
    public String deleteInstanceServer(@RequestParam(name="id", required=false) String id) {
    	Server server = serverService.getById(Long.valueOf(id));
    	Long appInstanceId = server.getAppInstance().getId();
    	server.setAppInstance(null);
    	serverService.updateServer(server);


    	return "redirect:/instancedetail?id="+appInstanceId;
    }
    
    @PostMapping("/addInstanceServer")
    public String addOrupdateInstanceServer(@ModelAttribute("server") Server server) {


    	serverService.removeAppInstance(server.getAppInstance());
    	serverService.updateAppInstance(server.getAppInstance(), server.getId());
        return "redirect:/instancedetail?id="+server.getAppInstance().getId();
    }
  //----------------------- 
    
    //------Vendor---------------    
    @GetMapping("/deleteInstanceCompany")
    public String deleteInstanceCompany(@RequestParam(name="id", required=false) String id) {
    	Company company = companyService.getById(Long.valueOf(id));
    	Long appInstanceId = company.getAppInstance().getId();
    	company.setAppInstance(null);
    	companyService.updateCompany(company);


    	return "redirect:/instancedetail?id="+appInstanceId;
    }
    
    @PostMapping("/addInstanceCompany")
    public String addOrupdateInstanceCompany(@ModelAttribute("company") Company company) {


    	companyService.removeAppInstance(company.getAppInstance());
    	companyService.updateAppInstance(company.getAppInstance(), company.getId());
        return "redirect:/instancedetail?id="+company.getAppInstance().getId();
    }
  //----------------------- 
    
    
    //------Department---------------    
    @GetMapping("/deleteInstanceDepartment")
    public String deleteInstanceDepartment(@RequestParam(name="id", required=false) String id) {

    	appInstanceService.removeDeparmentbyInstanceId(Long.valueOf(id));

    	return "redirect:/instancedetail?id="+id;
    }
    
    @PostMapping("/addInstanceDepartment")
    public String addOrupdateInstanceDepartment(@ModelAttribute("appinstance") AppInstance appInstance) {

    	AppInstance appInstanceEntity= appInstanceService.getById(appInstance.getId());
    	appInstanceEntity.setDepartment(appInstance.getDepartment());
    	appInstanceService.updateAppInstance(appInstanceEntity);
        return "redirect:/instancedetail?id="+appInstance.getId();
    }
  //-----------------------

    //------Support---------------    
    @GetMapping("/deleteInstanceSupport")
    public String deleteInstanceSupport(@RequestParam(name="id", required=false) String id) {

    	appInstanceService.removeSupportbyInstanceId(Long.valueOf(id));

    	return "redirect:/instancedetail?id="+id;
    }
    
    @PostMapping("/addInstanceSupport")
    public String addOrupdateInstanceSupport(@ModelAttribute("appinstance") AppInstance appInstance) {

    	AppInstance appInstanceEntity= appInstanceService.getById(appInstance.getId());
    	appInstanceEntity.setSupport(appInstance.getSupport());
    	appInstanceService.updateAppInstance(appInstanceEntity);
        return "redirect:/instancedetail?id="+appInstance.getId();
    }
  //-----------------------
    
    //------Sites---------------    
    @GetMapping("/deleteInstanceSite")
    public String deleteInstanceSite(@RequestParam(name="siteid", required=false) String siteid,@RequestParam(name="appinstanceid", required=false) String appinstanceid) {

    	AppInstance appInstanceEntity= appInstanceService.getById(Long.valueOf(appinstanceid));
    	Site siteEntity= siteService.getById(Long.valueOf(siteid));
    	
    	siteEntity.removeAppInstance(appInstanceEntity);
    	
    	siteService.updateSite(siteEntity);
    	
    	
    	return "redirect:/instancedetail?id="+appinstanceid;
    }
    
    @PostMapping("/addInstanceSite")
    public String addOrupdateInstanceSite(@ModelAttribute("appinstance") AppInstance appInstance) {

    	AppInstance appInstanceEntity= appInstanceService.getById(appInstance.getId());
    	
    	Set<Site> selectedLocations=new HashSet<Site>();
    	selectedLocations.addAll(appInstanceEntity.getSites());
    	selectedLocations.addAll(appInstance.getSites());
    	selectedLocations.remove(null);
    	
    	List<Site> sites=new ArrayList<Site>(selectedLocations);

  

    	for(Site site:sites)
    	{
    		if(!site.getAppInstances().contains(appInstanceEntity))
    		{
	    		site.addAppInstance(appInstanceEntity);
	    		siteService.updateSite(site);
    		}
    	}


        return "redirect:/instancedetail?id="+appInstance.getId();
    }
  //-----------------------    
    
}
