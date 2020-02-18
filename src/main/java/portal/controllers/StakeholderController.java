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
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import portal.entity.Department;
import portal.entity.Stakeholder;
import portal.entity.Stakeholderext;
import portal.models.StakeholderModel;
import portal.service.DepartmentService;
import portal.service.ImportService;
import portal.service.SLARoleService;
import portal.service.SiteService;
import portal.service.StakeholderService;
import portal.service.StakeholderextService;
import portal.utility.InvalidDataFormatException;
import portal.utility.InvalidTemplateFormatException;
import portal.validator.StakeholderValidator;




@Controller
public class StakeholderController {
	@Autowired
	private StakeholderService stakeholderService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private SLARoleService slaRoleService;
	
	@Autowired
	private SiteService siteService;
	
	@Autowired
	private StakeholderextService stakeholderextService;
	
	@Autowired
	private StakeholderValidator stakeholderValidator;
	
	@Autowired
	private ImportService importService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AppdepartmentController.class);
	
    @GetMapping("/stakeholders")
    public String stakeholdertable(ModelMap model) {
    	model.addAttribute("stakeholders", stakeholderService.getAll());

    	model.addAttribute("roles", slaRoleService.getAll());

        return "stakeholders";
    }
    
    @PostMapping("/addStakeholder")
    public String addStakeholder(@ModelAttribute("stakeholder") Stakeholder stakeholder) {

    	stakeholderService.addStakeholder(stakeholder);
        return "redirect:/stakeholders";
    }
 
    @PostMapping("/updateStakeholder")
    public String updateStakeholder(@ModelAttribute("stakeholderModel") StakeholderModel stakeholderModel,BindingResult bindingResult,ModelMap model) {
    	
    	
    	
    	stakeholderValidator.validate(stakeholderModel, bindingResult);
    	if (bindingResult.hasErrors()) {
    	
    		setModel(model,stakeholderService.getById(stakeholderModel.getId()),stakeholderModel);
    		return "stakeholderdetail";
    	}
    	
    	
    	stakeholderService.updateDetail(stakeholderModel);

    	return "redirect:/stakeholderdetail?stakeholder="+stakeholderModel.getId();
    }
    
    @GetMapping("/addStakeholder")
    public String CreateStakeholder(ModelMap model) {
    	model.addAttribute("stakeholder", new Stakeholder());
    	model.addAttribute("departments", departmentService.getAll());
    	model.addAttribute("roles", slaRoleService.getAll());
    	model.addAttribute("sites",siteService.getAll());
        return "addStakeholder";
    }
    
    @GetMapping("/deleteStakeholder")
    public String deleteStakeholder(@ModelAttribute("stakeholder") Stakeholder stakeholder) {

    	stakeholder.removeAllDependence();
    	
    	stakeholderService.delete(stakeholder);
    	return "redirect:/stakeholders";
    }
    
    @GetMapping("/stakeholderdetail")
    public String stakeholderdetail(@ModelAttribute("stakeholder") Stakeholder stakeholder,ModelMap model) {
    	
    	setModel(model,stakeholder,getUpdatedStakeholder(stakeholder));
        return "stakeholderdetail";
    }

    //--department--
    @GetMapping("/deleteStakeHolderext")
    public String deleteStakeHolderext(@ModelAttribute("stakeholderext") Stakeholderext stakeholderext)
    {
    	Long id = stakeholderext.getStakeholder().getId();
    	stakeholderext.removeAllDependence();
    	stakeholderextService.delete(stakeholderext);
    	return "redirect:/stakeholderdetail?stakeholder="+id;
    }
    
    @PostMapping("/addStakeHolderext")
    public String addStakeHolderDepartment(@ModelAttribute("stakeholderext") Stakeholderext stakeholderext)
    {
    	stakeholderextService.save(stakeholderext);
    	return "redirect:/stakeholderdetail?stakeholder="+stakeholderext.getStakeholder().getId();
    }

    @PostMapping("/updateStakeHolderext")
    public String updateStakeHolderext(@ModelAttribute("stakeholderext") Stakeholderext stakeholderext)
    {
    	stakeholderextService.update(stakeholderext);
    	return "redirect:/stakeholderdetail?stakeholder="+stakeholderext.getStakeholder().getId();
    }
    
    //import
    @PostMapping("/stakeholderimport")
    public @ResponseBody ResponseEntity<String> departmentupload(@RequestParam("file") MultipartFile file,@ModelAttribute("department") Department department) {

    	try
    	{

    	JSONArray data=importService.getStakeholders(file).get();


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
    
    private StakeholderModel getUpdatedStakeholder(Stakeholder stakeholder)
    {
    	StakeholderModel stakeholderModel=new StakeholderModel();
    	stakeholderModel.setId(stakeholder.getId());
    	stakeholderModel.setEmail(stakeholder.getEmail());
    	stakeholderModel.setNote(stakeholder.getNote());
    	stakeholderModel.setPhone(ObjectUtils.isEmpty(stakeholder.getPhone())?null:String.valueOf(stakeholder.getPhone()));
    	stakeholderModel.setSite(ObjectUtils.isEmpty(stakeholder.getSite())? null:stakeholder.getSite().getId());
    	stakeholderModel.setPosition(stakeholder.getPosition());
    	stakeholderModel.setStakeholderName(stakeholder.getStakeholderName());
    	stakeholderModel.setBusinessunit(stakeholder.getBusinessunit());
    	return stakeholderModel;
    }
    private void setModel(ModelMap model,Stakeholder stakeholder,StakeholderModel stakeholderModel)
    {
    	model.addAttribute("stakeholderModel", stakeholderModel);
    	model.addAttribute("stakeholder", stakeholder);
    	model.addAttribute("departments", departmentService.getAll());
    	model.addAttribute("roles", slaRoleService.getAll());
    	model.addAttribute("sites",siteService.getAll());
    }
}
