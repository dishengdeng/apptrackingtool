package portal.service.Impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import portal.entity.Appdepartment;
import portal.entity.Contract;
import portal.entity.License;
import portal.entity.Project;
import portal.entity.Site;
import portal.repository.AppRepository;
import portal.repository.AppdepartmentRepository;
import portal.repository.CompanyRepository;
import portal.repository.ContractRepository;
import portal.repository.DepartmentRepository;
import portal.repository.LicenseRepository;
import portal.repository.ProjectRepository;
import portal.repository.SiteRepository;
import portal.service.AppdepartmentService;
import portal.utility.Action;

@Service
public class AppdepartmentServiceImpl implements AppdepartmentService{

	@Autowired
	private AppdepartmentRepository appdepartmentRepository;
	
	@Autowired
	private AppRepository appRepository;
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private SiteRepository siteRepository;
	
	@Autowired
	private ContractRepository contractRepository;
	
	@Autowired
	private LicenseRepository licenseRepository;
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Override
	public Appdepartment save(Appdepartment appdepartment) {

		return appdepartmentRepository.save(appdepartment);
	}

	@Override
	public Appdepartment update(Appdepartment appdepartment) {

		return appdepartmentRepository.saveAndFlush(appdepartment);
	}

	@Override
	public void delete(Appdepartment appdepartment) {
		appdepartmentRepository.delete(appdepartment);
		
	}

	@Override
	public List<Appdepartment> getAll() {

		return appdepartmentRepository.findAll();
	}

	@Override
	public Appdepartment saveAppdepartment(JSONObject appdepartment) {
		Action actionType=Action.valueOf(appdepartment.getString("action"));
		Appdepartment appdepart= actionType.equals(Action.CREATE) || actionType.equals(Action.COPY) || actionType.equals(Action.COPYTO)? new Appdepartment():appdepartmentRepository.findOne(appdepartment.getLong("id"));
		

		if(!ObjectUtils.isEmpty(appdepartment.getJSONObject("application").getString("id"))) appdepart.setApplication(appRepository.findOne(appdepartment.getJSONObject("application").getLong("id")));
		appdepart.setBusinesslead(appdepartment.getJSONObject("application").getString("businesslead"));
		appdepart.setAppowner(appdepartment.getJSONObject("application").getString("appowner"));
		appdepart.setGoverinplace(appdepartment.getJSONObject("application").getString("goverinplace"));
		appdepart.setUserbase(appdepartment.getJSONObject("application").getString("userbase"));
		appdepart.setBroadmap(appdepartment.getJSONObject("application").getString("broadmap"));
		appdepart.setNote(appdepartment.getJSONObject("application").getString("note"));
		
		appdepart.setCalgary(appdepartment.getJSONObject("zone").getString("calgary"));
		appdepart.setSouth(appdepartment.getJSONObject("zone").getString("south"));
		appdepart.setNorth(appdepartment.getJSONObject("zone").getString("north"));
		appdepart.setCentral(appdepartment.getJSONObject("zone").getString("central"));
		appdepart.setEdmonton(appdepartment.getJSONObject("zone").getString("edmonton"));
		
		Set<Site> sites= new HashSet<Site>();
		for(Object obj : appdepartment.getJSONArray("sites"))
		{
			JSONObject jsonObj=(JSONObject)obj;
			
			sites.add(siteRepository.findOne(jsonObj.getLong("id")));
		}
		appdepart.refreshsite(sites);
		appdepart.setSites(sites);
		
		appdepart.setSme(appdepartment.getJSONObject("support").getString("sme"));
		appdepart.setTrainer(appdepartment.getJSONObject("support").getString("trainer"));
		appdepart.setUseradmin(appdepartment.getJSONObject("support").getString("useradmin"));
		appdepart.setSystemadmin(appdepartment.getJSONObject("support").getString("systemadmin"));
		appdepart.setDbsupport(appdepartment.getJSONObject("support").getString("dbsupport"));
		appdepart.setServersupport(appdepartment.getJSONObject("support").getString("serversupport"));
		appdepart.setNetworksupport(appdepartment.getJSONObject("support").getString("networksupport"));
		
		if(!ObjectUtils.isEmpty(appdepartment.getJSONObject("department").getString("id"))) appdepart.setDepartment(departmentRepository.findOne(appdepartment.getJSONObject("department").getLong("id")));
		if(!ObjectUtils.isEmpty(appdepartment.getJSONObject("vendor").getString("id")))appdepart.setVendor(companyRepository.findOne(appdepartment.getJSONObject("vendor").getLong("id")));
		
		appdepart.setAhsitsla(appdepartment.getJSONObject("contract").getString("ahsitsla"));
		appdepart.setContractinplace(appdepartment.getJSONObject("contract").getString("contractinplace"));
		appdepart.setExpireDate(appdepartment.getJSONObject("contract").getString("expireDate"));
		appdepart.setVendorsla(appdepartment.getJSONObject("contract").getString("vendorsla"));
		Set<Contract> contracts= new HashSet<Contract>();
		for(Object obj : appdepartment.getJSONObject("contract").getJSONArray("contracts"))
		{
			JSONObject jsonObj=(JSONObject)obj;
			contracts.add(contractRepository.findOne(jsonObj.getLong("id")));
		}
		appdepart.refreshContract(contracts);
		appdepart.setContracts(contracts);
		
		Set<License> licenses= new HashSet<License>();
		for(Object obj : appdepartment.getJSONObject("contract").getJSONArray("licenses"))
		{
			JSONObject jsonObj=(JSONObject)obj;
			licenses.add(licenseRepository.findOne(jsonObj.getLong("id")));
		}
		appdepart.refreshlicense(licenses);
		appdepart.setLicenses(licenses);
		
		appdepart.setImp(appdepartment.getJSONObject("project").getString("imp"));
		appdepart.setCshrecimit(appdepartment.getJSONObject("project").getString("cshrecimit"));
		Set<Project> projects= new HashSet<Project>();
		for(Object obj : appdepartment.getJSONObject("project").getJSONArray("projects"))
		{
			JSONObject jsonObj=(JSONObject)obj;
			projects.add(projectRepository.findOne(jsonObj.getLong("id")));
		}
		appdepart.refreshProject(projects);
		appdepart.setProjects(projects);
		
		return appdepartmentRepository.saveAndFlush(appdepart);
	}

	@Override
	public Appdepartment findone(Long id) {
		
		return appdepartmentRepository.findOne(id);
	}

}
