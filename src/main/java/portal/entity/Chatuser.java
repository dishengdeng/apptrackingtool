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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

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


    @ManyToMany(cascade = CascadeType.ALL,fetch=FetchType.EAGER)
    @JoinTable(name = "chatuser_conv",
        joinColumns = @JoinColumn(name = "chatuser_id"),
        inverseJoinColumns = @JoinColumn(name = "conv_id")
    )
    private Set<Conversation> conversations = new HashSet<Conversation>();
    
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

	public void addConversation(Conversation conversation)
	{
		this.conversations.add(conversation);
	}
	
	public void removeConversation(Conversation conversation)
	{
		this.conversations.remove(conversation);
	}
	
	public Set<Conversation> getConversations() {
		return conversations;
	}

	public void setConversations(Set<Conversation> conversations) {
		this.conversations.addAll(conversations);
		conversations.forEach(conv->{
			conv.addChatuser(this);
		});
	}




    
    
}
