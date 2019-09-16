package portal.models;


import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonProperty;


public class AppdepartmentModel {
	@JsonProperty("id")
	private Long id;

	//--zone
	@JsonProperty("zone")
	private JSONObject zone;
	
	//--site
	@JsonProperty("sites")
    private JSONArray sites;
	//--department
	@JsonProperty("department")
    private JSONObject department;
	
	//--application information
	@JsonProperty("application")
    private JSONObject application;
	

   
    
   //--contract information 
	@JsonProperty("contract")
    private JSONObject contract;
	
	@JsonProperty("vendor")
    private JSONObject vendor;
    
    //----support information
	@JsonProperty("support")
	private JSONObject support;
    


    
    //--project information
	@JsonProperty("project")
    private JSONObject project;
	



	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public JSONObject getZone() {
		return zone;
	}


	public void setZone(JSONObject zone) {
		this.zone = zone;
	}




	public JSONArray getSites() {
		return sites;
	}


	public void setSites(JSONArray sites) {
		this.sites = sites;
	}


	public JSONObject getDepartment() {
		return department;
	}


	public void setDepartment(JSONObject department) {
		this.department = department;
	}


	public JSONObject getApplication() {
		return application;
	}


	public void setApplication(JSONObject application) {
		this.application = application;
	}


	public JSONObject getContract() {
		return contract;
	}


	public void setContract(JSONObject contract) {
		this.contract = contract;
	}


	public JSONObject getVendor() {
		return vendor;
	}


	public void setVendor(JSONObject vendor) {
		this.vendor = vendor;
	}


	public JSONObject getSupport() {
		return support;
	}


	public void setSupport(JSONObject support) {
		this.support = support;
	}


	public JSONObject getProject() {
		return project;
	}


	public void setProject(JSONObject project) {
		this.project = project;
	}



	
	
}
