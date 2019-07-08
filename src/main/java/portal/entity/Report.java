package portal.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import portal.jsonview.Views;


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
    
	public void removeAlldependence()
	{
		this.reportlevels.forEach(obj->{
			obj.deleteReport(this);
		});
		this.reportlevels=null;
	}
}
