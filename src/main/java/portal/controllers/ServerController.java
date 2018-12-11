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

import portal.entity.Server;
import portal.service.ClusterService;
import portal.service.FileService;
import portal.service.ServerService;



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
        return "redirect:/servers";
    }
    
    @GetMapping("/addServer")
    public String CreateServer(ModelMap model) {
    	model.addAttribute("server", new Server());
    	model.addAttribute("clusters", clusterService.getAll());
        return "addServer";
    }
    
    @GetMapping("/deleteServer")
    public String deleteServer(@RequestParam(name="id", required=true) String id,@RequestParam(name="serverName", required=true) String serverName) {
    	Server server = new Server();
    	server.setId(Long.valueOf(id));
    	server.setServerName(serverName);
    	serverService.delete(server);
    	return "redirect:/servers";
    }
    
  //------file management----
    @PostMapping("/serverupload")
    public String uploadServer(@RequestParam("file") MultipartFile file,@RequestParam("serverid") String id) {
    	Server server= serverService.getById(Long.valueOf(id));

		if(fileService.uploadFile(file, UPLOADED_FOLDER,id));
		{
			server.setAttachment(fileService.getFileName(file.getOriginalFilename()));
			serverService.updateServer(server);
		}


    		
    	
        return "redirect:/servers";
    }
    
    @GetMapping("/downloadserver")
    public ResponseEntity<Resource> downloadfile(@RequestParam(name="id", required=true) String id,HttpServletRequest request)
    {
    	
    	Server server= serverService.getById(Long.valueOf(id));
    	return fileService.downloadFile(UPLOADED_FOLDER, id, server.getAttachment(), request);
    }

    @GetMapping("/deleteserverfile")
    public String deletefile(@RequestParam(name="id", required=true) String id)
    {
    	
    	Server server= serverService.getById(Long.valueOf(id));
    	
		if(fileService.removeFile(UPLOADED_FOLDER, id, server.getAttachment()));
		{
			server.setAttachment(null);
			serverService.updateServer(server);
		}
		return "redirect:/servers";
    	
    }

}
