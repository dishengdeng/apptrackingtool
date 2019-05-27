package portal.service;

import java.util.List;
import java.util.Set;

import portal.entity.AppInstance;
import portal.entity.Department;
import portal.entity.Stakeholder;
import portal.models.DepartmentModel;




public interface DepartmentService {
	public Department addDepartment(Department department);
    void delete(Department department);
    List<Department> getAll();
    List<DepartmentModel> getAllDepartment();
    Department getByName(String departmentName);
    Department getById(Long id);
    Department updateDepartment(Department department);
    
    void updateAppIstanceDepartment(Set<AppInstance> appInstances,Department department);

    void updateStakeholderDepartment(Set<Stakeholder> stakeholders,Department department);
    void removFiles(String upload_foler,Department department);
}
