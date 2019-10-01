package portal.controllers;

import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import portal.entity.Answer;

import portal.entity.Department;
import portal.entity.File;

import portal.entity.Stakeholderext;
import portal.entity.Zacfield;

import portal.entity.Zacmap;

import portal.service.AnswerService;
import portal.service.AppService;
import portal.service.DepartmentService;
import portal.service.FileService;
import portal.service.QuestionService;
import portal.service.SLARoleService;
import portal.service.StakeholderService;
import portal.service.StakeholderextService;
import portal.service.UtilityService;
import portal.service.ZacService;
import portal.service.ZacfieldService;

import portal.service.ZacmapService;

import portal.utility.FileType;





@Controller
public class DepartmentController {
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private StakeholderextService stakeholderextService;
	
	@Autowired
	private AnswerService answerService;
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private AppService appService;
	
	@Autowired
	private StakeholderService stakeholderService;
	
	@Autowired
	private ZacfieldService zacfieldService;
	
	@Autowired
	private UtilityService utilityService;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private ZacService zacService;

	
	@Autowired
	private ZacmapService zacmapService;
	@Autowired
	private SLARoleService slaRoleService;
	
	
	private final String UPLOADED_FOLDER="files//department//";
	
    @GetMapping("/")
    public String departmenttable(ModelMap model) {
    	model.addAttribute("departments", departmentService.getAll());

        return "departments";
    }
    

    
    @PostMapping("/addDepartment")
    public String addDepartment(@ModelAttribute("department") Department department) {

    	departmentService.addDepartment(department);
        return "redirect:/";
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
    	Department department=departmentService.getById(Long.valueOf(id));
    	model.addAttribute("department",department);
    	model.addAttribute("stakeholders",stakeholderService.getAll());
    	model.addAttribute("roles", slaRoleService.getAll());

    	//--Zacmap-----
    	model.addAttribute("zacs",zacService.getAll());
    	model.addAttribute("zacmaps",departmentService.getZacmap(department));
    	model.addAttribute("questions", questionService.getAllQuestion());
    	model.addAttribute("zacfields", department.getZacfields().stream().sorted().collect(Collectors.toList()));
    	model.addAttribute("apps", appService.getAll());
    	model.addAttribute("departments",departmentService.getAll());

        return "departmentdetail";
    }    
    
    @GetMapping("/deleteDepartment")
    public String deleteDepartment(@ModelAttribute("department") Department department) {

    	department.getZaclists().forEach(obj->{
    		obj.removeForDepartment();
    		obj.getZacmap().removeAllDepedence();
    	});
    	department.removeAllDependence();
    	departmentService.removFiles(UPLOADED_FOLDER, department);
    	
    	departmentService.delete(department);
    	return "redirect:/";
    }
    
    //------Question---------------    
    @GetMapping("/deleteDepartmentQuestion")
    public String deleteDepartmentQuestion(@ModelAttribute("answer") Answer answer,@ModelAttribute("department") Department department) {


    	
    	answer.removeAllDependence();
    	answerService.DeleteAnswer(answer);
    	
    	
    	return "redirect:/departmentdetail?id="+department.getId();
    }
    
    @PostMapping("/addDepartmentQeustion")
    public String addDepartmentQeustion(@ModelAttribute("answer") Answer answer) {


    	
    	answerService.UpdateAnswer(answer);
    	
    	
    	return "redirect:/departmentdetail?id="+answer.getDepartment().getId();
    }     
    
   

    //------Zacmap---------------    
    @GetMapping("/deleteDepartmentZacmap")
    public String deleteApplicationZacmap(@ModelAttribute("zacmap") Zacmap zacmap,@ModelAttribute("department") Department department) {

    	

    	zacmap.getZaclists().forEach(obj->{
    		obj.removeForZacmap();
    	});    	
    	zacmap.removeAllDepedence();
    	zacmapService.deleteZacmap(zacmap); 
    	   	
    	
    	return "redirect:/departmentdetail?id="+department.getId();
    }
    
