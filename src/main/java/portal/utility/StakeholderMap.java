package portal.utility;


public enum StakeholderMap {
	Name(new String[]{"Name","0","A"}),
	BusinessUnit(new String[]{"Business Unit","1","B"}),
	Position(new String[]{"Position/Title","2","C"}),
	Location(new String[]{"Location (Site)","3","D"}),
	Role(new String[]{"Role","4","E"}),
	Email(new String[]{"Email","5","F"}),
	Phone(new String[]{"Phone","6","G"}),
	Influence(new String[]{"Influence(L-M-H)","7","H"}),
	Interest(new String[]{"Interest(L-M-H)","8","I"}),
	RACI(new String[]{"RACI for System Changes","9","J"}),
	Notes(new String[]{"Notes","10","K"});
	
	
	private final String columnName;
	
	private final int columnIndex;
	
	private final String columnAddress;

	
	private StakeholderMap(String[] column)
	{
		this.columnName=column[0];
		this.columnIndex=Integer.valueOf(column[1]);
		this.columnAddress=column[2];

	}


	public String getColumnName() {
		return columnName;
	}


	public int getColumnIndex() {
		return columnIndex;
	}


	public String getColumnAddress() {
		return columnAddress;
	}

}
