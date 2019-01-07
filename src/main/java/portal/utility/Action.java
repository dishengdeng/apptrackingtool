package portal.utility;



public enum Action {
	CREATE(1),
	UPDATE(0);
	
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