    @PostMapping("/addDepartmentZacmap")
    public void addDepartmentZacmap(HttpServletRequest request,HttpServletResponse response) throws Exception{


    	Zacmap zacmapObj=departmentService.saveZacmap(utilityService.getJSONObject(request));

    	
    	
    	response.setContentType("application/json");
    	if(ObjectUtils.isEmpty(zacmapObj)) {response.setStatus(202);} else {response.setStatus(200);}
    	response.getWriter().write(ObjectUtils.isEmpty(zacmapObj)? "{'status':'202'}":getResponse(zacmapObj).toString());
    	response.flushBuffer();
    	

    }
    
    @PostMapping("/updateDepartmentZacmap")
    public void updateDepartmentZacmap(HttpServletRequest request,HttpServletResponse response) throws Exception{


    	Zacmap zacmapObj=departmentService.updateZacmap(utilityService.getJSONObject(request));

    	
    	
    	response.setContentType("application/json");
    	if(ObjectUtils.isEmpty(zacmapObj)) 
    	{response.setStatus(202);} 
    	else 
    	{response.setStatus(200);}

    	response.getWriter().write(ObjectUtils.isEmpty(zacmapObj)? "{'status':'202'}":getResponse(zacmapObj).toString());
    	response.flushBuffer();
    	

    }
    
    private JSONObject getResponse(Zacmap zacmapObj)
    {
    	JSONObject responseObj=new JSONObject();
    	responseObj.put("zacmap_id", zacmapObj.getId());
    	responseObj.put("application_id", zacmapObj.getApplication().getId());
    	responseObj.put("detail", zacmapObj.getDetail());
    	return responseObj;
    }
    
  //--Stakeholder--    
    @GetMapping("/deleteDepartmentStakeholder")
    public String deleteDepartmentStakeholder(@ModelAttribute("stakeholderext") Stakeholderext stakeholderext) {


    	Long id = stakeholderext.getDepartment().getId();
    	stakeholderext.removeAllDependence();
    	stakeholderextService.delete(stakeholderext);
    	return "redirect:/departmentdetail?id="+id;
    }    
    
    @PostMapping("/addDepartmentStakeholder")
    public String addDepartmentStakeholder(@ModelAttribute("stakeholderext") Stakeholderext stakeholderext) {

    	stakeholderextService.save(stakeholderext);
    	return "redirect:/departmentdetail?id="+stakeholderext.getDepartment().getId();
    } 
    
    @PostMapping("/updateDepartmentStakeholder")
    public String updateDepartmentStakeholder(@ModelAttribute("stakeholderext") Stakeholderext stakeholderext) {

    	stakeholderextService.update(stakeholderext);
    	return "redirect:/departmentdetail?id="+stakeholderext.getDepartment().getId();
    } 
    

    
    //------Zacfield---------------    
    @GetMapping("/deletedepartmentzacfield")
    public String deletedepartmentzacfield(@ModelAttribute("zacfield") Zacfield zacfield,@ModelAttribute("department") Department department) {


    	zacfield.removeAllDepdence();
    	
    	zacfield.getZaclists().forEach(obj->{
    		obj.removeForZacfield();
    	});
    	zacfieldService.delete(zacfield);
    	
    	return "redirect:/departmentdetail?id="+department.getId();
    }
    
    @PostMapping("/adddepartmentzacfield")
    public void adddepartmentzacfield(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	response.setContentType("application/json");
    	if(departmentService.saveZacfield(utilityService.getJSONObject(request)))
    	{
    		response.setStatus(200);
    	}
    	else
    	{
    		response.setStatus(400);
    	}
    	
    	

    	response.flushBuffer();
    }
    
    @PostMapping("/updatedepartmentzacfield")
    public String updatedepartmentzacfield(@ModelAttribute("zacfield") Zacfield zacfield) {

    	zacfieldService.update(zacfield);
    	return "redirect:/departmentdetail?id="+zacfield.getDepartment().getId();
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
