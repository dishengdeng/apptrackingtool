package portal.utility;


public enum AppinventoryMap {
	ApplicationName(new String[]{"Application Name","0","A"}),
	ApplicationType(new String[]{"Application Type","1","B"}),
	ApplicationPurpose(new String[]{"Application Purpose","2","C"}),
	South(new String[]{"South","3","D"}),
	Calgary(new String[]{"Calgary","4","E"}),
	Central(new String[]{"Central","5","F"}),
	Edmonton(new String[]{"Edmonton","6","G"}),
	North(new String[]{"North","7","H"}),
	Site(new String[]{"Site","8","I"}),
	BusinessLead(new String[]{"Business Lead","9","J"}),
	ApplicationOwner(new String[]{"Application Owner","10","K"}),
	Goverinplace(new String[]{"Governance/ Standards in Place (Y/N)?","11","L"}),
	Userbase(new String[]{"Userbase","12","M"}),
	PowerUser(new String[]{"Power-User (alt. point person)","13","N"}),
	FrontlineUser(new String[]{"Frontline User","14","O"}),
	SubjectMatterExpert(new String[]{"Subject Matter Expert","15","P"}),
	Trainer(new String[]{"Trainer","16","Q"}),
	UserAdmin(new String[]{"User Admin","17","R"}),
	SystemAdmin(new String[]{"System Admin","18","S"}),
	AppServerSupport(new String[]{"App Server Support","19","T"}),
	DBServerSupport(new String[]{"DB Server Support","20","U"}),
	NetworkSupport(new String[]{"Network Support","21","V"}),
	Vendor(new String[]{"Vendor","22","W"}),
	Contractinplace(new String[]{"Contract / Licensing in Place (Y/N)?","23","X"}),
	Contractdetail(new String[]{"Contract / Licensing Details","24","Y"}),
	ExpirationDate(new String[]{"Expiration Date","25","Z"}),
	Frequency(new String[]{"Frequency","26","AA"}),
	VendorSla(new String[]{"Vendor SLA/SMA?","27","AB"}),
	AhsItSla(new String[]{"AHS-IT SLA?","28","AC"}),
	ApplicationVersion(new String[]{"Application Version","29","AD"}),
	Broadmap(new String[]{"Business Unit Initiatives on Roadmap?","30","AE"}),
	IMP(new String[]{"IMP","31","AF"}),
	Cshrecimit(new String[]{"CSHREC/IMIT","32","AG"}),
	Note(new String[]{"Additional Information/Notes","33","AH"});
	
	
	private final String columnName;
	
	private final int columnIndex;
	
	private final String columnAddress;

	
	private AppinventoryMap(String[] column)
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
