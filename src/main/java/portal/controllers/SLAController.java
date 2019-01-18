package portal.controllers;

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
    	model.addAttribute("appUnassginedInstances",appInstanceService.getUnassginedAppInstances());
    	model.addAttribute("appAssginedInstances",appService.getAll().stream().sorted().collect(Collectors.toList()));    	
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
    	slaService.updateAppIstanceSLA(sla.getAppInstances(), sla);
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
    	
    	slaService.removeAllSLA(sla);
    	slaService.delete(sla);
    	return "redirect:/slas";
    }
    
    //------file management----   
    @PostMapping("/slaupload")
    public String uploadSLA(@RequestParam("file") MultipartFile file,@RequestParam("slaid") String id) {
    	SLA sla= slaService.getById(Long.valueOf(id));
    	
    	File fileEntity = new File();
    	fileEntity.setFiletype(FileType.SLA);
    	fileEntity.setAttachment(fileService.getFileName(file.getOriginalFilename()));
    	fileEntity.setSla(sla);

		fileService.uploadFile(file, UPLOADED_FOLDER,id,fileEntity);



    		
 
        return "redirect:/slas";
    }
    
    @GetMapping("/downloadsla")
    public ResponseEntity<Resource> downloadfile(@RequestParam(name="id", required=true) String id,HttpServletRequest request)
    {
    	
    	File file= fileService.findById(Long.valueOf(id));
    	return fileService.downloadFile(UPLOADED_FOLDER,file.getSla().getId().toString(),file , request);
    }

    @GetMapping("/deleteslafile")
    public String deletefile(@RequestParam(name="id", required=true) String id)
    {
    	
    	File file= fileService.findById(Long.valueOf(id));
    	
		fileService.removeFile(UPLOADED_FOLDER,file.getSla().getId().toString(), file);

		return "redirect:/slas";
    	
    }
}
