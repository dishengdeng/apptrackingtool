package portal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import portal.entity.Zac;
import portal.service.ZacService;

@Controller
public class ZacController {
	@Autowired
	private ZacService zacService;
	
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
}
