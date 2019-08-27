package portal.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.annotation.JsonView;

import portal.jsonview.Views;

@Entity
@Table(name = "Zaclist")
public class Zaclist implements Comparable<Zaclist>{

	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE) 
	@Column(name = "id",nullable = false,unique=true)
	@JsonView(Views.Public.class)
	private Long id;
	
    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "department_id",referencedColumnName="id")
    private Department department; 
    
    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "zac_id",referencedColumnName="id")
    private Zac zac;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "zacmap_id",referencedColumnName="id")
    private Zacmap zacmap;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "zone_id",referencedColumnName="id")
    private Zone zone;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Zac getZac() {
		return zac;
	}

	public void setZac(Zac zac) {
		this.zac = zac;
	}



	public Zone getZone() {
		return zone;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}

	public Zacmap getZacmap() {
		return zacmap;
	}

	public void setZacmap(Zacmap zacmap) {
		this.zacmap = zacmap;
	}

	@Override
	public int compareTo(Zaclist obj) {
		if(ObjectUtils.isEmpty(obj)) return -1;
		if(ObjectUtils.isEmpty(this.zone)) return -1;
		return this.zone.getZoneName().compareTo(obj.getZone().getZoneName());
	}
    
    

}
