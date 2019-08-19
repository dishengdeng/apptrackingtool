package portal.utility;

public enum RACI {
	
	Responsible(0),
	Accountable(1),
	Consulted(2),
	Informed(3);
	
	private final int code;
	
	private RACI(int _code)
	{
		this.code=_code;
	}

	public int getCode() {
		return code;
	}
}
