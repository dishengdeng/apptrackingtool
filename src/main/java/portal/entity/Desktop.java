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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonView;

import portal.jsonview.Views;
import portal.utility.Convertor;

@Entity
@Table(name = "Desktop")
public class Desktop {
	
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE) 
	@Column(name = "id",nullable = false,unique=true)
	@JsonView(Views.Public.class)
	private Long id;

    @Column(name = "desktopName",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String desktopName;
	
    @Column(name = "description",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String description;
    

    @Column(name = "osVersion",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String osVersion;
    
    @Column(name = "hwplatform",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String hwplatform;
    
    @Column(name = "decomminsionDate")
    @Temporal(TemporalType.DATE)
    @JsonView(Views.Public.class)
	private Date decomminsionDate;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "appInstance_id")
    private AppInstance appInstance;    

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDesktopName() {
		return desktopName;
	}

	public void setDesktopName(String desktopName) {
		this.desktopName = desktopName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
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

	public AppInstance getAppInstance() {
		return appInstance;
	}

	public void setAppInstance(AppInstance appInstance) {
		this.appInstance = appInstance;
	}



	
}
