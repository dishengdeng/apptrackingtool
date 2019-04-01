package portal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import portal.entity.Chat;

import portal.entity.Conversation;
import portal.models.ChatModel;
import portal.service.ChatService;

import portal.service.ConversationService;
import portal.service.SecurityService;

@Controller
public class ChatController {
	@Autowired
	private ConversationService convService;
	
	@Autowired
	private ChatService chatService;
	
	
	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private SimpMessageSendingOperations messagingTemplate;
	
    @GetMapping("/chat")
    public String departmenttable(ModelMap model,@RequestParam(name="chatuser", required=true) String chatuser) {

    		Conversation conv=convService.saveConversation(new Conversation());
    	
    		conv.addChatUser(chatuser);
    		conv.addChatUser(securityService.findLoggedInUsername());
    		
    		convService.updateConversation(conv);
        	
        	model.addAttribute("conversation", conv);
        	model.addAttribute("toUser", chatuser);
        	model.addAttribute("sendUser", securityService.findLoggedInUsername());



        	return "chat";
    }
    
    @MessageMapping("/chat")
    public void receiveAndsend(ChatModel chatModel)
    {
    	Chat chat = chatService.saveChat(new Chat());


    	Conversation conv= convService.findbyid(Long.valueOf(chatModel.getConv()));
    	
    	chat.setUsername(chatModel.getSendby());
    	chat.setContent(chatModel.getMessage());
    	chat.setConversation(conv);
    	
    	Chat newchat=chatService.updateChat(chat);
    	
    	conv.getChatUsers().stream().forEach(user->{
    		messagingTemplate.convertAndSendToUser(user, "/subject/chat", newchat);
    	});
    }
}
