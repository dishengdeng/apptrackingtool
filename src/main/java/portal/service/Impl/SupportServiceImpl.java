package portal.service.Impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import portal.entity.AppInstance;
import portal.entity.Application;
import portal.entity.Support;
import portal.models.SupportModel;
import portal.repository.AppRepository;
import portal.repository.SupportRepository;
import portal.service.SupportService;

@Service
public class SupportServiceImpl implements SupportService{

	@Autowired
	private SupportRepository supportRepository;
	
	@Autowired
	private AppRepository appRepository;	
	@Override
	public Support addSupport(Support support) {

		return supportRepository.save(support);
	}

	@Override
	public void delete(Support support) {

		supportRepository.delete(support);
	}

	@Override
	public List<Support> getAll() {

		return supportRepository.findAll();
	}

	@Override
	public List<SupportModel> getAllSupport() {
		//To be implementing if doing Restful APIs
		return null;
	}

	@Override
	public Support getByName(String supportName) {

		return supportRepository.findByName(supportName);
	}

	@Override
	public Support getById(Long id) {

		return supportRepository.findOne(id);
	}

	@Override
	public Support updateSupport(Support support) {

		return supportRepository.saveAndFlush(support);
	}

	@Override
	public void updateAppInstanceSupport(List<AppInstance> appInstances, Support support) {
		
		supportRepository.removeAllSupport(support);	
		if(appInstances.size()>0)
		{
			for(AppInstance appInstance:appInstances)
			{
				supportRepository.updateAppIstanceSupport(appInstance.getId(), support);
			}
		}

		
	}

	@Override
	public void updateApplicationSupport(List<Application> applications, Support support) {
		if(applications.size()>0)
		{
			applications.forEach(app->{
				app.setSupport(support);
				appRepository.saveAndFlush(app);
			});
		}
		
	}

}
