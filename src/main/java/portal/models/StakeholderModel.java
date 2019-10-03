package portal.models;

import com.fasterxml.jackson.annotation.JsonProperty;





public class StakeholderModel {
	
	

	private Long id;
	
	@JsonProperty("id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	private String stakeholderName;
	
	
	private String note;
    
    
	private Long site;


	
	private String phone;
	
		
	private String position; 
    
	
	
	private String email;


	private String businessunit;

	
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

	

	
	@JsonProperty("site")
	public Long getSite() {
		return site;
	}
	public void setSite(Long site) {
		this.site = site;
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
	
	@JsonProperty("businessunit")
	public String getBusinessunit() {
		return businessunit;
	}
	
	public void setBusinessunit(String businessunit) {
		this.businessunit = businessunit;
	}
	

 
	
}
