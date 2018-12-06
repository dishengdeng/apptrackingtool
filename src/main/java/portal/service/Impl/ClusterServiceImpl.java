package portal.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import portal.entity.Cluster;
import portal.models.ClusterModel;
import portal.repository.ClusterRepository;
import portal.service.ClusterService;

@Service
public class ClusterServiceImpl implements ClusterService {

	@Autowired
	private ClusterRepository clusterRepository;
	@Override
	public Cluster addCluster(Cluster cluster) {
		
		return clusterRepository.save(cluster);
	}

	@Override
	public void delete(Cluster cluster) {
		
		clusterRepository.delete(cluster);
	}

	@Override
	public List<Cluster> getAll() {
		
		return clusterRepository.findAll();
	}

	@Override
	public List<ClusterModel> getAllCluster() {
		List<ClusterModel> clusterModels = new ArrayList<ClusterModel>();
		List<Cluster> clusters = clusterRepository.findAll();
		for(Cluster cluster:clusters)
		{
			ClusterModel clusterModel = new ClusterModel();
			clusterModel.setId(cluster.getId());
			clusterModel.setClusterName(cluster.getClusterName());
			clusterModel.setDescription(cluster.getDescription());
			clusterModels.add(clusterModel);
		}
		
		return clusterModels;
	}

	@Override
	public Cluster getByName(String clusterName) {
		
		return clusterRepository.findByName(clusterName);
	}

	@Override
	public Cluster getById(Long id) {
		
		return clusterRepository.findOne(id);
	}

	@Override
	public Cluster updateCluster(Cluster cluster) {
		
		return clusterRepository.saveAndFlush(cluster);
	}

}
