package portal.utility;



public enum FileType {
	SLA(0),
	CONTRACT(1),
	DEPARTMENT(2),
	SERVER(3),
	APPINSTANCE(4),
	APPLICATION(5);
	
	private final int fileCode;

	
	private FileType(int fileCode)
	{
		this.fileCode=fileCode;
	}
	
	public int getFileCode()
	{
		return this.fileCode;
	}
	

}
