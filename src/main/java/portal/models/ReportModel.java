package portal.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReportModel {
	
	private Long id;
	
	private String reportname;
	
	private String description;
	
	private List<ReportLevelModel> reportlevels;
	
	private List<StakeholderModel> stakeholders;
	@JsonProperty("id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@JsonProperty("ReportName")
	public String getReportname() {
		return reportname;
	}

	public void setReportname(String reportname) {
		this.reportname = reportname;
	}
	@JsonProperty("Description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	@JsonProperty("ReportLevels")
	public List<ReportLevelModel> getReportlevels() {
		return reportlevels;
	}

	public void setReportlevels(List<ReportLevelModel> reportlevels) {
		this.reportlevels = reportlevels;
	}
	@JsonProperty("Stakeholders")
	public List<StakeholderModel> getStakeholders() {
		return stakeholders;
	}

	public void setStakeholders(List<StakeholderModel> stakeholders) {
		this.stakeholders = stakeholders;
	}
	
	
	

}
