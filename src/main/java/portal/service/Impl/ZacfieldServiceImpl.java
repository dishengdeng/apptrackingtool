package portal.service.Impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import portal.entity.Department;
import portal.entity.Zacfield;
import portal.repository.ZacfieldRepository;
import portal.service.ZacfieldService;
@Service
public class ZacfieldServiceImpl implements ZacfieldService{

	@Autowired
	private ZacfieldRepository zacfieldRepository;
	
	@Override
	public Zacfield save(Zacfield zacfield) {

		return zacfieldRepository.saveAndFlush(zacfield);
	}

	@Override
	public Zacfield update(Zacfield zacfield) {

		return zacfieldRepository.saveAndFlush(zacfield);
	}

	@Override
	public void delete(Zacfield zacfield) {

		zacfieldRepository.delete(zacfield);
	}

	@Override
	public List<Zacfield> getAll() {

		return zacfieldRepository.findAll();
	}

	@Override
	public Zacfield findone(Long id) {

		return zacfieldRepository.findOne(id);
	}

	@Override
	public List<Zacfield> findbyDepartment(Department department) {
		
		return zacfieldRepository.findbyDepartment(department);
	}

}
