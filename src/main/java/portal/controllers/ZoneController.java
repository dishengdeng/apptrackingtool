package portal.controllers;



import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import portal.entity.Site;
import portal.entity.Zone;
import portal.service.AppInstanceService;
import portal.service.AppService;
import portal.service.SiteService;
import portal.service.ZoneService;
import portal.entity.AppInstance;
import portal.entity.Application;


@Controller
public class ZoneController {
	@Autowired
	private ZoneService zoneService;
	
	@Autowired
	private AppInstanceService appInstanceService;	
	
	@Autowired
	private SiteService siteService;
	
	@Autowired
	private AppService appService;	
	
    @GetMapping("/zones")
    public String zonetable(ModelMap model) {
    	model.addAttribute("zones", zoneService.getAll());
    	model.addAttribute("zoneModel", new Zone());
        return "zones";
    }
    
    @PostMapping("/addZone")
    public String addZone(@ModelAttribute("zone") Zone zone) {
    	zoneService.addZone(zone);
        return "redirect:/zones";
    }
 
    @PostMapping("/updateZone")
    public String updateZone(@ModelAttribute("zoneModel") Zone zone) {

    	zoneService.updateZone(zone);
        return "redirect:/zonedetail?zone="+zone.getId();
    }
    
    @GetMapping("/addZone")
    public String CreateZone(ModelMap model) {
    	model.addAttribute("zone", new Zone());
        return "addZone";
    }
    
    @GetMapping("/deleteZone")
    public String deleteZone(@ModelAttribute("zone") Zone zone) {

    	zone.removeAllDependence();
    	zoneService.delete(zone);
    	return "redirect:/zones";
    }

    @GetMapping("/zonedetail")
    public String zonedetail(@ModelAttribute("zone") Zone zone,ModelMap model) {
    	model.addAttribute("zone",zone);
    	model.addAttribute("sites",siteService.getAll());
    	model.addAttribute("appUnassginedInstances",appInstanceService.getUnassginedAppInstances());
    	model.addAttribute("appAssginedInstances",appService.getAll().stream().sorted().collect(Collectors.toList()));    	
        return "zonedetail";
    }
    
    //---site--
    @GetMapping("/deleteZoneSite")
    public String deleteZoneSite(@ModelAttribute("zone") Zone zone,@ModelAttribute("site") Site site,ModelMap model) {

    	site.setZone(null);
    	siteService.updateSite(site);
    	return "redirect:/zonedetail?zone="+zone.getId();
    }
    
    @PostMapping("/addZoneSite")
    public String addZoneSite(@ModelAttribute("zone") Zone zone) {


    	zoneService.updateZone(zone);
    	
        return "redirect:/zonedetail?zone="+zone.getId();
    } 
    
    //------application---------------    
    @GetMapping("/deleteZoneApp")
    public String deleteappzone(@ModelAttribute("application") Application application,@ModelAttribute("zone") Zone zone) {


    	
    	zone.removeApplication(application);
    	zoneService.updateZone(zone);
    	
    	
    	return "redirect:/zonedetail?zone="+zone.getId();
    }
    
    @PostMapping("/addZoneApp")
    public String addAppZone(@ModelAttribute("zone") Zone zone) {


    	
    	zoneService.updateZone(zone);
    	
    	return "redirect:/zonedetail?zone="+zone.getId();
    }    
    
    //---instance--
    @GetMapping("/deleteZoneInstance")
    public String deleteZoneSite(@ModelAttribute("instance") AppInstance instance,@ModelAttribute("zone") Zone zone,ModelMap model) {

    	zone.removeAppInstance(instance);
    	zoneService.updateZone(zone);
    	return "redirect:/zonedetail?zone="+zone.getId();
    }
    
    @PostMapping("/addZoneInstance")
    public String addZoneInstance(@ModelAttribute("zone") Zone zone) {


    	zoneService.updateZone(zone);
    	
        return "redirect:/zonedetail?zone="+zone.getId();
    }
}
