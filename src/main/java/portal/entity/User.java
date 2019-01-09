package portal.entity;

import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonView;

import portal.jsonview.Views;
import portal.utility.Action;

@Entity
@Table(name = "user")
public class User {
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE) 
	@Column(name = "id",nullable = false,unique=true)
	@JsonView(Views.Public.class)
	private Long id;

    @Column(name = "username",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String username;
	
    @Column(name = "password",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String password;

    @Column(name = "encodedpassword",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String encodedpassword;
    
    @Column(name = "passwordchg",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String passwordchg;    
    
    @Column(name = "passwordconfirm",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String passwordconfirm;
    
    @Column(name = "status",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String status;
    
    @Column(name = "action",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String action=Action.CREATE.name();
    
    @Column(name = "namebypass")
    @JsonView(Views.Public.class)
	private Boolean namebypass=false;
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", 
    joinColumns = @JoinColumn(name = "user_id"), 
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles=new ArrayList<Role>();

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordconfirm() {
		return passwordconfirm;
	}

	public void setPasswordconfirm(String passwordconfirm) {
		this.passwordconfirm = passwordconfirm;
	}

	public List<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(List<Role> roles) {
		
		this.roles = roles;
	}

	public String getPasswordchg() {
		return passwordchg;
	}

	public void setPasswordchg(String passwordchg) {
		this.passwordchg = passwordchg;
	}
	
	public String getRoleStrWithComma()
	{
		List<String> roleNames = new ArrayList<>();
		for(Role role:this.roles)
		{
			roleNames.add(role.getName());
		}
		return roleNames.stream().collect(Collectors.joining(","));
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Boolean getNamebypass() {
		return namebypass;
	}

	public void setNamebypass(Boolean namebypass) {
		this.namebypass = namebypass;
	}

	public String getEncodedpassword() {
		return encodedpassword;
	}

	public void setEncodedpassword(String encodedpassword) {
		this.encodedpassword = encodedpassword;
	}
    
    
}
