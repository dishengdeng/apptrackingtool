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
    
    @Column(name = "description",columnDefinition="VARCHAR(1000)")
    @JsonView(Views.Public.class)
	private String description;
    
    @Column(name = "notes",columnDefinition="VARCHAR(1000)")
    @JsonView(Views.Public.class)
	private String notes;
    

	
    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "application_id",referencedColumnName="id")
    private Application application;
    
    @OneToMany(
            mappedBy = "appInstance", 
            cascade = CascadeType.ALL,
            orphanRemoval = true
        )
    private Set<Appdepartment> appdepartments = new HashSet<Appdepartment>();
    
    @OneToMany(
            mappedBy = "appInstance", 
            cascade = CascadeType.ALL, 
            orphanRemoval = true 
        )
    private Set<Server> servers = new HashSet<Server>();
    

    
    @OneToOne(
            mappedBy = "appInstance", 
            cascade = CascadeType.ALL
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
	
	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
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
		servers.forEach(obj->{
			obj.setAppInstance(this);
		});
	}
	
	public void addServer(Server server)
	{
		this.servers.add(server);
	}
	
	public void removeServer(Server server)
	{
		this.servers.remove(server);
	}


	public Set<Appdepartment> getAppdepartments() {
		return appdepartments;
	}

	public void setAppdepartments(Set<Appdepartment> appdepartments) {
		this.appdepartments.addAll(appdepartments);
		appdepartments.forEach(obj->{
			obj.setAppInstance(this);
		});
	}
	
	public void AddAppdepartment(Appdepartment appdepartment)
	{
		this.appdepartments.add(appdepartment);
	}
	
	public void RemoveAppdepartment(Appdepartment appdepartment)
	{
		this.appdepartments.remove(appdepartment);
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



	public Desktop getDesktop() {
		return desktop;
	}

	public void setDesktop(Desktop desktop) {
		this.desktop = desktop;
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

	
	


	public void removeAllDependence()
	{

		
		if(!ObjectUtils.isEmpty(this.application)) this.application.getAppInstances().removeIf(obj->obj.equals(this));
		this.setApplication(null);
		
		if(!ObjectUtils.isEmpty(this.desktop)) this.desktop.setAppInstance(null);
		this.setDesktop(null);
		
		if(servers.size()>0) this.servers.forEach(obj->{
			obj.setAppInstance(null);
		});
		this.servers=null;
		
		this.appdepartments.forEach(obj->{
			obj.setAppInstance(null);
		});
		this.appdepartments=null;
		

	}
	

	
}
