package portal.service;

import java.util.List;

import portal.entity.AppInstance;
import portal.entity.Application;
import portal.entity.Company;
import portal.models.CompanyModel;




public interface CompanyService {
	public Company addCompany(Company company);
    void delete(Company company);
    List<Company> getAll();
    List<CompanyModel> getAllCompany();
    Company getByName(String companyName);
    Company getById(Long id);
    Company updateCompany(Company company);
    
    void updateAppIstanceCompany(List<AppInstance> appInstances,Company company);
    void removeAllCompany(Company company);
    List<Company> findAllVendor();
    
    List<Company> findApplicationByNotAssigned(Application application);
    void removeApplication(Application application);
    void updateApplication(Application application,Long id);
    
    Company findByApplication(Application application);    
    
}
