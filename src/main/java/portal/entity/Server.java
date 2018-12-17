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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonView;

import portal.jsonview.Views;
import portal.utility.Convertor;

@Entity
@Table(name = "Server")
public class Server {
	
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE) 
	@Column(name = "id",nullable = false,unique=true)
	@JsonView(Views.Public.class)
	private Long id;

    @Column(name = "serverName",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String serverName;
	
    @Column(name = "description",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String description;
    
    @Column(name = "address",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String address;
    
    @Column(name = "serverVersion",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String serverVersion;
    
    @Column(name = "hwplatform",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String hwplatform;
    
    @Column(name = "decomminsionDate")
    @Temporal(TemporalType.DATE)
    @JsonView(Views.Public.class)
	private Date decomminsionDate;

    
    @Column(name = "attachment",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String attachment;
    
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "appInstance_id",referencedColumnName="id")
    private AppInstance appInstance;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "cluster_id",referencedColumnName="id")
    private Cluster cluster;
    
	public Cluster getCluster() {
		return cluster;
	}

	public void setCluster(Cluster cluster) {
		this.cluster = cluster;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getServerVersion() {
		return serverVersion;
	}

	public void setServerVersion(String serverVersion) {
		this.serverVersion = serverVersion;
	}

	public String getHwplatform() {
		return hwplatform;
	}

	public void setHwplatform(String hwplatform) {
		this.hwplatform = hwplatform;
	}

	public Date getDecomminsionDate() {
		return decomminsionDate;
	}

	public void setDecomminsionDate(String decomminsionDate) {
		this.decomminsionDate = Convertor.JavaDate(decomminsionDate);
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public AppInstance getAppInstance() {
		return appInstance;
	}

	public void setAppInstance(AppInstance appInstance) {
		this.appInstance = appInstance;
	}


	
}
