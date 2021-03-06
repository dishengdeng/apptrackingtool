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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.annotation.JsonView;

import portal.jsonview.Views;
import portal.utility.Convertor;

@Entity
@Table(name = "appdepartment")
public class Appdepartment implements Cloneable{

	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE) 
	@Column(name = "id",nullable = false,unique=true)
	@JsonView(Views.Public.class)
	private Long id;

	//--zone
    @Column(name = "calgary",columnDefinition="VARCHAR(1000)")
    @JsonView(Views.Public.class)
	private String calgary;
    
    @Column(name = "edmonton",columnDefinition="VARCHAR(1000)")
    @JsonView(Views.Public.class)
	private String edmonton;
    
    @Column(name = "central",columnDefinition="VARCHAR(1000)")
    @JsonView(Views.Public.class)
	private String central;
    
    @Column(name = "south",columnDefinition="VARCHAR(1000)")
    @JsonView(Views.Public.class)
	private String south;
    
    @Column(name = "north",columnDefinition="VARCHAR(1000)")
    @JsonView(Views.Public.class)
	private String north;
	
	//--site
	@ManyToMany(mappedBy = "appdepartments",fetch = FetchType.LAZY)
    private Set<Site> sites = new HashSet<Site>();
	//--department
    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "department_id",referencedColumnName="id")
    private Department department;
	
	//--application information
    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "application_id",referencedColumnName="id")
    private Application application;
    
	//--instance information
    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "appinstance_id",referencedColumnName="id")
    private AppInstance appInstance;
	
    @Column(name = "businesslead",columnDefinition="VARCHAR(3000)")
    @JsonView(Views.Public.class)
	private String businesslead;
    
    @Column(name = "appowner",columnDefinition="VARCHAR(1000)")
    @JsonView(Views.Public.class)
	private String appowner;
    
    @Column(name = "goverinplace",columnDefinition="VARCHAR(1000)")
    @JsonView(Views.Public.class)
	private String goverinplace;
    
    @Column(name = "userbase",columnDefinition="VARCHAR(4000)")
    @JsonView(Views.Public.class)
	private String userbase;
    
    @Column(name = "broadmap",columnDefinition="VARCHAR(4000)")
    @JsonView(Views.Public.class)
	private String broadmap;
    
    @Column(name = "note",columnDefinition="VARCHAR(4000)")
    @JsonView(Views.Public.class)
	private String note;  
    
    @Column(name = "poweruser",columnDefinition="VARCHAR(1000)")
    @JsonView(Views.Public.class)
	private String poweruser;
    
    @Column(name = "fluser",columnDefinition="VARCHAR(1000)")
    @JsonView(Views.Public.class)
	private String fluser;
    
   //--contract information 
	@ManyToMany(mappedBy = "appdepartments",fetch = FetchType.LAZY)
    private Set<Contract> contracts = new HashSet<Contract>();
	
	@ManyToMany(mappedBy = "appdepartments",fetch = FetchType.LAZY)
    private Set<License> licenses = new HashSet<License>();
	
    @Column(name = "contractinplace",columnDefinition="VARCHAR(1000)")
    @JsonView(Views.Public.class)
	private String contractinplace;
    
    @Column(name = "contractdetail",columnDefinition="VARCHAR(1000)")
    @JsonView(Views.Public.class)
	private String contractdetail;
    
    @Column(name = "expireDate")
    @Temporal(TemporalType.DATE)
    @JsonView(Views.Public.class)
	private Date expireDate;
    
    @Column(name = "frequency",columnDefinition="VARCHAR(1000)")
    @JsonView(Views.Public.class)
	private String frequency;
    
    @Column(name = "vendorsla", length = 64,columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String vendorsla;
    

    @Column(name = "ahsitsla", length = 64,columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String ahsitsla;
    //--vendor
    @ManyToMany(mappedBy = "appdepartments",fetch = FetchType.LAZY)
    private Set<Company> vendors=new HashSet<Company>();
    
    //----support information
    @Column(name = "sme",columnDefinition="VARCHAR(500)")
    @JsonView(Views.Public.class)
	private String sme;
    
    @Column(name = "trainer",columnDefinition="VARCHAR(500)")
    @JsonView(Views.Public.class)
	private String trainer;
    
    @Column(name = "useradmin",columnDefinition="VARCHAR(500)")
    @JsonView(Views.Public.class)
	private String useradmin;
    
    @Column(name = "systemadmin",columnDefinition="VARCHAR(500)")
    @JsonView(Views.Public.class)
	private String systemadmin;
    
    @Column(name = "dbsupport",columnDefinition="VARCHAR(500)")
    @JsonView(Views.Public.class)
	private String dbsupport;
    
    @Column(name = "serversupport",columnDefinition="VARCHAR(500)")
    @JsonView(Views.Public.class)
	private String serversupport;
    
    @Column(name = "networksupport",columnDefinition="VARCHAR(500)")
    @JsonView(Views.Public.class)
	private String networksupport;
    
    //--project information
	@ManyToMany(mappedBy = "appdepartments",fetch = FetchType.LAZY)
    private Set<Project> projects = new HashSet<Project>();
	
    @Column(name = "imp",columnDefinition="VARCHAR(500)")
    @JsonView(Views.Public.class)
	private String imp;
    
    @Column(name = "cshrecimit",columnDefinition="VARCHAR(500)")
    @JsonView(Views.Public.class)
	private String cshrecimit;
    
    

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCalgary() {
		return calgary;
	}

	public void setCalgary(String calgary) {
		this.calgary = calgary;
	}

	public String getEdmonton() {
		return edmonton;
	}

	public void setEdmonton(String edmonton) {
		this.edmonton = edmonton;
	}

	public String getCentral() {
		return central;
	}

	public void setCentral(String central) {
		this.central = central;
	}

	public String getSouth() {
		return south;
	}

	public void setSouth(String south) {
		this.south = south;
	}

	public String getNorth() {
		return north;
	}

	public void setNorth(String north) {
		this.north = north;
	}

	public String getBusinesslead() {
		return businesslead;
	}

	public void setBusinesslead(String businesslead) {
		this.businesslead = businesslead;
	}

	public String getAppowner() {
		return appowner;
	}

	public void setAppowner(String appowner) {
		this.appowner = appowner;
	}

	public String getGoverinplace() {
		return goverinplace;
	}

	public void setGoverinplace(String goverinplace) {
		this.goverinplace = goverinplace;
	}

	public String getUserbase() {
		return userbase;
	}

	public void setUserbase(String userbase) {
		this.userbase = userbase;
	}

	public String getBroadmap() {
		return broadmap;
	}

	public void setBroadmap(String broadmap) {
		this.broadmap = broadmap;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPoweruser() {
		return poweruser;
	}

	public void setPoweruser(String poweruser) {
		this.poweruser = poweruser;
	}

	public String getFluser() {
		return fluser;
	}

	public void setFluser(String fluser) {
		this.fluser = fluser;
	}

	public String getContractinplace() {
		return contractinplace;
	}

	public void setContractinplace(String contractinplace) {
		this.contractinplace = contractinplace;
	}

	public String getContractdetail() {
		return contractdetail;
	}

	public void setContractdetail(String contractdetail) {
		this.contractdetail = contractdetail;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(String expireDate) {
		this.expireDate = Convertor.StringToDate(expireDate);
	}

	public String getVendorsla() {
		return vendorsla;
	}

	public void setVendorsla(String vendorsla) {
		this.vendorsla = vendorsla;
	}

	public String getAhsitsla() {
		return ahsitsla;
	}

	public void setAhsitsla(String ahsitsla) {
		this.ahsitsla = ahsitsla;
	}

	public String getSme() {
		return sme;
	}

	public void setSme(String sme) {
		this.sme = sme;
	}

	public String getTrainer() {
		return trainer;
	}

	public void setTrainer(String trainer) {
		this.trainer = trainer;
	}

	public String getUseradmin() {
		return useradmin;
	}

	public void setUseradmin(String useradmin) {
		this.useradmin = useradmin;
	}

	public String getSystemadmin() {
		return systemadmin;
	}

	public void setSystemadmin(String systemadmin) {
		this.systemadmin = systemadmin;
	}

	public String getDbsupport() {
		return dbsupport;
	}

	public void setDbsupport(String dbsupport) {
		this.dbsupport = dbsupport;
	}

	public String getServersupport() {
		return serversupport;
	}

	public void setServersupport(String serversupport) {
		this.serversupport = serversupport;
	}

	public String getNetworksupport() {
		return networksupport;
	}

	public void setNetworksupport(String networksupport) {
		this.networksupport = networksupport;
	}

	public String getImp() {
		return imp;
	}

	public void setImp(String imp) {
		this.imp = imp;
	}

	public String getCshrecimit() {
		return cshrecimit;
	}

	public void setCshrecimit(String cshrecimit) {
		this.cshrecimit = cshrecimit;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}


	public AppInstance getAppInstance() {
		return appInstance;
	}

	public void setAppInstance(AppInstance appInstance) {
		this.appInstance = appInstance;
	}

	public Set<Company> getVendors() {
		return vendors;
	}

	public void setVendors(Set<Company> vendors) {
		this.vendors.addAll(vendors);
		vendors.forEach(obj->{
			obj.addAppdepartments(this);
		});
	}
	
	public void addVendor(Company vendor)
	{
		this.vendors.add(vendor);
	}
	
	public void removeVendor(Company vendor)
	{
		this.vendors.remove(vendor);
	}
	
	public void refreshVendors(Set<Company> vendors)
	{
		this.vendors.forEach(obj->{
			if(!vendors.contains(obj)) obj.removeAppdepartments(this);
		});
		this.vendors.retainAll(vendors);
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Set<Site> getSites() {
		return sites;
	}

	public void setSites(Set<Site> sites) {
		this.sites.addAll(sites);
		sites.forEach(obj->{
			obj.addAppdepartments(this);
		});
	}
	
	public void refreshsite(Set<Site> sites)
	{
		this.sites.forEach(obj->{
			if(!sites.contains(obj)) obj.removeAppdepartments(this);
		});
		this.sites.retainAll(sites);		
	}
	
	public void addSite(Site site)
	{
		this.sites.add(site);
	}
	
	public void removeSite(Site site)
	{
		this.sites.remove(site);
	}
	


	public Set<Contract> getContracts() {
		return contracts;
	}

	public void setContracts(Set<Contract> contracts) {
		this.contracts.addAll(contracts);
		contracts.forEach(obj->{
			obj.addAppdepartments(this);
		});
		
	}
	
	public void refreshContract(Set<Contract> contracts)
	{
		this.contracts.forEach(obj->{
			if(!contracts.contains(obj)) obj.removeAppdepartments(this);
		});		
		this.contracts.retainAll(contracts);
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
			obj.addAppdepartments(this);
		});
	}
	
	public void refreshlicense(Set<License> licenses)
	{
		this.licenses.forEach(obj->{
			if(!licenses.contains(obj)) obj.removeAppdepartments(this);
		});		
		this.licenses.retainAll(licenses);
	}

	public void addLicense(License license)
	{
		this.licenses.add(license);
	}
	
	public void removeLicense(License license)
	{
		this.licenses.remove(license);
	}


	
	public Set<Project> getProjects() {
		return projects;
	}

	public void setProjects(Set<Project> projects) {

		this.projects.addAll(projects);
		projects.forEach(obj->{
			obj.addAppdepartments(this);
		});
	}
	
	public void refreshProject(Set<Project> projects)
	{
		this.projects.forEach(obj->{
			if(!projects.contains(obj)) obj.removeAppdepartments(this);
		});		
		this.projects.retainAll(projects);
	}
	
	public void addProject(Project project)
	{
		this.projects.add(project);
	}
	
	public void removeProject(Project project)
	{
		this.projects.remove(project);
	}


	
	public void removeAlldependence()
	{
		if(!ObjectUtils.isEmpty(this.application)) this.application.removeAppdepartments(this);
		this.setApplication(null);
		
		if(!ObjectUtils.isEmpty(this.appInstance)) this.appInstance.RemoveAppdepartment(this);
		this.setAppInstance(null);
		
		if(!ObjectUtils.isEmpty(this.department)) this.department.removeAppdepartments(this);
		this.setDepartment(null);
		
		this.vendors.forEach(obj->{
			obj.removeAppdepartments(this);
		});
		this.vendors=null;
		
		this.sites.forEach(obj->{
			obj.removeAppdepartments(this);
		});
		this.sites=null;
		
		this.contracts.forEach(obj->{
			obj.removeAppdepartments(this);
		});
		this.contracts=null;
		
		this.licenses.forEach(obj->{
			obj.removeAppdepartments(this);
		});
		this.licenses=null;
		
		this.projects.forEach(obj->{
			obj.removeAppdepartments(this);
		});
		this.projects=null;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		
		return super.clone();
	}
	
	
}
