package portal.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LicenseModel {
	
	@JsonProperty("id")
	private Long id;

	@JsonProperty("licenseNumber")
	private String licenseNumber;
	
	@JsonProperty("registrationDate")
	private String registrationDate;
    
	@JsonProperty("note")
	private String note;
    
	@JsonProperty("price")
	private String price;
    
	@JsonProperty("expireDate")
	private String expireDate;

	@JsonProperty("numberoflicenserequired")
	private String numberoflicenserequired;  
    
	@JsonProperty("renewalfrequency")
	private String renewalfrequency;  
    
	@JsonProperty("renewalowner")
	private String renewalowner; 
    
	@JsonProperty("warrenty")
	private String warrenty; 

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public String getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}

	public String getNumberoflicenserequired() {
		return numberoflicenserequired;
	}

	public void setNumberoflicenserequired(String numberoflicenserequired) {
		this.numberoflicenserequired = numberoflicenserequired;
	}

	public String getRenewalfrequency() {
		return renewalfrequency;
	}

	public void setRenewalfrequency(String renewalfrequency) {
		this.renewalfrequency = renewalfrequency;
	}

	public String getRenewalowner() {
		return renewalowner;
	}

	public void setRenewalowner(String renewalowner) {
		this.renewalowner = renewalowner;
	}

	public String getWarrenty() {
		return warrenty;
	}

	public void setWarrenty(String warrenty) {
		this.warrenty = warrenty;
	}    
}
