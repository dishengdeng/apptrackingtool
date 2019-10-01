package portal.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
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
@Table(name = "Zacfield")
public class Zacfield implements Comparable<Zacfield>{
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE) 
	@Column(name = "id",nullable = false,unique=true)
	@JsonView(Views.Public.class)
	private Long id;
	
    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "department_id",referencedColumnName="id")
    private Department department;
    
    @OneToMany(
            mappedBy = "zacfield", 
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
	
    @Column(name = "fieldName",columnDefinition="VARCHAR(500)")
    @JsonView(Views.Public.class)
	private String fieldName;
    
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
    
	
	
    public Set<Zaclist> getZaclists() {
		return zaclists;
	}

	public void setZaclists(Set<Zaclist> zaclists) {
		this.zaclists.addAll(zaclists);
		zaclists.forEach(obj->{
			obj.setZacfield(this);
		});
	}

	public void addZaclist(Zaclist zaclist)
	{
		this.zaclists.add(zaclist);
	}
	
	public void removeZaclist(Zaclist zaclist)
	{
		this.zaclists.remove(zaclist);
	}
	
	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public void removeAllDepdence()
    {
    	if(!ObjectUtils.isEmpty(this.department)) this.department.removeZacfield(this);
    	this.setDepartment(null);
    }

	@Override
	public int compareTo(Zacfield obj) {

		return this.fieldName.compareTo(obj.getFieldName());
	}
}
