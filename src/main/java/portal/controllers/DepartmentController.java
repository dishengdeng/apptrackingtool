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

import portal.entity.Department;
import portal.entity.File;
import portal.service.AppInstanceService;
import portal.service.AppService;
import portal.service.DepartmentService;
import portal.service.FileService;
import portal.service.StakeholderService;
import portal.utility.FileType;





@Controller
public class DepartmentController {
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private StakeholderService stakholderService;
	
	@Autowired
	private AppInstanceService appInstanceService;
	
	@Autowired
	private AppService appService;	
	
	@Autowired
	private FileService fileService;
	
	private final String UPLOADED_FOLDER="files//department//";
	
    @GetMapping("/departments")
    public String departmenttable(ModelMap model) {
    	model.addAttribute("departments", departmentService.getAll());
    	model.addAttribute("appUnassginedInstances",appInstanceService.getUnassginedAppInstances());
    	model.addAttribute("appAssginedInstances",appService.getAll().stream().sorted().collect(Collectors.toList()));
    	model.addAttribute("departmentModel", new Department());
        return "departments";
    }
    
    @PostMapping("/addDepartment")
    public String addDepartment(@ModelAttribute("department") Department department) {

    	departmentService.addDepartment(department);
        return "redirect:/departments";
    }
 
    @PostMapping("/updateDepartment")
    public String updateDepartment(@ModelAttribute("departmentModel") Department department) {
    	
    	departmentService.updateAppIstanceDepartment(department.getAppInstances(), department);
    	departmentService.updateStakeholderDepartment(department.getStakeholders(), department);
    	departmentService.updateDepartment(department);
        return "redirect:/departments";
    }
    
    @GetMapping("/addDepartment")
    public String CreateDepartment(ModelMap model) {
    	model.addAttribute("department", new Department());
        return "addDepartment";
    }
    
    @GetMapping("/deleteDepartment")
    public String deleteDepartment(@RequestParam(name="id", required=true) String id,@RequestParam(name="departmentName", required=true) String departmentName) {
    	Department department = departmentService.getById(Long.valueOf(id));	

    	appInstanceService.removeDeparment(department);   	
    	stakholderService.removeDepartment(department);   	

    	
    	departmentService.delete(department);
    	return "redirect:/departments";
    }
    

//------file management----
    @PostMapping("/departmentupload")
    public String uploadDepartment(@RequestParam("file") MultipartFile file,@RequestParam("departmentid") String id) {
    	Department department= departmentService.getById(Long.valueOf(id));


    	File fileEntity = new File();
    	fileEntity.setFiletype(FileType.SLA);
    	fileEntity.setAttachment(fileService.getFileName(file.getOriginalFilename()));
    	fileEntity.setDepartment(department);

		fileService.uploadFile(file, UPLOADED_FOLDER,id,fileEntity);


    		
    	
        return "redirect:/departments";
    }
    
    @GetMapping("/downloaddepartment")
    public ResponseEntity<Resource> downloadfile(@RequestParam(name="id", required=true) String id,HttpServletRequest request)
    {
    	
    	File file= fileService.findById(Long.valueOf(id));
    	return fileService.downloadFile(UPLOADED_FOLDER,file.getDepartment().getId().toString(),file , request);
    }

    @GetMapping("/deletedepartmentfile")
    public String deletefile(@RequestParam(name="id", required=true) String id)
    {
    	
    	File file= fileService.findById(Long.valueOf(id));
    	
		fileService.removeFile(UPLOADED_FOLDER,file.getDepartment().getId().toString(), file);
		return "redirect:/departments";
    	
    }


}
