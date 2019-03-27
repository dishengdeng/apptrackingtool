package portal.service.Impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import portal.entity.Role;
import portal.entity.User;
import portal.repository.UserRepository;
import portal.service.EmailService;
import portal.utility.Status;
@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private HttpServletRequest request;
	
	@Autowired
	private EmailService emailService;
	
	private final String user_prefix="ROLE_";
	
	private final String to="disheng.deng@ahs.ca";
	
	private final String from="CapM.Systems@albertahealthservices.ca";
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user=userRepository.findByName(username);
		
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		if(user!=null && user.getStatus().equals(Status.Active.name()))
		{
			if(user.getRoles().size()>0)
			{
				List<Role> roles=user.getRoles();
		        for (Role role : roles)
		        {
		        	if(isRejectAPILogin(request.getRequestURI(),role)) throw new org.springframework.security.core.userdetails.UsernameNotFoundException("User Not Found!");
		            grantedAuthorities.add(new SimpleGrantedAuthority(user_prefix+role.getName()));
		        }
				
			}
			else
			{
				grantedAuthorities.add(new SimpleGrantedAuthority(user_prefix+portal.utility.Role.GENERAL.name()));
			}
			
			sendEmail(emailService,user);
			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
		}
		
		throw new org.springframework.security.core.userdetails.UsernameNotFoundException("User Not Found!");
	

	}
	
	private boolean isRejectAPILogin(String URL,Role role)
	{
		if(URL.equals("/login") && role.getName().equals(portal.utility.Role.INTEGRATION.name()))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private void sendEmail(EmailService emailService, User user)
	{
		user.getRoles().forEach(role->{
			if(!role.getName().equals(portal.utility.Role.INTEGRATION.name()))
			{
				emailService.sendEmail(to, user.getUsername()+" Loggin", user.getUsername()+ " has logged in at " + (new Date()).toString(),from);
			}
		});
	}
	

}
