package portal.service.Impl;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import portal.entity.AppInstance;
import portal.entity.Application;
import portal.utility.Action;
import portal.utility.Status;
@Component
public class AppValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(Object o, Errors errors) {

	}
	
	
	public void validateStatus(Object o, Errors errors,Action action) {
		Application app = (Application) o;
		if(action==Action.UPDATE)
		{
			
			if(app.getAppStatus()==Status.Inactive && app.getAppInstances().size()>0)
			{
				boolean rejected=false;
				for(AppInstance obj:app.getAppInstances())
				{
					if(obj.getAppStatus()==Status.Active && !rejected)
					{
						errors.rejectValue("status", "app.status.change");
						rejected=true;
					}
				}

			
			}
		}
		else if(action==Action.DELETE)			
		{	
			if(app.getAppInstances().size()>0)
			{
				for(AppInstance obj:app.getAppInstances()){
					if(obj.getAppStatus()==Status.Active)
					{
						errors.rejectValue("deletion", "app.deletion");
					}
				};
			}
			
		}
	}

}
