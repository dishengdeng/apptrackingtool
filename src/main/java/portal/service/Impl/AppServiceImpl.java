package portal.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import portal.entity.Application;
import portal.entity.File;
import portal.models.App;
import portal.repository.AppRepository;
import portal.service.AppService;
import portal.service.FileService;
@Service
public class AppServiceImpl implements AppService{
    @Autowired
    private AppRepository appRepository;
    
	@Autowired
	private FileService fileService;
	
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

	@Override
	public Application findbyId(Long id) {

		return appRepository.findOne(id);
	}



	@Override
	public void removFiles(String upload_foler, Application application) {
		if(application.getFiles().size()>0)
		{
			for(File file:application.getFiles())
			{
				fileService.removeFile(upload_foler,application.getId().toString(), file);
			}			
		}
		
	}

	@Override
	public void saveDetails(Application application) {
		
		appRepository.saveDetails(application.getAppName(), application.getStatus(), 
									application.getAppVersion(), application.getAppType(), 
									application.getAppAliase(), application.getAppPrerequisite(), 
									application.getAppPurpose(), 
								ObjectUtils.isEmpty(application.getAppDecomminsionDate())? null:application.getAppDecomminsionDate(), 
										application.getAppComments(),
										application.getNotes(),
										application.getAppGovernance(), 
										application.getAppSupportByCapSys(),application.getId());
	}



}
