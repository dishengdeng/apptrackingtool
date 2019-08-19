package portal.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
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



import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonView;

import portal.jsonview.Views;
import portal.models.ParameterModel;
import portal.models.RunReportModel;
import portal.utility.ParameterCondition;
import portal.utility.ReportLevelType;


@Entity
@Table(name = "Report")
public class Report {
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE) 
	@Column(name = "id",nullable = false,unique=true)
	@JsonView(Views.Public.class)
	private Long id;

    @Column(name = "reportName",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String reportName;
	
    @Column(name = "description",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String description;
    

    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "reportlevelrel",
        joinColumns = @JoinColumn(name = "report_id"),
        inverseJoinColumns = @JoinColumn(name = "level_id")
    )
    private Set<ReportLevel> reportlevels = new HashSet<ReportLevel>(); 
    

 
    
    @OneToMany(
            mappedBy = "report", 
            cascade = CascadeType.ALL, 
            fetch = FetchType.LAZY,
            orphanRemoval = true
        )
    private Set<Parameter> parameters = new HashSet<Parameter>();  
    
    @OneToOne(mappedBy = "report")
    private File tempate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<ReportLevel> getReportlevels() {
		return reportlevels;
	}

	public void setReportlevels(Set<ReportLevel> reportlevels) {
		this.reportlevels.addAll(reportlevels);
		reportlevels.forEach(obj->{
			obj.addReport(this);
		});
	}

	public void addReportLevel(ReportLevel reportLevel)
	{
		this.reportlevels.add(reportLevel);
	}
	
	public void deleteReportLevel(ReportLevel reportLevel)
	{
		this.reportlevels.remove(reportLevel);
	}
 

	public File getTempate() {
		return tempate;
	}

	public void setTempate(File tempate) {
		this.tempate = tempate;
	}



	public Set<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(Set<Parameter> parameters) {
		this.parameters.addAll(parameters);
		parameters.forEach(obj->{
			obj.setReport(this);
		});
	}

	public void addParameter(Parameter parameter)
	{
		this.parameters.add(parameter);
	}
	
	public void removeParameter(Parameter parameter)
	{
		this.parameters.remove(parameter);
	}
	
	public void removeAlldependence()
	{
		this.reportlevels.forEach(obj->{
			obj.deleteReport(this);
		});
		this.reportlevels=null;
		
	
	}
	
	public boolean isContainReportLevel(ReportLevelType reportLevelType)
	{
		boolean result=false;
		
		for(ReportLevel obj : this.reportlevels)
		{
			if(obj.getReportLevelType().equals(reportLevelType)) result = true;
		}
			
		
		return result;
	}
	
	public RunReportModel<List<ParameterModel>> getParametersArray()
	{
		RunReportModel<List<ParameterModel>> reportmodel=new  RunReportModel<List<ParameterModel>>();
		reportmodel.setId(this.id);
		List<ParameterModel> parameters=new ArrayList<ParameterModel>();
		for(Parameter parameter:this.parameters)
		{
			ParameterModel model=new ParameterModel();
			model.setId(parameter.getId());
			model.setName(parameter.getName());
			model.setType(parameter.getType());
			model.setLabel(parameter.getLabel());
			model.setValue("");
			parameters.add(model);
			
			if(parameter.getCondition().equals(ParameterCondition.Between))
			{
				try {
					ParameterModel cloneModel=(ParameterModel) model.clone();
					cloneModel.setName("To"+cloneModel.getName());
					parameters.add(cloneModel);
				}
				catch(CloneNotSupportedException ex)
				{
					Logger.getLogger(Report.class.getName()).log(Level.SEVERE, ex.getMessage());
				}

			}
		}
		reportmodel.setParameters(parameters);
		return reportmodel;
		
	}
}
