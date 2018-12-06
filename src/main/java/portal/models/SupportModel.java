package portal.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SupportModel {

	@JsonProperty("id")	
	private Long id;

	@JsonProperty("supportName")	
	private String supportName;
    
	@JsonProperty("phone")
	private String phone;
    
	@JsonProperty("email")
	private String email;
    
	@JsonProperty("note")
	private String note;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSupportName() {
		return supportName;
	}

	public void setSupportName(String supportName) {
		this.supportName = supportName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
   

	
}
