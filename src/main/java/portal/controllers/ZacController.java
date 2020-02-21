package portal.controllers;





import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import portal.entity.Department;
import portal.entity.Zac;
import portal.entity.Zacmap;
import portal.service.AppService;
import portal.service.DepartmentService;
import portal.service.ImportService;
import portal.service.ZacService;
import portal.utility.InvalidDataFormatException;
import portal.utility.InvalidTemplateFormatException;


@Controller
public class ZacController {
	@Autowired
	private ZacService zacService;


	@Autowired
	private AppService appService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private ImportService importService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ZacController.class);
	
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
    	model.addAttribute("zacmapModel",new Zacmap());
    	model.addAttribute("applications",appService.getAll());
    	model.addAttribute("departments",departmentService.getAll());
 	
        return "zacdetail";
    }
    
    //import
    @PostMapping("/zacmapimport")
    public @ResponseBody ResponseEntity<String> departmentupload(@RequestParam("file") MultipartFile file,@ModelAttribute("department") Department department) {

    	try
    	{

    	JSONArray data=importService.getZacs(file, department).get();
    	importService.importZacmap(data, department);


    	}
    	catch(ExecutionException ex)
    	{
    		if(ex.getCause() instanceof InvalidTemplateFormatException)
    		{
    			return new ResponseEntity<String>(((InvalidTemplateFormatException)ex.getCause()).get_message(),HttpStatus.BAD_REQUEST);
    		}
    		if(ex.getCause() instanceof InvalidDataFormatException)
    		{
    			return new ResponseEntity<String>(((InvalidDataFormatException)ex.getCause()).get_message(),HttpStatus.BAD_REQUEST);
    		}    		
    		else
    		{
    			LOGGER.info(ex.getMessage());
    			return new ResponseEntity<String>("Internal Server Error",HttpStatus.INTERNAL_SERVER_ERROR);
    		}
    	}
    	catch(Exception ex)
    	{  
    		LOGGER.info(ex.getMessage());  		
    		return new ResponseEntity<String>("Internal Server Error",HttpStatus.INTERNAL_SERVER_ERROR);
    	}

    	return new ResponseEntity<String>("Successfully Submit your upload request. You will get a notice once it done. Or come back later to check",HttpStatus.CREATED);

    }

    
 
}
