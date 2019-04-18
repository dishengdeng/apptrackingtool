package portal.entity;



import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

import com.fasterxml.jackson.annotation.JsonView;

import portal.jsonview.Views;

@Entity
@Table(name = "Support")
public class Support {
	
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE) 
	@Column(name = "id",nullable = false,unique=true)
	@JsonView(Views.Public.class)
	private Long id;

    @Column(name = "supportName",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String supportName;

    @Column(name = "secondarysupport",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String secondarysupport;
    
    @Column(name = "othersupport",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String othersupport;
    
    @Column(name = "appowner",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String appowner;
    
    
    @Column(name = "trainer",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String trainer;
    
    
    @Column(name = "sme",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String sme;
    
    
    @Column(name = "businesslead",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String businesslead;
    
    
    @Column(name = "coes",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String coes;
    
    @Column(name = "ahshours",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String ahshours;

    
    @Column(name = "useradmin",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String useradmin;

    
    @Column(name = "sysadmin",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String sysadmin;

    @Column(name = "networksupport",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String networksupport;
    
    @Column(name = "commonissue",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String commonissue;
    
    @Column(name = "phone",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String phone;
    
    @Column(name = "email",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String email;
    
    @Column(name = "note", length = 64,columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String note;
 
    @Column(name = "location", length = 64,columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String location;
    
	@ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "instancesupport",
        joinColumns = @JoinColumn(name = "support_id"),
        inverseJoinColumns = @JoinColumn(name = "instance_id")
    )	
    private Set<AppInstance> appInstances = new HashSet<AppInstance>();
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "appsupport",
        joinColumns = @JoinColumn(name = "support_id"),
        inverseJoinColumns = @JoinColumn(name = "application_id")
    )
    private Set<Application> applications = new HashSet<Application>();   

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSupportName() {
		return supportName;
	}

	public void setSupportName(String supportName) {
		this.supportName = supportName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}


	public void addInstance(AppInstance app)
	{
		this.appInstances.add(app);		
	}
	
	public void removeInstance(AppInstance app)
	{
		this.appInstances.remove(app);
	}
	
	public Set<AppInstance> getAppInstances() {
		return appInstances;
	}

	public void setAppInstances(Set<AppInstance> appInstances) {
		
		this.appInstances.addAll(appInstances);
	}

	public void addApplication(Application application)
	{
		this.applications.add(application);
	}
	
	public void removeApplication(Application application)
	{
		this.applications.removeIf(obj->obj.equals(application));
	}
	public Set<Application> getApplications() {
		return applications;
	}

	public void setApplications(Set<Application> applications) {
		this.applications.addAll(applications);
		applications.forEach(application->{
			application.addSupport(this);
		});
	}

	public String getInstanceNameWithComma()
	{
		List<String> instanceName=new ArrayList<String>();
		for(AppInstance appinstance:this.appInstances)
		{
			instanceName.add(appinstance.getAppInstanceName());
		}
		
		return instanceName.stream().collect(Collectors.joining(","));
	}
	
	public String getSecondarysupport() {
		return secondarysupport;
	}

	public void setSecondarysupport(String secondarysupport) {
		this.secondarysupport = secondarysupport;
	}

	public String getOthersupport() {
		return othersupport;
	}

	public void setOthersupport(String othersupport) {
		this.othersupport = othersupport;
	}

	public String getAppowner() {
		return appowner;
	}

	public void setAppowner(String appowner) {
		this.appowner = appowner;
	}

	public String getTrainer() {
		return trainer;
	}

	public void setTrainer(String trainer) {
		this.trainer = trainer;
	}

	public String getSme() {
		return sme;
	}

	public void setSme(String sme) {
		this.sme = sme;
	}

	public String getBusinesslead() {
		return businesslead;
	}

	public void setBusinesslead(String businesslead) {
		this.businesslead = businesslead;
	}

	public String getCoes() {
		return coes;
	}

	public void setCoes(String coes) {
		this.coes = coes;
	}

	public String getAhshours() {
		return ahshours;
	}

	public void setAhshours(String ahshours) {
		this.ahshours = ahshours;
	}

	public String getUseradmin() {
		return useradmin;
	}

	public void setUseradmin(String useradmin) {
		this.useradmin = useradmin;
	}

	public String getSysadmin() {
		return sysadmin;
	}

	public void setSysadmin(String sysadmin) {
		this.sysadmin = sysadmin;
	}

	public String getNetworksupport() {
		return networksupport;
	}

	public void setNetworksupport(String networksupport) {
		this.networksupport = networksupport;
	}

	public String getCommonissue() {
		return commonissue;
	}

	public void setCommonissue(String commonissue) {
		this.commonissue = commonissue;
	}

	public void removeAllApp()
	{
		this.applications.forEach(obj->{
			obj.removeSupport(this);
		});
		this.applications=null;
	}
	
	public void removeAllInstance()
	{

		this.appInstances.forEach(obj->{
			obj.removeSupport(this);
		});
		this.appInstances=null;
		

	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
}
