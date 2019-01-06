package portal.service;

import java.util.List;

import portal.entity.Role;




public interface RoleService {
	public Role addRole(Role role);
    void delete(Role role);
    List<Role> getAll();
    Role getByName(String roleName);
    Role getById(Long id);
    Role updateRole(Role role);	
}
