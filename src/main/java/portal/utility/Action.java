package portal.utility;



public enum Action {
	CREATE(1),
	UPDATE(0),
	DELETE(2),
	COPY(3),
	COPYTO(4);
	private final int roleCode;

	
	private Action(int roleCode)
	{
		this.roleCode=roleCode;
	}
	
	public int getRoleCode()
	{
		return this.roleCode;
	}
	

}
