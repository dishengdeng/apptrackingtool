package portal.utility;



public enum FileType {
	SLA(0),
	CONTRACT(1),
	DEPARTMENT(2),
	SERVER(3);
	
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
