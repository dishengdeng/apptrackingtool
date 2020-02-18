package portal.utility;

public enum StakeholderDataMap {
	
	L("Low"),
	H("High"),
	M("Medium"),
	R("Responsible"),
	A("Accountable"),
	C("Consulted"),
	I("Informed");
	
	private final String value;
	
	private StakeholderDataMap(final String _value)
	{
		this.value=_value;
	}

	public String getValue() {
		return value;
	}
	
	
}
