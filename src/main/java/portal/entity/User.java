package portal.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    private Set<Role> roles=new HashSet<Role>();

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

	public Set<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles.retainAll(roles);
		this.roles.addAll(roles);
		roles.forEach(role->{
			role.addUser(this);
		});
	}
	
	public void addRole(Role role)
	{
		this.roles.add(role);
	}
	
	public void removeRole(Role role)
	{
		this.roles.remove(role);
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

	
	public void removeAlldependence()
	{
		this.roles.forEach(role->{
			role.removeUser(this);
		});
		this.roles=null;
	}
	@Override
	public boolean equals(Object obj)
	{
		if(this==obj) return true;
		
		if(obj==null) return false;
		
		if(this.getClass()!=obj.getClass()) return false;
		
		User other = (User) obj;
		
		if(this.getId()!=other.getId()) return false;
		
		return true;
	}
    
    
}
