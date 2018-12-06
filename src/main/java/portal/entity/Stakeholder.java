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
    
    
    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "slarole_id",referencedColumnName="id")
    private SLARole role;    
    

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
 
	
}
