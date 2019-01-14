package portal.entity;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonView;

import portal.jsonview.Views;
import portal.utility.Convertor;

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
    private List<AppInstance> appInstances = new ArrayList<AppInstance>();    
    
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

	public List<AppInstance> getAppInstances() {
		if(this.appInstances.size()>0)
		{
			return appInstances.stream().sorted().collect(Collectors.toList());
		}
		else
		{
			return appInstances;
		}
		
	}

	public void setAppInstances(List<AppInstance> appInstances) {
		this.appInstances = appInstances;
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
}
