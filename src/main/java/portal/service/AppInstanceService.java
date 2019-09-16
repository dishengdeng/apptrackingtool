package portal.service;

import java.util.List;

import portal.entity.AppInstance;
import portal.entity.Application;


import portal.models.AppInstanceModel;

public interface AppInstanceService {
	public AppInstance addAppInstance(AppInstance appInstance);
    void delete(AppInstance appInstance);
    List<AppInstance> getAll();
    List<AppInstanceModel> getAllAppInstance();
    AppInstance getByName(String appInstanceName);
    AppInstance getById(Long id);
    AppInstance updateAppInstance(AppInstance appInstance);	
    List<AppInstance> findNotAssgined();
    
    void removeApplication(Application application);
    void removFiles(String upload_foler,AppInstance appInstance);
    List<AppInstance> getUnassginedAppInstances();
}
