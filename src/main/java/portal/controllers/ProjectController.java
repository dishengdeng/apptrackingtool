package portal.controllers;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import portal.entity.AppInstance;
import portal.entity.Application;
import portal.entity.Project;
import portal.service.AppInstanceService;
import portal.service.AppService;
import portal.service.ProjectService;

@Controller
public class ProjectController {
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private AppInstanceService appInstanceService;
	
	@Autowired
	private AppService appService;	
	
	@GetMapping("/projects")
	public String projects(ModelMap model)
	{
		model.addAttribute("projects", projectService.getAll());
		return "projects";
	}
	
	
	@GetMapping("/addproject")
	public String addproject(ModelMap model)
	{
		model.addAttribute("project", new Project());
		return "addproject";
	}
	
	@PostMapping("/addproject")
	public String creatproject(@ModelAttribute("project") Project project)
	{
		projectService.addProject(project);
		return "redirect:/projects";
	}
	
	@GetMapping("/projectdetail")
	public String projectdetail(@ModelAttribute("project") Project project,ModelMap model)
	{
		model.addAttribute("project", project);
    	model.addAttribute("appUnassginedInstances",appInstanceService.getUnassginedAppInstances());
    	model.addAttribute("appAssginedInstances",appService.getAll().stream().sorted().collect(Collectors.toList()));		
		return "projectdetail";
	}
	
	@PostMapping("/updateproject")
	public String updateproject(@ModelAttribute("project") Project project)
	{
		projectService.updateProject(project);
		return "redirect:/projectdetail?project="+project.getId();
	}
	
	@GetMapping("/deleteproject")
	public String deleteproject(@ModelAttribute("project") Project project)
	{
		project.removeAllAppInstance();
		project.removeAllApplication();
		projectService.deleteProject(project);
		return "redirect:/projects";
	}

	//---app instance----
	@GetMapping("/deleteProjectInstance")
	public String deleteProjectInstance(@ModelAttribute("instance") AppInstance appInstance,@ModelAttribute("project") Project project)
	{
		project.removeAppInstance(appInstance);
		projectService.updateProject(project);
		return "redirect:/projectdetail?project="+project.getId();
	}
	
	@PostMapping("/addProjectInstance")
	public String addProjectInstance(@ModelAttribute("project") Project project)
	{
		projectService.updateProject(project);
		return "redirect:/projectdetail?project="+project.getId();
	}
	
	//---Application----
	@GetMapping("/deleteProjectApplication")
	public String deleteProjectApplication(@ModelAttribute("app") Application application,@ModelAttribute("project") Project project)
	{
		project.removeApplication(application);
		projectService.updateProject(project);
		return "redirect:/projectdetail?project="+project.getId();
	}
	
	@PostMapping("/addProjectApplication")
	public String addProjectApplication(@ModelAttribute("project") Project project)
	{
		projectService.updateProject(project);
		return "redirect:/projectdetail?project="+project.getId();
	}
}
