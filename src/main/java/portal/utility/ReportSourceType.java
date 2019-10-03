package portal.utility;

public enum ReportSourceType {
	SQL(0),
	JSON(1);
	
	private final int typeCode;
	
	private ReportSourceType(int _typeCode)
	{
		this.typeCode=_typeCode;
	}

	public int getTypeCode() {
		return typeCode;
	}
	
}
