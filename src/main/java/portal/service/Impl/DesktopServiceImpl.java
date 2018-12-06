package portal.service.Impl;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import portal.entity.AppInstance;
import portal.entity.Desktop;
import portal.models.DesktopModel;
import portal.repository.DesktopRepository;
import portal.service.DesktopService;

@Service
public class DesktopServiceImpl implements DesktopService{

	@Autowired
	private DesktopRepository desktopRepository;
	
	@Override
	public Desktop addDesktop(Desktop desktop) {

		return desktopRepository.save(desktop);
	}

	@Override
	public void delete(Desktop zone) {

		desktopRepository.delete(zone);
	}

	@Override
	public List<Desktop> getAll() {

		return desktopRepository.findAll();
	}

	@Override
	public List<DesktopModel> getAllDesktop() {
		
		List<DesktopModel> desktopModels = new ArrayList<DesktopModel>();
		List<Desktop> desktops = desktopRepository.findAll();
		for(Desktop desktop:desktops)
		{
			DesktopModel desktopModel= new DesktopModel();
			desktopModel.setId(desktop.getId());
			desktopModel.setDesktopName(desktop.getDesktopName());
			desktopModel.setDescription(desktop.getDescription());
			desktopModel.setHwplatform(desktop.getHwplatform());
			desktopModel.setOsVersion(desktop.getOsVersion());
			desktopModels.add(desktopModel);
		}
		return desktopModels;
	}

	@Override
	public Desktop getByName(String desktopName) {

		return desktopRepository.findByName(desktopName);
	}

	@Override
	public Desktop getById(Long id) {

		return desktopRepository.findOne(id);
	}

	@Override
	public Desktop updateDesktop(Desktop desktop) {

		return desktopRepository.saveAndFlush(desktop);
	}

	@Override
	public List<Desktop> findByNotAssigned(AppInstance appInstance) {

		List<Desktop> desktops = desktopRepository.findAll();
		desktops.removeIf(obj->appInstance.equals(obj.getAppInstance()) || obj.getAppInstance()!=null);
	
		
		return desktops;
	}

	@Override
	public void removeAppInstance(AppInstance appInstance) {
		desktopRepository.removeAppInstance(appInstance);
		
	}

	@Override
	public void updateAppInstance(AppInstance appInstance, Long id) {
		desktopRepository.updateAppInstance(appInstance, id);
		
	}

	@Override
	public Desktop findByAppInstance(AppInstance appInstance) {

		return desktopRepository.findByAppInstance(appInstance);
	}

}
