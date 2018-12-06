package portal.service;

import java.util.List;

import portal.entity.Cluster;
import portal.models.ClusterModel;



public interface ClusterService {
	public Cluster addCluster(Cluster cluster);
    void delete(Cluster cluster);
    List<Cluster> getAll();
    List<ClusterModel> getAllCluster();
    Cluster getByName(String clusterName);
    Cluster getById(Long id);
    Cluster updateCluster(Cluster cluster);	
}
