package portal.entity;


import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import portal.jsonview.Views;

@Entity
@Table(name = "Chatuser")
public class Chatuser {
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE) 
	@Column(name = "id",nullable = false,unique=true)
	@JsonView(Views.Public.class)
	private Long id;
	
    @Column(name = "username", length = 64,columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String username;

//    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
//    @JoinTable(name = "user_conv", 
//    joinColumns = @JoinColumn(name = "chatuser_id"), 
//    inverseJoinColumns = @JoinColumn(name = "conv_id"))
//    private Set<Conversation> conversations=new HashSet<Conversation>();
//    
//    @OneToMany(
//            mappedBy = "chatuser", 
//            cascade = CascadeType.ALL, 
//            orphanRemoval = true,
//            fetch=FetchType.EAGER
//        )
//    private Set<Chat> chats = new HashSet<Chat>();
    
    
    public Chatuser(String username)
    {
    	this.username=username;
    }
    
    public Chatuser()
    {
    	
    }
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

//	public void addConversation(Conversation conversation)
//	{
//		this.conversations.add(conversation);
//	}
//	
//	public void removeConversation(Conversation conversation)
//	{
//		this.conversations.remove(conversation);
//	}
//
//	public Set<Conversation> getConversations() {
//		return conversations;
//	}
//
//	public void setConversations(Set<Conversation> conversations) {
//		this.conversations.addAll(conversations);
//		conversations.forEach(conversation->{
//		conversation.addChatuser(this);
//	});
//	}
//
//	public void addChat(Chat chat)
//	{
//		this.chats.add(chat);
//	}
//	
//	public void removeChat(Chat chat)
//	{
//		this.chats.remove(chat);
//	}
//	public Set<Chat> getChats() {
//		return chats;
//	}
//
//	public void setChats(Set<Chat> chats) {
//		this.chats.addAll(chats);
//		chats.forEach(chat->{
//			chat.setChatuser(this);
//		});
//	}

    
    
}
