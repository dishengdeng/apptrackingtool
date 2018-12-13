package portal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    	model.addAttribute("updateSite", new Site());
    	model.addAttribute("zones", zoneService.getAll());
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

}
