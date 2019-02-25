package portal.controllers;


import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import portal.entity.AppInstance;
import portal.entity.Site;
import portal.service.AppInstanceService;
import portal.service.AppService;
import portal.service.SiteService;
import portal.service.ZoneService;


@Controller
public class SiteController {
	@Autowired
	private ZoneService zoneService;
	@Autowired
	private SiteService siteService;
	
	@Autowired
	private AppInstanceService appInstanceService;	
	
	@Autowired
	private AppService appService;	
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
    public String deleteSite(@RequestParam(name="id", required=true) String id,@RequestParam(name="siteName", required=true) String siteName) {
    	Site site = new Site();
    	site.setId(Long.valueOf(id));
    	site.setSiteName(siteName);;
    	siteService.updateSite(site);
    	siteService.delete(site);
    	return "redirect:/sites";
    }

    @GetMapping("/sitedetail")
    public String sitedetail(@ModelAttribute("site") Site site,ModelMap model) {
    	model.addAttribute("site", site);
    	model.addAttribute("zones", zoneService.getAll());
    	model.addAttribute("appUnassginedInstances",appInstanceService.getUnassginedAppInstances());
    	model.addAttribute("appAssginedInstances",appService.getAll().stream().sorted().collect(Collectors.toList()));
    	return "sitedetail";
    }
    
    //------app instance---------------    
    @GetMapping("/deleteSiteInstance")
    public String deleteSiteInstance(@ModelAttribute("instance") AppInstance appInstance,@ModelAttribute("site") Site site) {


    	
    	site.removeAppInstance(appInstance);
    	
    	siteService.updateSite(site);
    	
    	
    	return "redirect:/sitedetail?site="+site.getId();
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
