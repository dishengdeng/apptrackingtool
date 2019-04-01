package portal.service;

import portal.entity.Chat;

public interface ChatService {
	public Chat saveChat(Chat chat);
	public void removeChat(Chat chat);
	public Chat updateChat(Chat chat);
}
