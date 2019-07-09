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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.annotation.JsonView;

import portal.jsonview.Views;

@Entity
@Table(name = "Stakeholder")
public class Stakeholder {
	
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE) 
	@Column(name = "id",nullable = false,unique=true)
	@JsonView(Views.Public.class)
	private Long id;

    @Column(name = "stakeholderName",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String stakeholderName;
	
    @Column(name = "note",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String note;
    

    @Column(name = "firstname",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String firstname;
    
    @Column(name = "lastname",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String lastname;
    
    @Column(name = "address", length = 64,columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String address;

    @Column(name = "phone",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String phone;   

    @Column(name = "position",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String position; 
    
    @Column(name = "email",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String email;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "department_id",referencedColumnName="id")
    private Department department;
    
    @Column(name = "influence",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String influence;
    
    @Column(name = "interest", length = 64,columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String interest;

    @Column(name = "raciforsyschanges",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String raciforsyschanges;    
    
    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "slarole_id",referencedColumnName="id")
    private SLARole role;    
    
    @ManyToMany(mappedBy = "stakeholders")
    private Set<Report> reports= new HashSet<Report>();
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStakeholderName() {
		return stakeholderName;
	}

	public void setStakeholderName(String stakeholderName) {
		this.stakeholderName = stakeholderName;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getRaciforsyschanges() {
		return raciforsyschanges;
	}

	public void setRaciforsyschanges(String raciforsyschanges) {
		this.raciforsyschanges = raciforsyschanges;
	}
	
	public Set<Report> getReports() {
		return reports;
	}

	public void setReports(Set<Report> reports) {
		this.reports.addAll(reports);
		this.reports.forEach(obj->{
			obj.addStakeholder(this);
		});
	}
	
	public void addReport(Report report)
	{
		this.reports.add(report);
	}
	
	public void removeReport(Report report)
	{
		this.reports.remove(report);
	}
	
	public void removeAllDependence()
	{
		if(!ObjectUtils.isEmpty(this.department)) this.department.removeStakeholder(this);
		this.department=null;
		
		if(!ObjectUtils.isEmpty(this.role)) this.role.removeStakeholder(this);
		this.role=null;
		
		this.reports.forEach(obj->{
			obj.removeStakeholder(this);
		});
		this.reports=null;
	}

	
	


	@Override
	public boolean equals(Object obj)
	{
		if(this==obj) return true;
		
		if(obj==null) return false;
		
		if(this.getClass()!=obj.getClass()) return false;
		
		Stakeholder other = (Stakeholder) obj;
		
		if(this.getId()!=other.getId()) return false;
		
		return true;
	}
}
