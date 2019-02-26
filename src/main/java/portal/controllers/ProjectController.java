package portal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import portal.entity.Project;

import portal.service.ProjectService;

@Controller
public class ProjectController {
	
	@Autowired
	private ProjectService projectService;
	
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
	
	@GetMapping("/projectdetail")
	public String projectdetail(@ModelAttribute("project") Project project,ModelMap model)
	{
		model.addAttribute("project", project);
		
		return "projectdetail";
	}
	

}
