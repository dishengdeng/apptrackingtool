package portal.entity;

import java.util.HashSet;
import java.util.Set;

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
    
    @OneToMany(
            mappedBy = "role", 
            cascade = CascadeType.PERSIST,
            fetch=FetchType.LAZY
        )
    private Set<Stakeholderext> stakeholderexts = new HashSet<>();

    public SLARole()
    {}

    public SLARole(String _SLARoleName)
    {
    	this.SLARoleName=_SLARoleName;
    }
    
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


	
	public Set<Stakeholderext> getStakeholderexts() {
		return stakeholderexts;
	}

	public void setStakeholderexts(Set<Stakeholderext> stakeholderexts) {
		this.stakeholderexts.addAll(stakeholderexts);
		stakeholderexts.forEach(obj->{
			obj.setRole(this);
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
	
	public void removeAllDedenpence()
	{
		this.stakeholderexts.forEach(obj->{
			obj.setRole(null);
		});
		this.stakeholderexts=null;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(this==obj) return true;
		
		if(obj==null) return false;
		
		if(this.getClass()!=obj.getClass()) return false;
		
		SLARole other = (SLARole) obj;
		
		if(this.getId()!=other.getId()) return false;
		
		return true;
	}


	
}
