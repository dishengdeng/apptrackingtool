package portal.service.Impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.util.Set;

import org.json.JSONArray;
import portal.utility.JSONObjectWithEmpty;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;



import portal.entity.Answer;
import portal.entity.AppInstance;
import portal.entity.Application;
import portal.entity.Company;
import portal.entity.Contract;
import portal.entity.Department;
import portal.entity.License;
import portal.entity.Project;
import portal.entity.Server;
import portal.entity.Site;
import portal.entity.Stakeholder;
import portal.entity.Support;
import portal.entity.Zac;
import portal.entity.Zacmap;
import portal.entity.Zone;
import portal.service.JsonWriter;

@Service
public class JsonWriterImpl implements JsonWriter{



	@Override
	public ByteArrayOutputStream writeJsonWithNoNull(JSONObjectWithEmpty reportModel) throws Exception{

    	ByteArrayOutputStream output=new ByteArrayOutputStream();
    	output.write(reportModel.toString().getBytes());
    	//getMapper().writeValue(output, reportModel.toString());
    	
    	return output;
		
	}
	
//	private class NullSerializer extends JsonSerializer<Object>
//	{
//	   public void serialize(Object value, JsonGenerator jgen,	SerializerProvider provider) throws IOException, JsonProcessingException
//	   {
//
//	       jgen.writeString("");
//	   }
//	}
//	
//	private ObjectMapper getMapper()
//	{
//		DefaultSerializerProvider.Impl sp = new DefaultSerializerProvider.Impl();
//		sp.setNullValueSerializer(new NullSerializer());
//    	ObjectMapper mapper=new ObjectMapper();
//    	mapper.setSerializerProvider(sp);
//    	return mapper;
//	}

	@Override
	public ByteArrayInputStream writeJsonWithNoNullIn(JSONObjectWithEmpty reportModel) throws Exception {
		ByteArrayInputStream input= new ByteArrayInputStream(reportModel.toString().getBytes());
		return input;
	}

	@Override
	public JSONArray getStakeholders(Set<Stakeholder> stakeholders) throws Exception{
		JSONArray stakeholderArray=new JSONArray();
		for(Stakeholder stakeholder:stakeholders)
		{
			boolean isNoProperty=true;
			
			JSONObjectWithEmpty stakeholderObj= new JSONObjectWithEmpty();
			stakeholderObj.put("id",stakeholder.getId());
			stakeholderObj.put("stakeholderName",stakeholder.getStakeholderName());
			stakeholderObj.put("note", stakeholder.getNote());
			stakeholderObj.put("firstname", stakeholder.getFirstname());
			stakeholderObj.put("lastname", stakeholder.getLastname());
			stakeholderObj.put("address", stakeholder.getAddress());
			stakeholderObj.put("phone", stakeholder.getPhone());
			stakeholderObj.put("position", stakeholder.getPosition());
			stakeholderObj.put("email", stakeholder.getEmail());
			
			isNoProperty=ObjectUtils.isEmpty(stakeholder.getDepartment());			
			JSONObjectWithEmpty departObj= new JSONObjectWithEmpty();
			departObj.put("departmentName", isNoProperty ? "":stakeholder.getDepartment().getDepartmentName());
			departObj.put("description", isNoProperty ? "":stakeholder.getDepartment().getDescription());
			stakeholderObj.put("department", departObj);
			
			stakeholderObj.put("influence", stakeholder.getInfluence());
			
			isNoProperty=ObjectUtils.isEmpty(stakeholder.getRole());
			JSONObjectWithEmpty roleObj= new JSONObjectWithEmpty();
			roleObj.put("slaroleName", isNoProperty ? "":stakeholder.getRole().getSLARoleName());
			roleObj.put("description", isNoProperty ? "":stakeholder.getRole().getDescription());
			
			stakeholderObj.put("role", roleObj);
			stakeholderObj.put("interest", stakeholder.getInterest());
			stakeholderObj.put("raciforsyschanges", stakeholder.getRaciforsyschanges());
			
			stakeholderArray.put(stakeholderObj);
			
		}
		
		return stakeholderArray;
	}

