package portal.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import portal.entity.Site;
import portal.entity.Zone;

import portal.service.SiteService;
import portal.service.ZoneService;



@Controller
public class ZoneController {
	@Autowired
	private ZoneService zoneService;
	

	
	@Autowired
	private SiteService siteService;

	
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
    

}
