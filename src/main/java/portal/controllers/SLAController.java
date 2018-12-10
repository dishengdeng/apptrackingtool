package portal.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import portal.entity.SLA;
import portal.service.FileService;
import portal.service.SLAService;




@Controller
public class SLAController {
	
	private final String UPLOADED_FOLDER="files//sla//";
	
	@Autowired
	private SLAService slaService;
	
	@Autowired
	private FileService fileService;	
	
    @GetMapping("/slas")
    public String slatable(ModelMap model) {
    	model.addAttribute("slas", slaService.getAll());
    	model.addAttribute("updateSLA", new SLA());    	
        return "slas";
    }
    
    @PostMapping("/addSLA")
    public String addSLA(@ModelAttribute("sla") SLA sla) {
    	slaService.addSLA(sla);
        return "redirect:/slas";
    }
 
    @PostMapping("/updateSLA")
    public String updateSLA(@ModelAttribute("updateSLA") SLA sla) {

    	slaService.updateSLA(sla);
        return "redirect:/slas";
    }
    
    @GetMapping("/addSLA")
    public String CreateSLA(ModelMap model) {
    	model.addAttribute("sla", new SLA());

        return "addSLA";
    }
    
    @GetMapping("/deleteSLA")
    public String deleteSLA(@RequestParam(name="id", required=true) String id,@RequestParam(name="slaName", required=true) String slaName) {
    	SLA sla = new SLA();
    	sla.setId(id);
    	sla.setSlaName(slaName);
    	slaService.delete(sla);
    	return "redirect:/slas";
    }
    
    //------file management----   
    @PostMapping("/slaupload")
    public String uploadSLA(@RequestParam("file") MultipartFile file,@RequestParam("slaid") String id) {
    	SLA sla= slaService.getById(Long.valueOf(id));
    	if(!file.getOriginalFilename().isEmpty())
    	{

    			if(fileService.uploadFile(file, UPLOADED_FOLDER,id));
    			{
        			sla.setAttachment(file.getOriginalFilename());
        			slaService.updateSLA(sla);
    			}


    		
    	}
        return "redirect:/slas";
    }
    
    @GetMapping("/downloadsla")
    public ResponseEntity<Resource> downloadfile(@RequestParam(name="id", required=true) String id,HttpServletRequest request)
    {
    	
    	SLA sla= slaService.getById(Long.valueOf(id));
    	return fileService.downloadFile(UPLOADED_FOLDER, id, sla.getAttachment(), request);
    }

    @GetMapping("/deleteslafile")
    public String deletefile(@RequestParam(name="id", required=true) String id)
    {
    	
    	SLA sla= slaService.getById(Long.valueOf(id));
    	
		if(fileService.removeFile(UPLOADED_FOLDER, id, sla.getAttachment()));
		{
			sla.setAttachment(null);
			slaService.updateSLA(sla);
		}
		return "redirect:/slas";
    	
    }
}
