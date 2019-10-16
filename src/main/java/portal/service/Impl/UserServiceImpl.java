package portal.service.Impl;


import java.util.ArrayList;

import java.util.List;


import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import portal.entity.User;
import portal.repository.UserRepository;
import portal.service.UserService;
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Autowired
    private SessionRegistry sessionRegistry;
    
	@Override
	public void saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setEncodedpassword(new String(Base64.encodeBase64(user.getPassword().getBytes())));
		userRepository.save(user);
		
	}

	@Override
	public User findByName(String username) {

		return userRepository.findByName(username);
	}

	@Override
	public User updateUser(User user) {
	
		return userRepository.saveAndFlush(user);
	}

	@Override
	public List<User> getAll() {

		return userRepository.findAll();
	}

	@Override
	public void deleteUser(User user) {
		userRepository.delete(user);
		
	}

	@Override
	public User findById(Long id) {

		return userRepository.findOne(id);
	}

	@Override
	public List<String> getAllloggedUsers() {
		List<String> loggedUsers= new ArrayList<String>();
		
		sessionRegistry.getAllPrincipals().stream().forEach(user->{

            for (SessionInformation information : sessionRegistry.getAllSessions(user, false)) {
                loggedUsers.add(((UserDetails)information.getPrincipal()).getUsername());

            }
			
		});

		return loggedUsers;
	}

	@Override
	public boolean logoutUserSession(String username) {
		boolean result=false;
        for (Object principal : sessionRegistry.getAllPrincipals()) {
                UserDetails userDetails = (UserDetails) principal;
                if (userDetails.getUsername().equals(username)) {
                    for (SessionInformation information : sessionRegistry.getAllSessions(userDetails, false)) {
                        information.expireNow();
                        result=true;
                    }
                }
            
        }
        
        return result;
	}

}
