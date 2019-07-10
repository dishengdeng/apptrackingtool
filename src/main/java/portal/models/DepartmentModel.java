package portal.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DepartmentModel {
	
	
	private Long id;

	
	private String departmentName;
	
	
	private String description; 

		
	private String purpose;
    

    
	
	private String stragicplan;

		
	private String roadMap;   

	
	private String goal; 
    
	
	private String painpoint;
 
	
	@JsonProperty("id")   
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@JsonProperty("departmentName")
	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	
	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@JsonProperty("purpose")
	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	@JsonProperty("stragicplan")
	public String getStragicplan() {
		return stragicplan;
	}

	public void setStragicplan(String stragicplan) {
		this.stragicplan = stragicplan;
	}
	
	@JsonProperty("roadMap")
	public String getRoadMap() {
		return roadMap;
	}

	public void setRoadMap(String roadMap) {
		this.roadMap = roadMap;
	}
	
	@JsonProperty("goal")
	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}
	
	@JsonProperty("painpoint")
	public String getPainpoint() {
		return painpoint;
	}

	public void setPainpoint(String painpoint) {
		this.painpoint = painpoint;
	}
	


	
}
