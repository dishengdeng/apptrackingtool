package portal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import portal.entity.Chat;
import portal.entity.Chatuser;
import portal.entity.Conversation;
import portal.models.ChatModel;
import portal.service.ChatService;
import portal.service.ChatuserService;
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
	
	@Autowired
	private ChatuserService chatuserService;
	
    @GetMapping("/chat")
    public String userconversation(ModelMap model,@RequestParam(name="chatuser", required=true) String chatuser,@RequestParam(name="conversation", required=false) String converstion) {

    		if(StringUtils.isEmpty(converstion))
    		{
        		Conversation conv=convService.saveConversation(new Conversation());
            	
        		Chatuser toUser=ObjectUtils.isEmpty(chatuserService.findbyname(chatuser))? new Chatuser(chatuser):chatuserService.findbyname(chatuser);
        		toUser.addConversation(conv);
        		
        		Chatuser sentUser=ObjectUtils.isEmpty(chatuserService.findbyname(securityService.findLoggedInUsername()))? new Chatuser(securityService.findLoggedInUsername()):chatuserService.findbyname(securityService.findLoggedInUsername());
        		sentUser.addConversation(conv);
        		
        		conv.addChatuser(toUser);
        		conv.addChatuser(sentUser);
        		
        		convService.updateConversation(conv);
            	
        		model.addAttribute("toUser", chatuser);
            	model.addAttribute("conversation", conv);
    		}
    		else
    		{
    	    	Conversation conv=convService.findbyid(Long.valueOf(converstion));
    	    	model.addAttribute("toUser", chatuser);
    	    	model.addAttribute("conversation", conv);
    		}



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
    	chat.setDatechat(chatModel.getDate());
    	
    	chatService.updateChat(chat);
    	
    	conv.getChatusers().forEach(user->{
    		messagingTemplate.convertAndSendToUser(user.getUsername(), "/subject/chat", chatModel);
    	});
    }
}
