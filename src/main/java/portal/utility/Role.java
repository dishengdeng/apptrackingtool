package portal.utility;



public enum Role {
	ADMIN(1),
	GENERAL(0);
	
	private final int roleCode;

	
	private Role(int roleCode)
	{
		this.roleCode=roleCode;
	}
	
	public int getRoleCode()
	{
		return this.roleCode;
	}
	

}
