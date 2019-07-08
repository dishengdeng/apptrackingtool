package portal.utility;

public enum ReportLevelType {
	APPLICATION(0),
	STAKEHOLDER(1);
	
	private final int levelCode;

	
	private ReportLevelType(int levelCode)
	{
		this.levelCode=levelCode;
	}


	public int getLevelCode() {
		return levelCode;
	}
}
