package portal.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SLAModel {
	
	@JsonProperty("id")
	private Long id;

	@JsonProperty("slaName")
	private String slaName;
	
	@JsonProperty("description")
	private String description;    

	@JsonProperty("effectivedate")
	private String effectivedate;
    
	@JsonProperty("terminationdate")
	private String terminationdate;
    
	@JsonProperty("approvername")
	private String approvername;

	@JsonProperty("approvaldate")
	private String approvaldate;
    
	@JsonProperty("docreferece")
	private String docreferece;
    
	@JsonProperty("attachment")
	private String attachment;    



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSlaName() {
		return slaName;
	}

	public void setSlaName(String slaName) {
		this.slaName = slaName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEffectivedate() {
		return effectivedate;
	}

	public void setEffectivedate(String effectivedate) {
		this.effectivedate = effectivedate;
	}

	public String getTerminationdate() {
		return terminationdate;
	}

	public void setTerminationdate(String terminationdate) {
		this.terminationdate = terminationdate;
	}

	public String getApprovername() {
		return approvername;
	}

	public void setApprovername(String approvername) {
		this.approvername = approvername;
	}

	public String getApprovaldate() {
		return approvaldate;
	}

	public void setApprovaldate(String approvaldate) {
		this.approvaldate = approvaldate;
	}

	public String getDocreferece() {
		return docreferece;
	}

	public void setDocreferece(String docreferece) {
		this.docreferece = docreferece;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}


}
