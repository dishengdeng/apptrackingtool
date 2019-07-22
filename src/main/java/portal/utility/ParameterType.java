package portal.utility;

public enum ParameterType {
	
	String(0),
	Number(1),
	Date(2),
	Boolean(3);
	
	private final int typeCode;
	
	private ParameterType(int _typeCode)
	{
		this.typeCode=_typeCode;
	}
	
	public int getTypeCode() {
		return typeCode;
	}

}
