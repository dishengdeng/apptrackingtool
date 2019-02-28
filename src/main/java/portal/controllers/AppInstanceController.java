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
import portal.entity.Project;
import portal.entity.Server;
import portal.entity.Site;
import portal.entity.Zone;
import portal.service.AppInstanceService;
import portal.service.CompanyService;
import portal.service.ContractService;
import portal.service.DesktopService;
import portal.service.FileService;
import portal.service.LicenseService;
import portal.service.ProjectService;
import portal.service.SLAService;
import portal.service.ServerService;
import portal.service.SiteService;
import portal.service.SupportService;
import portal.service.ZoneService;
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
	private ProjectService projectService;

	@Autowired
	private SiteService siteService;
	
	@Autowired
	private ZoneService zoneService;
	
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

    	appInstanceService.updateAppInstance(appInstance);
        return "redirect:/instancedetail?id="+appInstance.getId();
    }
    
    @GetMapping("/addAppInstance")
    public String CreateAppInstance(ModelMap model) {
    	model.addAttribute("appinstance", new AppInstance());
        return "addAppInstance";
    }
    
    @GetMapping("/deleteAppInstance")
    public String DeleteAppInstance(@ModelAttribute("instance") AppInstance appInstance) {

    	
    	appInstance.removeAllDependence();
    	appInstanceService.removFiles(UPLOADED_FOLDER, appInstance);
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
    	
    	//--Project----
    	model.addAttribute("instanceprojects",appInstance.getProjects());    	
    	model.addAttribute("projects",projectService.getAll());    	
    	
    	//--Sites----
    	model.addAttribute("instancelocations",appInstance.getSites());
    	model.addAttribute("sites",siteService.getAll());
    	
    	//--Zone-----
    	model.addAttribute("instancezones",appInstance.getZones());
    	model.addAttribute("zones",zoneService.getAll());    	
    	
    	return "instancedetail";
    }
    
