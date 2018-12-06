package portal.entity;



import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import portal.jsonview.Views;

@Entity
@Table(name = "AppInstance")
public class AppInstance {
	
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE) 
	@Column(name = "id",nullable = false,unique=true)
	@JsonView(Views.Public.class)
	private Long id;

    @Column(name = "appInstanceName",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String appInstanceName;
    

    @Column(name = "status",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String status;
    
    @Column(name = "description", length = 64,columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String description;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "deparment_id",referencedColumnName="id")
    private Department department;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "support_id",referencedColumnName="id")
    private Support support;
    
	@ManyToMany(mappedBy = "appInstances")
    private List<Site> sites = new ArrayList<Site>();
	
    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "application_id",referencedColumnName="id")
    private Application application;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAppInstanceName() {
		return appInstanceName;
	}

	public void setAppInstanceName(String appInstanceName) {
		this.appInstanceName = appInstanceName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Support getSupport() {
		return support;
	}

	public void setSupport(Support support) {
		this.support = support;
	}

	public List<Site> getSites() {
		return sites;
	}

	public void setSites(List<Site> sites) {
		this.sites = sites;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	@Override
	public boolean equals(Object obj)
	{
		if(this==obj) return true;
		
		if(obj==null) return false;
		
		if(this.getClass()!=obj.getClass()) return false;
		
		AppInstance other = (AppInstance) obj;
		
		if(this.getId()!=other.getId()) return false;
		
		return true;
	}
	
}
