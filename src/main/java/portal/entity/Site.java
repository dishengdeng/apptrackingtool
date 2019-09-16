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
import javax.persistence.OneToMany;
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
    

    @OneToMany(
            mappedBy = "site", 
            cascade = CascadeType.ALL, 
            orphanRemoval = true,
            fetch=FetchType.EAGER
        )
    private Set<Stakeholder> stakeholders = new HashSet<>();
    
	@ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "appdepartsite",
        joinColumns = @JoinColumn(name = "site_id"),
        inverseJoinColumns = @JoinColumn(name = "appdepartment_id")
    )
    private Set<Appdepartment> appdepartments = new HashSet<Appdepartment>();
    
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

	
	public Set<Stakeholder> getStakeholders() {
		return stakeholders;
	}

	public void setStakeholders(Set<Stakeholder> stakeholders) {
		this.stakeholders.addAll(stakeholders);
		stakeholders.forEach(obj->{
			obj.setSite(this);
		});
	}

	public void addStakeholder(Stakeholder stakeholder)
	{
		this.stakeholders.add(stakeholder);
	}
	
	public void removeStakeholder(Stakeholder stakeholder)
	{
		this.stakeholders.remove(stakeholder);
	}
	
	
	
	public Set<Appdepartment> getAppdepartments() {
		return appdepartments;
	}

	public void setAppdepartments(Set<Appdepartment> appdepartments) {
		this.appdepartments.addAll(appdepartments);
		appdepartments.forEach(obj->{
			obj.addSite(this);
		});
	}
	
	public void addAppdepartments(Appdepartment appdepartment)
	{
		this.appdepartments.add(appdepartment);
	}
	
	public void removeAppdepartments(Appdepartment appdepartment)
	{
		this.appdepartments.remove(appdepartment);
	}

	public void removeAllDependence()
	{

    	
    	if(!ObjectUtils.isEmpty(this.zone)) this.zone.removeSite(this);
    	this.setZone(null);
    	
    	this.stakeholders.forEach(obj->{
    		obj.setSite(null);
    	});
    	this.stakeholders=null;
    	
    	this.appdepartments.forEach(obj->{
    		obj.removeSite(this);
    	});
    	this.appdepartments=null;
    	

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
