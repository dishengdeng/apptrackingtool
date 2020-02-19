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
import javax.persistence.JoinColumn;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.annotation.JsonView;

import portal.jsonview.Views;


@Entity
@Table(name = "Stakeholder")
public class Stakeholder {
	
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE) 
	@Column(name = "id",nullable = false,unique=true)
	@JsonView(Views.Public.class)
	private Long id;

    @Column(name = "stakeholderName",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String stakeholderName;
	
    @Column(name = "note",columnDefinition="VARCHAR(1000)")
    @JsonView(Views.Public.class)
	private String note;
    

    @Column(name = "firstname",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String firstname;
    
    @Column(name = "lastname",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String lastname;
    
    @Column(name = "address",columnDefinition="VARCHAR(1000)")
    @JsonView(Views.Public.class)
	private String address;

    @Column(name = "phone",columnDefinition="number(10)")
    @JsonView(Views.Public.class)
	private Long phone;   

    @Column(name = "position",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String position; 
    
    @Column(name = "email",columnDefinition="VARCHAR(1000)")
    @JsonView(Views.Public.class)
	private String email;
    
    @Column(name = "businessunit",columnDefinition="VARCHAR(1000)")
    @JsonView(Views.Public.class)
	private String businessunit;
    
 

    
    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "site_id",referencedColumnName="id")
    private Site site;
    
    @OneToMany(
            mappedBy = "stakeholder", 
            cascade = CascadeType.ALL, 
            orphanRemoval = true,
            fetch=FetchType.EAGER
        )
    private Set<Stakeholderext> stakeholderexts = new HashSet<>();
    
    public Stakeholder()
    {}
    
    public Stakeholder(String _stakeholderName)
    {
    	this.stakeholderName=_stakeholderName;
    }    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStakeholderName() {
		return stakeholderName;
	}

	public void setStakeholderName(String stakeholderName) {
		this.stakeholderName = stakeholderName;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}


	public Long getPhone() {
		return phone;
	}

	public void setPhone(Long phone) {
		this.phone = phone;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}



	public String getBusinessunit() {
		return businessunit;
	}

	public void setBusinessunit(String businessunit) {
		this.businessunit = businessunit;
	}

	public Set<Stakeholderext> getStakeholderexts() {
		return stakeholderexts;
	}

	public void setStakeholderexts(Set<Stakeholderext> stakeholderexts) {
		this.stakeholderexts.addAll(stakeholderexts);
		stakeholderexts.forEach(obj->{
			obj.setStakeholder(this);
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




	
	
	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}



	


	public void removeAllDependence()
	{

		
		if(!ObjectUtils.isEmpty(this.site)) this.site.removeStakeholder(this);
		this.setSite(null);
		
		this.stakeholderexts.forEach(obj->{
			obj.setStakeholder(null);
		});
		this.stakeholderexts=null;

	}

	
	


	@Override
	public boolean equals(Object obj)
	{
		if(this==obj) return true;
		
		if(obj==null) return false;
		
		if(this.getClass()!=obj.getClass()) return false;
		
		Stakeholder other = (Stakeholder) obj;
		
		if(this.getId()!=other.getId()) return false;
		
		return true;
	}
}
