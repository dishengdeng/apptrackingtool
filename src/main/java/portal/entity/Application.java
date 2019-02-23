package portal.entity;




import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
    
    @Column(name = "AppType", length = 64)
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
    
    @Column(name = "AppGovernance",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String AppGovernance;
    
    @Column(name = "AppSupportByCapSys",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String AppSupportByCapSys;
    
    @OneToMany(
            mappedBy = "application", 
            cascade = CascadeType.ALL, 
            orphanRemoval = true
        )
    private Set<AppInstance> appInstances = new HashSet<AppInstance>();
    
    @OneToOne(
            mappedBy = "application", 
            cascade = CascadeType.ALL, 
            orphanRemoval = true
        )
    private Company manufacturer;
    
    @OneToMany(
            mappedBy = "application", 
            cascade = CascadeType.ALL, 
            orphanRemoval = true
        )
    private Set<File> files = new HashSet<File>();
    
    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "deparment_id",referencedColumnName="id")
    private Department department;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "support_id",referencedColumnName="id")
    private Support support;
    
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


	
	public Set<AppInstance> getAppInstances() {
		if(appInstances.size()>0)
		{
			return appInstances.stream().sorted().collect(Collectors.toSet());
		}
		else
		{
			return appInstances;
		}
		
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

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@Override
	public boolean equals(Object obj)
	{
		if(this==obj) return true;
		
		if(obj==null) return false;
		
		if(this.getClass()!=obj.getClass()) return false;
		
		AppInstance other = (AppInstance) obj;
		
		if(this.getId()!=other.getId()) return false;
		
		return true;
	}

	@Override
	public int compareTo(Application app) {
		
		return this.AppName.compareToIgnoreCase(app.getAppName());
	}

	public Support getSupport() {
		return support;
	}

	public void setSupport(Support support) {
		this.support = support;
	}

	public Company getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(Company manufacturer) {
		this.manufacturer = manufacturer;
	}
	
	public void removeAllInstance()
	{
		this.appInstances.forEach(obj->{
			obj.setApplication(null);
		});
		this.appInstances=null;
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
