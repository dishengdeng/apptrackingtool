package portal.service.Impl;


import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import portal.service.MessageSourceService;

@Service
public class MessageSourceServiceImpl implements MessageSourceService{
	
	@Autowired
	private MessageSource messagesource;
	
	@Override
	public String getMessage(String key, Locale locale) {
		
		return messagesource.getMessage(key, null, locale);
	}

	@Override
	public String getMessage(String key, Object[] args, Locale locale) {

		return messagesource.getMessage(key, args, locale);
	}

	@Override
	public String getMessage(String key) {
		
		return messagesource.getMessage(key, null, null);
	}


}