	@Override
	public JSONArray getApplications(Set<Application> applications) throws Exception {
		JSONArray appArray=new JSONArray();
		for(Application application:applications)
		{

			
			JSONObjectWithEmpty appObj= new JSONObjectWithEmpty();
			appObj.put("id",application.getId());
			appObj.put("ApplicationName",application.getAppName());
			appObj.put("status", application.getStatus());
			appObj.put("Version", application.getAppVersion());
			appObj.put("Type", application.getAppType());
			appObj.put("Aliase", application.getAppAliase());
			appObj.put("Prerequisite", application.getAppPrerequisite());
			appObj.put("Purpose", application.getAppPurpose());
			appObj.put("DecomminsionDate", application.getAppDecomminsionDate());
			appObj.put("Comment", application.getAppComments());
			appObj.put("Notes", application.getNotes());
			appObj.put("Governance", application.getAppGovernance());
			appObj.put("Governance", application.getAppGovernance());
			appObj.put("Governance", application.getAppGovernance());
			appObj.put("Governance", application.getAppGovernance());
			appObj.put("Strategicplan", application.getStrategicplan());
			appObj.put("Operationsplan", application.getOperationsplan());
			appObj.put("Painpoints", application.getPainpoints());
			appObj.put("Businessgoals", application.getBusinessgoals());
			appObj.put("asop", application.getAsop());
			appObj.put("rit", application.getRit());
			appObj.put("roe", application.getRoe());
			
			
			JSONArray projArray=new JSONArray();
			for(Project project:application.getProjects())
			{
				JSONObjectWithEmpty projObj= new JSONObjectWithEmpty();
				projObj.put("id", project.getId());
				projObj.put("ProjectName", project.getProjectname());
				projObj.put("projectcolloquialname", project.getProjectcolloquialname());
				projObj.put("Description", project.getDescription());
				projObj.put("StartDate", project.getStartdate());
				projObj.put("EndDate", project.getEnddate());
				projArray.put(projObj);
			}
			appObj.put("projects",projArray);
			
			JSONArray zoneArray=new JSONArray();
			for(Zone zone:application.getZones())
			{
				JSONObjectWithEmpty zoneObj= new JSONObjectWithEmpty();
				zoneObj.put("id", zone.getId());
				zoneObj.put("ZoneName", zone.getZoneName());

				zoneArray.put(zoneObj);
			}
			appObj.put("zones",zoneArray);
			
			JSONArray siteArray=new JSONArray();
			for(Site site:application.getSites())
			{
				JSONObjectWithEmpty siteObj= new JSONObjectWithEmpty();
				siteObj.put("id", site.getId());
				siteObj.put("SiteName", site.getSiteName());

				siteArray.put(siteObj);
			}
			appObj.put("sites",siteArray);
			
			JSONArray zacmapArray=new JSONArray();
			for(Zacmap zacMap:application.getZacmaps())
			{
				boolean isNoProperty=true;
				JSONObjectWithEmpty zacmapObj= new JSONObjectWithEmpty();
				isNoProperty =ObjectUtils.isEmpty(zacMap.getZac());
				zacmapObj.put("id", zacMap.getId());
				zacmapObj.put("rate", isNoProperty?"":zacMap.getZac().getRate());
				zacmapObj.put("name", isNoProperty?"":zacMap.getZac().getName());
				isNoProperty =ObjectUtils.isEmpty(zacMap.getDepartment());
				zacmapObj.put("Department", isNoProperty?"":zacMap.getDepartment().getDepartmentName());
				zacmapArray.put(zacmapObj);
			}
			appObj.put("zacmap",zacmapArray);				

			JSONArray vendorArray=new JSONArray();
			for(Company vendor:application.getManufacturers())
			{
				JSONObjectWithEmpty vendorObj= new JSONObjectWithEmpty();
				vendorObj.put("id", vendor.getId());
				vendorObj.put("Vendor", vendor.getManufacturer());
				vendorObj.put("CompanyName", vendor.getCompanyName());
				vendorObj.put("Email", vendor.getEmail());
				vendorArray.put(vendorObj);
			}
			appObj.put("vendors",vendorArray);
			
			JSONArray departmentArray=new JSONArray();
			for(Department deparment:application.getDepartments())
			{
				JSONObjectWithEmpty deparmentObj= new JSONObjectWithEmpty();
				deparmentObj.put("id", deparment.getId());
				deparmentObj.put("DepartmentName", deparment.getDepartmentName());

				departmentArray.put(deparmentObj);
			}
			appObj.put("Departments",departmentArray);
			
			JSONArray supportArray=new JSONArray();
			for(Support support:application.getSupports())
			{
				JSONObjectWithEmpty supportsObj= new JSONObjectWithEmpty();
				supportsObj.put("id", support.getId());
				supportsObj.put("Support", support.getSupportName());
				supportsObj.put("SupportType", support.getSupporttype());
				supportsObj.put("SecondarySupport", support.getSecondarysupport());
				supportArray.put(supportsObj);
			}
			appObj.put("supports",supportArray);
			
			JSONArray contractArray=new JSONArray();
			for(Contract contract:application.getContracts())
			{
				JSONObjectWithEmpty contractObj= new JSONObjectWithEmpty();
				contractObj.put("id", contract.getId());
				contractObj.put("ContractName", contract.getContractName());
				contractObj.put("ExpireDate", contract.getExpireDate());
				contractObj.put("Description", contract.getDescription());
				contractArray.put(contractObj);
			}
			appObj.put("contracts",contractArray);
			
			JSONArray licenseArray=new JSONArray();
			for(License license:application.getLicenses())
			{
				JSONObjectWithEmpty licenseObj= new JSONObjectWithEmpty();
				licenseObj.put("id", license.getId());
				licenseObj.put("LicenseNumber", license.getLicenseNumber());
				licenseObj.put("ExpireDate", license.getExpireDate());
				licenseObj.put("RegistrationDate", license.getRegistrationDate());
				licenseArray.put(licenseObj);
			}
			appObj.put("licenses",licenseArray);
			
			JSONArray instanceArray=new JSONArray();
			for(AppInstance instance:application.getAppInstances())
			{
				JSONObjectWithEmpty instanceObj= new JSONObjectWithEmpty();
				instanceObj.put("id", instance.getId());
				instanceObj.put("AppInstance", instance.getAppInstanceName());
				instanceObj.put("Status", instance.getStatus());
				instanceObj.put("Description", instance.getDescription());
				instanceArray.put(instanceObj);
			}
			appObj.put("AppInstances",instanceArray);			
			
			appArray.put(appObj);
			
		}
		
		return appArray;
	}

