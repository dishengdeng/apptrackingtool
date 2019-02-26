package portal.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonView;

import portal.jsonview.Views;
import portal.utility.Convertor;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "Project")
public class Project {
	
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE) 
	@Column(name = "id",nullable = false,unique=true)
	@JsonView(Views.Public.class)
	private Long id;
	
    @Column(name = "projectname",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String projectname;
    
    @Column(name = "projectcolloquialname",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String projectcolloquialname;
    
    @Column(name = "description",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String description;
    
    @Column(name = "startdate")
    @Temporal(TemporalType.DATE)
    @JsonView(Views.Public.class)
	private Date startdate; 
    
    @Column(name = "enddate")
    @Temporal(TemporalType.DATE)
    @JsonView(Views.Public.class)
	private Date enddate;
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "instanceproject",
        joinColumns = @JoinColumn(name = "project_id"),
        inverseJoinColumns = @JoinColumn(name = "appInstance_id")
    )
    private Set<AppInstance> appInstances = new HashSet<AppInstance>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProjectname() {
		return projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}

	public String getProjectcolloquialname() {
		return projectcolloquialname;
	}

	public void setProjectcolloquialname(String projectcolloquialname) {
		this.projectcolloquialname = projectcolloquialname;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = Convertor.JavaDate(startdate);
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = Convertor.JavaDate(enddate);
	}

	public Set<AppInstance> getAppInstances() {
		return appInstances;
	}

	public void setAppInstances(Set<AppInstance> appInstances) {
		this.appInstances.addAll(appInstances);
	}
	
	public void removeAppInstance(AppInstance appInstance)
	{
		this.appInstances.removeIf(obj->obj.equals(appInstance));
		appInstance.getProjects().remove(this);
	}
	
	public void addAppInstance(AppInstance appInstance)
	{
		this.appInstances.add(appInstance);
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(this==obj) return true;
		
		if(obj==null) return false;
		
		if(this.getClass()!=obj.getClass()) return false;
		
		Project other = (Project) obj;
		
		if(this.getId()!=other.getId()) return false;
		
		return true;
	}

}
