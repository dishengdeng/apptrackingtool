package portal.service;

import java.util.List;
import java.util.Set;

import portal.entity.User;

public interface UserService {
	public void saveUser(User user);
	public User findByName(String username);
	public User updateUser(User user);
	public List<User> getAll();
	public void deleteUser(User user);
	public User findById(Long id);
	public Set<String> getAllloggedUsers();
	public boolean logoutUserSession(String username);

}