	@Override
	public JSONArray getInstances(Set<AppInstance> appInstances) throws Exception {
		JSONArray instanceArray=new JSONArray();
		for(AppInstance instance:appInstances)
		{
			JSONObjectWithEmpty instanceObj= new JSONObjectWithEmpty();
			instanceObj.put("id", instance.getId());
			instanceObj.put("Instance", instance.getAppInstanceName());
			instanceObj.put("Status", instance.getStatus());
			instanceObj.put("Version", instance.getVersion());
			instanceObj.put("Description", instance.getDescription());
			instanceObj.put("Notes", instance.getNotes());
			instanceObj.put("Userbase", instance.getUserbase());
			boolean isNoProperty=true;
			
			isNoProperty=ObjectUtils.isEmpty(instance.getDepartment());
			JSONObjectWithEmpty departmentObj= new JSONObjectWithEmpty();
			departmentObj.put("id", isNoProperty?"":instance.getDepartment().getId());
			departmentObj.put("departmentName", isNoProperty?"":instance.getDepartment().getDepartmentName());
			departmentObj.put("Description", isNoProperty?"":instance.getDepartment().getDescription());
			instanceObj.put("Department", departmentObj);
			
			JSONArray supportArray=new JSONArray();
			for(Support support:instance.getSupports())
			{
				JSONObjectWithEmpty supportsObj= new JSONObjectWithEmpty();
				supportsObj.put("id", support.getId());
				supportsObj.put("Support", support.getSupportName());
				supportsObj.put("SupportType", support.getSupporttype());
				supportsObj.put("SecondarySupport", support.getSecondarysupport());
				supportArray.put(supportsObj);
			}
			instanceObj.put("supports",supportArray);
			
			JSONArray siteArray=new JSONArray();
			for(Site site:instance.getSites())
			{
				JSONObjectWithEmpty siteObj= new JSONObjectWithEmpty();
				siteObj.put("id", site.getId());
				siteObj.put("SiteName", site.getSiteName());

				siteArray.put(siteObj);
			}
			instanceObj.put("sites",siteArray);
			
			JSONArray zoneArray=new JSONArray();
			for(Zone zone:instance.getZones())
			{
				JSONObjectWithEmpty zoneObj= new JSONObjectWithEmpty();
				zoneObj.put("id", zone.getId());
				zoneObj.put("ZoneName", zone.getZoneName());

				zoneArray.put(zoneObj);
			}
			instanceObj.put("zones",zoneArray);			

			JSONArray projArray=new JSONArray();
			for(Project project:instance.getProjects())
			{
				JSONObjectWithEmpty projObj= new JSONObjectWithEmpty();
				projObj.put("id", project.getId());
				projObj.put("ProjectName", project.getProjectname());
				projObj.put("projectcolloquialname", project.getProjectcolloquialname());
				projObj.put("Description", project.getDescription());
				projObj.put("StartDate", project.getStartdate());
				projObj.put("EndDate", project.getEnddate());
				projArray.put(projObj);
			}
			instanceObj.put("projects",projArray);
			
			isNoProperty=ObjectUtils.isEmpty(instance.getApplication());
			JSONObjectWithEmpty appObj= new JSONObjectWithEmpty();
			appObj.put("id", isNoProperty?"":instance.getApplication().getId());
			appObj.put("Application", isNoProperty?"":instance.getApplication().getAppName());
			appObj.put("Status", isNoProperty?"":instance.getApplication().getStatus());
			appObj.put("Version", isNoProperty?"":instance.getApplication().getAppVersion());
			instanceObj.put("Application", appObj);
			
			JSONArray contractArray=new JSONArray();
			for(Contract contract:instance.getContracts())
			{
				JSONObjectWithEmpty contractObj= new JSONObjectWithEmpty();
				contractObj.put("id", contract.getId());
				contractObj.put("ContractName", contract.getContractName());
				contractObj.put("ExpireDate", contract.getExpireDate());
				contractObj.put("Description", contract.getDescription());
				contractArray.put(contractObj);
			}
			instanceObj.put("contracts",contractArray);
			
			JSONArray vendorArray=new JSONArray();
			for(Company vendor:instance.getCompanys())
			{
				JSONObjectWithEmpty vendorObj= new JSONObjectWithEmpty();
				vendorObj.put("id", vendor.getId());
				vendorObj.put("Vendor", vendor.getManufacturer());
				vendorObj.put("CompanyName", vendor.getCompanyName());
				vendorObj.put("Email", vendor.getEmail());
				vendorArray.put(vendorObj);
			}
			instanceObj.put("vendors",vendorArray);
			
			isNoProperty=ObjectUtils.isEmpty(instance.getSla());
			JSONObjectWithEmpty slaObj= new JSONObjectWithEmpty();
			slaObj.put("id", isNoProperty?"":instance.getSla().getId());
			slaObj.put("SLA", isNoProperty?"":instance.getSla().getSlaName());
			slaObj.put("Description", isNoProperty?"":instance.getSla().getDescription());
			slaObj.put("EffectiveDate", isNoProperty?"":instance.getSla().getEffectivedate());
			slaObj.put("TerminationDate", isNoProperty?"":instance.getSla().getTerminationdate());
			instanceObj.put("SLA", slaObj);
			
			JSONArray serverArray=new JSONArray();
			for(Server server:instance.getServers())
			{
				JSONObjectWithEmpty serverObj= new JSONObjectWithEmpty();
				serverObj.put("id", server.getId());
				serverObj.put("Description", server.getDescription());
				serverObj.put("ServerName", server.getServerName());
				serverObj.put("Address", server.getAddress());
				serverArray.put(serverObj);
			}
			instanceObj.put("servers",serverArray);
			
			JSONArray licenseArray=new JSONArray();
			for(License license:instance.getLicenses())
			{
				JSONObjectWithEmpty licenseObj= new JSONObjectWithEmpty();
				licenseObj.put("id", license.getId());
				licenseObj.put("LicenseNumber", license.getLicenseNumber());
				licenseObj.put("ExpireDate", license.getExpireDate());
				licenseObj.put("RegistrationDate", license.getRegistrationDate());
				licenseArray.put(licenseObj);
			}
			instanceObj.put("licenses",licenseArray);	
			
			isNoProperty=ObjectUtils.isEmpty(instance.getDesktop());
			JSONObjectWithEmpty desktopObj= new JSONObjectWithEmpty();
			desktopObj.put("id", isNoProperty?"":instance.getDesktop().getId());
			desktopObj.put("Desktop", isNoProperty?"":instance.getDesktop().getDesktopName());
			desktopObj.put("Description", isNoProperty?"":instance.getDesktop().getDescription());
			desktopObj.put("DecomminsionDate", isNoProperty?"":instance.getDesktop().getDecomminsionDate());
			instanceObj.put("Desktop", desktopObj);
			
			instanceArray.put(instanceObj);
		}
		return instanceArray;
	}
	
	

