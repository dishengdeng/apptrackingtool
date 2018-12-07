package portal.utility;

public enum Status {
	Active(1),
	Inactive(0);
	
	private final int statusCode;
	
	private Status(int statuscode)
	{
		this.statusCode=statuscode;
	}
	
	public int getStatusCode()
	{
		return this.statusCode;
	}
}
