package portal.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ZoneModel {
	@JsonProperty("id")
	private Long id;
	
	@JsonProperty("zoneName")
	private  String zoneName;

	@JsonProperty("description")
	private  String description;
	
	@JsonProperty("note")
	private  String note;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
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
	

}
