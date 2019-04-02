package portal.entity;



import java.util.HashSet;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;



import com.fasterxml.jackson.annotation.JsonView;

import portal.jsonview.Views;

@Entity
@Table(name = "Conversation")
public class Conversation {
	
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE) 
	@Column(name = "id",nullable = false,unique=true)
	@JsonView(Views.Public.class)
	private Long id;
	
    @Column(name = "datecov", length = 64,columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String datecov;

    
    @OneToMany(
            mappedBy = "conversation", 
            cascade = CascadeType.ALL, 
            orphanRemoval = true,
            fetch=FetchType.EAGER
        )
    private Set<Chat> chats = new HashSet<Chat>();
    
	@ManyToMany(mappedBy = "conversations",fetch=FetchType.EAGER)
    private Set<Chatuser> chatusers = new HashSet<Chatuser>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDatecov() {
		return datecov;
	}

	public void setDatecov(String datecov) {
		this.datecov = datecov;
	}
	


	public void addChat(Chat chat)
	{
		this.chats.add(chat);
	}
	
	public void removeChat(Chat chat)
	{
		this.chats.remove(chat);
	}
	
	public Set<Chat> getChats() {
		return chats;
	}

	public void setChats(Set<Chat> chats) {
		this.chats.addAll(chats);
		chats.forEach(chat->{
			chat.setConversation(this);
		});
	}
	
	public void addChatuser(Chatuser chatuser)
	{
		this.chatusers.add(chatuser);
	}
	
	public void removeChatuser(Chatuser chatuser)
	{
		this.chatusers.remove(chatuser);
	}
	
	public Set<Chatuser> getChatusers() {
		return chatusers;
	}

	public void setChatusers(Set<Chatuser> chatusers) {
		this.chatusers.addAll(chatusers);
		chatusers.forEach(user->{
			user.addConversation(this);
		});
	}




}
