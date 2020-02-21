package portal.service;

import java.util.List;

import portal.entity.Department;
import portal.entity.Zacfield;

public interface ZacfieldService {
	public Zacfield save(Zacfield zacfield);
	public Zacfield update(Zacfield zacfield);
	public void delete(Zacfield zacfield);
	public List<Zacfield> getAll();
	public Zacfield findone(Long id);
	public List<Zacfield> findbyDepartment(Department department);
}
