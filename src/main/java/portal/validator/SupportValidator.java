package portal.validator;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


import portal.entity.Support;

@Component
public class SupportValidator implements Validator{
	
	private final String phonenumPattern="\\d{10}";
	
	private final String emailPattern="^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";

	@Override
	public boolean supports(Class<?> clazz) {
		
		return Support.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Support support=(Support)target;
		
		if(!StringUtils.isEmpty(support.getPhone()) && !Pattern.compile(phonenumPattern).matcher(support.getPhone()).matches())
		{
			errors.rejectValue("phone", "error.phone");
		}
		
		if(!StringUtils.isEmpty(support.getEmail()) && !Pattern.compile(emailPattern, Pattern.CASE_INSENSITIVE).matcher(support.getEmail()).matches())
		{
			errors.rejectValue("email", "error.email");
		}
		
	}
	
}
