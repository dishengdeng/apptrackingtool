package portal.service.Impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import portal.entity.User;
import portal.service.UserService;
import portal.utility.Action;
@Component
public class UserValidator implements Validator{
    @Autowired
    private UserService userService;
    
	@Override
	public boolean supports(Class<?> aClass) {
		return User.class.equals(aClass);
	}

	//private final String pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}$";
	
	@Override
	public void validate(Object o, Errors errors) {
        User user = (User) o;

        
        
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "status", "NotEmpty");
        
        if (user.getUsername().length() > 32) {
            errors.rejectValue("username", "Size.user.username");
        }
        
        if(!user.getNamebypass())
        {
            if (userService.findByName(user.getUsername()) != null) {
                errors.rejectValue("username", "Duplicate.user.username");
            }
        }

        
        if(user.getAction().equals(Action.CREATE.name()))
        {
        	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");        	
            if (user.getPassword().length() < 6) {
                errors.rejectValue("password", "Size.user.password");
            }
            
            if (!user.getPasswordconfirm().equals(user.getPassword())) {
                errors.rejectValue("passwordconfirm", "Diff.user.passwordConfirm");
            }

        }
        

        
        if(user.getAction().equals(Action.UPDATE.name()))
        {
        	if(user.getPasswordchg()!=null && !user.getPasswordchg().isEmpty())
        	{
                if (user.getPasswordchg().length() < 6) {
                    errors.rejectValue("passwordchg", "Size.user.passwordchg");
                }
                
                if (!user.getPasswordconfirm().equals(user.getPasswordchg())) {
                    errors.rejectValue("passwordconfirm", "Diff.user.passwordConfirm");
                }
        	}

        }


        

      
		
	}

}
