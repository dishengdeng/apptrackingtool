package portal.controllers;

import java.util.ArrayList;
import java.util.stream.Collectors;

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

import portal.entity.AppInstance;
import portal.entity.File;
import portal.entity.SLA;
import portal.service.AppInstanceService;
import portal.service.AppService;
import portal.service.FileService;
import portal.service.SLAService;
import portal.utility.FileType;




@Controller
public class SLAController {
	
	private final String UPLOADED_FOLDER="files//sla//";
	
	@Autowired
	private SLAService slaService;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private AppInstanceService appInstanceService;
	
	@Autowired
	private AppService appService;			
	
    @GetMapping("/slas")
    public String slatable(ModelMap model) {
    	model.addAttribute("slas", slaService.getAll());	
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
    	return "redirect:/sladetail?sla="+sla.getId();
    }
    
    @GetMapping("/addSLA")
    public String CreateSLA(ModelMap model) {
    	model.addAttribute("sla", new SLA());

        return "addSLA";
    }
    
    @GetMapping("/deleteSLA")
    public String deleteSLA(@ModelAttribute("sla") SLA sla) {

    	
    	slaService.removeAllSLA(sla);
    	slaService.removFiles(UPLOADED_FOLDER, sla);
    	slaService.delete(sla);
    	return "redirect:/slas";
    }
    @GetMapping("/sladetail")
    public String supportdetail(@ModelAttribute("sla") SLA sla,ModelMap model) {
    	model.addAttribute("sla",sla);
    	model.addAttribute("appUnassginedInstances",appInstanceService.getUnassginedAppInstances());
    	model.addAttribute("appAssginedInstances",appService.getAll().stream().sorted().collect(Collectors.toList()));
        return "sladetail";
    } 
    //--Instance--    
    @GetMapping("/deleteSLAInstance")
    public String deleteSupportInstance(@ModelAttribute("instance") AppInstance appInstance,@ModelAttribute("sla") SLA sla,ModelMap model) {
    	appInstance.setSla(null);
    	appInstanceService.updateAppInstance(appInstance);

    	return "redirect:/sladetail?sla="+sla.getId();
    }    
    
    @PostMapping("/addSLAInstance")
    public String addContractInstance(ModelMap model,@ModelAttribute("sla") SLA sla) {

    	slaService.updateAppIstanceSLA(new ArrayList<>(sla.getAppInstances()), sla);

    	return "redirect:/sladetail?sla="+sla.getId();
    }    
    
    //------file management----   
    @PostMapping("/slaupload")
    public String uploadSLA(@RequestParam("file") MultipartFile file,@ModelAttribute("sla") SLA sla) {

    	
    	File fileEntity = new File();
    	fileEntity.setFiletype(FileType.SLA);
    	fileEntity.setAttachment(fileService.getFileName(file.getOriginalFilename()));
    	fileEntity.setSla(sla);

		fileService.uploadFile(file, UPLOADED_FOLDER,sla.getId().toString(),fileEntity);



    		
 
		return "redirect:/sladetail?sla="+sla.getId();
    }
    
    @GetMapping("/downloadsla")
    public ResponseEntity<Resource> downloadfile(@ModelAttribute("file") File file,HttpServletRequest request)
    {
    	

    	return fileService.downloadFile(UPLOADED_FOLDER,file.getSla().getId().toString(),file , request);
    }

    @GetMapping("/deleteslafile")
    public String deletefile(@ModelAttribute("file") File file,@ModelAttribute("sla") SLA sla)
    {
    	

    	
		fileService.removeFile(UPLOADED_FOLDER,file.getSla().getId().toString(), file);

		return "redirect:/sladetail?sla="+sla.getId();
    	
    }
}
