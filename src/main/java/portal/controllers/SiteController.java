package portal.controllers;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;



import portal.entity.Site;
import portal.service.SiteService;
import portal.service.ZoneService;


@Controller
public class SiteController {
	@Autowired
	private ZoneService zoneService;
	@Autowired
	private SiteService siteService;
	

    @GetMapping("/sites")
    public String sitetable(ModelMap model) {
    	model.addAttribute("sites", siteService.getAll());
        return "sites";
    }
    
    @PostMapping("/addSite")
    public String addSite(@ModelAttribute("site") Site site) {
    	siteService.addSite(site);
        return "redirect:/sites";
    }
 
    @PostMapping("/updateSite")
    public String updateSite(@ModelAttribute("updateSite") Site site) {

    	siteService.updateSite(site);
        return "redirect:/sites";
    }
    
    @GetMapping("/addSite")
    public String CreateSite(ModelMap model) {
    	model.addAttribute("site", new Site());
    	model.addAttribute("zones", zoneService.getAll());
        return "addSite";
    }
    
    @GetMapping("/deleteSite")
    public String deleteSite(@ModelAttribute("site") Site site) {
    	site.removeAllDependence();
    	siteService.delete(site);
    	return "redirect:/sites";
    }

    @GetMapping("/sitedetail")
    public String sitedetail(@ModelAttribute("site") Site site,ModelMap model) {
    	model.addAttribute("site", site);
    	model.addAttribute("zones", zoneService.getAll());

    	return "sitedetail";
    }
    
    
    @PostMapping("/addSiteInstance")
    public String addOrupdateInstanceSite(@ModelAttribute("site") Site site) {


    	
    	siteService.updateSite(site);
    	

    	return "redirect:/sitedetail?site="+site.getId();
    }
    
    //---zone--
    @GetMapping("/deleteSiteZone")
    public String deleteSiteInstance(@ModelAttribute("site") Site site) {

    	
    	site.setZone(null);
    	siteService.updateSite(site);
    	
    	
    	return "redirect:/sitedetail?site="+site.getId();
    }
    
    @PostMapping("/addSiteZone")
    public String addSiteZone(@ModelAttribute("site") Site site) {


    	
    	siteService.updateSite(site);
    	

    	return "redirect:/sitedetail?site="+site.getId();
    }
}
