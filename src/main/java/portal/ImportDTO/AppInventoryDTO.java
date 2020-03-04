package portal.ImportDTO;




import java.util.concurrent.Callable;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;


import portal.entity.Appdepartment;
import portal.entity.Application;
import portal.entity.Company;
import portal.entity.Department;
import portal.entity.Site;
import portal.repository.AppRepository;
import portal.repository.AppdepartmentRepository;
import portal.repository.CompanyRepository;

import portal.repository.SiteRepository;
import portal.utility.AppinventoryMap;

public class AppInventoryDTO implements Callable<AppInventoryDTO>{
	private static final Logger LOGGER = LoggerFactory.getLogger(AppInventoryDTO.class);
	private final JSONObject data;
	
	private final AppdepartmentRepository appdepartmentRepository;
	
	private final Department department;
	
	private final AppRepository appRepository;
	
	private final SiteRepository siteRepository;
	
	private final CompanyRepository companyRepository;
	

	
	private Appdepartment appdepartment;
	

	
	private final Object obj;
	
	public AppInventoryDTO(final JSONObject _data,
							final Department _department,
							final AppdepartmentRepository _appdepartmentRepository,
							final AppRepository _appRepository,
							final SiteRepository _siteRepository,
							final CompanyRepository _companyRepository,
							final Object _obj
							)
	{
		this.data=_data;
		this.appdepartmentRepository=_appdepartmentRepository;
		this.department=_department;
		this.appRepository=_appRepository;
		this.siteRepository=_siteRepository;
		this.companyRepository=_companyRepository;
		this.obj=_obj;
	}
	
	
	public Appdepartment getAppdepartment() {
		return appdepartment;
	}


	public void setAppdepartment(Appdepartment appdepartment) {
		this.appdepartment = appdepartment;
	}


	@Override
	@Transactional
	public AppInventoryDTO call() throws Exception {

			if(ObjectUtils.isEmpty(appdepartmentRepository.findbyAppNameAndDepartment(department,data.getString(AppinventoryMap.ApplicationName.name()))))
			{
				Appdepartment appdepartment= new Appdepartment();

				//zone
				appdepartment.setSouth(data.getString(AppinventoryMap.South.name()));
				appdepartment.setNorth(data.getString(AppinventoryMap.North.name()));
				appdepartment.setCentral(data.getString(AppinventoryMap.Central.name()));
				appdepartment.setCalgary(data.getString(AppinventoryMap.Calgary.name()));
				appdepartment.setEdmonton(data.getString(AppinventoryMap.Edmonton.name()));
				


				appdepartment.setBusinesslead(data.getString(AppinventoryMap.BusinessLead.name()));
				
				appdepartment.setAppowner(data.getString(AppinventoryMap.ApplicationOwner.name()));
				
				appdepartment.setGoverinplace(data.getString(AppinventoryMap.Goverinplace.name()));
				
				appdepartment.setUserbase(data.getString(AppinventoryMap.Userbase.name()));
		

				//support information
				appdepartment.setSme(data.getString(AppinventoryMap.SubjectMatterExpert.name()));
				appdepartment.setTrainer(data.getString(AppinventoryMap.Trainer.name()));
				appdepartment.setUseradmin(data.getString(AppinventoryMap.UserAdmin.name()));
				appdepartment.setSystemadmin(data.getString(AppinventoryMap.SystemAdmin.name()));
				appdepartment.setServersupport(data.getString(AppinventoryMap.AppServerSupport.name()));
				appdepartment.setDbsupport(data.getString(AppinventoryMap.DBServerSupport.name()));
				appdepartment.setNetworksupport(data.getString(AppinventoryMap.NetworkSupport.name()));
				
				//Contract information
				appdepartment.setContractinplace(data.getString(AppinventoryMap.Contractinplace.name()));
				appdepartment.setContractdetail(data.getString(AppinventoryMap.Contractdetail.name()));
				appdepartment.setExpireDate(data.getString(AppinventoryMap.ExpirationDate.name()));
				appdepartment.setFrequency(data.getString(AppinventoryMap.Frequency.name()));
				appdepartment.setVendorsla(data.getString(AppinventoryMap.VendorSla.name()));
				appdepartment.setAhsitsla(data.getString(AppinventoryMap.AhsItSla.name()));
				
				appdepartment.setBroadmap(data.getString(AppinventoryMap.Broadmap.name()));
				appdepartment.setImp(data.getString(AppinventoryMap.IMP.name()));
				appdepartment.setCshrecimit(data.getString(AppinventoryMap.Cshrecimit.name()));
				appdepartment.setNote(data.getString(AppinventoryMap.Note.name()));
				Appdepartment newEntity=appdepartmentRepository.saveAndFlush(appdepartment);
				
				
				newEntity.setDepartment(department);
				
				//Application
				Application applicationEntity=appRepository.findByName(data.getString(AppinventoryMap.ApplicationName.name()));
				Application application=ObjectUtils.isEmpty(applicationEntity)?new Application(data.getString(AppinventoryMap.ApplicationName.name())):applicationEntity;
				application.setAppType(data.getString(AppinventoryMap.ApplicationType.name()));
				application.setAppPurpose(data.getString(AppinventoryMap.ApplicationPurpose.name()));
				application.setAppVersion(data.getString(AppinventoryMap.ApplicationVersion.name()));
				newEntity.setApplication(appRepository.saveAndFlush(application));
				LOGGER.info("Application is"+application.getAppName());	

					//site
				//put thread into queue, otherwise it will create multiple objects
				synchronized(obj)
				{


					JSONArray sites=data.getJSONArray(AppinventoryMap.Site.name());
					
					if(!ObjectUtils.isEmpty(sites) && sites.length()>0)
					{
						for(Object siteName:sites)
						{
					
							

							Site siteEntity=siteRepository.findByName((String)siteName);
							Site site=ObjectUtils.isEmpty(siteEntity)?siteRepository.saveAndFlush(new Site((String) siteName)):siteEntity;
							appdepartmentRepository.saveSite(newEntity.getId(), site.getId());
							
							
						}				
					}

				
					//vendor information
					JSONArray vendors=data.getJSONArray(AppinventoryMap.Vendor.name());
					if(!ObjectUtils.isEmpty(vendors) && vendors.length()>0)
					{
						for(Object vendorName:vendors)
						{
							Company companyEntity=companyRepository.findByName((String)vendorName);
							Company company=ObjectUtils.isEmpty(companyEntity)?companyRepository.saveAndFlush(new Company((String)vendorName)):companyEntity;
							appdepartmentRepository.saveVendor(newEntity.getId(), company.getId());
						}	
					}

					}		
			
				
				appdepartmentRepository.saveAndFlush(newEntity);



			}
		


		return this;
	

	

	}
	

}
