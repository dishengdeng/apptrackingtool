package portal.utility;

public enum SupportType {
	Network(0),
	AppServer(1),
	DBServer(2);
	
	private final int type;

	
	private SupportType(int type)
	{
		this.type=type;
	}


	public int getType() {
		return type;
	}
	

}
