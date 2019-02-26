package portal.service;

import java.util.List;

import portal.entity.Project;

public interface ProjectService {
	
	public Project addProject(Project project);
	public Project updateProject(Project project);
	public void deleteProject(Project project);
	public Project findbyId(Long id);
	public List<Project> getAll();

}
