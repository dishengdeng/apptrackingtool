package portal.controllers;

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

import portal.entity.File;
import portal.entity.Server;
import portal.service.ClusterService;
import portal.service.FileService;
import portal.service.ServerService;
import portal.utility.FileType;



@Controller
public class ServerController {
	@Autowired
	private ClusterService clusterService;
	@Autowired
	private ServerService serverService;
	
	@Autowired
	private FileService fileService;
	
	private final String UPLOADED_FOLDER="files//server//";
	
    @GetMapping("/servers")
    public String servertable(ModelMap model) {
    	model.addAttribute("servers", serverService.getAll());
    	model.addAttribute("updateServer", new Server());
    	model.addAttribute("clusters", clusterService.getAll());
        return "servers";
    }
    
    @PostMapping("/addServer")
    public String addServer(@ModelAttribute("server") Server server) {
    	serverService.addServer(server);
        return "redirect:/servers";
    }
 
    @PostMapping("/updateServer")
    public String updateServer(@ModelAttribute("updateServer") Server server) {

    	serverService.updateServer(server);
    	return "redirect:/serverdetail?server="+server.getId().toString();
    }
    
    @GetMapping("/addServer")
    public String CreateServer(ModelMap model) {
    	model.addAttribute("server", new Server());
    	model.addAttribute("clusters", clusterService.getAll());
        return "addServer";
    }
    
    @GetMapping("/deleteServer")
    public String deleteServer(@ModelAttribute("server") Server server) {

    	server.removeAlldependence();
    	serverService.removFiles(UPLOADED_FOLDER, server);
    	serverService.delete(server);
    	return "redirect:/servers";
    }
    
    @GetMapping("/serverdetail")
    public String serverdetail(@ModelAttribute("server") Server server,ModelMap model) {
    	model.addAttribute("server", server);
    	model.addAttribute("clusters", clusterService.getAll());
    	model.addAttribute("appInstances", serverService.getAppInstancesNotContainServer(server));
        return "serverdetail";
    }
 //------instance------
    @GetMapping("/deleteServerInstance")
    public String deleteServerInstance(@ModelAttribute("server") Server server) {
    	server.setAppInstance(null);
    	serverService.updateServer(server);
    	return "redirect:/serverdetail?server="+server.getId().toString();
    }
    
    @PostMapping("/addServerInstance")
    public String addServerInstance(@ModelAttribute("server") Server server) {

    	serverService.updateServer(server);
    	return "redirect:/serverdetail?server="+server.getId().toString();
    }    
    
  //------file management----
    @PostMapping("/serverupload")
    public String uploadServer(@RequestParam("file") MultipartFile file,@ModelAttribute("server") Server server) {


    	File fileEntity = new File();
    	fileEntity.setFiletype(FileType.SERVER);
    	fileEntity.setAttachment(fileService.getFileName(file.getOriginalFilename()));
    	fileEntity.setServer(server);

		fileService.uploadFile(file, UPLOADED_FOLDER,server.getId().toString(),fileEntity);


    		
    	
        return "redirect:/serverdetail?server="+server.getId().toString();
    }
    
    @GetMapping("/downloadserver")
    public ResponseEntity<Resource> downloadfile(@ModelAttribute("file") File file,HttpServletRequest request)
    {
    	
 
    	return fileService.downloadFile(UPLOADED_FOLDER,file.getServer().getId().toString(),file , request);
    }

    @GetMapping("/deleteserverfile")
    public String deletefile(@ModelAttribute("file") File file,@RequestParam(name="serverid", required=true) String id)
    {
    	


		fileService.removeFile(UPLOADED_FOLDER,id, file);
		
		return "redirect:/serverdetail?server="+id;
    	
    }

}
