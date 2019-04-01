package portal.service;

import portal.entity.Chatuser;

public interface ChatuserService {
	public Chatuser saveChatuser(Chatuser chatuser);
	public void removeChatuser(Chatuser chatuser);
	public Chatuser updateChatuser(Chatuser chatuser);
	public Chatuser findbyname(String username);
}
