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

import portal.entity.Department;
import portal.service.AppInstanceService;
import portal.service.DepartmentService;
import portal.service.FileService;
import portal.service.StakeholderService;





@Controller
public class DepartmentController {
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private StakeholderService stakholderService;
	
	@Autowired
	private AppInstanceService appInstanceService;
	
	@Autowired
	private FileService fileService;
	
	private final String UPLOADED_FOLDER="files//department//";
	
    @GetMapping("/departments")
    public String departmenttable(ModelMap model) {
    	model.addAttribute("departments", departmentService.getAll());
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
    	
    	department.setStakeholders(stakholderService.findbyDepartment(department));

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


			if(fileService.uploadFile(file, UPLOADED_FOLDER,id));
			{
				department.setAttachment(fileService.getFileName(file.getOriginalFilename()));
    			departmentService.updateDepartment(department);
			}


    		
    	
        return "redirect:/departments";
    }
    
    @GetMapping("/downloaddepartment")
    public ResponseEntity<Resource> downloadfile(@RequestParam(name="id", required=true) String id,HttpServletRequest request)
    {
    	
    	Department department= departmentService.getById(Long.valueOf(id));
    	return fileService.downloadFile(UPLOADED_FOLDER, id, department.getAttachment(), request);
    }

    @GetMapping("/deletedepartmentfile")
    public String deletefile(@RequestParam(name="id", required=true) String id)
    {
    	
    	Department department= departmentService.getById(Long.valueOf(id));
    	
		if(fileService.removeFile(UPLOADED_FOLDER, id, department.getAttachment()));
		{
			department.setAttachment(null);
			departmentService.updateDepartment(department);
		}
		return "redirect:/departments";
    	
    }


}
