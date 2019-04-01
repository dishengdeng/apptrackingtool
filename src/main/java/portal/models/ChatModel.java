package portal.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChatModel {

	private String conv;
	private String message;
	private String sendby;
	
	@JsonProperty("conversation")
	public String getConv() {
		return conv;
	}
	public void setConv(String conv) {
		this.conv = conv;
	}
	
	@JsonProperty("message")
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	@JsonProperty("name")
	public String getSendby() {
		return sendby;
	}
	public void setSendby(String sendby) {
		this.sendby = sendby;
	}
	
	

	
}
