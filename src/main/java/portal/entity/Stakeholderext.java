package portal.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
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
import portal.utility.RACI;

@Entity
@Table(name = "stakeholderext")
public class Stakeholderext {
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE) 
	@Column(name = "id",nullable = false,unique=true)
	@JsonView(Views.Public.class)
	private Long id;
	
    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "department_id",referencedColumnName="id")
    private Department department;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "slarole_id",referencedColumnName="id")
    private SLARole role;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "stakeholder_id",referencedColumnName="id")
    private Stakeholder stakeholder;
    
    @Column(name = "influence",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String influence;
    
    @Column(name = "interest", length = 64,columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String interest;

    @ElementCollection
    @CollectionTable(name="stakerextaci", joinColumns=@JoinColumn(name="stakeraci_id"))    
    @Column(name = "raciforsyschanges")
    @JsonView(Views.Public.class)
	private Set<RACI> raciforsyschanges= new HashSet<RACI>();
    
    @Column(name = "note",columnDefinition="VARCHAR(4000)")
    @JsonView(Views.Public.class)
	private String note;

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

	public SLARole getRole() {
		return role;
	}

	public void setRole(SLARole role) {
		this.role = role;
	}

	public Stakeholder getStakeholder() {
		return stakeholder;
	}

	public void setStakeholder(Stakeholder stakeholder) {
		this.stakeholder = stakeholder;
	}

	public String getInfluence() {
		return influence;
	}

	public void setInfluence(String influence) {
		this.influence = influence;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public Set<RACI> getRaciforsyschanges() {
		return raciforsyschanges;
	}

	public void setRaciforsyschanges(Set<RACI> raciforsyschanges) {
		this.raciforsyschanges.retainAll(raciforsyschanges);
		this.raciforsyschanges.addAll(raciforsyschanges);
	}
    
    public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public void removeAllDependence()
    {
		if(!ObjectUtils.isEmpty(this.stakeholder)) this.stakeholder.removeStakeholderext(this);;
		this.setStakeholder(null);
		
		if(!ObjectUtils.isEmpty(this.department)) this.department.removeStakeholderext(this);;
		this.setDepartment(null);
		
		if(!ObjectUtils.isEmpty(this.role)) this.role.removeStakeholderext(this);;
		this.setRole(null);
    }

}
