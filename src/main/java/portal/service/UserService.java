package portal.service;

import portal.entity.User;

public interface UserService {
	public void saveUser(User user);
	public User findByName(String username);

}
