package portal.controllers;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import portal.entity.Cluster;
import portal.entity.Server;
import portal.service.ClusterService;
import portal.service.ServerService;



@Controller
public class ClusterController {
	@Autowired
	private ClusterService clusterService;
	
	@Autowired
	private ServerService serverService;
	
    @GetMapping("/clusters")
    public String clustertable(ModelMap model) {
    	model.addAttribute("clusters", clusterService.getAll());
    	model.addAttribute("clusterModel", new Cluster());
        return "clusters";
    }
    
    @PostMapping("/addCluster")
    public String addCluster(@ModelAttribute("cluster") Cluster cluster) {

    	clusterService.addCluster(cluster);
        return "redirect:/clusters";
    }
 
    @PostMapping("/updateCluster")
    public String updateCluster(@ModelAttribute("clusterModel") Cluster cluster) {

    	clusterService.updateCluster(cluster);
        return "redirect:/clusters";
    }
    
    @GetMapping("/addCluster")
    public String CreateCluster(ModelMap model) {
    	model.addAttribute("cluster", new Cluster());
        return "addCluster";
    }
    
    @GetMapping("/deleteCluster")
    public String deleteCluster(@RequestParam(name="id", required=true) String id,@RequestParam(name="clusterName", required=true) String clusterName) {
    	Cluster cluster = new Cluster();
    	cluster.setId(Long.valueOf(id));
    	cluster.setClusterName(clusterName);
    	

    	removeServerFromCluster(cluster);
    	clusterService.delete(cluster);
    	return "redirect:/clusters";
    }

    private void removeServerFromCluster(Cluster cluster)
    {
    	List<Server> servers=serverService.findbyCluster(cluster);
    	if(servers.size()>0)
    	{
        	for(Server server:servers)
        	{
        		server.setCluster(null);
        		serverService.updateServer(server);
        	}
    	}

    }
}
