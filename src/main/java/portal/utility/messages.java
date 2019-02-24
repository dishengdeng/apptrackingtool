package portal.utility;






public enum messages {
	
	USER_UPDATE_SUCCESSFUL("user.update.successful"),
	APP_DELETE_ERROR("app.deletion");
	
	
	private final String key;

	
	private messages(String key)
	{
		this.key=key;
	}


	@Override
	public String toString() {
		
		return this.key;
	}
	
	

	

}
