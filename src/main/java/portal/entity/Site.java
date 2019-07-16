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

import org.springframework.util.ObjectUtils;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;

import com.fasterxml.jackson.annotation.JsonView;

import portal.jsonview.Views;


@Entity
@Table(name = "Site")
public class Site implements Comparable<Site>{
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
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "appsite",
        joinColumns = @JoinColumn(name = "site_id"),
        inverseJoinColumns = @JoinColumn(name = "application_id")
    )
    private Set<Application> applications = new HashSet<Application>();
    
	@ManyToMany(mappedBy = "sites")
    private Set<Report> reports = new HashSet<Report>();
    
    public void addAppInstance(AppInstance appInstance)
    {
    	appInstances.add(appInstance);
    }
    
    public void removeAppInstance(AppInstance appInstance)
    {
    	appInstances.removeIf(obj->obj.equals(appInstance));

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
		appInstances.forEach(obj->{
			obj.addSite(this);
		});
	}

	public Set<Application> getApplications() {
		return applications;
	}

	public void setApplications(Set<Application> applications) {
		this.applications.addAll(applications);
		applications.forEach(obj->{
			obj.addSite(this);
		});
	}
	
	public void addApplication(Application application)
	{
		this.applications.add(application);
	}
	
	public void removeApplication(Application application)
	{
		this.applications.removeIf(obj->obj.equals(application));
	}
	
	
	public Set<Report> getReports() {
		return reports;
	}

	public void setReports(Set<Report> reports) {
		this.reports.addAll(reports);
		reports.forEach(obj->{
			obj.addSite(this);
		});
	}
	
	public void addReport(Report report)
	{
		this.reports.add(report);
	}
	
	public void removeReport(Report report)
	{
		this.reports.remove(report);
	}
	
	public void removeAllDependence()
	{
		this.applications.forEach(obj->{
			obj.removeSite(this);
		});
		this.applications=null;
		
    	this.appInstances.forEach(instance->{
    		instance.removeSite(this);
    	});
    	this.appInstances=null;
    	
    	if(!ObjectUtils.isEmpty(this.zone)) this.zone.removeSite(this);
    	this.setZone(null);
    	
    	this.reports.forEach(obj->{
    		obj.removeSite(this);
    	});
    	this.reports=null;
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
	
	@Override
	public int compareTo(Site o) {

		return this.siteName.compareToIgnoreCase(o.getSiteName());
	}
	
	
}
