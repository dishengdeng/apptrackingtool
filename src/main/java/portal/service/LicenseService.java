package portal.service;

import java.util.List;

import portal.entity.AppInstance;
import portal.entity.License;
import portal.models.LicenseModel;




public interface LicenseService {
	public License addLicense(License license);
    void delete(License license);
    List<License> getAll();
    List<LicenseModel> getAllLicense();
    License getByNumber(String licenseNumber);
    License getById(Long id);
    License updateLicense(License license);	
    List<License> findByNotAssigned(AppInstance appInstance);
    void removeAppInstance(AppInstance appInstance);
    void updateAppInstance(AppInstance appInstance,Long id);    
    License findByAppInstance(AppInstance appInstance);
}
