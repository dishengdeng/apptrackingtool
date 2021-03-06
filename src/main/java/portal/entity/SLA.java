package portal.entity;



import java.util.ArrayList;
import java.util.Date;
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

import javax.persistence.OneToMany;

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


    
    @OneToMany(
            mappedBy = "sla", 
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
		this.effectivedate = Convertor.StringToDate(effectivedate);
	}

	public Date getTerminationdate() {
		return terminationdate;
	}

	public void setTerminationdate(String terminationdate) {
		this.terminationdate = Convertor.StringToDate(terminationdate);
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
		this.approvaldate = Convertor.StringToDate(approvaldate);
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


	public void removeAlldependence()
	{
		
	}


	public Set<File> getFiles() {
		return files;
	}

	public void setFiles(Set<File> files) {
		this.files.addAll(files);
	}
	public String getFileNameWithComma()
	{
		List<String> fileName=new ArrayList<String>();
		for(File file:this.files)
		{
			fileName.add(file.getAttachment());
		}
		
		return fileName.stream().collect(Collectors.joining(","));
	}



	
}
