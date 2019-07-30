package portal.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.annotation.JsonView;

import portal.jsonview.Views;
import portal.utility.ParameterCondition;
import portal.utility.ParameterType;

@Entity
@Table(name = "Parameter")
public class Parameter{
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE) 
	@Column(name = "id",nullable = false,unique=true)
	@JsonView(Views.Public.class)
	private Long id;
	
    @Column(name = "name",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String name;
    
    @Column(name = "label")
    @JsonView(Views.Public.class)
	private String label;
    
    @Column(name = "type")
    @JsonView(Views.Public.class)
	private ParameterType type;
    
    @Column(name = "condition")
    @JsonView(Views.Public.class)
	private ParameterCondition condition;
    
    @Column(name = "value")
    @JsonView(Views.Public.class)
	private String value;

	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "report_id",referencedColumnName="id")
    private Report report;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ParameterType getType() {
		return type;
	}

	public void setType(ParameterType type) {
		this.type = type;
	}

	public ParameterCondition getCondition() {
		return condition;
	}

	public void setCondition(ParameterCondition condition) {
		this.condition = condition;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Report getReport() {
		return report;
	}

	public void setReport(Report report) {
		this.report = report;
	}
    
    public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void removeAlldependence()
    {
    	if(!ObjectUtils.isEmpty(this.report)) this.report.removeParameter(this);
    	this.setReport(null);
    }


}

