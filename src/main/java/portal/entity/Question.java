package portal.entity;

import java.util.HashSet;
import java.util.Set;

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
@Table(name = "Question")
public class Question {
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE) 
	@Column(name = "id",nullable = false,unique=true)
	@JsonView(Views.Public.class)
	private Long id;
	
    @Column(name = "questionName",columnDefinition="VARCHAR(1000)")
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
	

    @OneToMany(
            mappedBy = "question", 
            cascade = CascadeType.ALL,
            orphanRemoval = true
        )
    private Set<Answer> answers = new HashSet<Answer>();    
    


    
    
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

    	
    	this.answers.forEach(obj->{
    		obj.setQuestion(null);
    	});
    	this.answers=null;
    }
}