	@Override
	public JSONArray getDepartment(Set<Department> departments) throws Exception {
		JSONArray departmentArray=new JSONArray();
		for(Department department:departments)
		{
			JSONObjectWithEmpty departmentObj= new JSONObjectWithEmpty();
			departmentObj.put("id", department.getId());
			departmentObj.put("Department", department.getDepartmentName());
			departmentObj.put("Description", department.getDescription());
			departmentObj.put("Purpose", department.getPurpose());
			departmentObj.put("Stragicplan", department.getStragicplan());
			departmentObj.put("RoadMap", department.getRoadMap());
			departmentObj.put("Goal", department.getGoal());
			departmentObj.put("Painpoint", department.getPainpoint());
			boolean isNoProperty=true;
			
			JSONArray answersArray=new JSONArray();
			for(Answer answer:department.getAnswers())
			{
				JSONObjectWithEmpty answerObj= new JSONObjectWithEmpty();
				answerObj.put("id", answer.getId());
				answerObj.put("answer", answer.getText());
				isNoProperty=ObjectUtils.isEmpty(answer.getQuestion());
				answerObj.put("Question", isNoProperty?"":answer.getQuestion().getQuestionName());
				answersArray.put(answerObj);
			}
			departmentObj.put("ProcessExcellent",answersArray);
			
			JSONArray stakeholderArray=new JSONArray();
			for(Stakeholder stakeholder:department.getStakeholders())
			{
				JSONObjectWithEmpty stakeholderObj= new JSONObjectWithEmpty();
				stakeholderObj.put("id", stakeholder.getId());
				stakeholderObj.put("Stakeholder", stakeholder.getStakeholderName());
				stakeholderArray.put(stakeholderObj);
			}
			departmentObj.put("stakeholders",stakeholderArray);
			
			JSONArray applicationArray=new JSONArray();
			for(Application application:department.getApplications())
			{
				JSONObjectWithEmpty applicationObj= new JSONObjectWithEmpty();
				applicationObj.put("id", application.getId());
				applicationObj.put("Application", application.getAppName());
				applicationArray.put(applicationObj);
			}
			departmentObj.put("applications",applicationArray);
			
			JSONArray instanceArray=new JSONArray();
			for(AppInstance instance:department.getAppInstances())
			{
				JSONObjectWithEmpty instanceObj= new JSONObjectWithEmpty();
				instanceObj.put("id", instance.getId());
				instanceObj.put("AppInstance", instance.getAppInstanceName());
				instanceObj.put("Status", instance.getStatus());
				instanceObj.put("Description", instance.getDescription());
				instanceArray.put(instanceObj);
			}
			departmentObj.put("AppInstances",instanceArray);
			
			JSONArray zacmapArray=new JSONArray();
			for(Zacmap zacMap:department.getZacmaps())
			{

				JSONObjectWithEmpty zacmapObj= new JSONObjectWithEmpty();
				isNoProperty =ObjectUtils.isEmpty(zacMap.getZac());
				zacmapObj.put("id", zacMap.getId());
				zacmapObj.put("rate", isNoProperty?"":zacMap.getZac().getRate());
				zacmapObj.put("name", isNoProperty?"":zacMap.getZac().getName());
				isNoProperty =ObjectUtils.isEmpty(zacMap.getApplication());
				zacmapObj.put("Application", isNoProperty?"":zacMap.getApplication().getAppName());
				zacmapArray.put(zacmapObj);
			}
			departmentObj.put("zacmap",zacmapArray);			
			
			departmentArray.put(departmentObj);
		}
		return departmentArray;
	}

	
	@Override
	public JSONArray getZones(Set<Zone> zones) throws Exception {
		JSONArray zoneArray=new JSONArray();
		for(Zone zone:zones)
		{
			JSONObjectWithEmpty zoneObj= new JSONObjectWithEmpty();
			zoneObj.put("id", zone.getId());
			zoneObj.put("Zone", zone.getZoneName());
			zoneObj.put("Description", zone.getDescription());
			zoneObj.put("Note", zone.getNote());
			
			JSONArray siteArray=new JSONArray();
			for(Site site:zone.getSites())
			{
				JSONObjectWithEmpty siteObj= new JSONObjectWithEmpty();
				siteObj.put("id", site.getId());
				siteObj.put("SiteName", site.getSiteName());

				siteArray.put(siteObj);
			}
			zoneObj.put("sites",siteArray);
			
			JSONArray applicationArray=new JSONArray();
			for(Application application:zone.getApplications())
			{
				JSONObjectWithEmpty applicationObj= new JSONObjectWithEmpty();
				applicationObj.put("id", application.getId());
				applicationObj.put("Application", application.getAppName());
				applicationArray.put(applicationObj);
			}
			zoneObj.put("applications",applicationArray);
			
			JSONArray instanceArray=new JSONArray();
			for(AppInstance instance:zone.getAppInstances())
			{
				JSONObjectWithEmpty instanceObj= new JSONObjectWithEmpty();
				instanceObj.put("id", instance.getId());
				instanceObj.put("AppInstance", instance.getAppInstanceName());
				instanceObj.put("Status", instance.getStatus());
				instanceObj.put("Description", instance.getDescription());
				instanceArray.put(instanceObj);
			}
			zoneObj.put("AppInstances",instanceArray);			
			
			zoneArray.put(zoneObj);
		}
		return zoneArray;
	}

