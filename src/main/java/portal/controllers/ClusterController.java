package portal.controllers;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import portal.entity.Cluster;

import portal.service.ClusterService;




@Controller
public class ClusterController {
	@Autowired
	private ClusterService clusterService;
	
	
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
    public String deleteCluster(@ModelAttribute("cluster") Cluster cluster) {
    	cluster.removeAllDependence();
    	clusterService.delete(cluster);
    	return "redirect:/clusters";
    }

}
