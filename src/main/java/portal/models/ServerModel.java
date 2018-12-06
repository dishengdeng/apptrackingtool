package portal.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ServerModel {
	
	@JsonProperty("id")
	private Long id;

	@JsonProperty("serverName")
	private String serverName;
	
	@JsonProperty("description")
	private String description;
    
	@JsonProperty("address")
	private String address;
    
	@JsonProperty("serverVersion")
	private String serverVersion;
    
	@JsonProperty("hwplatform")
	private String hwplatform;
    
	@JsonProperty("decomminsionDate")
	private String decomminsionDate; 	

	@JsonProperty("attachment")
	private String attachment;
	
	@JsonProperty("clusterModel")
    private ClusterModel clusterModel;
    
	public ClusterModel getClusterModel() {
		return clusterModel;
	}

	public void setClusterModel(ClusterModel clusterModel) {
		this.clusterModel = clusterModel;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getServerVersion() {
		return serverVersion;
	}

	public void setServerVersion(String serverVersion) {
		this.serverVersion = serverVersion;
	}

	public String getHwplatform() {
		return hwplatform;
	}

	public void setHwplatform(String hwplatform) {
		this.hwplatform = hwplatform;
	}

	public String getDecomminsionDate() {
		return decomminsionDate;
	}

	public void setDecomminsionDate(String decomminsionDate) {
		this.decomminsionDate = decomminsionDate;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}


	
}
