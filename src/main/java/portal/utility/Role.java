package portal.utility;



public enum Role {
	ADMIN(0),
	GENERAL(1),
	SYSADMIN(2),
	USER(3),
	INTEGRATION(4),
	DEVELOPER(5);
	
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
