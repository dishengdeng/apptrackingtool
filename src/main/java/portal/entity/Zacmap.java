package portal.entity;

import java.util.HashSet;
import java.util.Set;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.annotation.JsonView;

import portal.jsonview.Views;

@Entity
@Table(name = "Zacmap")
public class Zacmap {
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE) 
	@Column(name = "id",nullable = false,unique=true)
	@JsonView(Views.Public.class)
	private Long id;
	
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "application_id",referencedColumnName="id")
    private Application application; 
    
    @OneToMany(
            mappedBy = "zacmap", 
           
            orphanRemoval = true,
            fetch=FetchType.LAZY
        )
    private Set<Zaclist> zaclists = new HashSet<Zaclist>();
    
    @Column(name = "detail", columnDefinition="VARCHAR(1000)")
    @JsonView(Views.Public.class)
    private String detail;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}
	
	public Set<Zaclist> getZaclists() {
		return zaclists;
	}

	public void setZaclists(Set<Zaclist> zaclists) {
		this.zaclists.addAll(zaclists);
		zaclists.forEach(obj->{
			obj.setZacmap(this);
		});
	}
	public void removeZaclist(Zaclist zaclist)
	{
		this.zaclists.remove(zaclist);
	}
	
	public void addZaclist(Zaclist zaclist)
	{
		this.zaclists.add(zaclist);
	}
	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public void removeAllDepedence()
	{
		if(!ObjectUtils.isEmpty(this.application)) this.application.removeZacmap(this);
		this.setApplication(null);
		


	}
    
}
