package portal.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import portal.jsonview.Views;

@Entity
@Table(name = "SLARole")
public class SLARole {
	
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE) 
	@Column(name = "id",nullable = false,unique=true)
	@JsonView(Views.Public.class)
	private Long id;

    @Column(name = "SLARoleName",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String SLARoleName;
	
    @Column(name = "description",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String description;
    

    @Column(name = "responsibility",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String responsibility;
    
    @Column(name = "influence",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String influence;
    
    @Column(name = "interest", length = 64,columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String interest;

    @Column(name = "raciforsyschanges",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String raciforsyschanges;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSLARoleName() {
		return SLARoleName;
	}

	public void setSLARoleName(String SLARoleName) {
		this.SLARoleName = SLARoleName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getResponsibility() {
		return responsibility;
	}

	public void setResponsibility(String responsibility) {
		this.responsibility = responsibility;
	}

	public String getInfluence() {
		return influence;
	}

	public void setInfluence(String influence) {
		this.influence = influence;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public String getRaciforsyschanges() {
		return raciforsyschanges;
	}

	public void setRaciforsyschanges(String raciforsyschanges) {
		this.raciforsyschanges = raciforsyschanges;
	} 
    



	
}