//------SLA---------------    
    @GetMapping("/deleteInstanceSLA")
    public String deleteInstanceSLA(@ModelAttribute("instance") AppInstance appinstance) {

    	appinstance.setSla(null);

    	appInstanceService.updateAppInstance(appinstance);
    	return "redirect:/instancedetail?id="+appinstance.getId();
    }
    
    @PostMapping("/addInstanceSLA")
    public String addOrupdateInstanceSLA(@ModelAttribute("appinstance") AppInstance appinstance) {


    	appInstanceService.updateAppInstance(appinstance);
        return "redirect:/instancedetail?id="+appinstance.getId();
    }
  //-----------------------
    
  //------Contract---------------    
    @GetMapping("/deleteInstanceContract")
    public String deleteInstanceContract(@ModelAttribute("instance") AppInstance appinstance) {

    	appinstance.setContract(null);
    	appInstanceService.updateAppInstance(appinstance);

    	return "redirect:/instancedetail?id="+appinstance.getId();
    }
    
    @PostMapping("/addInstanceContract")
    public String addOrupdateInstanceContract(@ModelAttribute("appinstance") AppInstance appinstance) {

    	appInstanceService.updateAppInstance(appinstance);
        return "redirect:/instancedetail?id="+appinstance.getId();
    }
  //----------------------- 
    
    
    //------License---------------    
    @GetMapping("/deleteInstanceLicense")
    public String deleteInstanceLicense(@ModelAttribute("license") License license) {

    	Long appInstanceId = license.getAppInstance().getId();
    	license.setAppInstance(null);
    	licenseService.updateLicense(license);


    	return "redirect:/instancedetail?id="+appInstanceId;
    }
    
    @PostMapping("/addInstanceLicense")
    public String addOrupdateInstanceLicense(@ModelAttribute("license") License license) {


//    	licenseService.removeAppInstance(license.getAppInstance());
//    	licenseService.updateAppInstance(license.getAppInstance(), license.getId());
    	licenseService.updateLicense(license);
        return "redirect:/instancedetail?id="+license.getAppInstance().getId();
    }
  //----------------------- 
    
    
    //------Desktop---------------    
    @GetMapping("/deleteInstanceDesktop")
    public String deleteInstanceDesktop(@ModelAttribute("desktop") Desktop desktop) {

    	Long appInstanceId = desktop.getAppInstance().getId();
    	desktop.setAppInstance(null);
    	desktopService.updateDesktop(desktop);


    	return "redirect:/instancedetail?id="+appInstanceId;
    }
    
    @PostMapping("/addInstanceDesktop")
    public String addOrupdateInstanceDesktop(@ModelAttribute("desktop") Desktop desktop) {


    	desktopService.updateDesktop(desktop);
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
    public String deleteInstanceCompany(@ModelAttribute("instance") AppInstance appInstance) {

    	appInstance.setCompany(null);
    	appInstanceService.updateAppInstance(appInstance);

    	return "redirect:/instancedetail?id="+appInstance.getId();
    }
    
    @PostMapping("/addInstanceCompany")
    public String addOrupdateInstanceCompany(@ModelAttribute("appinstance") AppInstance appInstance) {


    	appInstanceService.updateAppInstance(appInstance);
        return "redirect:/instancedetail?id="+appInstance.getId();
    }
  //----------------------- 
    
    
    //------Department---------------    
    @GetMapping("/deleteInstanceDepartment")
    public String deleteInstanceDepartment(@ModelAttribute("instance") AppInstance appInstance) {
    	appInstance.setDepartment(null);
    	appInstanceService.updateAppInstance(appInstance);

    	return "redirect:/instancedetail?id="+appInstance.getId();
    }
    
    @PostMapping("/addInstanceDepartment")
    public String addOrupdateInstanceDepartment(@ModelAttribute("appinstance") AppInstance appInstance) {

    	appInstanceService.updateAppInstance(appInstance);
        return "redirect:/instancedetail?id="+appInstance.getId();
    }
  //-----------------------

    //------Support---------------    
    @GetMapping("/deleteInstanceSupport")
    public String deleteInstanceSupport(@ModelAttribute("instance") AppInstance appInstance) {

    	appInstance.setSupport(null);
    	appInstanceService.updateAppInstance(appInstance);

    	return "redirect:/instancedetail?id="+appInstance.getId();
    }
    
    @PostMapping("/addInstanceSupport")
    public String addOrupdateInstanceSupport(@ModelAttribute("appinstance") AppInstance appInstance) {


    	appInstanceService.updateAppInstance(appInstance);
        return "redirect:/instancedetail?id="+appInstance.getId();
    }
  //-----------------------
    
    //-----project------
	@GetMapping("/deleteInstanceProject")
	public String deleteProjectInstance(@ModelAttribute("project") Project project,@ModelAttribute("appinstance") AppInstance appInstance)
	{
		project.removeAppInstance(appInstance);
		projectService.updateProject(project);
		return "redirect:/instancedetail?id="+appInstance.getId();
	}
	
	@PostMapping("/addInstanceProject")
	public String addProjectInstance(@ModelAttribute("appInstance") AppInstance appInstance)
	{
		appInstanceService.updateAppInstance(appInstance);
		return "redirect:/instancedetail?id="+appInstance.getId();
	}
    
    //------Sites---------------    
    @GetMapping("/deleteInstanceSite")
    public String deleteInstanceSite(@ModelAttribute("site") Site site,@ModelAttribute("appinstance") AppInstance appInstance) {


    	
    	site.removeAppInstance(appInstance);
    	
    	siteService.updateSite(site);
    	
    	
    	return "redirect:/instancedetail?id="+appInstance.getId();
    }
    
    @PostMapping("/addInstanceSite")
    public String addOrupdateInstanceSite(@ModelAttribute("appInstance") AppInstance appInstance) {


    	
    	appInstanceService.updateAppInstance(appInstance);
    	
        return "redirect:/instancedetail?id="+appInstance.getId();
    }
    
    //------Zones---------------    
    @GetMapping("/deleteInstanceZone")
    public String deleteInstanceZone(@ModelAttribute("zone") Zone zone,@ModelAttribute("appinstance") AppInstance appInstance) {


    	
    	zone.removeAppInstance(appInstance);
    	zoneService.updateZone(zone);
    	
    	
    	return "redirect:/instancedetail?id="+appInstance.getId();
    }
    
    @PostMapping("/addInstanceZone")
    public String addInstanceZone(@ModelAttribute("appInstance") AppInstance appInstance) {


    	
    	appInstanceService.updateAppInstance(appInstance);
    	
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
