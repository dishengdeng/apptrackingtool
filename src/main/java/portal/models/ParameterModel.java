package portal.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import portal.utility.ParameterType;

public class ParameterModel {
	private Long id;
	
	private String name;
	
	private ParameterType type;
	
	private String value;
	@JsonProperty("id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@JsonProperty("name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	@JsonProperty("value")
	public String getValue() {
		return value;
	}
	@JsonProperty("type")
	public ParameterType getType() {
		return type;
	}

	public void setType(ParameterType type) {
		this.type = type;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
