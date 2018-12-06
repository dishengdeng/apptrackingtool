package portal.service;

import java.util.List;


import portal.entity.Department;
import portal.models.DepartmentModel;




public interface DepartmentService {
	public Department addDepartment(Department department);
    void delete(Department department);
    List<Department> getAll();
    List<DepartmentModel> getAllDepartment();
    Department getByName(String departmentName);
    Department getById(Long id);
    Department updateDepartment(Department department);	
}
