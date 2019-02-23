package portal.entity;




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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.annotation.JsonView;

import portal.jsonview.Views;
import portal.utility.Status;

@Entity
@Table(name = "AppInstance")
public class AppInstance implements Comparable<AppInstance>{
	
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE) 
	@Column(name = "id",nullable = false,unique=true)
	@JsonView(Views.Public.class)
	private Long id;

    @Column(name = "appInstanceName",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String appInstanceName;
    

    @Column(name = "status",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String status;
    
	@Column(name = "version", length = 64)
    @JsonView(Views.Public.class)
	private String version;
    
    @Column(name = "description", length = 64,columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String description;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "deparment_id",referencedColumnName="id")
    private Department department;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "support_id",referencedColumnName="id")
    private Support support;
    
	@ManyToMany(mappedBy = "appInstances")
    private Set<Site> sites = new HashSet<Site>();
	
    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "application_id",referencedColumnName="id")
    private Application application;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "contract_id",referencedColumnName="id")
    private Contract contract;
    
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id")
    private Company company;
    
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "sla_id")
    private SLA sla;
    
    @OneToMany(
            mappedBy = "appInstance", 
            cascade = CascadeType.ALL, 
            orphanRemoval = true
        )
    private Set<Server> servers = new HashSet<Server>();
    
    @OneToOne(
            mappedBy = "appInstance", 
            cascade = CascadeType.ALL, 
            orphanRemoval = true
        )
    private License license;
    
    @OneToOne(
            mappedBy = "appInstance", 
            cascade = CascadeType.ALL, 
            orphanRemoval = true
        )
    private Desktop desktop;
    
    @OneToMany(
            mappedBy = "appInstance", 
            cascade = CascadeType.ALL, 
            orphanRemoval = true
        )
    private Set<File> files = new HashSet<File>();
    
	public Long getId() {
		return id;
	}

	public void setId(String id) {
		this.id = Long.valueOf(id);
	}

	public String getAppInstanceName() {
		return appInstanceName;
	}

	public void setAppInstanceName(String appInstanceName) {
		this.appInstanceName = appInstanceName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Support getSupport() {
		return support;
	}

	public void setSupport(Support support) {
		this.support = support;
	}



	public Set<Site> getSites() {
		return sites;
	}

	public void setSites(Set<Site> sites) {
		this.sites.addAll(sites);
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}



	public Set<Server> getServers() {
		return servers;
	}

	public void setServers(Set<Server> servers) {
		this.servers.addAll(servers);
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
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
	
	public Status getAppStatus()
	{
		if(ObjectUtils.isEmpty(this.status)) return Status.Inactive;
		return Status.valueOf(this.status);
	}

	@Override
	public int compareTo(AppInstance o) {

		return this.appInstanceName.compareToIgnoreCase(o.getAppInstanceName());
	}

	public License getLicense() {
		return license;
	}

	public void setLicense(License license) {
		this.license = license;
	}

	public Desktop getDesktop() {
		return desktop;
	}

	public void setDesktop(Desktop desktop) {
		this.desktop = desktop;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public SLA getSla() {
		return sla;
	}

	public void setSla(SLA sla) {
		this.sla = sla;
	}

	public Set<File> getFiles() {
		return files;
	}

	public void setFiles(Set<File> files) {
		this.files.addAll(files);
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}


	

	
}
