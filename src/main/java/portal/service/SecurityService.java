package portal.service;

import portal.entity.User;
import portal.utility.Role;

public interface SecurityService {

	public String findLoggedInUsername();
	public void autologin(String username, String password);
	public String apilogin(String username, String password);
	public Boolean hasRole(Role role);
	public User getCurrentUserProfile();
}
