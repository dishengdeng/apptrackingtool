package portal.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SiteModel {
	@JsonProperty("id")
	private Long id;
	
	@JsonProperty("siteName")
	private  String siteName;

	@JsonProperty("description")
	private  String description;
	
	@JsonProperty("note")
	private  String note;
	
	@JsonProperty("zone")
	private ZoneModel zone;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public ZoneModel getZone() {
		return zone;
	}

	public void setZone(ZoneModel zone) {
		this.zone = zone;
	}
	

}
