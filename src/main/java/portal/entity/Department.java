package portal.entity;

import java.util.ArrayList;
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

import com.fasterxml.jackson.annotation.JsonView;

import portal.jsonview.Views;

@Entity
@Table(name = "Department")
public class Department {
	
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE) 
	@Column(name = "id",nullable = false,unique=true)
	@JsonView(Views.Public.class)
	private Long id;

    @Column(name = "departmentName",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String departmentName;
	
    @Column(name = "description",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String description;
    

    @Column(name = "purpose",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String purpose;
    

    
    @Column(name = "stragicplan", length = 64,columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String stragicplan;

    @Column(name = "roadMap",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String roadMap;   

    @Column(name = "goal",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String goal; 
    
    @Column(name = "painpoint",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String painpoint;
 
    @Column(name = "attachment",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String attachment;
    
    @OneToMany(
            mappedBy = "department", 
            cascade = CascadeType.ALL, 
            orphanRemoval = true
        )
    private List<Stakeholder> stakeholders = new ArrayList<>();
    
    @OneToMany(
            mappedBy = "department", 
            cascade = CascadeType.ALL, 
            orphanRemoval = true
        )
    private List<AppInstance> appInstances = new ArrayList<AppInstance>();
    
    @OneToMany(
            mappedBy = "department", 
            cascade = CascadeType.ALL, 
            orphanRemoval = true
        )
    private List<File> files = new ArrayList<File>();
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}



	public String getStragicplan() {
		return stragicplan;
	}

	public void setStragicplan(String stragicplan) {
		this.stragicplan = stragicplan;
	}

	public String getRoadMap() {
		return roadMap;
	}

	public void setRoadMap(String roadMap) {
		this.roadMap = roadMap;
	}

	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	public String getPainpoint() {
		return painpoint;
	}

	public void setPainpoint(String painpoint) {
		this.painpoint = painpoint;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public List<Stakeholder> getStakeholders() {
		return stakeholders;
	}

	public void setStakeholders(List<Stakeholder> stakeholders) {
		this.stakeholders = stakeholders;
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

	public String getStakeHolderNameWithComma()
	{
		List<String> stakeHolderName=new ArrayList<String>();
		for(Stakeholder stakeholder:this.stakeholders)
		{
			stakeHolderName.add(stakeholder.getStakeholderName());
		}
		
		return stakeHolderName.stream().collect(Collectors.joining(","));
	}

	public List<File> getFiles() {
		return files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
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
