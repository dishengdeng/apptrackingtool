package portal.utility;

public enum ReportFormat {
	PDF(0),
	EXCEL(1);
	
	private final int formatCode;

	
	private ReportFormat(int formatCode)
	{
		this.formatCode=formatCode;
	}


	public int getFormatCode() {
		return formatCode;
	}

}
