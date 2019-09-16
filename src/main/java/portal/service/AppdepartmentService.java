package portal.service;

import java.util.List;

import org.json.JSONObject;

import portal.entity.Appdepartment;


public interface AppdepartmentService {
	public Appdepartment save(Appdepartment appdepartment);
	public Appdepartment update(Appdepartment appdepartment);
	public void delete(Appdepartment appdepartment);
	public List<Appdepartment> getAll();
	public Appdepartment saveAppdepartment(JSONObject appdepartment);
	public Appdepartment findone(Long id);
}
