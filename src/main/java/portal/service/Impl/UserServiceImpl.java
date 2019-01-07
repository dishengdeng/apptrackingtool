package portal.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
    
	@Override
	public void saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
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

}
