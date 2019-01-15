package portal.entity;




import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
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

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonView;

import portal.jsonview.Views;
import portal.utility.Convertor;

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

    @Column(name = "attachment",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String attachment; 
    
    @OneToMany(
            mappedBy = "contract", 
            cascade = CascadeType.ALL, 
            orphanRemoval = true
        )
    private List<AppInstance> appInstances = new ArrayList<AppInstance>();   

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

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		List<String> filelist;
		
		if(StringUtils.isEmpty(this.attachment))  filelist=new ArrayList<>();
		else filelist=new ArrayList<>(Arrays.asList(StringUtils.split(this.attachment, ",")));
		
		filelist.add(attachment);
		this.attachment = filelist.stream().collect(Collectors.joining(","));
	}


	public List<AppInstance> getAppInstances() {
		return appInstances;
	}

	public void setAppInstances(List<AppInstance> appInstances) {
		this.appInstances = appInstances;
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

	public List<String> getAttachmentList()
	{
		if(StringUtils.isEmpty(this.attachment)) return new ArrayList<>();
		
		return new ArrayList<>(Arrays.asList(StringUtils.split(this.attachment, ",")));
	}

	public void removeAttachment(String attachment)
	{
		List<String> filelist=new ArrayList<>(Arrays.asList(StringUtils.split(this.attachment, ",")));
		filelist.remove(attachment);
		this.attachment = filelist.stream().collect(Collectors.joining(","));
	}

	
}
