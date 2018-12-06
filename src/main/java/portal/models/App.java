package portal.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class App {
	@JsonProperty("appName")
	private  String appName;

	@JsonProperty("appDecomminsionDate")
	private  String appDecomminsionDate;
	
	@JsonProperty("id")
	private Long id;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getAppDecomminsionDate() {
		return appDecomminsionDate;
	}
	public void setAppDecomminsionDate(String appDecomminsionDate) {
		this.appDecomminsionDate = appDecomminsionDate;
	}

}
