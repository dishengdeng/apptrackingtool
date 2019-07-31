package portal.models;

import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonProperty;


import portal.utility.ParameterType;

public class ParameterModel implements Cloneable{
	private Long id;
	
	private String name;
	
	private ParameterType type;
	
	private String label;
	
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
	public ParameterModel()
	{
		
	}
	
	public ParameterModel(JSONObject obj)
	{
		this.id=obj.getLong("id");
		this.name=obj.getString("name");
		this.label=obj.getString("label");
		this.type=ParameterType.valueOf(obj.getString("type"));
		this.value=obj.getString("value");
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
	@JsonProperty("label")
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	
}
