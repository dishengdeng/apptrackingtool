package portal.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class App {
	
	private  String appName;

	
	private  String appDecomminsionDate;
	
	
	private Long id;
	
	@JsonProperty("id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@JsonProperty("ApplicationName")
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	
	@JsonProperty("appDecomminsionDate")
	public String getAppDecomminsionDate() {
		return appDecomminsionDate;
	}
	public void setAppDecomminsionDate(String appDecomminsionDate) {
		this.appDecomminsionDate = appDecomminsionDate;
	}

}