	@Override
	public JSONArray getSites(Set<Site> sites) throws Exception {
		JSONArray siteArray=new JSONArray();
		for(Site site:sites)
		{
			JSONObjectWithEmpty siteObj= new JSONObjectWithEmpty();
			siteObj.put("id", site.getId());
			siteObj.put("Site", site.getSiteName());
			siteObj.put("Description", site.getDescription());
			siteObj.put("Note", site.getNote());
			boolean isNoProperty=true;
			
			isNoProperty=ObjectUtils.isEmpty(site.getZone());
			siteObj.put("Zone", isNoProperty?"":site.getZone().getZoneName());
			
			JSONArray applicationArray=new JSONArray();
			for(Application application:site.getApplications())
			{
				JSONObjectWithEmpty applicationObj= new JSONObjectWithEmpty();
				applicationObj.put("id", application.getId());
				applicationObj.put("Application", application.getAppName());
				applicationArray.put(applicationObj);
			}
			siteObj.put("applications",applicationArray);
			
			JSONArray instanceArray=new JSONArray();
			for(AppInstance instance:site.getAppInstances())
			{
				JSONObjectWithEmpty instanceObj= new JSONObjectWithEmpty();
				instanceObj.put("id", instance.getId());
				instanceObj.put("AppInstance", instance.getAppInstanceName());
				instanceObj.put("Status", instance.getStatus());
				instanceObj.put("Description", instance.getDescription());
				instanceArray.put(instanceObj);
			}
			siteObj.put("AppInstances",instanceArray);				
			
			siteArray.put(siteObj);
		}
		return siteArray;
	}

