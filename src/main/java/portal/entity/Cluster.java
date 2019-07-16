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
@Table(name = "ServerCluster")
public class Cluster {
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE) 
	@Column(name = "id",nullable = false,unique=true)
	@JsonView(Views.Public.class)
	private Long id;

    @Column(name = "clusterName",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String clusterName;
	
    @Column(name = "description",columnDefinition="VARCHAR(250)")
    @JsonView(Views.Public.class)
	private String description;
    
    @OneToMany(
            mappedBy = "cluster", 
            cascade = CascadeType.ALL, 
            orphanRemoval = true
        )
    private Set<Server> servers = new HashSet<Server>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String zoneName) {
		this.clusterName = zoneName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Server> getServers() {
		return servers;
	}

	public void setServers(Set<Server> servers) {
		this.servers.addAll(servers);
		servers.forEach(obj->{
			obj.setCluster(this);
		});
	}
	
	public void addServer(Server server)
	{
		this.servers.add(server);
	}
	
	public void removeServer(Server server)
	{
		this.servers.remove(server);
	}

	public void removeAllDependence()
	{
		this.servers.forEach(obj->{
			obj.setCluster(null);
		});
		this.servers=null;
	}
	
}
