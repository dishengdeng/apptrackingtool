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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



import com.fasterxml.jackson.annotation.JsonView;

import portal.jsonview.Views;
import portal.utility.Convertor;

@Entity
@Table(name = "License")
public class License {
	
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE) 
	@Column(name = "id",nullable = false,unique=true)
	@JsonView(Views.Public.class)
	private Long id;

	@Column(name = "licenseNumber",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String licenseNumber;
	
    @Column(name = "registrationDate")
    @Temporal(TemporalType.DATE)
    @JsonView(Views.Public.class)
	private Date registrationDate;
    

    @Column(name = "note",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String note;
    
    @Column(name = "price",columnDefinition="NUMERIC(9,2)")
    @JsonView(Views.Public.class)
	private Double price;
    
    @Column(name = "expireDate")
    @Temporal(TemporalType.DATE)
    @JsonView(Views.Public.class)
	private Date expireDate;

    @Column(name = "numberoflicenserequired",columnDefinition="NUMERIC(4)")
    @JsonView(Views.Public.class)
	private int numberoflicenserequired;  
    
    @Column(name = "renewalfrequency",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String renewalfrequency;  
    
    @Column(name = "renewalowner",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String renewalowner; 
    
    @Column(name = "warrenty",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String warrenty; 
    
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "instancelicense",
        joinColumns = @JoinColumn(name = "license_id"),
        inverseJoinColumns = @JoinColumn(name = "instance_id")
    )
    private Set<AppInstance> appInstances = new HashSet<AppInstance>();
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "applicense",
        joinColumns = @JoinColumn(name = "license_id"),
        inverseJoinColumns = @JoinColumn(name = "application_id")
    )
    private Set<Application> applications = new HashSet<Application>();

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = Convertor.JavaDate(registrationDate);
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(String expireDate) {
		this.expireDate = Convertor.JavaDate(expireDate);
	}

	public int getNumberoflicenserequired() {
		return numberoflicenserequired;
	}

	public void setNumberoflicenserequired(String numberoflicenserequired) {
		this.numberoflicenserequired = Convertor.JavaInt(numberoflicenserequired);
	}

	public String getRenewalfrequency() {
		return renewalfrequency;
	}

	public void setRenewalfrequency(String renewalfrequency) {
		this.renewalfrequency = renewalfrequency;
	}

	public String getRenewalowner() {
		return renewalowner;
	}

	public void setRenewalowner(String renewalowner) {
		this.renewalowner = renewalowner;
	}

	public String getWarrenty() {
		return warrenty;
	}

	public void setWarrenty(String warrenty) {
		this.warrenty = warrenty;
	}

	public void addInstance(AppInstance instance)
	{
		this.appInstances.add(instance);
	}
	
	public void removeInstance(AppInstance instance)
	{
		this.appInstances.remove(instance);
	}
	
	public Set<AppInstance> getAppInstances() {
		return appInstances;
	}

	public void setAppInstances(Set<AppInstance> appInstances) {
		this.appInstances.addAll(appInstances);
		appInstances.forEach(obj->{
			obj.addLicense(this);
		});
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
		applications.forEach(obj->{
			obj.addLicense(this);
		});
	}

	public void removeAllDependence()
	{

		
		this.applications.forEach(obj->{
			obj.removeLicense(this);
		});
		
		this.applications=null;
		
		this.appInstances.forEach(obj->{
			obj.removeLicense(this);
		});
		this.appInstances=null;
		
	}
  
}