	@Override
	public JSONArray getVendors(Set<Company> vendors) throws Exception {
		JSONArray vendorArray=new JSONArray();
		for(Company vendor:vendors)
		{
			JSONObjectWithEmpty vendorObj= new JSONObjectWithEmpty();
			vendorObj.put("id", vendor.getId());
			vendorObj.put("Vendor", vendor.getCompanyName());
			vendorObj.put("Address", vendor.getAddress());
			vendorObj.put("Phone", vendor.getPhone());
			vendorObj.put("Email", vendor.getEmail());
			vendorObj.put("ContactName", vendor.getContactName());
			vendorObj.put("ContactPhone", vendor.getContactPhone());
			vendorObj.put("BackupContactName", vendor.getBackupContactName());
			vendorObj.put("BackupContactPhone", vendor.getBackupContactPhone());
			vendorObj.put("Sales", vendor.getSales());
			vendorObj.put("PM", vendor.getPm());
			vendorObj.put("MobilePhone", vendor.getMobilephone());
			vendorObj.put("Manufacturer", vendor.getManufacturer());
			
			JSONArray applicationArray=new JSONArray();
			for(Application application:vendor.getApplications())
			{
				JSONObjectWithEmpty applicationObj= new JSONObjectWithEmpty();
				applicationObj.put("id", application.getId());
				applicationObj.put("Application", application.getAppName());
				applicationArray.put(applicationObj);
			}
			vendorObj.put("applications",applicationArray);
			
			JSONArray instanceArray=new JSONArray();
			for(AppInstance instance:vendor.getAppInstances())
			{
				JSONObjectWithEmpty instanceObj= new JSONObjectWithEmpty();
				instanceObj.put("id", instance.getId());
				instanceObj.put("AppInstance", instance.getAppInstanceName());
				instanceObj.put("Status", instance.getStatus());
				instanceObj.put("Description", instance.getDescription());
				instanceArray.put(instanceObj);
			}
			vendorObj.put("AppInstances",instanceArray);				
			
			vendorArray.put(vendorObj);
		}
		return vendorArray;
	}

