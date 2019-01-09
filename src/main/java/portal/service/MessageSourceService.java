package portal.service;

import java.util.Locale;

public interface MessageSourceService {
	public String getMessage(String key,Locale locale);
	public String getMessage(String key,Object[] args, Locale locale);
	public String getMessage(String key);
}
