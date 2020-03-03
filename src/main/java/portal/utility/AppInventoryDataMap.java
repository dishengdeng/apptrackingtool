package portal.utility;

public enum AppInventoryDataMap {
	
	YES("Yes"),
	NO("No"),
	PARTIAL("Partial"),
	NA("N/A");
	
	private final String value;
	
	private AppInventoryDataMap(final String _value)
	{
		this.value=_value;
	}

	public String getValue() {
		return value;
	}
	
	
}