	@Override
	public JSONArray getContracts(Set<Contract> contracts) throws Exception {
		JSONArray contractArray=new JSONArray();
		for(Contract contract:contracts)
		{
			JSONObjectWithEmpty contractObj= new JSONObjectWithEmpty();
			contractObj.put("id", contract.getId());
			contractObj.put("Contract", contract.getContractName());
			contractObj.put("Description", contract.getDescription());
			contractObj.put("SMADescription", contract.getSmaDescription());
			contractObj.put("SLADescription", contract.getSlaDescription());
			contractObj.put("ExpireDate", contract.getExpireDate());
			contractObj.put("EffectiveDate", contract.getEffectivedate());
			contractObj.put("ApprovalDate", contract.getApprovaldate());
			contractObj.put("ApproverName", contract.getApprovername());
			contractObj.put("ContractLicensing in Place", contract.getClinplace());
			contractObj.put("Vendor SLA", contract.getVendorsla());
			contractObj.put("AHS-IT SLA", contract.getAhsitsla());
			
			JSONArray applicationArray=new JSONArray();
			for(Application application:contract.getApplications())
			{
				JSONObjectWithEmpty applicationObj= new JSONObjectWithEmpty();
				applicationObj.put("id", application.getId());
				applicationObj.put("Application", application.getAppName());
				applicationArray.put(applicationObj);
			}
			contractObj.put("applications",applicationArray);
			
			JSONArray instanceArray=new JSONArray();
			for(AppInstance instance:contract.getAppInstances())
			{
				JSONObjectWithEmpty instanceObj= new JSONObjectWithEmpty();
				instanceObj.put("id", instance.getId());
				instanceObj.put("AppInstance", instance.getAppInstanceName());
				instanceObj.put("Status", instance.getStatus());
				instanceObj.put("Description", instance.getDescription());
				instanceArray.put(instanceObj);
			}
			contractObj.put("AppInstances",instanceArray);	
			
			contractArray.put(contractObj);
		}
		return contractArray;
	}

	@Override
	public JSONArray getLicense(Set<License> licenses) throws Exception {
		JSONArray licenseArray=new JSONArray();
		for(License license:licenses)
		{
			JSONObjectWithEmpty licenseObj= new JSONObjectWithEmpty();
			licenseObj.put("id", license.getId());
			licenseObj.put("LicenseNumber", license.getLicenseNumber());
			licenseObj.put("RegistrationDate", license.getRegistrationDate());
			licenseObj.put("Note", license.getNote());
			licenseObj.put("Price", license.getPrice());
			licenseObj.put("ExpireDate", license.getExpireDate());
			licenseObj.put("NumberofLicenseRequired", license.getNumberoflicenserequired());
			licenseObj.put("ExpireDate", license.getExpireDate());
			licenseObj.put("RenewalFrequency", license.getRenewalfrequency());
			licenseObj.put("RenewalOwner", license.getRenewalowner());
			licenseObj.put("Warrenty", license.getWarrenty());
			
			JSONArray applicationArray=new JSONArray();
			for(Application application:license.getApplications())
			{
				JSONObjectWithEmpty applicationObj= new JSONObjectWithEmpty();
				applicationObj.put("id", application.getId());
				applicationObj.put("Application", application.getAppName());
				applicationArray.put(applicationObj);
			}
			licenseObj.put("applications",applicationArray);
			
			JSONArray instanceArray=new JSONArray();
			for(AppInstance instance:license.getAppInstances())
			{
				JSONObjectWithEmpty instanceObj= new JSONObjectWithEmpty();
				instanceObj.put("id", instance.getId());
				instanceObj.put("AppInstance", instance.getAppInstanceName());
				instanceObj.put("Status", instance.getStatus());
				instanceObj.put("Description", instance.getDescription());
				instanceArray.put(instanceObj);
			}
			licenseObj.put("AppInstances",instanceArray);
			
			licenseArray.put(licenseObj);
		}
		return licenseArray;
	}

	@Override
	public JSONArray getServers(Set<Server> servers) throws Exception {
		JSONArray serverArray=new JSONArray();
		for(Server server:servers)
		{
			JSONObjectWithEmpty serverObj= new JSONObjectWithEmpty();
			serverObj.put("id", server.getId());
			serverObj.put("ServerName", server.getServerName());
			serverObj.put("Description", server.getDescription());
			serverObj.put("Address", server.getAddress());
			serverObj.put("ServerVersion", server.getServerVersion());
			serverObj.put("ServerVersion", server.getServerVersion());
			serverObj.put("HWplatform", server.getHwplatform());
			serverObj.put("DecomminsionDate", server.getDecomminsionDate());
			boolean isNoProperty=true;
			isNoProperty=ObjectUtils.isEmpty(server.getCluster());
			serverObj.put("Cluster", isNoProperty?"":server.getCluster().getClusterName());
			isNoProperty=ObjectUtils.isEmpty(server.getAppInstance());
			serverObj.put("Cluster", isNoProperty?"":server.getAppInstance().getAppInstanceName());
			serverArray.put(serverObj);
		}
		return serverArray;
	}

