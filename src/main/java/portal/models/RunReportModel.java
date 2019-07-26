package portal.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import portal.utility.ReportFormat;

public class RunReportModel<T> {

	private long id;
	
	private ReportFormat reportFormat;
	
	private T parameters;
	@JsonProperty("report")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	@JsonProperty("reportFormat")
	public ReportFormat getReportFormat() {
		return reportFormat;
	}

	public void setReportFormat(ReportFormat reportFormat) {
		this.reportFormat = reportFormat;
	}
	@JsonProperty("parameters")
	public T getParameters() {
		return parameters;
	}

	public void setParameters(T parameters) {
		this.parameters = parameters;
	}
	

	
}
