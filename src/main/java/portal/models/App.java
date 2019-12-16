package portal.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class App {
	
	private  String appName;

	
	private  String appDecomminsionDate;
	
	private  String notes;
	
	private  String url;
	private Long id;
	
	public App()
	{
		
	}
	
	public App(Long _id,String _appName,String _appDecomminsionDate,String _notes,String _url)
	{
		this.id=_id;
		this.appDecomminsionDate=_appDecomminsionDate;
		this.appName=_appName;
		this.notes=_notes;
		this.url=_url;
	}
	
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
	
	@JsonProperty("notes")
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	@JsonProperty("url")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	

}
