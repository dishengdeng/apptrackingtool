package portal.service.Impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import portal.entity.Role;
import portal.entity.User;
import portal.repository.UserRepository;
@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	@Autowired
    private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user=userRepository.findByName(username);
		Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
		if(user!=null)
		{
			if(user.getRoles().size()>0)
			{
		        for (Role role : user.getRoles())
		        {
		            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
		        }
				
			}
			else
			{
				grantedAuthorities.add(new SimpleGrantedAuthority(portal.utility.Role.GENERAL.name()));
			}
			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
		}
		
		throw new org.springframework.security.core.userdetails.UsernameNotFoundException("User Not Found!");
	

	}
	

}
