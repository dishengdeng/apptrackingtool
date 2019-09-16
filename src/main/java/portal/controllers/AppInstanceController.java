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

import portal.entity.Server;

import portal.service.AppInstanceService;

import portal.service.DesktopService;
import portal.service.FileService;


import portal.service.ServerService;

import portal.utility.FileType;

@Controller
public class AppInstanceController {
	
	private final String UPLOADED_FOLDER="files//instance//";
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private AppInstanceService appInstanceService;
	

	

	
	@Autowired
	private DesktopService desktopService;
	
	@Autowired
	private ServerService serverService;


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
   

 
    	
    	//--Desktop-----
    	model.addAttribute("desktop",appInstance.getDesktop());
    	model.addAttribute("desktops",desktopService.findByNotAssigned(appInstance));
    	
    	//--Server-----
    	model.addAttribute("serverSelected",appInstance.getServers());
    	model.addAttribute("servers",serverService.findByNotAssigned(appInstance));  
    	
 
    	
  	
    	
    	return "instancedetail";
    }

    

    
    

    
    
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
