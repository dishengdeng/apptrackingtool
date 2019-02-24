package portal.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;

import com.fasterxml.jackson.annotation.JsonView;

import portal.jsonview.Views;


@Entity
@Table(name = "Site")
public class Site {
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE) 
	@Column(name = "id",nullable = false,unique=true)
	@JsonView(Views.Public.class)
	private Long id;

    @Column(name = "siteName",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String siteName;
	
    @Column(name = "description",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String description;
    
    @Column(name = "note",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String note;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "zone_id",referencedColumnName="id")
    private Zone zone;
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "instancelocation",
        joinColumns = @JoinColumn(name = "site_id"),
        inverseJoinColumns = @JoinColumn(name = "appInstance_id")
    )
    private Set<AppInstance> appInstances = new HashSet<AppInstance>();
    
    public void addAppInstance(AppInstance appInstance)
    {
    	appInstances.add(appInstance);
    }
    
    public void removeAppInstance(AppInstance appInstance)
    {
    	appInstances.remove(appInstance);
    	appInstance.getSites().removeIf(obj->obj.equals(this));
    }
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Zone getZone() {
		return zone;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Set<AppInstance> getAppInstances() {
		return appInstances;
	}

	public void setAppInstances(Set<AppInstance> appInstances) {
		this.appInstances.addAll(appInstances);
	}

	@Override
	public boolean equals(Object obj)
	{
		if(this==obj) return true;
		
		if(obj==null) return false;
		
		if(this.getClass()!=obj.getClass()) return false;
		
		Site other = (Site) obj;
		
		if(this.getId()!=other.getId()) return false;
		
		return true;
	}
	
	
}
