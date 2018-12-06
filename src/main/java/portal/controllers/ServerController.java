package portal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import portal.entity.Server;
import portal.service.ClusterService;
import portal.service.ServerService;



@Controller
public class ServerController {
	@Autowired
	private ClusterService clusterService;
	@Autowired
	private ServerService serverService;
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

}
