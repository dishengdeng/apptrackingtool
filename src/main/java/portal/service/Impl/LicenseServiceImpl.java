package portal.service.Impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import portal.entity.AppInstance;
import portal.entity.License;
import portal.models.LicenseModel;
import portal.repository.LicenseRepository;
import portal.service.LicenseService;

@Service
public class LicenseServiceImpl implements LicenseService{

	@Autowired
	private LicenseRepository licenseRepository;
	
	@Override
	public License addLicense(License license) {

		return licenseRepository.save(license);
	}

	@Override
	public void delete(License license) {

		licenseRepository.delete(license);
	}

	@Override
	public List<License> getAll() {

		return licenseRepository.findAll();
	}

	@Override
	public List<LicenseModel> getAllLicense() {
		//To be implementing if doing restful APIs
		return null;
	}

	@Override
	public License getByNumber(String licenseNumber) {

		return licenseRepository.findByNumber(licenseNumber);
	}

	@Override
	public License getById(Long id) {

		return licenseRepository.findOne(id);
	}

	@Override
	public License updateLicense(License license) {

		return licenseRepository.saveAndFlush(license);
	}

	@Override
	public List<License> findByNotAssigned(AppInstance appInstance) {
		
		List<License> licenses = licenseRepository.findAll();
		licenses.removeIf(obj->appInstance.equals(obj.getAppInstance()) || obj.getAppInstance()!=null);
	
		
		return licenses;
	}

	@Override
	public void removeAppInstance(AppInstance appInstance) {
		licenseRepository.removeAppInstance(appInstance);
		
	}

	@Override
	public void updateAppInstance(AppInstance appInstance, Long id) {
		licenseRepository.updateAppInstance(appInstance, id);
		
	}

	@Override
	public License findByAppInstance(AppInstance appInstance) {
		
		return licenseRepository.findByAppInstance(appInstance);
	}

}
