package portal.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import portal.entity.Department;
import portal.models.DepartmentModel;
import portal.repository.DepartmentRepository;
import portal.service.DepartmentService;
@Service
public class DepartmentServiceImpl implements DepartmentService{

	@Autowired
	DepartmentRepository departmentRepository;
	
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

		return departmentRepository.saveAndFlush(department);
	}

}
