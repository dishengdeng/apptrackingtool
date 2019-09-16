package portal.utility;

public enum Appdepartmenttype {
	APPLICATION("application"),
	DEPARTMENT("department"),
	VENDOR("vendor"),
	CONTRACT("contract"),
	PROJECT("project"),
	LICENSE("license");	

	private final String typetext;
	
	private Appdepartmenttype(String _typetext)
	{
		this.typetext=_typetext;
	}

	public String getTypetext() {
		return typetext;
	}


}
