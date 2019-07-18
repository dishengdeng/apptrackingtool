package portal.service.Impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
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
import portal.entity.Zacmap;
import portal.entity.Zone;
import portal.service.JsonWriter;

@Service
public class JsonWriterImpl implements JsonWriter{



	@Override
	public ByteArrayOutputStream writeJsonWithNoNull(JSONObject reportModel) throws Exception{

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
	
//	private ObjectMapper getMapper()
//	{
//		DefaultSerializerProvider.Impl sp = new DefaultSerializerProvider.Impl();
//		sp.setNullValueSerializer(new NullSerializer());
//    	ObjectMapper mapper=new ObjectMapper();
//    	mapper.setSerializerProvider(sp);
//    	return mapper;
//	}

	@Override
	public ByteArrayInputStream writeJsonWithNoNullIn(JSONObject reportModel) throws Exception {
		ByteArrayInputStream input= new ByteArrayInputStream(reportModel.toString().getBytes());
		return input;
	}

	@Override
	public JSONArray getStakeholders(Set<Stakeholder> stakeholders) throws Exception{
		JSONArray stakeholderArray=new JSONArray();
		for(Stakeholder stakeholder:stakeholders)
		{
			boolean isNoProperty=true;
			
			JSONObject stakeholderObj= new JSONObject();
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
			JSONObject departObj= new JSONObject();
			departObj.put("departmentName", isNoProperty ? "":stakeholder.getDepartment().getDepartmentName());
			departObj.put("description", isNoProperty ? "":stakeholder.getDepartment().getDescription());
			stakeholderObj.put("department", departObj);
			
			stakeholderObj.put("influence", stakeholder.getInfluence());
			
			isNoProperty=ObjectUtils.isEmpty(stakeholder.getRole());
			JSONObject roleObj= new JSONObject();
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

			
			JSONObject appObj= new JSONObject();
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
				JSONObject projObj= new JSONObject();
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
				JSONObject zoneObj= new JSONObject();
				zoneObj.put("id", zone.getId());
				zoneObj.put("ZoneName", zone.getZoneName());

				zoneArray.put(zoneObj);
			}
			appObj.put("zones",zoneArray);
			
			JSONArray siteArray=new JSONArray();
			for(Site site:application.getSites())
			{
				JSONObject siteObj= new JSONObject();
				siteObj.put("id", site.getId());
				siteObj.put("SiteName", site.getSiteName());

				siteArray.put(siteObj);
			}
			appObj.put("sites",siteArray);
			
			JSONArray zacmapArray=new JSONArray();
			for(Zacmap zacMap:application.getZacmaps())
			{
				boolean isNoProperty=true;
				JSONObject zacmapObj= new JSONObject();
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
				JSONObject vendorObj= new JSONObject();
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
				JSONObject deparmentObj= new JSONObject();
				deparmentObj.put("id", deparment.getId());
				deparmentObj.put("DepartmentName", deparment.getDepartmentName());

				departmentArray.put(deparmentObj);
			}
			appObj.put("Departments",departmentArray);
			
			JSONArray supportArray=new JSONArray();
			for(Support support:application.getSupports())
			{
				JSONObject supportsObj= new JSONObject();
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
				JSONObject contractObj= new JSONObject();
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
				JSONObject licenseObj= new JSONObject();
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
				JSONObject instanceObj= new JSONObject();
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
			JSONObject instanceObj= new JSONObject();
			instanceObj.put("id", instance.getId());
			instanceObj.put("Instance", instance.getAppInstanceName());
			instanceObj.put("Status", instance.getStatus());
			instanceObj.put("Version", instance.getVersion());
			instanceObj.put("Description", instance.getDescription());
			instanceObj.put("Notes", instance.getNotes());
			instanceObj.put("Userbase", instance.getUserbase());
			boolean isNoProperty=true;
			
			isNoProperty=ObjectUtils.isEmpty(instance.getDepartment());
			JSONObject departmentObj= new JSONObject();
			departmentObj.put("id", isNoProperty?"":instance.getDepartment().getId());
			departmentObj.put("departmentName", isNoProperty?"":instance.getDepartment().getDepartmentName());
			departmentObj.put("Description", isNoProperty?"":instance.getDepartment().getDescription());
			instanceObj.put("Department", departmentObj);
			
			JSONArray supportArray=new JSONArray();
			for(Support support:instance.getSupports())
			{
				JSONObject supportsObj= new JSONObject();
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
				JSONObject siteObj= new JSONObject();
				siteObj.put("id", site.getId());
				siteObj.put("SiteName", site.getSiteName());

				siteArray.put(siteObj);
			}
			instanceObj.put("sites",siteArray);
			
			JSONArray zoneArray=new JSONArray();
			for(Zone zone:instance.getZones())
			{
				JSONObject zoneObj= new JSONObject();
				zoneObj.put("id", zone.getId());
				zoneObj.put("ZoneName", zone.getZoneName());

				zoneArray.put(zoneObj);
			}
			instanceObj.put("zones",zoneArray);			

			JSONArray projArray=new JSONArray();
			for(Project project:instance.getProjects())
			{
				JSONObject projObj= new JSONObject();
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
			JSONObject appObj= new JSONObject();
			appObj.put("id", isNoProperty?"":instance.getApplication().getId());
			appObj.put("Application", isNoProperty?"":instance.getApplication().getAppName());
			appObj.put("Status", isNoProperty?"":instance.getApplication().getStatus());
			appObj.put("Version", isNoProperty?"":instance.getApplication().getAppVersion());
			instanceObj.put("Application", appObj);
			
			JSONArray contractArray=new JSONArray();
			for(Contract contract:instance.getContracts())
			{
				JSONObject contractObj= new JSONObject();
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
				JSONObject vendorObj= new JSONObject();
				vendorObj.put("id", vendor.getId());
				vendorObj.put("Vendor", vendor.getManufacturer());
				vendorObj.put("CompanyName", vendor.getCompanyName());
				vendorObj.put("Email", vendor.getEmail());
				vendorArray.put(vendorObj);
			}
			instanceObj.put("vendors",vendorArray);
			
			isNoProperty=ObjectUtils.isEmpty(instance.getSla());
			JSONObject slaObj= new JSONObject();
			slaObj.put("id", isNoProperty?"":instance.getSla().getId());
			slaObj.put("SLA", isNoProperty?"":instance.getSla().getSlaName());
			slaObj.put("Description", isNoProperty?"":instance.getSla().getDescription());
			slaObj.put("EffectiveDate", isNoProperty?"":instance.getSla().getEffectivedate());
			slaObj.put("TerminationDate", isNoProperty?"":instance.getSla().getTerminationdate());
			instanceObj.put("SLA", slaObj);
			
			JSONArray serverArray=new JSONArray();
			for(Server server:instance.getServers())
			{
				JSONObject serverObj= new JSONObject();
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
				JSONObject licenseObj= new JSONObject();
				licenseObj.put("id", license.getId());
				licenseObj.put("LicenseNumber", license.getLicenseNumber());
				licenseObj.put("ExpireDate", license.getExpireDate());
				licenseObj.put("RegistrationDate", license.getRegistrationDate());
				licenseArray.put(licenseObj);
			}
			instanceObj.put("licenses",licenseArray);	
			
			isNoProperty=ObjectUtils.isEmpty(instance.getDesktop());
			JSONObject desktopObj= new JSONObject();
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
			JSONObject departmentObj= new JSONObject();
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
				JSONObject answerObj= new JSONObject();
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
				JSONObject stakeholderObj= new JSONObject();
				stakeholderObj.put("id", stakeholder.getId());
				stakeholderObj.put("Stakeholder", stakeholder.getStakeholderName());
				stakeholderArray.put(stakeholderObj);
			}
			departmentObj.put("stakeholders",stakeholderArray);
			
			JSONArray applicationArray=new JSONArray();
			for(Application application:department.getApplications())
			{
				JSONObject applicationObj= new JSONObject();
				applicationObj.put("id", application.getId());
				applicationObj.put("Application", application.getAppName());
				applicationArray.put(applicationObj);
			}
			departmentObj.put("applications",applicationArray);
			
			JSONArray instanceArray=new JSONArray();
			for(AppInstance instance:department.getAppInstances())
			{
				JSONObject instanceObj= new JSONObject();
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

				JSONObject zacmapObj= new JSONObject();
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






}
