package portal.service;

import java.util.List;

import portal.entity.AppInstance;
import portal.entity.Desktop;

import portal.models.DesktopModel;



public interface DesktopService {
	public Desktop addDesktop(Desktop desktop);
    void delete(Desktop desktop);
    List<Desktop> getAll();
    List<DesktopModel> getAllDesktop();
    Desktop getByName(String desktopName);
    Desktop getById(Long id);
    Desktop updateDesktop(Desktop desktop);
    
    List<Desktop> findByNotAssigned(AppInstance appInstance);
    void removeAppInstance(AppInstance appInstance);
    void updateAppInstance(AppInstance appInstance,Long id);
    
    Desktop findByAppInstance(AppInstance appInstance);
}
