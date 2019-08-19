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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import javax.persistence.Table;


import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonView;

import portal.jsonview.Views;

@Entity
@Table(name = "Company")
public class Company {
	
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE) 
	@Column(name = "id",nullable = false,unique=true)
	@JsonView(Views.Public.class)
	private Long id;

    @Column(name = "companyName",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String companyName;
	
    @Column(name = "address",columnDefinition="VARCHAR(1000)")
    @JsonView(Views.Public.class)
	private String address;
    

    @Column(name = "phone",columnDefinition="VARCHAR(1000)")
    @JsonView(Views.Public.class)
	private String phone;
    
    @Column(name = "email",columnDefinition="VARCHAR(1000)")
    @JsonView(Views.Public.class)
	private String email;
    
    @Column(name = "contactName",columnDefinition="VARCHAR(1000)")
    @JsonView(Views.Public.class)
	private String contactName;

    @Column(name = "contactPhone",columnDefinition="VARCHAR(1000)")
    @JsonView(Views.Public.class)
	private String contactPhone;
    
    @Column(name = "backupContactName",columnDefinition="VARCHAR(1000)")
    @JsonView(Views.Public.class)
	private String backupContactName;

    @Column(name = "backupContactPhone",columnDefinition="VARCHAR(1000)")
    @JsonView(Views.Public.class)
	private String backupContactPhone;  
    
    @Column(name = "sales",columnDefinition="VARCHAR(1000)")
    @JsonView(Views.Public.class)
	private String sales;
    
    @Column(name = "pm",columnDefinition="VARCHAR(1000)")
    @JsonView(Views.Public.class)
	private String pm;
    
    @Column(name = "mobilephone",columnDefinition="VARCHAR(1000)")
    @JsonView(Views.Public.class)
	private String mobilephone;    

    
    @Column(name = "manufacturer",columnDefinition="VARCHAR(1000)")
    @JsonView(Views.Public.class)
	private String manufacturer;
    
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "instancecompany",
        joinColumns = @JoinColumn(name = "company_id"),
        inverseJoinColumns = @JoinColumn(name = "instance_id")
    )	
    private Set<AppInstance> appInstances = new HashSet<AppInstance>();
    
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "appcompany",
        joinColumns = @JoinColumn(name = "company_id"),
        inverseJoinColumns = @JoinColumn(name = "app_id")
    )	
    private Set<Application> applications = new HashSet<Application>();
	

    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}


	public void addApplication(Application application)
	{
		this.applications.add(application);
	}
	
	public void removeApplication(Application application)
	{
		this.applications.remove(application);
	}
	public Set<Application> getApplications() {
		return applications;
	}

	public void setApplications(Set<Application> applications) {
		this.applications.addAll(applications);
	}

	public boolean isVendor()
	{
		if(StringUtils.isEmpty(this.manufacturer)) return true;
		
		return false;
	}


	
	public String getBackupContactName() {
		return backupContactName;
	}

	public void setBackupContactName(String backupContactName) {
		this.backupContactName = backupContactName;
	}

	public String getBackupContactPhone() {
		return backupContactPhone;
	}

	public void setBackupContactPhone(String backupContactPhone) {
		this.backupContactPhone = backupContactPhone;
	}

	public String getSales() {
		return sales;
	}

	public void setSales(String sales) {
		this.sales = sales;
	}

	public String getPm() {
		return pm;
	}

	public void setPm(String pm) {
		this.pm = pm;
	}

	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public Set<AppInstance> getAppInstances() {
		return appInstances;
	}

	public void setAppInstances(Set<AppInstance> appInstances) {
		this.appInstances.addAll(appInstances);
		appInstances.forEach(obj->{
			obj.addCompany(this);
		});
	}
	
	public void addAppInstance(AppInstance appInstance)
	{
		this.appInstances.add(appInstance);
	}
	
	public void removeAppInstance(AppInstance appInstance)
	{
		this.appInstances.removeIf(obj->obj.equals(appInstance));
	}
	
	



	public void removeAllDependence()
	{
		this.applications.forEach(obj->{
			obj.removeManufacturer(this);
		});
		this.applications=null;
		
		this.appInstances.forEach(obj->{
			obj.removeCompany(this);
		});
		this.appInstances=null;
		

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
	
}
