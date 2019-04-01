package portal.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import portal.entity.Conversation;
import portal.repository.ConversationRepository;
import portal.service.ConversationService;

@Service
public class ConversationServiceImpl implements ConversationService{
	
	@Autowired
	private ConversationRepository convRepository;

	@Override
	public Conversation saveConversation(Conversation conversation) {
	
		return convRepository.save(conversation);
	}

	@Override
	public void removeConversation(Conversation conversation) {
	
		convRepository.delete(conversation);
	}

	@Override
	public void updateConversation(Conversation conversation) {
		
		convRepository.saveAndFlush(conversation);
	}

	@Override
	public Conversation findbyid(Long id) {
		
		return convRepository.findOne(id);
	}

}
