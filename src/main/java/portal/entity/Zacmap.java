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
@Table(name = "Zacmap")
public class Zacmap {
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE) 
	@Column(name = "id",nullable = false,unique=true)
	@JsonView(Views.Public.class)
	private Long id;
	
    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "application_id",referencedColumnName="id")
    private Application application; 
    
    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "department_id",referencedColumnName="id")
    private Department department; 
    
    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "zac_id",referencedColumnName="id")
    private Zac zac;

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
    
	
	public void removeAllDepedence()
	{
		if(!ObjectUtils.isEmpty(this.application)) this.application.removeZacmap(this);
		this.setApplication(null);
		
		if(!ObjectUtils.isEmpty(this.department)) this.department.removeZacmap(this);
		this.setDepartment(null);;
		
		if(!ObjectUtils.isEmpty(this.zac)) this.zac.removeZacmap(this);
		this.setZac(null);
	}
    
}
