package portal.utility;

public enum ReportLevelType {
	APPLICATION(0),
	STAKEHOLDER(1),
	APPINSTANCE(2),
	DEPARTMENT(3),
	ZONE(4),
	SITE(5),
	VENDOR(6),
	CONTRACT(7),
	LICENSE(8),
	SERVER(9),
	SUPPORT(10),
	ZAC(11);
	
	private final int levelCode;

	
	private ReportLevelType(int levelCode)
	{
		this.levelCode=levelCode;
	}


	public int getLevelCode() {
		return levelCode;
	}
}
