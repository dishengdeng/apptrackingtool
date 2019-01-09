package portal.service.Impl;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import portal.entity.User;
import portal.service.SecurityService;
import portal.service.UserService;
import portal.utility.CurrentUser;
import portal.utility.Role;
@Service
public class SecurityServiceImpl implements SecurityService{
    @Autowired
    private AuthenticationManager authenticationManager;
   

    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    private UserService userService;
    
    private final String USER_PREFIX="ROLE_";
    
	@Override
	public String findLoggedInUsername() {
			return SecurityContextHolder.getContext().getAuthentication().getName();
	}

	@Override
	public void autologin(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password,userDetails.getAuthorities());

        
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);   
        

        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
            
        	SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        }
		
	}

	@Override
	public Boolean hasRole(Role role) {
		SimpleGrantedAuthority autority=new SimpleGrantedAuthority(USER_PREFIX+role.name());
		return SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(autority);
	}

	@Override
	public User getCurrentUserProfile() {
		
		return userService.findByName(CurrentUser.updatedLogginUserName);
	}
	

}
