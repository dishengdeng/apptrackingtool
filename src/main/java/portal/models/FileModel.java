package portal.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FileModel {
	@JsonProperty("id")
	private Long id;
	
	@JsonProperty("filename")
	private  String filename;

	@JsonProperty("createdat")
	private  String createdat;
	
	@JsonProperty("createdby")
	private  String createdby;
	
	@JsonProperty("departmentid")
	private Long departmentid;

	public Long getDepartmentid() {
		return departmentid;
	}

	public void setDepartmentid(Long departmentid) {
		this.departmentid = departmentid;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getCreatedat() {
		return createdat;
	}

	public void setCreatedat(String createdat) {
		this.createdat = createdat;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	
	

}
