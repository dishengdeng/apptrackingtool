package portal.entity;




import java.util.Date;
import java.util.HashSet;
import java.util.Set;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.ManyToMany;

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
    
    @Column(name = "AppAliase",columnDefinition="VARCHAR(1000)")
    @JsonView(Views.Public.class)
	private String AppAliase;

    @Column(name = "AppPrerequisite",columnDefinition="VARCHAR(1000)")
    @JsonView(Views.Public.class)
	private String AppPrerequisite;
    
    @Column(name = "AppPurpose",columnDefinition="VARCHAR(1000)")
    @JsonView(Views.Public.class)
	private String AppPurpose;
    
    
    @Column(name = "AppDecomminsionDate")
    @Temporal(TemporalType.DATE)
    @JsonView(Views.Public.class)
	private Date AppDecomminsionDate;
 
    @Column(name = "AppComments",columnDefinition="VARCHAR(1000)")
    @JsonView(Views.Public.class)
	private String AppComments;
    
    @Column(name = "notes", columnDefinition="VARCHAR(1000)")
    @JsonView(Views.Public.class)
	private String notes;
    
    @Column(name = "AppGovernance",columnDefinition="VARCHAR(1000)")
    @JsonView(Views.Public.class)
	private String AppGovernance;
    
    @Column(name = "AppSupportByCapSys",columnDefinition="VARCHAR(1000)")
    @JsonView(Views.Public.class)
	private String AppSupportByCapSys;
    
    @Column(name = "strategicplan", columnDefinition="VARCHAR(1000)")
    @JsonView(Views.Public.class)
	private String strategicplan;
    
    @Column(name = "operationsplan", columnDefinition="VARCHAR(1000)")
    @JsonView(Views.Public.class)
	private String operationsplan;
    
    @Column(name = "painpoints", columnDefinition="VARCHAR(1000)")
    @JsonView(Views.Public.class)
	private String painpoints;
    
    @Column(name = "businessgoals", columnDefinition="VARCHAR(1000)")
    @JsonView(Views.Public.class)
	private String businessgoals;
    
    @Column(name = "asop", columnDefinition="VARCHAR(1000)")
    @JsonView(Views.Public.class)
	private String asop;
    
    @Column(name = "rit", columnDefinition="VARCHAR(1000)")
    @JsonView(Views.Public.class)
	private String rit;
    
    @Column(name = "roe", columnDefinition="VARCHAR(1000)")
    @JsonView(Views.Public.class)
	private String roe;
    
    @OneToMany(
            mappedBy = "application", 
            cascade = CascadeType.ALL,
            orphanRemoval = true
        )
    private Set<Appdepartment> appdepartments = new HashSet<Appdepartment>();
    

    
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

	
	public Set<File> getFiles() {
		return files;
	}

	public void setFiles(Set<File> files) {
		this.files.addAll(files);
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


	public Set<Appdepartment> getAppdepartments() {
		return appdepartments;
	}

	public void setAppdepartments(Set<Appdepartment> appdepartments) {
		this.appdepartments.addAll(appdepartments);
		appdepartments.forEach(obj->{
			obj.setApplication(this);
		});
	}
	
	public void addAppdepartments(Appdepartment appdepartment)
	{
		this.appdepartments.add(appdepartment);
	}
	
	public void removeAppdepartments(Appdepartment appdepartment)
	{
		this.appdepartments.remove(appdepartment);
	}

	public void removeAllDependence()
	{
		this.manufacturers.forEach(obj->{
			obj.removeApplication(this);
		});
		this.manufacturers=null;
		

		this.appdepartments.forEach(obj->{
			obj.setApplication(null);
		});
		this.appdepartments=null;
		
		this.appInstances.forEach(obj->{
			obj.setApplication(null);
		});
		this.appInstances=null;
		
		this.zacmaps.forEach(obj->{
			obj.setApplication(this);
		});
		this.zacmaps=null;
		

		

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
