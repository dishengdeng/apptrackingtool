package portal.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.OneToMany;
import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonView;

import portal.jsonview.Views;

@Entity
@Table(name = "Zone")
public class Zone implements Comparable<Zone>{
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


	public void removeAllDependence()
	{
		
		this.sites.forEach(site->{
			site.setZone(null);
		});
		this.sites=null;
		

		
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

	@Override
	public int compareTo(Zone obj) {
		return this.zoneName.compareTo(obj.getZoneName());
	}
	
}
