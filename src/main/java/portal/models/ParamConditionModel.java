package portal.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ParamConditionModel {

	private String name;
	
	private String value;
	
	public ParamConditionModel(String _name,String _value)
	{
		this.name=_name;
		this.value=_value;
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

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
