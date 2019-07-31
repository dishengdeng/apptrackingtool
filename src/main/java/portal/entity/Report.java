package portal.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;



import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonView;

import portal.jsonview.Views;
import portal.models.ParameterModel;
import portal.models.RunReportModel;
import portal.utility.ParameterCondition;
import portal.utility.ReportLevelType;


@Entity
@Table(name = "Report")
public class Report {
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE) 
	@Column(name = "id",nullable = false,unique=true)
	@JsonView(Views.Public.class)
	private Long id;

    @Column(name = "reportName",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String reportName;
	
    @Column(name = "description",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String description;
    

    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "reportlevelrel",
        joinColumns = @JoinColumn(name = "report_id"),
        inverseJoinColumns = @JoinColumn(name = "level_id")
    )
    private Set<ReportLevel> reportlevels = new HashSet<ReportLevel>(); 
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "reportstakeholder",
        joinColumns = @JoinColumn(name = "report_id"),
        inverseJoinColumns = @JoinColumn(name = "stakeholder_id")
    )
    private Set<Stakeholder> stakeholders = new HashSet<Stakeholder>();
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "reportapplication",
        joinColumns = @JoinColumn(name = "report_id"),
        inverseJoinColumns = @JoinColumn(name = "application_id")
    )
    private Set<Application> applications = new HashSet<Application>();
    
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "reportappinstance",
        joinColumns = @JoinColumn(name = "report_id"),
        inverseJoinColumns = @JoinColumn(name = "instance_id")
    )
    private Set<AppInstance> appInstances = new HashSet<AppInstance>();
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "reportdepartment",
        joinColumns = @JoinColumn(name = "report_id"),
        inverseJoinColumns = @JoinColumn(name = "department_id")
    )
    private Set<Department> departments = new HashSet<Department>();
    
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "reportzone",
        joinColumns = @JoinColumn(name = "report_id"),
        inverseJoinColumns = @JoinColumn(name = "zone_id")
    )
    private Set<Zone> zones = new HashSet<Zone>();
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "reportsite",
        joinColumns = @JoinColumn(name = "report_id"),
        inverseJoinColumns = @JoinColumn(name = "site_id")
    )
    private Set<Site> sites = new HashSet<Site>();
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "reportcompany",
        joinColumns = @JoinColumn(name = "report_id"),
        inverseJoinColumns = @JoinColumn(name = "company_id")
    )
    private Set<Company> companys = new HashSet<Company>();
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "reportcontract",
        joinColumns = @JoinColumn(name = "report_id"),
        inverseJoinColumns = @JoinColumn(name = "contract_id")
    )
    private Set<Contract> contracts = new HashSet<Contract>();
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "reportlicense",
        joinColumns = @JoinColumn(name = "report_id"),
        inverseJoinColumns = @JoinColumn(name = "license_id")
    )
    private Set<License> licenses = new HashSet<License>();
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "reportserver",
        joinColumns = @JoinColumn(name = "report_id"),
        inverseJoinColumns = @JoinColumn(name = "server_id")
    )
    private Set<Server> servers = new HashSet<Server>();
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "reportsupport",
        joinColumns = @JoinColumn(name = "report_id"),
        inverseJoinColumns = @JoinColumn(name = "support_id")
    )
    private Set<Support> supports = new HashSet<Support>();
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "reportzac",
        joinColumns = @JoinColumn(name = "report_id"),
        inverseJoinColumns = @JoinColumn(name = "zac_id")
    )
    private Set<Zac> zacs = new HashSet<Zac>();
 
    
    @OneToMany(
            mappedBy = "report", 
            cascade = CascadeType.ALL, 
            fetch = FetchType.LAZY,
            orphanRemoval = true
        )
    private Set<Parameter> parameters = new HashSet<Parameter>();  
    
    @OneToOne(mappedBy = "report")
    private File tempate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<ReportLevel> getReportlevels() {
		return reportlevels;
	}

	public void setReportlevels(Set<ReportLevel> reportlevels) {
		this.reportlevels.addAll(reportlevels);
		reportlevels.forEach(obj->{
			obj.addReport(this);
		});
	}

	public void addReportLevel(ReportLevel reportLevel)
	{
		this.reportlevels.add(reportLevel);
	}
	
	public void deleteReportLevel(ReportLevel reportLevel)
	{
		this.reportlevels.remove(reportLevel);
	}
 

	public File getTempate() {
		return tempate;
	}

	public void setTempate(File tempate) {
		this.tempate = tempate;
	}

	public Set<Stakeholder> getStakeholders() {
		return stakeholders;
	}

	public void setStakeholders(Set<Stakeholder> stakeholders) {
		this.stakeholders.addAll(stakeholders);
		stakeholders.forEach(obj->{
			obj.addReport(this);
		});
	}
	
	public void addStakeholder(Stakeholder stakeholder)
	{
		this.stakeholders.add(stakeholder);
	}

	public void removeStakeholder(Stakeholder stakeholder)
	{
		this.stakeholders.remove(stakeholder);
	}
	
	
	
	public Set<Application> getApplications() {
		return applications;
	}

	public void setApplications(Set<Application> applications) {
		this.applications.addAll(applications);
		applications.forEach(obj->{
			obj.addReport(this);
		});
	}
	
	public void addApplication(Application application)
	{
		this.applications.add(application);
	}
	
	public void removeApplication(Application application)
	{
		this.applications.remove(application);
	}
	
	public Set<AppInstance> getAppInstances() {
		return appInstances;
	}

	public void setAppInstances(Set<AppInstance> appInstances) {
		this.appInstances.addAll(appInstances);
		appInstances.forEach(obj->{
			obj.addReport(this);
		});
		
	}
	
	public void addAppInstance(AppInstance appInstance)
	{
		this.appInstances.add(appInstance);
	}
	
	public void removeAppInstance(AppInstance appInstance)
	{
		this.appInstances.remove(appInstance);
	}

	
	
	public Set<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(Set<Department> departments) {
		this.departments.addAll(departments);
		departments.forEach(obj->{
			obj.addReport(this);
		});
	}
	
	public void addDepartment(Department department)
	{
		this.departments.add(department);
	}
	
	public void removeDepartment(Department department)
	{
		this.departments.remove(department);
	}

	
	
	public Set<Zone> getZones() {
		return zones;
	}

	public void setZones(Set<Zone> zones) {
		this.zones.addAll(zones);
		zones.forEach(obj->{
			obj.addReport(this);
		});
	}
	
	public void addZone(Zone zone)
	{
		this.zones.add(zone);
	}
	
	public void removeZone(Zone zone)
	{
		this.zones.remove(zone);
	}

	
	
	public Set<Site> getSites() {
		return sites;
	}

	public void setSites(Set<Site> sites) {
		this.sites.addAll(sites);
		sites.forEach(obj->{
			obj.addReport(this);
		});
	}
	
	public void addSite(Site site)
	{
		this.sites.add(site);
	}

	
	public void removeSite(Site site)
	{
		this.sites.remove(site);
	}
	
	
	
	public Set<Company> getCompanys() {
		return companys;
	}

	public void setCompanys(Set<Company> companys) {
		this.companys.addAll(companys);
		companys.forEach(obj->{
			obj.addReport(this);
		});
	}
	
	public void addCompany(Company company)
	{
		this.companys.add(company);
	}
	
	public void removeCompany(Company company)
	{
		this.companys.remove(company);
	}

	
	
	public Set<Contract> getContracts() {
		return contracts;
	}

	public void setContracts(Set<Contract> contracts) {
		this.contracts.addAll(contracts);
		contracts.forEach(obj->{
			obj.addReport(this);
		});
	}
	
	public void addContract(Contract contract)
	{
		this.contracts.add(contract);
	}
	
	public void removeContract(Contract contract)
	{
		this.contracts.remove(contract);
	}

	
	
	public Set<License> getLicenses() {
		return licenses;
	}

	public void setLicenses(Set<License> licenses) {
		this.licenses.addAll(licenses);
		licenses.forEach(obj->{
			obj.addReport(this);
		});
	}
	
	public void addLicense(License license)
	{
		this.licenses.add(license);
	}
	
	public void removeLicense(License license)
	{
		this.licenses.remove(license);
	}

	
	
	public Set<Server> getServers() {
		return servers;
	}

	public void setServers(Set<Server> servers) {
		this.servers.addAll(servers);
		servers.forEach(obj->{
			obj.addReport(this);
		});
	}
	
	public void addServer(Server server)
	{
		this.servers.add(server);
	}
	
	public void removeServer(Server server)
	{
		this.servers.remove(server);
	}	

	
	
	public Set<Support> getSupports() {
		return supports;
	}

	public void setSupports(Set<Support> supports) {
		this.supports.addAll(supports);
		supports.forEach(obj->{
			obj.addReport(this);
		});
	}
	
	public void addSupport(Support support)
	{
		this.supports.add(support);
	}
	
	public void removeSupport(Support support)
	{
		this.supports.remove(support);
	}
	
	public Set<Zac> getZacs() {
		return zacs;
	}

	public void setZacs(Set<Zac> zacs) {
		this.zacs.addAll(zacs);
		zacs.forEach(obj->{
			obj.addReport(this);
		});
	}
	
	public void addZac(Zac zac)
	{
		this.zacs.add(zac);
	}
	
	public void removeZac(Zac zac)
	{
		this.zacs.remove(zac);
	}	


	public Set<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(Set<Parameter> parameters) {
		this.parameters.addAll(parameters);
		parameters.forEach(obj->{
			obj.setReport(this);
		});
	}

	public void addParameter(Parameter parameter)
	{
		this.parameters.add(parameter);
	}
	
	public void removeParameter(Parameter parameter)
	{
		this.parameters.remove(parameter);
	}
	
	public void removeAlldependence()
	{
		this.reportlevels.forEach(obj->{
			obj.deleteReport(this);
		});
		this.reportlevels=null;
		
		this.stakeholders.forEach(obj->{
			obj.removeReport(this);
		});
		this.stakeholders=null;
		
		this.applications.forEach(obj->{
			obj.removeReport(this);
		});
		this.applications=null;
		
		this.appInstances.forEach(obj->{
			obj.removeReport(this);
		});
		this.appInstances=null;
		
		this.departments.forEach(obj->{
			obj.removeReport(this);
		});
		this.departments=null;
		
		this.zones.forEach(obj->{
			obj.removeReport(this);
		});		
		this.zones=null;
		
		this.sites.forEach(obj->{
			obj.removeReport(this);
		});
		this.sites=null;
		
		this.companys.forEach(obj->{
			obj.removeReport(this);
		});
		this.companys=null;
		
		this.contracts.forEach(obj->{
			obj.removeReport(this);
		});
		this.contracts=null;
		
		this.licenses.forEach(obj->{
			obj.removeReport(this);
		});
		this.licenses=null;
		
		this.servers.forEach(obj->{
			obj.removeReport(this);
		});
		this.servers=null;
		
		this.supports.forEach(obj->{
			obj.removeReport(this);
		});
		this.supports=null;
		
		this.zacs.forEach(obj->{
			obj.removeReport(this);
		});
		this.zacs=null;
	}
	
	public boolean isContainReportLevel(ReportLevelType reportLevelType)
	{
		boolean result=false;
		
		for(ReportLevel obj : this.reportlevels)
		{
			if(obj.getReportLevelType().equals(reportLevelType)) result = true;
		}
			
		
		return result;
	}
	
	public RunReportModel<List<ParameterModel>> getParametersArray()
	{
		RunReportModel<List<ParameterModel>> reportmodel=new  RunReportModel<List<ParameterModel>>();
		reportmodel.setId(this.id);
		List<ParameterModel> parameters=new ArrayList<ParameterModel>();
		for(Parameter parameter:this.parameters)
		{
			ParameterModel model=new ParameterModel();
			model.setId(parameter.getId());
			model.setName(parameter.getName());
			model.setType(parameter.getType());
			model.setLabel(parameter.getLabel());
			model.setValue("");
			parameters.add(model);
			
			if(parameter.getCondition().equals(ParameterCondition.Between))
			{
				try {
					ParameterModel cloneModel=(ParameterModel) model.clone();
					cloneModel.setName("To"+cloneModel.getName());
					parameters.add(cloneModel);
				}
				catch(CloneNotSupportedException ex)
				{
					Logger.getLogger(Report.class.getName()).log(Level.SEVERE, ex.getMessage());
				}

			}
		}
		reportmodel.setParameters(parameters);
		return reportmodel;
		
	}
}
