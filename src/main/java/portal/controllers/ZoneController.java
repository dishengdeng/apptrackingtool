package portal.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import portal.entity.Site;
import portal.entity.Zone;

import portal.models.ZoneModel;
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
    	model.addAttribute("zoneModel", new ZoneModel());
        return "zones";
    }
    
    @PostMapping("/addZone")
    public String addZone(@ModelAttribute("zone") ZoneModel zoneModel) {
    	Zone zone = new Zone();
    	zone.setZoneName((zoneModel.getZoneName()));
    	zone.setDescription(zoneModel.getDescription());
    	zone.setNote(zoneModel.getNote());
    	zoneService.addZone(zone);
        return "redirect:/zones";
    }
 
    @PostMapping("/updateZone")
    public String updateZone(@ModelAttribute("zoneModel") ZoneModel zoneModel) {
    	Zone zone = new Zone();
    	zone.setId(zoneModel.getId());
    	zone.setZoneName((zoneModel.getZoneName()));
    	zone.setDescription(zoneModel.getDescription());
    	zone.setNote(zoneModel.getNote());
    	zoneService.updateZone(zone);
        return "redirect:/zones";
    }
    
    @GetMapping("/addZone")
    public String CreateZone(ModelMap model) {
    	model.addAttribute("zone", new ZoneModel());
        return "addZone";
    }
    
    @GetMapping("/deleteZone")
    public String deleteZone(@RequestParam(name="id", required=true) String id,@RequestParam(name="zoneName", required=true) String zoneName) {
    	Zone zone = new Zone();
    	zone.setId(Long.valueOf(id));
    	zone.setZoneName(zoneName);
    	
    	removeSiteFromZone(zone);
    	
    	zoneService.delete(zone);
    	return "redirect:/zones";
    }

    private void removeSiteFromZone(Zone zone)
    {
    	List<Site> sites=siteService.getAllbyZone(zone);
    	if(sites.size()>0)
    	{
        	for(Site site:sites)
        	{
        		site.setZone(null);
        		siteService.updateSite(site);
        	}
    	}

    }
}
