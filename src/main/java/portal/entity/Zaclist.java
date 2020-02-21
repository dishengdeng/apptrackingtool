package portal.entity;


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
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id",referencedColumnName="id")
    private Department department; 
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zac_id",referencedColumnName="id")
    private Zac zac;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zacmap_id",referencedColumnName="id")
    private Zacmap zacmap;
    

    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zacfield_id",referencedColumnName="id")
    private Zacfield zacfield;

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



	
	public Zacfield getZacfield() {
		return zacfield;
	}

	public void setZacfield(Zacfield zacfield) {
		this.zacfield = zacfield;

	}

	public Zacmap getZacmap() {
		return zacmap;
	}

	public void setZacmap(Zacmap zacmap) {
		this.zacmap = zacmap;
	}
	
	public void removeAlldependence()
	{
		if(!ObjectUtils.isEmpty(this.department)) this.department.removeZaclist(this);
		this.setDepartment(null);

		
		if(!ObjectUtils.isEmpty(this.zac)) this.zac.removeZaclist(this);
		this.setZac(null);
		
		if(!ObjectUtils.isEmpty(this.zacmap)) this.zacmap.removeZaclist(this);
		this.setZacmap(null);
		
		if(!ObjectUtils.isEmpty(this.zacfield)) this.zacfield.removeZaclist(this);
		this.setZacfield(null);
		

	}
	
	public void removeForZacmap()
	{
		if(!ObjectUtils.isEmpty(this.department)) this.department.removeZaclist(this);
		this.setDepartment(null);
		

		if(!ObjectUtils.isEmpty(this.zac)) this.zac.removeZaclist(this);
		this.setZac(null);
		
		if(!ObjectUtils.isEmpty(this.zacfield)) this.zacfield.removeZaclist(this);
		this.setZacfield(null);
	}
	
	public void removeForDepartment()
	{
		if(!ObjectUtils.isEmpty(this.zac)) this.zac.removeZaclist(this);
		this.setZac(null);
	}

	public void removeForZacfield()
	{
		
		if(!ObjectUtils.isEmpty(this.department)) this.department.removeZaclist(this);
		this.setDepartment(null);
		
		if(!ObjectUtils.isEmpty(this.zac)) this.zac.removeZaclist(this);
		this.setZac(null);
		
		if(!ObjectUtils.isEmpty(this.zacmap)) this.zacmap.removeZaclist(this);
		this.setZacmap(null);
	}

	@Override
	public int compareTo(Zaclist obj) {
		if(ObjectUtils.isEmpty(obj)) return -1;
		if(ObjectUtils.isEmpty(this.zacfield)) return -1;
		return this.zacfield.getFieldName().compareTo(obj.getZacfield().getFieldName());
	}
    
    

}
