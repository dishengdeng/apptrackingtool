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
	SubjectMatterExpert(new String[]{"Subject Matter Expert","13","N"}),
	Trainer(new String[]{"Trainer","14","O"}),
	UserAdmin(new String[]{"User Admin","15","P"}),
	SystemAdmin(new String[]{"System Admin","16","Q"}),
	AppServerSupport(new String[]{"App Server Support","17","R"}),
	DBServerSupport(new String[]{"DB Server Support","18","S"}),
	NetworkSupport(new String[]{"Network Support","19","T"}),
	Vendor(new String[]{"Vendor","20","U"}),
	Contractinplace(new String[]{"Contract / Licensing in Place (Y/N)?","21","V"}),
	Contractdetail(new String[]{"Contract / Licensing Details","22","W"}),
	ExpirationDate(new String[]{"Expiration Date","23","X"}),
	Frequency(new String[]{"Frequency","24","Y"}),
	VendorSla(new String[]{"Vendor SLA/SMA?","25","Z"}),
	AhsItSla(new String[]{"AHS-IT SLA?","26","AA"}),
	ApplicationVersion(new String[]{"Application Version","27","AB"}),
	Broadmap(new String[]{"Business Unit Initiatives on Roadmap?","28","AC"}),
	IMP(new String[]{"IMP","29","AD"}),
	Cshrecimit(new String[]{"CSHREC/IMIT","30","AE"}),
	Note(new String[]{"Additional Information/Notes","31","AF"});
	
	
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
