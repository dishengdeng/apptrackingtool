package portal.entity;




import java.util.Date;
import java.util.HashSet;
import java.util.Set;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.annotation.JsonView;

import portal.jsonview.Views;
import portal.utility.Convertor;
import portal.utility.Status;

/**
 * @author Mike
 */

@Entity
@Table(name = "Application")
public class Application implements Comparable<Application>{
	@Id
    @GeneratedValue(generator="ApplicationInvSeq",strategy=GenerationType.SEQUENCE) 
    @SequenceGenerator(name="ApplicationInvSeq",sequenceName="ApplicationInvSeq_INV_SEQ", allocationSize=5)	
	@Column(name = "id", length = 6, nullable = false,unique=true)
	@JsonView(Views.Public.class)
	private Long id;
	
    @Column(name = "AppName", length = 64, unique=true)
    @JsonView(Views.Public.class)
	private String AppName; 
    
    @Column(name = "status",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String status;

	@Column(name = "AppVersion", length = 64)
    @JsonView(Views.Public.class)
	private String AppVersion;
    
    @Column(name = "AppType", length = 128)
    @JsonView(Views.Public.class)
	private String AppType;
    
    @Column(name = "AppAliase",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String AppAliase;

    @Column(name = "AppPrerequisite",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String AppPrerequisite;
    
    @Column(name = "AppPurpose",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String AppPurpose;
    
    
    @Column(name = "AppDecomminsionDate")
    @Temporal(TemporalType.DATE)
    @JsonView(Views.Public.class)
	private Date AppDecomminsionDate;
 
    @Column(name = "AppComments",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String AppComments;
    
    @Column(name = "notes", length = 64,columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String notes;
    
    @Column(name = "AppGovernance",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String AppGovernance;
    
    @Column(name = "AppSupportByCapSys",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String AppSupportByCapSys;
    
    @Column(name = "strategicplan")
    @JsonView(Views.Public.class)
	private String strategicplan;
    
    @Column(name = "operationsplan")
    @JsonView(Views.Public.class)
	private String operationsplan;
    
    @Column(name = "painpoints")
    @JsonView(Views.Public.class)
	private String painpoints;
    
    @Column(name = "businessgoals")
    @JsonView(Views.Public.class)
	private String businessgoals;
    
    @Column(name = "asop")
    @JsonView(Views.Public.class)
	private String asop;
    
    @Column(name = "rit")
    @JsonView(Views.Public.class)
	private String rit;
    
    @Column(name = "roe")
    @JsonView(Views.Public.class)
	private String roe;
    
	@ManyToMany(mappedBy = "applications")
    private Set<Project> projects = new HashSet<Project>();
	
	@ManyToMany(mappedBy = "applications")
    private Set<Zone> zones = new HashSet<Zone>();
	
	@ManyToMany(mappedBy = "applications")
    private Set<Site> sites = new HashSet<Site>();
    
    @OneToMany(
            mappedBy = "application", 
            cascade = CascadeType.ALL,
            orphanRemoval = true
        )
    private Set<AppInstance> appInstances = new HashSet<AppInstance>();
    
    @OneToMany(
            mappedBy = "application", 
            cascade = CascadeType.ALL,
            orphanRemoval = true
        )
    private Set<Zacmap> zacmaps = new HashSet<Zacmap>();
    
	@ManyToMany(mappedBy = "applications")
    private Set<Company> manufacturers = new HashSet<Company>();
    
    @OneToMany(
            mappedBy = "application", 
            cascade = CascadeType.ALL, 
            orphanRemoval = true
        )
    private Set<File> files = new HashSet<File>();
    
	@ManyToMany(mappedBy = "applications")
    private Set<Department> departments = new HashSet<Department>();
    
    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "zac_id",referencedColumnName="id")
    private Zac zac;
    
	@ManyToMany(mappedBy = "applications")
    private Set<Support> supports = new HashSet<Support>();
	
	@ManyToMany(mappedBy = "applications")
    private Set<Contract> contracts = new HashSet<Contract>();
	
	@ManyToMany(mappedBy = "applications")
    private Set<License> licenses = new HashSet<License>();
	
	@ManyToMany(mappedBy = "applications")
    private Set<Report> reports = new HashSet<Report>();
    
    public Long getId() {
		return id;
	}

    public void setId(String id) {
		this.id=Long.valueOf(id);
	}
    
	public String getAppName() {
		return AppName;
	}

	public void setAppName(String appName) {
		AppName = appName;
	}

	public String getAppVersion() {
		return AppVersion;
	}

	public void setAppVersion(String appVersion) {
		AppVersion = appVersion;
	}

	public String getAppType() {
		return AppType;
	}

	public void setAppType(String appType) {
		AppType = appType;
	}

	public String getAppAliase() {
		return AppAliase;
	}

	public void setAppAliase(String appAliase) {
		AppAliase = appAliase;
	}

	public String getAppPrerequisite() {
		return AppPrerequisite;
	}

	public void setAppPrerequisite(String appPrerequisite) {
		AppPrerequisite = appPrerequisite;
	}

	public String getAppPurpose() {
		return AppPurpose;
	}

	public void setAppPurpose(String appPurpose) {
		AppPurpose = appPurpose;
	}

	public Date getAppDecomminsionDate() {
		return AppDecomminsionDate;
	}

	public void setAppDecomminsionDate(String appDecomminsionDate) {
		AppDecomminsionDate = Convertor.JavaDate(appDecomminsionDate);
	}

	public String getAppComments() {
		return AppComments;
	}

	public void setAppComments(String appComments) {
		AppComments = appComments;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getAppGovernance() {
		return AppGovernance;
	}

	public void setAppGovernance(String appGovernance) {
		AppGovernance = appGovernance;
	}

	public String getAppSupportByCapSys() {
		return AppSupportByCapSys;
	}

	public void setAppSupportByCapSys(String appSupportByCapSys) {
		AppSupportByCapSys = appSupportByCapSys;
	}


	
	public String getStrategicplan() {
		return strategicplan;
	}

	public void setStrategicplan(String strategicplan) {
		this.strategicplan = strategicplan;
	}

	public String getOperationsplan() {
		return operationsplan;
	}

	public void setOperationsplan(String operationsplan) {
		this.operationsplan = operationsplan;
	}

	public String getPainpoints() {
		return painpoints;
	}

	public void setPainpoints(String painpoints) {
		this.painpoints = painpoints;
	}

	public String getBusinessgoals() {
		return businessgoals;
	}

	public void setBusinessgoals(String businessgoals) {
		this.businessgoals = businessgoals;
	}

	public String getAsop() {
		return asop;
	}

	public void setAsop(String asop) {
		this.asop = asop;
	}

	public String getRit() {
		return rit;
	}

	public void setRit(String rit) {
		this.rit = rit;
	}

	public String getRoe() {
		return roe;
	}

	public void setRoe(String roe) {
		this.roe = roe;
	}

	public Set<AppInstance> getAppInstances() {

		return appInstances;
		
	}

	public void setAppInstances(Set<AppInstance> appInstances) {
		this.appInstances.addAll(appInstances);
	}

	public Set<Zone> getZones() {
		return zones;
	}

	public void setZones(Set<Zone> zones) {
		this.zones.addAll(zones);
		zones.forEach(obj->{
			obj.addApplication(this);
		});
	}
	
	public void addZone(Zone zone)
	{
		this.zones.add(zone);
	}
	
	public void removeZone(Zone zone)
	{
		this.zones.removeIf(obj->obj.equals(zone));
	}

	public Set<Site> getSites() {
		return sites;
	}

	public void setSites(Set<Site> sites) {
		this.sites.addAll(sites);
		sites.forEach(obj->{
			obj.addApplication(this);
		});
	}
	
	public void removeSite(Site site)
	{
		this.sites.removeIf(obj->obj.equals(site));
	}
	
	public void addSite(Site site)
	{
		this.sites.add(site);
	}
	
	public Set<File> getFiles() {
		return files;
	}

	public void setFiles(Set<File> files) {
		this.files.addAll(files);
	}

	public void AddDepartment(Department department)
	{
		this.departments.add(department);
	}
	
	public void removeDepartment(Department department)
	{
		this.departments.remove(department);
	}

	public Set<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(Set<Department> departments) {
		this.departments.addAll(departments);
		departments.forEach(obj->{
			obj.addApplication(this);
		});
	}

	public Zac getZac() {
		return zac;
	}

	public void setZac(Zac zac) {
		this.zac = zac;
	}

	@Override
	public boolean equals(Object obj)
	{
		if(this==obj) return true;
		
		if(obj==null) return false;
		
		if(this.getClass()!=obj.getClass()) return false;
		
		Application other = (Application) obj;
		
		if(this.getId()!=other.getId()) return false;
		
		return true;
	}
	
	@Override
	public int hashCode() {
	    return Long.valueOf(this.getId()).hashCode();
	}

	@Override
	public int compareTo(Application app) {
		
		return this.AppName.compareToIgnoreCase(app.getAppName());
	}

	public void addContract(Contract contract)
	{
		this.contracts.add(contract);
	}
	
	public void removeContract(Contract contract)
	{
		this.contracts.remove(contract);
	}
	
	public Set<Contract> getContracts() {
		return contracts;
	}

	public void setContracts(Set<Contract> contracts) {
		this.contracts.addAll(contracts);
		contracts.forEach(obj->{
			obj.addApplication(this);
		});
	}

	public void addSupport(Support support)
	{
		this.supports.add(support);
	}

	public void removeSupport(Support support)
	{
		this.supports.removeIf(obj->obj.equals(support));
	}
	public Set<Support> getSupports() {
		return supports;
	}

	public void setSupports(Set<Support> supports) {
		this.supports.addAll(supports);
		supports.forEach(support->{
			support.addApplication(this);
		});
	}

	public void AddManufacturer(Company company)
	{
		this.manufacturers.add(company);
	}
	
	public void removeManufacturer(Company company)
	{
		this.manufacturers.remove(company);
	}
	
	public Set<Company> getManufacturers() {
		return manufacturers;
	}

	public void setManufacturers(Set<Company> manufacturers) {
		this.manufacturers.addAll(manufacturers);
		manufacturers.forEach(obj->{
			obj.addApplication(this);
		});
	}

	public Set<Project> getProjects() {
		return projects;
	}

	public void setProjects(Set<Project> projects) {
		this.projects.addAll(projects);
		projects.forEach(obj->{
			obj.addApplication(this);
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
	public Set<License> getLicenses() {
		return licenses;
	}

	public void setLicenses(Set<License> licenses) {
		this.licenses.addAll(licenses);
		licenses.forEach(obj->{
			obj.addApplication(this);
		});
	}

	public void addZacmap(Zacmap zacmap)
	{
		this.zacmaps.add(zacmap);
	}
	
	public void removeZacmap(Zacmap zacmap)
	{
		this.zacmaps.remove(zacmap);
	}
	
	public Set<Zacmap> getZacmaps() {
		return zacmaps;
	}

	public void setZacmaps(Set<Zacmap> zacmaps) {
		this.zacmaps.addAll(zacmaps);
		zacmaps.forEach(obj->{
			obj.setApplication(this);
		});
	}

	
	
	public Set<Report> getReports() {
		return reports;
	}

	public void setReports(Set<Report> reports) {
		this.reports.addAll(reports);
		reports.forEach(obj->{
			obj.addApplication(this);
		});
	}
	
	public void addReport(Report report)
	{
		this.reports.add(report);
	}
	
	public void removeReport(Report report)
	{
		this.reports.remove(report);
	}

	public void removeAllDependence()
	{
		this.manufacturers.forEach(obj->{
			obj.removeApplication(this);
		});
		this.manufacturers=null;
		
		this.supports.forEach(support->{
			support.removeApplication(this);
		});
		this.supports=null;
		
		this.departments.forEach(obj->{
			obj.removeApplication(this);
		});
		this.departments=null;
		
		if(!ObjectUtils.isEmpty(this.zac)) this.zac.removeApplication(this);
		this.setZac(null);
		
		this.appInstances.forEach(obj->{
			obj.setApplication(null);
		});
		this.appInstances=null;
		
		this.zacmaps.forEach(obj->{
			obj.setApplication(this);
		});
		this.zacmaps=null;
		
		this.projects.forEach(obj->{
			obj.removeApplication(this);
		});
		this.projects=null;
		
		this.zones.forEach(obj->{
			obj.removeApplication(this);
		});
		this.zones=null;
		
		this.sites.forEach(obj->{
			obj.removeApplication(this);
		});
		
		this.sites=null;
		
		this.contracts.forEach(obj->{
			obj.removeApplication(this);
		});
		
		this.contracts=null;
		
		this.licenses.forEach(obj->{
			obj.removeApplication(this);
		});
		
		this.licenses=null;
		
		this.reports.forEach(obj->{
			obj.removeApplication(this);
		});
	}
	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public Status getAppStatus()
	{
		if(ObjectUtils.isEmpty(this.status)) return Status.Inactive;
		return Status.valueOf(this.status);
	}
	
}
