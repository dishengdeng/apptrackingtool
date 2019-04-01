package portal.service.Impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import portal.entity.Chat;
import portal.repository.ChatRepository;
import portal.service.ChatService;
@Service
public class ChatServiceImpl implements ChatService{

	@Autowired
	private ChatRepository chatRepository;

	@Override
	public Chat saveChat(Chat chat) {

		return chatRepository.save(chat);
	}

	@Override
	public void removeChat(Chat chat) {

		chatRepository.delete(chat);
	}

	@Override
	public Chat updateChat(Chat chat) {

		return chatRepository.saveAndFlush(chat);
	}
}
