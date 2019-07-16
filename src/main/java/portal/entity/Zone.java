package portal.entity;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

import com.fasterxml.jackson.annotation.JsonView;

import portal.jsonview.Views;

@Entity
@Table(name = "Zone")
public class Zone {
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE) 
	@Column(name = "id",nullable = false,unique=true)
	@JsonView(Views.Public.class)
	private Long id;

    @Column(name = "zoneName",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String zoneName;
	
    @Column(name = "description",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String description;
    
    @Column(name = "note",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String note;
    
    @OneToMany(
            mappedBy = "zone", 
            cascade = CascadeType.ALL, 
            orphanRemoval = true 
        )
    private Set<Site> sites = new HashSet<Site>();
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "instancezone",
        joinColumns = @JoinColumn(name = "zone_id"),
        inverseJoinColumns = @JoinColumn(name = "appInstance_id")
    )
    private Set<AppInstance> appInstances = new HashSet<AppInstance>();    

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "appzone",
        joinColumns = @JoinColumn(name = "zone_id"),
        inverseJoinColumns = @JoinColumn(name = "application_id")
    )
    private Set<Application> applications = new HashSet<Application>();
    
	@ManyToMany(mappedBy = "zones")
    private Set<Report> reports = new HashSet<Report>();
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Set<Site> getSites() {
		return sites;
	}

	public void setSites(Set<Site> sites) {
		this.sites.addAll(sites);
		sites.forEach(site->{
			site.setZone(this);
		});
	}
	
	public void removeSite(Site site)
	{
		this.sites.remove(site);
		site.setZone(null);
	}	

	
	public Set<AppInstance> getAppInstances() {
		return appInstances;
	}

	public void setAppInstances(Set<AppInstance> appInstances) {
		this.appInstances.addAll(appInstances);
		appInstances.forEach(instance->{
			instance.addZone(this);
		});
	}
	
	public void addappInstance(AppInstance instance)
	{
		this.appInstances.add(instance);
	}
	
	public void removeAppInstance(AppInstance instance)
	{
		this.appInstances.remove(instance);

	}
	


	public Set<Application> getApplications() {
		return applications;
	}

	public void setApplications(Set<Application> applications) {
		this.applications.addAll(applications);
		applications.forEach(obj->{
			obj.addZone(this);
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
			obj.addZone(this);
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
			obj.removeZone(this);
		});
		this.applications=null;
		
		this.appInstances.forEach(obj->{
			obj.removeZone(this);
		});
		this.appInstances=null;
		
		this.sites.forEach(site->{
			site.setZone(null);
		});
		this.sites=null;
		
		this.reports.forEach(obj->{
			obj.removeZone(this);
		});
		this.reports=null;
	}

	@Override
	public boolean equals(Object obj)
	{
		if(this==obj) return true;
		
		if(obj==null) return false;
		
		if(this.getClass()!=obj.getClass()) return false;
		
		Zone other = (Zone) obj;
		
		if(this.getId()!=other.getId()) return false;
		
		return true;
	}
	
}
