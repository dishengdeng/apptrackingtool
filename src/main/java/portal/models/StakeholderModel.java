package portal.models;

import com.fasterxml.jackson.annotation.JsonProperty;



public class StakeholderModel {
	
	
	private Long id;

	
	private String stakeholderName;
	
	
	private String note;
    

	
	private String firstname;
    
	
	private String lastname;
    
	
	private String address;

	
	private String phone;
	
		
	private String position; 
    
	
	private String influence;
	
	private String interest;
	
	private String raciforsyschanges;
	
	private String email;

	
	private DepartmentModel departmentModel;

	
	private SLARoleModel slaRole;
	
	@JsonProperty("id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@JsonProperty("stakeholderName")
	public String getStakeholderName() {
		return stakeholderName;
	}

	public void setStakeholderName(String stakeholderName) {
		this.stakeholderName = stakeholderName;
	}
	
	
	@JsonProperty("note")
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	@JsonProperty("firstname")
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	@JsonProperty("lastname")
	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	@JsonProperty("address")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@JsonProperty("phone")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@JsonProperty("position")
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
	@JsonProperty("email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@JsonProperty("department")
	public DepartmentModel getDepartmentModel() {
		return departmentModel;
	}

	public void setDepartmentModel(DepartmentModel departmentModel) {
		this.departmentModel = departmentModel;
	}
	

	@JsonProperty("influence")
	public String getInfluence() {
		return influence;
	}
	@JsonProperty("role")
	public SLARoleModel getSlaRole() {
		return slaRole;
	}

	public void setSlaRole(SLARoleModel slaRole) {
		this.slaRole = slaRole;
	}

	public void setInfluence(String influence) {
		this.influence = influence;
	}
	@JsonProperty("interest")
	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}
	@JsonProperty("raciforsyschanges")
	public String getRaciforsyschanges() {
		return raciforsyschanges;
	}

	public void setRaciforsyschanges(String raciforsyschanges) {
		this.raciforsyschanges = raciforsyschanges;
	}
 
	
}
