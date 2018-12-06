package portal.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import portal.entity.SLARole;

public class StakeholderModel {
	
	@JsonProperty("id")
	private Long id;

	@JsonProperty("stakeholderName")
	private String stakeholderName;
	
	@JsonProperty("note")
	private String note;
    

	@JsonProperty("firstname")
	private String firstname;
    
	@JsonProperty("lastname")
	private String lastname;
    
	@JsonProperty("address")
	private String address;

	@JsonProperty("phone")
	private String phone;
	
	@JsonProperty("position")	
	private String position; 
    
	@JsonProperty("email")
	private String email;

	@JsonProperty("department")
	private DepartmentModel departmentModel;

	@JsonProperty("role")
	private SLARole slaRole;

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

	public DepartmentModel getDepartmentModel() {
		return departmentModel;
	}

	public void setDepartmentModel(DepartmentModel departmentModel) {
		this.departmentModel = departmentModel;
	}

	public SLARole getSlaRole() {
		return slaRole;
	}

	public void setSlaRole(SLARole slaRole) {
		this.slaRole = slaRole;
	}
 
	
}
