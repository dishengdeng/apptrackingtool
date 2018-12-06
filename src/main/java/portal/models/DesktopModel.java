package portal.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DesktopModel {
	
	@JsonProperty("id")
	private Long id;

	@JsonProperty("desktopName")
	private String desktopName;
	
	@JsonProperty("description")
	private String description;
    

	@JsonProperty("osVersion")
	private String osVersion;
    
	@JsonProperty("hwplatform")
	private String hwplatform;
    
	@JsonProperty("decomminsionDate")
	private String decomminsionDate;

    

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDesktopName() {
		return desktopName;
	}

	public void setDesktopName(String desktopName) {
		this.desktopName = desktopName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
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


	
}
