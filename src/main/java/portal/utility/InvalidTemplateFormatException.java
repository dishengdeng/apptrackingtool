package portal.utility;

public class InvalidTemplateFormatException extends Exception{
	private static final long serialVersionUID = 30889855677356689L;

	private final String _message;
	
	public InvalidTemplateFormatException(String message)
	{
		super(message);
		this._message=message;

	}

	public String get_message() {
		return _message;
	}
	
	
}
