package portal.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

import com.fasterxml.jackson.annotation.JsonView;

import portal.jsonview.Views;

@Entity
@Table(name = "Question")
public class Question {
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE) 
	@Column(name = "id",nullable = false,unique=true)
	@JsonView(Views.Public.class)
	private Long id;
	
    @Column(name = "questionName")
    @JsonView(Views.Public.class)
	private String questionName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getQuestionName() {
		return questionName;
	}

	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}
	
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "question_department",
        joinColumns = @JoinColumn(name = "question_id"),
        inverseJoinColumns = @JoinColumn(name = "department_id")
    )
    private Set<Department> departments = new HashSet<Department>();

    @OneToMany(
            mappedBy = "question", 
            cascade = CascadeType.ALL,
            orphanRemoval = true
        )
    private Set<Answer> answers = new HashSet<Answer>();    
    
	public Set<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(Set<Department> departments) {
		this.departments.addAll(departments);
		departments.forEach(obj->{
			obj.addQuestion(this);
		});
	}
    
    public void addDepartment(Department department)
    {
    	this.departments.add(department);
    }
    
    public void removeDepartment(Department department)
    {
    	this.departments.remove(department);
    }

    
    
    public Set<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(Set<Answer> answers) {
		this.answers.addAll(answers);
		answers.forEach(obj->{
			obj.setQuestion(this);;
		});;
	}
	
	public void addAnswer(Answer answer)
	{
		this.answers.add(answer);
	}
	
	public void removeAnswer(Answer answer)
	{
		this.answers.remove(answer);
	}

	public void removeAllDepandence()
    {
    	this.departments.forEach(obj->{
    		obj.removeQuestion(this);
    	});
    	this.departments=null;
    	
    	this.answers.forEach(obj->{
    		obj.setQuestion(null);
    	});
    	this.answers=null;
    }
}
