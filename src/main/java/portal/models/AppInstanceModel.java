package portal.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AppInstanceModel {
	
	@JsonProperty("id")
	private Long id;
	
	@JsonProperty("appInstanceName")
	private String appInstanceName;
    
	@JsonProperty("status")
	private String status;
    
	@JsonProperty("description")
	private String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAppInstanceName() {
		return appInstanceName;
	}

	public void setAppInstanceName(String appInstanceName) {
		this.appInstanceName = appInstanceName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
