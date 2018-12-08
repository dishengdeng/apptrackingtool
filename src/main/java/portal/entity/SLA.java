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
@Table(name = "SLA")
public class SLA {
	
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE) 
	@Column(name = "id",nullable = false,unique=true)
	@JsonView(Views.Public.class)
	private Long id;

    @Column(name = "slaName",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String slaName;
	
    @Column(name = "description",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String description;
    

    @Column(name = "effectivedate")
    @Temporal(TemporalType.DATE)
    @JsonView(Views.Public.class)
	private Date effectivedate;
    
    @Column(name = "terminationdate")
    @Temporal(TemporalType.DATE)
    @JsonView(Views.Public.class)
	private Date terminationdate;
    
    @Column(name = "approvername", length = 64,columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String approvername;

    @Column(name = "approvaldate")
    @Temporal(TemporalType.DATE)
    @JsonView(Views.Public.class)
	private Date approvaldate;
    
    @Column(name = "docreferece", length = 64,columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String docreferece;
    
    @Column(name = "attachment", length = 64,columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String attachment;   

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "appInstance_id")
    private AppInstance appInstance;

	public Long getId() {
		return id;
	}

	public void setId(String id) {
		this.id = Long.valueOf(id);
	}

	public String getSlaName() {
		return slaName;
	}

	public void setSlaName(String slaName) {
		this.slaName = slaName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public java.util.Date getEffectivedate() {
		return effectivedate;
	}

	public void setEffectivedate(String effectivedate) {
		this.effectivedate = Convertor.JavaDate(effectivedate);
	}

	public Date getTerminationdate() {
		return terminationdate;
	}

	public void setTerminationdate(String terminationdate) {
		this.terminationdate = Convertor.JavaDate(terminationdate);
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

	public String getDocreferece() {
		return docreferece;
	}

	public void setDocreferece(String docreferece) {
		this.docreferece = docreferece;
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
