package portal.validator;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import portal.entity.Stakeholder;

@Component
public class StakeholderValidator implements Validator{
	
	private final String phonenumPattern="^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$";
	
	private final String emailPattern="^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";

	@Override
	public boolean supports(Class<?> clazz) {
		
		return Stakeholder.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Stakeholder stakeholder=(Stakeholder)target;
		
		if(!StringUtils.isEmpty(stakeholder.getPhone()) && !Pattern.compile(phonenumPattern).matcher(stakeholder.getPhone()).matches())
		{
			errors.rejectValue("phone", "error.phone");
		}
		
		if(!StringUtils.isEmpty(stakeholder.getEmail()) && !Pattern.compile(emailPattern, Pattern.CASE_INSENSITIVE).matcher(stakeholder.getEmail()).matches())
		{
			errors.rejectValue("email", "error.email");
		}
		
	}
	
}