	@Override
	public JSONArray getSupports(Set<Support> supports) throws Exception {
		JSONArray supportArray=new JSONArray();
		for(Support support:supports)
		{
			JSONObjectWithEmpty supportObj= new JSONObjectWithEmpty();
			supportObj.put("id", support.getId());
			supportObj.put("SupportName", support.getSupportName());
			supportObj.put("SecondarySupport", support.getSecondarysupport());
			supportObj.put("OtherSupport", support.getOthersupport());
			supportObj.put("AppOwner", support.getAppowner());
			supportObj.put("Trainer", support.getTrainer());
			supportObj.put("SME", support.getSme());
			supportObj.put("BusinessLead", support.getBusinesslead());
			supportObj.put("COES", support.getCoes());
			supportObj.put("AHSHours", support.getAhshours());
			supportObj.put("UserAdmin", support.getUseradmin());
			supportObj.put("SysAdmin", support.getSysadmin());
			supportObj.put("NetworkSupport", support.getNetworksupport());
			supportObj.put("SupportType", support.getSupporttype());
			supportObj.put("CommonIssue", support.getCommonissue());
			supportObj.put("Phone", support.getPhone());
			supportObj.put("Email", support.getEmail());
			supportObj.put("Note", support.getNote());
			supportObj.put("Location", support.getLocation());
			
			JSONArray applicationArray=new JSONArray();
			for(Application application:support.getApplications())
			{
				JSONObjectWithEmpty applicationObj= new JSONObjectWithEmpty();
				applicationObj.put("id", application.getId());
				applicationObj.put("Application", application.getAppName());
				applicationArray.put(applicationObj);
			}
			supportObj.put("applications",applicationArray);
			
			JSONArray instanceArray=new JSONArray();
			for(AppInstance instance:support.getAppInstances())
			{
				JSONObjectWithEmpty instanceObj= new JSONObjectWithEmpty();
				instanceObj.put("id", instance.getId());
				instanceObj.put("AppInstance", instance.getAppInstanceName());
				instanceObj.put("Status", instance.getStatus());
				instanceObj.put("Description", instance.getDescription());
				instanceArray.put(instanceObj);
			}
			supportObj.put("AppInstances",instanceArray);			
			
			supportArray.put(supportObj);
		}
		return supportArray;
	}

	@Override
	public JSONArray getZacs(Set<Zac> zacs) throws Exception {
		JSONArray zacArray=new JSONArray();
		for(Zac zac:zacs)
		{
			JSONObjectWithEmpty zacObj= new JSONObjectWithEmpty();
			zacObj.put("id", zac.getId());
			zacObj.put("rate", zac.getRate());
			zacObj.put("name", zac.getName());
			zacObj.put("description", zac.getDescription());
			boolean isNoProperty=true;
			JSONArray applicationArray=new JSONArray();
			for(Application application:zac.getApplications())
			{
				JSONObjectWithEmpty applicationObj= new JSONObjectWithEmpty();
				applicationObj.put("id", application.getId());
				applicationObj.put("Application", application.getAppName());
				applicationArray.put(applicationObj);
			}
			zacObj.put("applications",applicationArray);
			
			JSONArray zacmapArray=new JSONArray();
			for(Zacmap zacmap:zac.getZacmaps())
			{
				JSONObjectWithEmpty zacmapObj= new JSONObjectWithEmpty();
				zacmapObj.put("id", zacmap.getId());
				isNoProperty=ObjectUtils.isEmpty(zacmap.getApplication());
				zacmapObj.put("Application", isNoProperty?"":zacmap.getApplication().getAppName());
				isNoProperty=ObjectUtils.isEmpty(zacmap.getDepartment());
				zacmapObj.put("Department", isNoProperty?"":zacmap.getDepartment().getDepartmentName());				
				zacmapArray.put(zacmapObj);
			}
			zacObj.put("zacmaps",zacmapArray);			
			
			zacArray.put(zacObj);
		}
		return zacArray;
	}






}
