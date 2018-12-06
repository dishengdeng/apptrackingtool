package portal.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import portal.entity.Application;
import portal.models.App;
import portal.repository.AppRepository;
import portal.service.AppService;
@Service
public class AppServiceImpl implements AppService{
    @Autowired
    private AppRepository appRepository;
	
	@Override
	public Application addApplication(Application application) {
		Application app = appRepository.save(application);
		return app;
	}

	@Override
	public void delete(Application application) {
		appRepository.delete(application);
		
	}

	@Override
	public List<Application> getAll() {

		return appRepository.findAll();
	}

	@Override
	public Application getByName(String AppName) {
		
		return appRepository.findByName(AppName);
	}

	@Override
	public Application updateApp(Application application) {
		
		return appRepository.saveAndFlush(application);
	}

	@Override
	public List<App> getAllApp() {
		List<Application> Apps = appRepository.findAll();
		List<App> appModels= new ArrayList<App>();
		
		for(Application app:Apps)
		{
			App appModel = new App();
			appModel.setId(app.getId());
			appModel.setAppName(app.getAppName());
			appModel.setAppDecomminsionDate(app.getAppDecomminsionDate().toString());
			appModels.add(appModel);
		}
		
		return appModels;
	}

}
