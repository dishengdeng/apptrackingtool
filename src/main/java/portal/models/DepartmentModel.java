package portal.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DepartmentModel {
	
	@JsonProperty("id")
	private Long id;

	@JsonProperty("departmentName")
	private String departmentName;
	
	@JsonProperty("description")
	private String description; 

	@JsonProperty("purpose")	
	private String purpose;
    

    
	@JsonProperty("stragicplan")
	private String stragicplan;

	@JsonProperty("roadMap")	
	private String roadMap;   

	@JsonProperty("goal")
	private String goal; 
    
	@JsonProperty("painpoint")
	private String painpoint;
 
	@JsonProperty("attachment")
	private String attachment;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}


	public String getStragicplan() {
		return stragicplan;
	}

	public void setStragicplan(String stragicplan) {
		this.stragicplan = stragicplan;
	}

	public String getRoadMap() {
		return roadMap;
	}

	public void setRoadMap(String roadMap) {
		this.roadMap = roadMap;
	}

	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	public String getPainpoint() {
		return painpoint;
	}

	public void setPainpoint(String painpoint) {
		this.painpoint = painpoint;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	
}
