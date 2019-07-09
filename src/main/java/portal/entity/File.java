package portal.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonView;

import portal.jsonview.Views;
import portal.utility.Convertor;
import portal.utility.FileType;

@Entity
@Table(name = "file")
public class File implements Comparable<File>{
	
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE) 
	@Column(name = "id",nullable = false,unique=true)
	@JsonView(Views.Public.class)
	private Long id;
	
    @Column(name = "attachment", length = 64,columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String attachment;
    
    @Column(name = "createdby", length = 64,columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String createdby;
    
    @Column(name = "createdat", length = 64,columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String createdat;
    
    @Column(name = "filetype", length = 64,columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private FileType filetype;    
    
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "sla_id")
    private SLA sla;
    
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "contract_id")
    private Contract contract;
    
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "server_id")
    private Server server;

    
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "department_id")
    private Department department;
    
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "instance_id")
    private AppInstance appInstance;
	
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "application_id")
    private Application application;
	
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "report_id", referencedColumnName = "id")
	private Report report;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public SLA getSla() {
		return sla;
	}

	public void setSla(SLA sla) {
		this.sla = sla;
	}

	public FileType getFiletype() {
		return filetype;
	}

	public void setFiletype(FileType filetype) {
		this.filetype = filetype;
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}


	public String getCreatedat() {
		return createdat;
	}

	public void setCreatedat(String createdat) {
		this.createdat = createdat;
	}
	
	public Date getCreateDate()
	{
		return Convertor.JavaDateTime(this.createdat);
	}

	public AppInstance getAppInstance() {
		return appInstance;
	}

	public void setAppInstance(AppInstance appInstance) {
		this.appInstance = appInstance;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}


	
	public Report getReport() {
		return report;
	}

	public void setReport(Report report) {
		this.report = report;
	}

	public void removeAllDependence()
	{
		this.setAppInstance(null);
		this.setApplication(null);
		this.setSla(null);
		this.setContract(null);
		this.setServer(null);
		this.setDepartment(null);
		this.setReport(null);
	}

	@Override
	public int compareTo(File o) {

		return this.attachment.compareToIgnoreCase(o.getAttachment());
	}	
}
