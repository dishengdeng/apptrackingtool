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

import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import portal.jsonview.Views;

@Entity
@Table(name = "Department")
public class Department implements Comparable<Department>,Cloneable{
	
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
    private Set<File> files = new HashSet<File>();
    
    @OneToMany(
            mappedBy = "department", 
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch=FetchType.LAZY
        )
    private Set<Zaclist> zaclists = new HashSet<Zaclist>();



    @OneToMany(
            mappedBy = "department", 
            cascade = CascadeType.PERSIST, 

            fetch=FetchType.LAZY
        )
    private Set<Stakeholderext> stakeholderexts = new HashSet<>();
    
    
    @OneToMany(
            mappedBy = "department", 
            cascade = CascadeType.ALL,
            orphanRemoval = true
        )
    private Set<Appdepartment> appdepartments = new HashSet<Appdepartment>();
    
    @OneToMany(
            mappedBy = "department", 
            cascade = CascadeType.PERSIST, 

            fetch=FetchType.LAZY
        )
    private Set<Zacfield> zacfields = new HashSet<>();
    
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



	public Set<Stakeholderext> getStakeholderexts() {
		return stakeholderexts;
	}

	public void setStakeholderexts(Set<Stakeholderext> stakeholderexts) {
		this.stakeholderexts.addAll(stakeholderexts);
		stakeholderexts.forEach(obj->{
			obj.setDepartment(this);
		});
	}
	
	public void addStakeholderext(Stakeholderext stakeholderext)
	{
		this.stakeholderexts.add(stakeholderext);
	}
	
	public void removeStakeholderext(Stakeholderext stakeholderext)
	{
		this.stakeholderexts.remove(stakeholderext);
	}
	
	
	public Set<Zaclist> getZaclists() {
		return zaclists;
	}

	public void setZaclists(Set<Zaclist> zaclists) {
		this.zaclists.addAll(zaclists);
		zaclists.forEach(obj->{
			obj.setDepartment(this);
		});
	}
	
	public void addZaclist(Zaclist Zaclist)
	{
		this.zaclists.add(Zaclist);
	}
	
	public void removeZaclist(Zaclist Zaclist)
	{
		this.zaclists.remove(Zaclist);
	}
	






	public Set<Appdepartment> getAppdepartments() {
		return appdepartments;
	}

	public void setAppdepartments(Set<Appdepartment> appdepartments) {
		this.appdepartments.addAll(appdepartments);
		appdepartments.forEach(obj->{
			obj.setDepartment(this);
		});
	}
	
	public void addAppdepartments(Appdepartment appdepartment)
	{
		this.appdepartments.add(appdepartment);
	}
	
	public void removeAppdepartments(Appdepartment appdepartment)
	{
		this.appdepartments.remove(appdepartment);
	}

	
	
	public Set<Zacfield> getZacfields() {
		return zacfields;
	}

	public void setZacfields(Set<Zacfield> zacfields) {
		this.zacfields.addAll(zacfields);
		zacfields.forEach(obj->{
			obj.setDepartment(this);
		});
	}
	
	public void addZacfield(Zacfield zacfield)
	{
		this.zacfields.add(zacfield);
	}
	
	public void removeZacfield(Zacfield zacfield)
	{
		this.zacfields.remove(zacfield);
	}

	public Set<File> getFiles() {
		return files;
	}

	public void setFiles(Set<File> files) {
		this.files.addAll(files);
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
		this.answers.forEach(obj->{
			obj.setDepartment(null);
		});
		this.answers=null;
		

		
		this.stakeholderexts.forEach(obj->{
			obj.setDepartment(null);
		});
		this.stakeholderexts=null;
		
		this.appdepartments.forEach(obj->{
			obj.setDepartment(null);
		});
		this.appdepartments=null;

	}

	@Override
	public int compareTo(Department obj) {
		
		return this.departmentName.compareTo(obj.getDepartmentName());
	}

	@Override
	public Object clone() throws CloneNotSupportedException {

		return super.clone();
	}
	
}
