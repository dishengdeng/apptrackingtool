package portal.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import portal.entity.Role;
import portal.repository.RoleRepository;
import portal.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public Role addRole(Role role) {
		
		return roleRepository.save(role);
	}

	@Override
	public void delete(Role role) {
		
		roleRepository.delete(role);
		
	}

	@Override
	public List<Role> getAll() {
		
		return roleRepository.findAll();
	}

	@Override
	public Role getByName(String roleName) {
	
		return roleRepository.findByName(roleName);
	}

	@Override
	public Role getById(Long id) {

		return roleRepository.findOne(id);
	}

	@Override
	public Role updateRole(Role role) {

		return roleRepository.saveAndFlush(role);
	}

}
