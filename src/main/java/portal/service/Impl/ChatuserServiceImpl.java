package portal.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import portal.entity.Chatuser;
import portal.repository.ChatuserRepository;
import portal.service.ChatuserService;

@Service
public class ChatuserServiceImpl implements ChatuserService{
	@Autowired
	private ChatuserRepository chatuserRepository;

	@Override
	public Chatuser saveChatuser(Chatuser chatuser) {

		return chatuserRepository.save(chatuser);
	}

	@Override
	public void removeChatuser(Chatuser chatuser) {

		chatuserRepository.delete(chatuser);
	}

	@Override
	public Chatuser updateChatuser(Chatuser chatuser) {
	
		return chatuserRepository.saveAndFlush(chatuser);
	}

	@Override
	public Chatuser findbyname(String username) {
		
		return chatuserRepository.findByName(username);
	}
}
