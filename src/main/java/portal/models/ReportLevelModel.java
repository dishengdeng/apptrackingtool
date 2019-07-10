package portal.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReportLevelModel {
	
	private Long id;
	
	private String reportLevelType;

	@JsonProperty("id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@JsonProperty("ReportLevelType")
	public String getReportLevelType() {
		return reportLevelType;
	}

	public void setReportLevelType(String reportLevelType) {
		this.reportLevelType = reportLevelType;
	}
	
	
}
