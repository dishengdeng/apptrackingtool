package portal.models;


import com.fasterxml.jackson.annotation.JsonProperty;


public class JSESSIONID {
	
	private  String sessionid;

	public JSESSIONID(String sessionid)
	{
		this.sessionid = sessionid;
	}
	
	@JsonProperty("JSESSIONID")
	public String getJSESSIONID() {
		return sessionid;
	}

	public void setJSESSIONID(String sessionid) {
		this.sessionid = sessionid;
	}	
	
}
