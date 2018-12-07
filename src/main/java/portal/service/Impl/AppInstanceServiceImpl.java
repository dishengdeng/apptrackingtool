package portal.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import portal.entity.AppInstance;
import portal.entity.Application;
import portal.entity.Department;
import portal.entity.Support;
import portal.models.AppInstanceModel;
import portal.repository.AppInstanceRepository;
import portal.service.AppInstanceService;
import portal.utility.Status;

@Service
public class AppInstanceServiceImpl implements AppInstanceService{

	@Autowired
	private AppInstanceRepository appInstanceRepository;
	
	@Override
	public AppInstance addAppInstance(AppInstance appInstance) {

		return appInstanceRepository.save(appInstance);
	}

	@Override
	public void delete(AppInstance appInstance) {

		appInstanceRepository.delete(appInstance);
		
	}

	@Override
	public List<AppInstance> getAll() {

		return appInstanceRepository.findAll();
	}

	@Override
	public List<AppInstanceModel> getAllAppInstance() {
		//To Be implementing if doing Restful APIs
		return null;
	}

	@Override
	public AppInstance getByName(String appInstanceName) {
		
		return appInstanceRepository.findByName(appInstanceName);
	}

	@Override
	public AppInstance getById(Long id) {

		return appInstanceRepository.findOne(id);
	}

	@Override
	public AppInstance updateAppInstance(AppInstance appInstance) {

		return appInstanceRepository.saveAndFlush(appInstance);
	}

	@Override
	public void removeDeparmentbyInstanceId(Long id) {
		appInstanceRepository.removeDeparmentbyInstanceId(id);
		
	}

	@Override
	public void removeDeparment(Department department) {
		appInstanceRepository.removeDeparment(department);
		
	}

	@Override
	public void removeSupportbyInstanceId(Long id) {
		appInstanceRepository.removeSupportbyInstanceId(id);
		
	}

	@Override
	public void removeSupport(Support support) {
		appInstanceRepository.removeSupport(support);
		
	}

	@Override
	public List<AppInstance> findNotAssgined(Application application) {
		List<AppInstance> appInstances = appInstanceRepository.findAll();
		appInstances.removeIf(obj->obj.getAppStatus()==Status.Inactive || obj.getApplication()!=null);
		return appInstances;
	}

	@Override
	public void removeApplication(Application application) {
		appInstanceRepository.removeApplication(application);
		
	}

}
