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

import portal.entity.AppInstance;
import portal.entity.Department;
import portal.entity.File;
import portal.entity.Stakeholder;
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

        return "departments";
    }
    

    
    @PostMapping("/addDepartment")
    public String addDepartment(@ModelAttribute("department") Department department) {

    	departmentService.addDepartment(department);
        return "redirect:/departments";
    }
    
    @PostMapping("/updateDepartment")
    public String updateDepartment(@ModelAttribute("department") Department department) {
    	

    	departmentService.updateDepartment(department);
        return "redirect:/departmentdetail?id="+department.getId();
    }
    
    @GetMapping("/addDepartment")
    public String CreateDepartment(ModelMap model) {
    	model.addAttribute("department", new Department());
        return "addDepartment";
    }
    
    @GetMapping("/departmentdetail")
    public String DepartmentDetial(@RequestParam(name="id", required=true) String id,ModelMap model) {
    	model.addAttribute("department",departmentService.getById(Long.valueOf(id)));
    	model.addAttribute("stakeholders",stakholderService.getUnassginedStakeholders());
    	model.addAttribute("appUnassginedInstances",appInstanceService.getUnassginedAppInstances());
    	model.addAttribute("appAssginedInstances",appService.getAll().stream().sorted().collect(Collectors.toList()));
        return "departmentdetail";
    }    
    
    @GetMapping("/deleteDepartment")
    public String deleteDepartment(@RequestParam(name="id", required=true) String id,@RequestParam(name="departmentName", required=true) String departmentName) {
    	Department department = departmentService.getById(Long.valueOf(id));	

    	appInstanceService.removeDeparment(department);   	
    	stakholderService.removeDepartment(department);   	
    	departmentService.removFiles(UPLOADED_FOLDER, department);
    	
    	departmentService.delete(department);
    	return "redirect:/departments";
    }
//--Instance--    
    @GetMapping("/deleteDepartmentInstance")
    public String deleteDepartmentInstance(@ModelAttribute("instance") AppInstance appInstance,@ModelAttribute("department") Department department,ModelMap model) {
    	appInstance.setDepartment(null);
    	appInstanceService.updateAppInstance(appInstance);

    	return "redirect:/departmentdetail?id="+department.getId();
    }    
    
    @PostMapping("/addDepartmentInstance")
    public String addDepartmentInstance(ModelMap model,@ModelAttribute("department") Department department) {

    	departmentService.updateAppIstanceDepartment(department.getAppInstances(), department);

    	return "redirect:/departmentdetail?id="+department.getId();
    }    

    
  //--Stakeholder--    
    @GetMapping("/deleteDepartmentStakeholder")
    public String deleteDepartmentStakeholder(@ModelAttribute("stakeholder") Stakeholder stakeholder,@ModelAttribute("department") Department department) {
    	stakeholder.setDepartment(null);
    	stakholderService.updateStakeholder(stakeholder);

    	return "redirect:/departmentdetail?id="+department.getId();
    }    
    
    @PostMapping("/addDepartmentStakeholder")
    public String addDepartmentStakeholder(ModelMap model,@ModelAttribute("department") Department department) {

    	departmentService.updateStakeholderDepartment(department.getStakeholders(), department);

    	return "redirect:/departmentdetail?id="+department.getId();
    } 
//------file management----
    @PostMapping("/departmentupload")
    public String departmentupload(@RequestParam("file") MultipartFile file,ModelMap model,@ModelAttribute("department") Department department) {

    	

    	File fileEntity = new File();
    	fileEntity.setFiletype(FileType.SLA);
    	fileEntity.setAttachment(fileService.getFileName(file.getOriginalFilename()));
    	fileEntity.setDepartment(department);
    	fileEntity=fileService.uploadFile(file, UPLOADED_FOLDER,department.getId().toString(),fileEntity);
    	

		

//		FileModel fileModel= new FileModel();
//		fileModel.setId(fileEntity.getId());
//		fileModel.setFilename(fileEntity.getAttachment());
//		fileModel.setDepartmentid(department.getId());
        //return new ResponseEntity<FileModel>(fileModel,HttpStatus.OK);
    	return "redirect:/departmentdetail?id="+department.getId();
    }
    
    @GetMapping("/downloaddepartment")
    public ResponseEntity<Resource> downloadfile(@RequestParam(name="id", required=true) String id,HttpServletRequest request)
    {
    	
    	File file= fileService.findById(Long.valueOf(id));
    	return fileService.downloadFile(UPLOADED_FOLDER,file.getDepartment().getId().toString(),file , request);
    }

    @GetMapping("/deletedepartmentfile")
    public String deletefile(@ModelAttribute("file") File file,ModelMap model,@ModelAttribute("department") Department department)
    {
    	

		fileService.removeFile(UPLOADED_FOLDER,file.getDepartment().getId().toString(), file);
		
		

    	
    	return "redirect:/departmentdetail?id="+department.getId();
    	
    }


}
