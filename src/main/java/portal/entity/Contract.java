package portal.entity;




import java.util.ArrayList;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.annotation.JsonView;

import portal.jsonview.Views;
import portal.utility.Convertor;
import portal.utility.Status;

@Entity
@Table(name = "Contract")
public class Contract {
	
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE) 
	@Column(name = "id",nullable = false,unique=true)
	@JsonView(Views.Public.class)
	private Long id;

    @Column(name = "contractName",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String contractName;
	
    @Column(name = "description",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String description;
    

    @Column(name = "smaDescription",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String smaDescription;
    
    @Column(name = "slaDescription",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String slaDescription;
    
    @Column(name = "expireDate")
    @Temporal(TemporalType.DATE)
    @JsonView(Views.Public.class)
	private Date expireDate;

    @Column(name = "effectivedate")
    @Temporal(TemporalType.DATE)
    @JsonView(Views.Public.class)
	private Date effectivedate;

    @Column(name = "approvername", length = 64,columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String approvername;
    
    @Column(name = "clinplace", length = 64,columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String clinplace;
    
    @Column(name = "vendorsla", length = 64,columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String vendorsla;
    
    @Column(name = "ahsitsla", length = 64,columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String ahsitsla;

    @Column(name = "approvaldate")
    @Temporal(TemporalType.DATE)
    @JsonView(Views.Public.class)
	private Date approvaldate;
    
	@ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "instancecontract",
        joinColumns = @JoinColumn(name = "contract_id"),
        inverseJoinColumns = @JoinColumn(name = "instance_id")
    )	
    private Set<AppInstance> appInstances = new HashSet<AppInstance>();
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "appcontract",
        joinColumns = @JoinColumn(name = "contract_id"),
        inverseJoinColumns = @JoinColumn(name = "application_id")
    )
    private Set<Application> applications = new HashSet<Application>();
    

    
    @OneToMany(
            mappedBy = "contract", 
            cascade = CascadeType.ALL, 
            orphanRemoval = true
        )
    private Set<File> files = new HashSet<File>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSmaDescription() {
		return smaDescription;
	}

	public void setSmaDescription(String smaDescription) {
		this.smaDescription = smaDescription;
	}

	public String getSlaDescription() {
		return slaDescription;
	}

	public void setSlaDescription(String slaDescription) {
		this.slaDescription = slaDescription;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(String expireDate) {
		this.expireDate = Convertor.JavaDate(expireDate);
	}




	public Date getEffectivedate() {
		return effectivedate;
	}

	public void setEffectivedate(String effectivedate) {
		this.effectivedate = Convertor.JavaDate(effectivedate);
	}

	public String getApprovername() {
		return approvername;
	}

	public void setApprovername(String approvername) {
		this.approvername = approvername;
	}

	public Date getApprovaldate() {
		return approvaldate;
	}

	public void setApprovaldate(String approvaldate) {
		this.approvaldate = Convertor.JavaDate(approvaldate);
	}

	public void removeApplication(Application application)
	{
		this.applications.removeIf(obj->obj.equals(application));
	}

	public void addApplication(Application application)
	{
		this.applications.add(application);
	}
	
	public Set<Application> getApplications() {
		return applications;
	}

	public String getClinplace() {
		return clinplace;
	}

	public void setClinplace(String clinplace) {
		this.clinplace = clinplace;
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

	public void setApplications(Set<Application> applications) {
		this.applications.addAll(applications);
		applications.forEach(obj->{
			obj.addContract(this);
		});
	}

	public void addInstance(AppInstance appInstance)
	{
		this.appInstances.add(appInstance);
	}
	
	public void removeInstance(AppInstance appInstance)
	{
		this.appInstances.remove(appInstance);
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



	public Set<File> getFiles() {
		return files;
	}

	public void setFiles(Set<File> files) {
		this.files.addAll(files);
	}
	
	



	public void removeAllDependence()
	{
		this.appInstances.forEach(obj->{
			obj.removeContract(this);
		});
		
		this.appInstances=null;
		
		this.applications.forEach(obj->{
			obj.removeContract(this);
		});
		this.applications=null;
		

	}
	
	public Status getContractStatus()
	{
		Date currentdate= new Date();
		final int daywarn=90;
		if(ObjectUtils.isEmpty(this.expireDate)) return Status.GREEN;
		
		long diff=this.expireDate.getTime()-currentdate.getTime();
		long diffday = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		
		if(diffday>daywarn) return Status.YELLOW;
		if(diffday<daywarn && diffday>0) return Status.YELLOW;
		
		return Status.RED;
		
	}

	
}
