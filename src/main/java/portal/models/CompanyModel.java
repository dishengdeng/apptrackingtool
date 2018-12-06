package portal.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CompanyModel {
	
	@JsonProperty("id")
	private Long id;

	@JsonProperty("companyName")
	private String companyName;
	
	@JsonProperty("address")
	private String address;
    
	@JsonProperty("phone")
	private String phone;
    
	@JsonProperty("email")
	private String email;
    
	@JsonProperty("contactName")
	private String contactName;

	@JsonProperty("contactPhone")
	private String contactPhone;    

	@JsonProperty("manufacturer")    
	private String manufacturer;  
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}




	
}
