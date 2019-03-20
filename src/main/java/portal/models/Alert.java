package portal.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Alert {

	private String title;
	private String content;
	
	@JsonProperty("title")
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@JsonProperty("content")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
