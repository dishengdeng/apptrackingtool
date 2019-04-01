package portal.service;

import portal.entity.Conversation;

public interface ConversationService {

	public Conversation saveConversation(Conversation conversation);
	public void removeConversation(Conversation conversation);
	public void updateConversation(Conversation conversation);
	public Conversation findbyid(Long id);
}
