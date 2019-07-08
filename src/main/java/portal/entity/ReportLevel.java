package portal.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import portal.jsonview.Views;
import portal.utility.ReportLevelType;

@Entity
@Table(name = "ReportLevel")
public class ReportLevel {
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE) 
	@Column(name = "id",nullable = false,unique=true)
	@JsonView(Views.Public.class)
	private Long id;
	
    @Column(name = "reportleveltype")
    @JsonView(Views.Public.class)
	private ReportLevelType reportLevelType;

    
    @ManyToMany(mappedBy = "reportlevels")
    private Set<Report> reports= new HashSet<Report>();
    
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ReportLevelType getReportLevelType() {
		return reportLevelType;
	}

	public void setReportLevelType(ReportLevelType reportLevelType) {
		this.reportLevelType = reportLevelType;
	}

	public Set<Report> getReports() {
		return reports;
	}

	public void setReports(Set<Report> reports) {
		this.reports.addAll(reports);
		reports.forEach(obj->{
			obj.addReportLevel(this);
		});
	}
	
	public void addReport(Report report)
	{
		this.reports.add(report);
	}
	
	
	public void deleteReport(Report report)
	{
		this.reports.remove(report);
	}
    
    public void removeAlldependence()
    {
    	this.reports.forEach(obj->{
    		obj.deleteReportLevel(this);
    	});
    	this.reports=null;
    }

}
