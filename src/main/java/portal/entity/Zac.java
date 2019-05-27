package portal.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import portal.jsonview.Views;

@Entity
@Table(name = "Zac")
public class Zac {
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE) 
	@Column(name = "id",nullable = false,unique=true)
	@JsonView(Views.Public.class)
	private Long id;

    @Column(name = "rate",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String rate;
    
    @Column(name = "name")
    @JsonView(Views.Public.class)
	private String name;	
    
    @Column(name = "description")
    @JsonView(Views.Public.class)
	private String description;
    
    @OneToMany(
            mappedBy = "zac", 
            cascade = CascadeType.ALL, 
            orphanRemoval = true,
            fetch=FetchType.EAGER
        )
    private Set<Application> applications = new HashSet<Application>();
    
    @OneToMany(
            mappedBy = "zac", 
            cascade = CascadeType.ALL,
            orphanRemoval = true
        )
    private Set<Zacmap> zacmaps = new HashSet<Zacmap>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void addApplication(Application application)
	{
		this.applications.add(application);
	}
	
	public void removeApplication(Application application)
	{
		this.applications.removeIf(obj->obj.equals(application));
	}
	
	public Set<Application> getApplications() {
		return applications;
	}

	public void setApplications(Set<Application> applications) {
		this.applications.addAll(applications);
		applications.forEach(obj->{
			obj.setZac(this);
		});
	}
	public void addZacmap(Zacmap zacmap)
	{
		this.zacmaps.add(zacmap);
	}
	
	public void removeZacmap(Zacmap zacmap)
	{
		this.zacmaps.remove(zacmap);
	}
	
	public Set<Zacmap> getZacmaps() {
		return zacmaps;
	}

	public void setZacmaps(Set<Zacmap> zacmaps) {
		this.zacmaps.addAll(zacmaps);
		zacmaps.forEach(obj->{
			obj.setZac(this);
		});
	}
	
	public Set<Application> Applicationlist()
	{
		List<Application> apps=new ArrayList<Application>();
		this.zacmaps.forEach(obj->{			
			apps.add(obj.getApplication());
		});
		return new HashSet<Application>(apps);
	}
	
	public List<Zacmap> getbyApplication(Application application)
	{
		return this.zacmaps.stream().filter(obj->obj.getApplication().equals(application)).collect(Collectors.toList());
		
	}
	public void removeAllDependence()
	{
		this.applications.forEach(obj->{
			obj.setZac(null);
		});
		this.applications=null;
		
		this.zacmaps.forEach(obj->{
			obj.setZac(null);
		});
		this.zacmaps=null;
	}
    

}
