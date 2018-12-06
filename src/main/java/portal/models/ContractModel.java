package portal.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ContractModel {
	
	@JsonProperty("id")
	private Long id;

	@JsonProperty("contractName")
	private String contractName;
	
	@JsonProperty("description")
	private String description;
    

	@JsonProperty("smaDescription")
	private String smaDescription;
    
	@JsonProperty("slaDescription")
	private String slaDescription;
    
	@JsonProperty("expireDate")
	private String expireDate;

	@JsonProperty("attachment")
	private String attachment;   

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSmaDescription() {
		return smaDescription;
	}

	public void setSmaDescription(String smaDescription) {
		this.smaDescription = smaDescription;
	}

	public String getSlaDescription() {
		return slaDescription;
	}

	public void setSlaDescription(String slaDescription) {
		this.slaDescription = slaDescription;
	}

	public String getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}





	
}
