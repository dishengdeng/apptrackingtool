package portal.service;

import java.util.List;



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
    

    List<Company> findAllVendor();
    List<Company> findAllManufacturer();

    

    
}
