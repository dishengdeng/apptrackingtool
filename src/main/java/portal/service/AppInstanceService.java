package portal.service;

import java.util.List;

import portal.entity.AppInstance;
import portal.entity.Application;

import portal.entity.Department;
import portal.entity.Support;
import portal.models.AppInstanceModel;

public interface AppInstanceService {
	public AppInstance addAppInstance(AppInstance appInstance);
    void delete(AppInstance appInstance);
    List<AppInstance> getAll();
    List<AppInstanceModel> getAllAppInstance();
    AppInstance getByName(String appInstanceName);
    AppInstance getById(Long id);
    AppInstance updateAppInstance(AppInstance appInstance);	
    
    void removeDeparmentbyInstanceId(Long id);
    
    void removeDeparment(Department department);
    
    void removeSupportbyInstanceId(Long id);
    
    void removeSupport(Support support);
    
    List<AppInstance> findNotAssgined(Application application);
    
    void removeApplication(Application application);
    void removFiles(String upload_foler,AppInstance appInstance);
    List<AppInstance> getUnassginedAppInstances();
}
