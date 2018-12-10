package portal.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import portal.entity.AppInstance;
import portal.entity.Application;
import portal.entity.Company;
import portal.models.CompanyModel;
import portal.repository.CompanyRepository;
import portal.service.CompanyService;
@Service
public class CompanyServiceImpl implements CompanyService{

	@Autowired
	private CompanyRepository companyRepository;
	
	@Override
	public Company addCompany(Company company) {

		return companyRepository.save(company);
	}

	@Override
	public void delete(Company company) {
		companyRepository.delete(company);
		
	}

	@Override
	public List<Company> getAll() {

		return companyRepository.findAll();
	}

	@Override
	public List<CompanyModel> getAllCompany() {
		
		List<CompanyModel> companyModels = new ArrayList<CompanyModel>();
		List<Company> companys = companyRepository.findAll();
		
		for(Company company:companys)
		{
			CompanyModel companyModel = new CompanyModel();
			companyModel.setId(company.getId());
			companyModel.setCompanyName(company.getCompanyName());
			companyModel.setAddress(company.getAddress());
			companyModel.setContactName(company.getContactName());
			companyModel.setContactPhone(company.getContactPhone());
			companyModel.setEmail(company.getEmail());
			companyModel.setPhone(company.getPhone());
			companyModel.setManufacturer(company.getManufacturer());
			companyModels.add(companyModel);
		}
		
		return companyModels;
	}

	@Override
	public Company getByName(String companyName) {
		
		return companyRepository.findByName(companyName);
	}

	@Override
	public Company getById(Long id) {

		return companyRepository.findOne(id);
	}

	@Override
	public Company updateCompany(Company company) {

		return companyRepository.saveAndFlush(company);
	}

	@Override
	public List<Company> findByNotAssigned(AppInstance appInstance) {
		List<Company> companys = companyRepository.findAll();
		companys.removeIf(obj->(obj != null && !obj.isVendor()) || (appInstance.equals(obj.getAppInstance()) || obj.getAppInstance()!=null));
	
		
		return companys;
	}

	@Override
	public void removeAppInstance(AppInstance appInstance) {
		companyRepository.removeAppInstance(appInstance);
		
	}

	@Override
	public void updateAppInstance(AppInstance appInstance, Long id) {
		companyRepository.updateAppInstance(appInstance, id);
		
	}

	@Override
	public Company findByAppInstance(AppInstance appInstance) {

		return companyRepository.findByAppInstance(appInstance);
	}

	@Override
	public List<Company> findApplicationByNotAssigned(Application application) {
		List<Company> companys = companyRepository.findAll();
		companys.removeIf(obj->(obj != null && obj.isVendor()) || (application.equals(obj.getApplication()) || obj.getApplication()!=null));
	
		
		return companys;
	}

	@Override
	public void removeApplication(Application application) {
		companyRepository.removeApplication(application);
		
	}

	@Override
	public void updateApplication(Application application, Long id) {
		companyRepository.updateApplication(application, id);
		
	}

	@Override
	public Company findByApplication(Application application) {

		return companyRepository.findByApplication(application);
	}

}
