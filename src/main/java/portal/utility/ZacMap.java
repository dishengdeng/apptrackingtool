package portal.utility;


public enum ZacMap {
	APPLICATION("Application Name"),
	ZACFIELDS("ZACFIELDS"),
	DETAIL("Details");
	
	
	private final String value;
	


	
	private ZacMap(String _value)
	{
		this.value=_value;


	}




	public String getValue() {
		return value;
	}

	

}
