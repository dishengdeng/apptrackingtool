package portal.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SLARoleModel {
	
	@JsonProperty("id")
	private Long id;
	
	@JsonProperty("SLARoleName")
	private String SLARoleName;
	
	@JsonProperty("description")	
	private String description;
	
	@JsonProperty("responsibility")
	private String responsibility;
	
	@JsonProperty("influence")    
	private String influence;
	
	@JsonProperty("interest")    
	private String interest;
	
	@JsonProperty("raciforsyschanges")
	private String raciforsyschanges;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSLARoleName() {
		return SLARoleName;
	}

	public void setSLARoleName(String SLARoleName) {
		this.SLARoleName = SLARoleName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getResponsibility() {
		return responsibility;
	}

	public void setResponsibility(String responsibility) {
		this.responsibility = responsibility;
	}

	public String getInfluence() {
		return influence;
	}

	public void setInfluence(String influence) {
		this.influence = influence;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public String getRaciforsyschanges() {
		return raciforsyschanges;
	}

	public void setRaciforsyschanges(String raciforsyschanges) {
		this.raciforsyschanges = raciforsyschanges;
	} 
    
	
}
