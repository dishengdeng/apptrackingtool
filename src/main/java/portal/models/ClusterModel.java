package portal.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClusterModel {
	@JsonProperty("id")
	private Long id;

	@JsonProperty("clusterName")
	private String clusterName;
	
	@JsonProperty("description")
	private String description;
    


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String zoneName) {
		this.clusterName = zoneName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	
}
