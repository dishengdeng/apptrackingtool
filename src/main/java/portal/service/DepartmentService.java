package portal.service;

import java.util.List;
import java.util.Set;

import org.json.JSONObject;

import portal.entity.AppInstance;
import portal.entity.Department;
import portal.entity.Stakeholder;
import portal.entity.Zacmap;
import portal.entity.Zone;
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
    
    public Set<Zacmap> getZacmap(Department department);
    
    public Zacmap saveZacmap(JSONObject zacmapObj);
    public Zacmap updateZacmap(JSONObject zacmapObj);
    public Set<Zone> getNewZoneofDepartment(Department department);
}
