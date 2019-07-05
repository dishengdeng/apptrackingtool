package portal.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import portal.entity.AppInstance;
import portal.entity.Application;
import portal.service.AppService;
import portal.utility.Action;
import portal.utility.Status;
@Component
public class AppValidator implements Validator{

	@Autowired
	private AppService appService;
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
		
		Application appEntity=appService.findbyId(app.getId());
		if(action==Action.UPDATE)
		{
			
			if(app.getAppStatus()==Status.Inactive && appEntity.getAppInstances().size()>0)
			{
				boolean rejected=false;
				for(AppInstance obj:appEntity.getAppInstances())
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
			if(appEntity.getAppInstances().size()>0)
			{
				for(AppInstance obj:appEntity.getAppInstances()){
					if(obj.getAppStatus()==Status.Active)
					{
						errors.rejectValue("id", "app.deletion");
					}
				};
			}
			
		}
	}

}
