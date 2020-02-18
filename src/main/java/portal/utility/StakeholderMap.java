package portal.utility;


public enum StakeholderMap {
	Name(new String[]{"Name","0","A"}),
	Position(new String[]{"Position/Title","1","B"}),
	Location(new String[]{"Location (Site)","2","C"}),
	Role(new String[]{"Role","3","D"}),
	Email(new String[]{"Email","4","E"}),
	Phone(new String[]{"Phone","5","F"}),
	Influence(new String[]{"Influence(L-M-H)","6","G"}),
	Interest(new String[]{"Interest(L-M-H)","7","H"}),
	RACI(new String[]{"RACI for System Changes","8","I"}),
	Notes(new String[]{"Notes","9","J"});
	
	
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
