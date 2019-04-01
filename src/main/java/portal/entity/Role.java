package portal.entity;


import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import portal.jsonview.Views;

@Entity
@Table(name = "role")
public class Role {
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE) 
	@Column(name = "id",nullable = false,unique=true)
	@JsonView(Views.Public.class)
	private Long id;

    @Column(name = "name",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String name;
    
    @Column(name = "description",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String description;    

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users.addAll(users);
		users.forEach(user->{
			user.addRole(this);
		});
	}
	
	public void addUser(User user)
	{
		this.users.add(user);
	}
	
	public void removeUser(User user)
	{
		this.users.removeIf(obj->obj.equals(user));
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	} 
	
	public void removeAllDependence()
	{
		this.users.forEach(user->{
			user.removeRole(this);
		});
		this.users=null;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(this==obj) return true;
		
		if(obj==null) return false;
		
		if(this.getClass()!=obj.getClass()) return false;
		
		Role other = (Role) obj;
		
		if(this.getId()!=other.getId()) return false;
		
		return true;
	}

}
