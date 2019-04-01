package portal.entity;


import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

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
    
    
    @Column(name = "chatusers", length = 64,columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String chatusers;   

    
    @OneToMany(
            mappedBy = "conversation", 
            cascade = CascadeType.ALL, 
            orphanRemoval = true,
            fetch=FetchType.EAGER
        )
    private Set<Chat> chats = new HashSet<Chat>();

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

	public String getChatusers() {
		return chatusers;
	}

	public void setChatusers(String chatusers) {
		this.chatusers = chatusers;
	}
	
	public void addChatUser(String chatuser)
	{
		this.chatusers = StringUtils.join(new String[]{this.chatusers,chatuser}, "|");
	}
	
	public List<String> getChatUsers()
	{

		return Arrays.asList(StringUtils.split(this.chatusers, "|"));
	}


}
