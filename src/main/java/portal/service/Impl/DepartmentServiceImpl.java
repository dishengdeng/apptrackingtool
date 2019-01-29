package portal.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import portal.entity.AppInstance;
import portal.entity.Department;
import portal.entity.File;
import portal.entity.Stakeholder;
import portal.models.DepartmentModel;
import portal.repository.DepartmentRepository;
import portal.service.DepartmentService;
import portal.service.FileService;
@Service
public class DepartmentServiceImpl implements DepartmentService{

	@Autowired
	DepartmentRepository departmentRepository;
	
	@Autowired
	private FileService fileService;
	
	@Override
	public Department addDepartment(Department department) {

		return departmentRepository.save(department);
	}

	@Override
	public void delete(Department department) {
		departmentRepository.delete(department);
		
	}

	@Override
	public List<Department> getAll() {
	
		return departmentRepository.findAll();
	}

	@Override
	public List<DepartmentModel> getAllDepartment() {
		List<DepartmentModel> departmentModels = new ArrayList<DepartmentModel>();
		List<Department> departments=departmentRepository.findAll();
		for(Department department:departments)
		{
			DepartmentModel departmentModel= new DepartmentModel();
			departmentModel.setId(department.getId());
			departmentModel.setDepartmentName(department.getDepartmentName());
			departmentModel.setDescription(department.getDescription());
			departmentModel.setGoal(department.getGoal());
			departmentModel.setPainpoint(department.getPainpoint());
			departmentModel.setPurpose(department.getPurpose());
			departmentModel.setRoadMap(department.getRoadMap());
			departmentModel.setStragicplan(department.getStragicplan());
			departmentModels.add(departmentModel);
		}
		return departmentModels;
	}

	@Override
	public Department getByName(String departmentName) {

		return departmentRepository.findByName(departmentName);
	}

	@Override
	public Department getById(Long id) {

		return departmentRepository.findOne(id);
	}

	@Override
	public Department updateDepartment(Department department) {
		department.setFiles(departmentRepository.findOne(department.getId()).getFiles());
		return departmentRepository.saveAndFlush(department);
	}

	@Override
	public void updateAppIstanceDepartment(Set<AppInstance> appInstances, Department department) {
		departmentRepository.removeAllDepartment(department);
		if(appInstances.size()>0)
		{
			for(AppInstance appInstance:appInstances)
			{
				departmentRepository.updateAppIstanceDepartment(appInstance.getId(), department);
			}
		}

		
	}

	@Override
	public void updateStakeholderDepartment(Set<Stakeholder> stakeholders, Department department) {
		
		departmentRepository.removeAllStakeholderDepartment(department);	
		if(stakeholders.size()>0)
		{
			for(Stakeholder stakeholder:stakeholders)
			{
				departmentRepository.updateStakeholderDepartment(stakeholder.getId(), department);
			}
		}

		
	}

	@Override
	public void removFiles(String upload_foler, Department department) {
		if(department.getFiles().size()>0)
		{
			for(File file:department.getFiles())
			{
				fileService.removeFile(upload_foler,department.getId().toString(), file);
			}			
		}

		
		
	}

}
