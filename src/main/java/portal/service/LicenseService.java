package portal.service;

import java.util.List;


import portal.entity.License;
import portal.models.LicenseModel;




public interface LicenseService {
	public License addLicense(License license);
    void delete(License license);
    List<License> getAll();
    List<LicenseModel> getAllLicense();

    License getById(Long id);
    License updateLicense(License license);	

}
