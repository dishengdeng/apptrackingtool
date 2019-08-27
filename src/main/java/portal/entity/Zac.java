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
            orphanRemoval = true
        )
    private Set<Zaclist> zaclists = new HashSet<Zaclist>();

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


	public void addZaclist(Zaclist Zaclist)
	{
		this.zaclists.add(Zaclist);
	}
	
	public void removeZaclist(Zaclist Zaclist)
	{
		this.zaclists.remove(Zaclist);
	}
	

	
	public Set<Zaclist> getZaclists() {
		return zaclists;
	}

	public void setZaclists(Set<Zaclist> zaclists) {
		this.zaclists.addAll(zaclists);
		zaclists.forEach(obj->{
			obj.setZac(this);
		});
	}

	public void removeAllDependence()
	{

		
		this.zaclists.forEach(obj->{
			obj.setZac(null);
		});
		this.zaclists=null;

	}
    

}
