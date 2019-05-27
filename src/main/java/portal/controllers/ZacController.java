package portal.controllers;



import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import portal.entity.Application;
import portal.entity.Zac;
import portal.entity.Zacmap;
import portal.service.AppService;
import portal.service.DepartmentService;
import portal.service.ZacService;
import portal.service.ZacmapService;

@Controller
public class ZacController {
	@Autowired
	private ZacService zacService;

	@Autowired
	private ZacmapService zacmapService;
	@Autowired
	private AppService appService;
	
	@Autowired
	private DepartmentService departmentService;
	
    @GetMapping("/zacs")
    public String departmenttable(ModelMap model) {
    	model.addAttribute("zacs", zacService.getAll());

        return "zacs";
    }
    
    @PostMapping("/addZac")
    public String addZac(@ModelAttribute("zac") Zac zac) {

    	zacService.saveZac(zac);
        return "redirect:/zacs";
    }
    
    @GetMapping("/addZac")
    public String createZac(ModelMap model) {
    	model.addAttribute("zac", new Zac());
        return "addZac";
    }
    
    @GetMapping("/deleteZac")
    public String deleteZac(@ModelAttribute("zac") Zac zac) {


    	zac.removeAllDependence();

    	
    	zacService.deleteZac(zac);;
    	return "redirect:/zacs";
    }
    @PostMapping("/updateZac")
    public String updateZac(@ModelAttribute("zac") Zac zac) {

    	zacService.updateZac(zac);
        return "redirect:/zacdetail?zac="+zac.getId();
    }    

    @GetMapping("/zacdetail")
    public String zacdetail(@ModelAttribute("zac") Zac zac,ModelMap model) {
    	model.addAttribute("zac",zac);
    	model.addAttribute("applications",appService.getAll());
    	model.addAttribute("departments",departmentService.getAll());
 	
        return "zacdetail";
    }
    
    
    //------application---------------    
    @GetMapping("/deleteZacApp")
    public String deleteZacApp(@ModelAttribute("application") Application application,@ModelAttribute("zac") Zac zac) {


    	
    	application.setZac(null);
    	appService.updateApp(application);
    	
    	
    	return "redirect:/zacdetail?zac="+zac.getId();
    }
    
    @PostMapping("/addZacApp")
    public String addZacApp(@ModelAttribute("zac") Zac zac) {


    	
    	zacService.updateZac(zac);
    	
    	
    	return "redirect:/zacdetail?zac="+zac.getId();
    }  
    
    //------Zacmap---------------    
    @GetMapping("/deleteZacZacmap")
    public String deleteZacZacmap(@ModelAttribute("application") Application application,@ModelAttribute("zac") Zac zac) {


    	List<Zacmap> Zacmaps=zac.getZacmaps().stream().filter(obj->obj.getApplication().equals(application)).collect(Collectors.toList());
    	Zacmaps.forEach(obj->{
    		obj.removeAllDepedence();
    		zacmapService.deleteZacmap(obj);
    	});
  
    	
    	
    	return "redirect:/zacdetail?zac="+zac.getId();
    }
    
    @PostMapping("/addZacZacmap")
    public String addZacZacmap(@ModelAttribute("zac") Zac zac) {


    	
    	zacService.updateZac(zac);
    	
    	
    	return "redirect:/zacdetail?zac="+zac.getId();
    } 
}
