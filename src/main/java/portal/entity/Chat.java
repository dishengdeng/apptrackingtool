package portal.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import portal.jsonview.Views;

@Entity
@Table(name = "Chat")
public class Chat {
	
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE) 
	@Column(name = "id",nullable = false,unique=true)
	@JsonView(Views.Public.class)
	private Long id;
	
    @Column(name = "datechat", length = 64,columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String datechat;
    
    @Column(name = "content", length = 64,columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String content;
 
    @Column(name = "username", length = 64,columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String username;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn(name = "conversation_id",referencedColumnName="id")
    private Conversation conversation;
    

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDatechat() {
		return datechat;
	}

	public void setDatechat(String datechat) {
		this.datechat = datechat;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Conversation getConversation() {
		return conversation;
	}

	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}




}
