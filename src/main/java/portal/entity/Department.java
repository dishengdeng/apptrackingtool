package portal.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
	
    @Column(name = "description",columnDefinition="VARCHAR(1000)")
    @JsonView(Views.Public.class)
	private String description;
    

    @Column(name = "purpose",columnDefinition="VARCHAR(1000)")
    @JsonView(Views.Public.class)
	private String purpose;
    

    
    @Column(name = "stragicplan",columnDefinition="VARCHAR(1000)")
    @JsonView(Views.Public.class)
	private String stragicplan;

    @Column(name = "roadMap",columnDefinition="VARCHAR(1000)")
    @JsonView(Views.Public.class)
	private String roadMap;   

    @Column(name = "goal",columnDefinition="VARCHAR(1000)")
    @JsonView(Views.Public.class)
	private String goal; 
    
    @Column(name = "painpoint",columnDefinition="VARCHAR(1000)")
    @JsonView(Views.Public.class)
	private String painpoint;
 
    @Column(name = "attachment",columnDefinition="VARCHAR(1000)")
    @JsonView(Views.Public.class)
	private String attachment;

    @OneToMany(
            mappedBy = "department", 
            cascade = CascadeType.ALL,
            orphanRemoval = true
        )
    private Set<Answer> answers = new HashSet<Answer>(); 
	
    @OneToMany(
            mappedBy = "department", 
            cascade = CascadeType.ALL, 
            orphanRemoval = true,
            fetch=FetchType.EAGER
        )
    private Set<Stakeholder> stakeholders = new HashSet<>();
    
    @OneToMany(
            mappedBy = "department", 
            cascade = CascadeType.ALL, 
            orphanRemoval = true,
            fetch=FetchType.EAGER
        )
    private Set<AppInstance> appInstances = new HashSet<AppInstance>();
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "appdepartment",
        joinColumns = @JoinColumn(name = "department_id"),
        inverseJoinColumns = @JoinColumn(name = "application_id")
    )
    private Set<Application> applications = new HashSet<Application>();
    
    @OneToMany(
            mappedBy = "department", 
            cascade = CascadeType.ALL, 
            orphanRemoval = true,
            fetch=FetchType.EAGER
        )
    private Set<File> files = new HashSet<File>();
    
    @OneToMany(
            mappedBy = "department", 
            cascade = CascadeType.ALL,
            orphanRemoval = true
        )
    private Set<Zacmap> zacmaps = new HashSet<Zacmap>();

    
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


	public void AddAnswer(Answer answer)
	{
		this.answers.add(answer);
	}
	
	public void RemoveAnswer(Answer answer)
	{
		this.answers.remove(answer);
	}

	public Set<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(Set<Answer> answers) {
		this.answers.addAll(answers);
		answers.forEach(obj->{
			obj.setDepartment(this);
		});
	}

	public Set<Stakeholder> getStakeholders() {
		return stakeholders;
	}

	public void setStakeholders(Set<Stakeholder> stakeholders) {

		this.stakeholders.addAll(stakeholders);
		stakeholders.forEach(obj->{
			obj.setDepartment(this);
		});
	}
	
	public void addStakeholder(Stakeholder stakeholder)
	{
		this.stakeholders.add(stakeholder);
	}
	
	public void removeStakeholder(Stakeholder stakeholder)
	{
		this.stakeholders.removeIf(obj->obj.equals(stakeholder));
	}

	public Set<AppInstance> getAppInstances() {
		return appInstances;
	}

	public void setAppInstances(Set<AppInstance> appInstances) {

		this.appInstances.addAll(appInstances);
		appInstances.forEach(obj->{
			obj.setDepartment(this);
		});
	}
	
	public void addAppInstance(AppInstance appInstance)
	{
		this.appInstances.add(appInstance);
	}
	
	public void RemoveAppInstance(AppInstance appInstance)
	{
		this.appInstances.removeIf(obj->obj.equals(appInstance));
	}

	public Set<Application> getApplications() {
		return applications;
	}

	public void setApplications(Set<Application> applications) {

		this.applications.addAll(applications);
		applications.forEach(obj->{
			obj.AddDepartment(this);
		});
	}
	public void addZacmap(Zacmap zacmap)
	{
		this.zacmaps.add(zacmap);
	}
	
	public void removeZacmap(Zacmap zacmap)
	{
		this.zacmaps.remove(zacmap);
	}
	
	public Set<Zacmap> getZacmaps() {
		return zacmaps;
	}

	public void setZacmaps(Set<Zacmap> zacmaps) {
		this.zacmaps.addAll(zacmaps);
		zacmaps.forEach(obj->{
			obj.setDepartment(this);
		});
	}	
	public void addApplication(Application application)
	{
		this.applications.add(application);
	}
	
	public void removeApplication(Application application)
	{
		this.applications.removeIf(obj->obj.equals(application));
	}

	public Set<File> getFiles() {
		return files;
	}

	public void setFiles(Set<File> files) {
		this.files.addAll(files);
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


	
	public void addFile(File file)
	{
		this.files.add(file);
	}
	
	public void removeFile(File file)
	{
		this.files.remove(file);
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
	
	public void removeAllDependence()
	{
		this.stakeholders.forEach(obj->{
			obj.setDepartment(null);
		});
		this.stakeholders=null;
		
		this.appInstances.forEach(obj->{
			obj.setDepartment(null);
		});
		this.appInstances=null;
		
		this.applications.forEach(obj->{
			obj.removeDepartment(this);
		});
		this.applications=null;
		
		this.zacmaps.forEach(obj->{
			obj.setDepartment(null);
		});
		this.zacmaps=null;
		
		this.answers.forEach(obj->{
			obj.setDepartment(null);
		});
		this.answers=null;
		

	}
}
