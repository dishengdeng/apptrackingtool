package portal.controllers;



import java.util.ArrayList;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import portal.entity.AppInstance;
import portal.entity.Desktop;
import portal.entity.File;
import portal.entity.License;
import portal.entity.Server;
import portal.entity.Site;
import portal.models.App;
import portal.service.AppInstanceService;
import portal.service.CompanyService;
import portal.service.ContractService;
import portal.service.DesktopService;
import portal.service.FileService;
import portal.service.LicenseService;
import portal.service.SLAService;
import portal.service.ServerService;
import portal.service.SiteService;
import portal.service.SupportService;
import portal.utility.FileType;
import portal.service.DepartmentService;

@Controller
public class AppInstanceController {
	
	private final String UPLOADED_FOLDER="files//instance//";
	
	@Autowired
	private FileService fileService;
	
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
//    	AppInstance AppinstanceEntity=appInstanceService.getById(appInstance.getId());
//    	appInstance.setApplication(AppinstanceEntity.getApplication());
//    	appInstance.setDepartment(AppinstanceEntity.getDepartment());
//    	appInstance.setSupport(AppinstanceEntity.getSupport());
//    	appInstance.setSites(AppinstanceEntity.getSites());
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
    	//slaService.removeAppInstance(appInstance);
    	licenseService.removeAppInstance(appInstance);
    	desktopService.removeAppInstance(appInstance);
    	serverService.removeAppInstance(appInstance);
    	//companyService.removeAppInstance(appInstance);
    	siteService.removeAppInstance(appInstance);
    	appInstance.setApplication(null);
    	appInstance.setDepartment(null);
    	appInstance.setSupport(null);
    	appInstance.setContract(null);
    	appInstance.setCompany(null);
    	appInstance.setSla(null);
    	appInstanceService.delete(appInstance);
    	return "redirect:/instances";
    }
    
    @GetMapping("/instancedetail")
    public String appInstanceDetail(@RequestParam(name="id", required=false) String id,ModelMap model) {
    	AppInstance appInstance = appInstanceService.getById(Long.valueOf(id));
    	model.addAttribute("appinstance", appInstance);
   
    	//--SLA-----
    	model.addAttribute("sla",appInstance.getSla());
    	model.addAttribute("slas",slaService.getAll());
    	
    	//--Contract-----
    	model.addAttribute("contract",appInstance.getContract());
    	model.addAttribute("contracts",contractService.getAll());
    	
    	//--License-----
    	model.addAttribute("license",appInstance.getLicense());
    	model.addAttribute("licenses",licenseService.findByNotAssigned(appInstance));
    	
    	//--Desktop-----
    	model.addAttribute("desktop",appInstance.getDesktop());
    	model.addAttribute("desktops",desktopService.findByNotAssigned(appInstance));
    	
    	//--Server-----
    	model.addAttribute("serverSelected",appInstance.getServers());
    	model.addAttribute("servers",serverService.findByNotAssigned(appInstance));  
    	
    	//--Vendor-----
    	model.addAttribute("company",appInstance.getCompany());
    	model.addAttribute("companys",companyService.getAll());     	

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
    	AppInstance appInstance = appInstanceService.getById(Long.valueOf(id));
    	appInstance.setSla(null);

    	appInstanceService.updateAppInstance(appInstance);
    	return "redirect:/instancedetail?id="+Long.valueOf(id);
    }
    
    @PostMapping("/addInstanceSLA")
    public String addOrupdateInstanceSLA(@ModelAttribute("appinstance") AppInstance appinstance) {


    	AppInstance appinstanceEntity = appInstanceService.getById(appinstance.getId());
    	appinstanceEntity.setSla(appinstance.getSla());
    	appInstanceService.updateAppInstance(appinstanceEntity);
        return "redirect:/instancedetail?id="+appinstance.getId();
    }
  //-----------------------
    
  //------Contract---------------    
    @GetMapping("/deleteInstanceContract")
    public String deleteInstanceContract(@RequestParam(name="id", required=false) String id) {
    	AppInstance appInstance = appInstanceService.getById(Long.valueOf(id));
    	appInstance.setContract(null);
    	appInstanceService.updateAppInstance(appInstance);

    	return "redirect:/instancedetail?id="+Long.valueOf(id);
    }
    
    @PostMapping("/addInstanceContract")
    public String addOrupdateInstanceContract(@ModelAttribute("appinstance") AppInstance appinstance) {

    	AppInstance appinstanceEntity = appInstanceService.getById(appinstance.getId());
    	appinstanceEntity.setContract(appinstance.getContract());
    	appInstanceService.updateAppInstance(appinstanceEntity);
        return "redirect:/instancedetail?id="+appinstance.getId();
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
    public String deleteInstanceServer(@ModelAttribute("server") Server server) {
    	Long appInstanceId = server.getAppInstance().getId();
    	server.setAppInstance(null);
    	serverService.updateServer(server);


    	return "redirect:/instancedetail?id="+appInstanceId;
    }
    
    @PostMapping("/addInstanceServer")
    public String addOrupdateInstanceServer(@ModelAttribute("appInstance") AppInstance appInstance) {


    	
    	List<Server> servers= new ArrayList<>(appInstance.getServers());

    	servers.forEach(obj->{
    		obj.setAppInstance(appInstance);
    		serverService.updateServer(obj);
    	});

    	
        return "redirect:/instancedetail?id="+appInstance.getId();
    }
  //----------------------- 
    
    //------Vendor---------------    
    @GetMapping("/deleteInstanceCompany")
    public String deleteInstanceCompany(@RequestParam(name="id", required=false) String id) {
    	AppInstance appInstance = appInstanceService.getById(Long.valueOf(id));
    	appInstance.setCompany(null);
    	appInstanceService.updateAppInstance(appInstance);

    	return "redirect:/instancedetail?id="+Long.valueOf(id);
    }
    
    @PostMapping("/addInstanceCompany")
    public String addOrupdateInstanceCompany(@ModelAttribute("appinstance") AppInstance appInstance) {

    	AppInstance appInstanceEntity=appInstanceService.getById(appInstance.getId());
    	appInstanceEntity.setCompany(appInstance.getCompany());
    	appInstanceService.updateAppInstance(appInstanceEntity);
        return "redirect:/instancedetail?id="+appInstance.getId();
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
    public String deleteInstanceSite(@ModelAttribute("site") Site site,@ModelAttribute("appinstance") AppInstance appInstance) {


    	
    	site.removeAppInstance(appInstance);
    	
    	siteService.updateSite(site);
    	
    	
    	return "redirect:/instancedetail?id="+appInstance.getId();
    }
    
    @PostMapping("/addInstanceSite")
    public String addOrupdateInstanceSite(@ModelAttribute("appInstance") AppInstance appInstance) {


    	
    	List<Site> sites=new ArrayList<Site>(appInstance.getSites());
    	
    	sites.forEach(obj->{
    		if(!obj.getAppInstances().contains(appInstance))
    		{
    			Site site=obj;
	    		site.addAppInstance(appInstance);
	    		siteService.updateSite(site);
    		}
    	});
    	

        return "redirect:/instancedetail?id="+appInstance.getId();
    }
  //----------------------- 
    
    //------file management----
    @PostMapping("/appinstanceupload")
    public String uploadappInstance(@RequestParam("file") MultipartFile file,@ModelAttribute("appInstance") AppInstance appInstance) {


    	File fileEntity = new File();
    	fileEntity.setFiletype(FileType.APPINSTANCE);
    	fileEntity.setAttachment(fileService.getFileName(file.getOriginalFilename()));
    	fileEntity.setAppInstance(appInstance);

		fileService.uploadFile(file, UPLOADED_FOLDER,appInstance.getId().toString(),fileEntity);
		
        return "redirect:/instancedetail?id="+appInstance.getId();
    }
    
    @GetMapping("/downloadappinstance")
    public ResponseEntity<Resource> downloadfile(@ModelAttribute("file") File file,HttpServletRequest request)
    {
    	


    	return fileService.downloadFile(UPLOADED_FOLDER,file.getAppInstance().getId().toString(),file , request);
    }

    @GetMapping("/deleteappinstancefile")
    public String deletefile(@ModelAttribute("file") File file,@ModelAttribute("appInstance") AppInstance appInstance)
    {
    	

    	
		fileService.removeFile(UPLOADED_FOLDER, file.getAppInstance().getId().toString(),file);
		return "redirect:/instancedetail?id="+appInstance.getId();
    	
    }    
    
}
