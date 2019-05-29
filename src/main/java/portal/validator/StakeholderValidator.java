package portal.validator;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import portal.entity.Stakeholder;

@Component
public class StakeholderValidator implements Validator{
	
	private final String phonenumPattern="\\d{9}";

	@Override
	public boolean supports(Class<?> clazz) {
		
		return Stakeholder.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Stakeholder stakeholder=(Stakeholder)target;
		
		if(!Pattern.compile(phonenumPattern).matcher(stakeholder.getPhone()).matches())
		{
			errors.rejectValue("phone", "error.phone");
		}
		
	}
	
}
