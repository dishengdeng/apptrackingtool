package portal.utility;

public class InvalidDataFormatException extends Exception{
	private static final long serialVersionUID = 3088985567735783689L;

	private final String _message;
	
	public InvalidDataFormatException(String message)
	{
		super(message);
		this._message=message;

	}

	public String get_message() {
		return _message;
	}
	
	
}
