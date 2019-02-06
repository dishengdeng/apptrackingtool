package portal.entity;



import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
	
    @Column(name = "address",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String address;
    

    @Column(name = "phone",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String phone;
    
    @Column(name = "email",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String email;
    
    @Column(name = "contactName", length = 64,columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String contactName;

    @Column(name = "contactPhone", length = 64,columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String contactPhone;    

    
    @Column(name = "manufacturer", length = 64,columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String manufacturer;
    
    @OneToMany(
            mappedBy = "company", 
            cascade = CascadeType.ALL, 
            orphanRemoval = true
        )
    private Set<AppInstance> appInstances = new HashSet<AppInstance>();
    
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "application_id")
    private Application application;
    
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

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	public boolean isVendor()
	{
		if(StringUtils.isEmpty(this.manufacturer)) return true;
		
		return false;
	}


	
	public Set<AppInstance> getAppInstances() {
		return appInstances;
	}

	public void setAppInstances(Set<AppInstance> appInstances) {
		this.appInstances.addAll(appInstances);
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
