package portal.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import portal.entity.Project;
import portal.repository.ProjectRepository;
import portal.service.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService{
	
	@Autowired
	private ProjectRepository projectRepository;

	@Override
	public Project addProject(Project project) {

		return projectRepository.save(project);
	}

	@Override
	public Project updateProject(Project project) {

		return projectRepository.saveAndFlush(project);
	}

	@Override
	public void deleteProject(Project project) {
		
		projectRepository.delete(project);
	}

	@Override
	public Project findbyId(Long id) {

		return projectRepository.findOne(id);
	}

	@Override
	public List<Project> getAll() {

		return projectRepository.findAll();
	}

}
