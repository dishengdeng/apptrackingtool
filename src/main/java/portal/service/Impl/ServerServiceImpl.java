package portal.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import portal.entity.AppInstance;
import portal.entity.Cluster;
import portal.entity.Server;
import portal.models.ServerModel;
import portal.repository.ServerRepository;
import portal.service.ServerService;
@Service
public class ServerServiceImpl implements ServerService{
	@Autowired
	private ServerRepository serverRepository;
	
	@Override
	public Server addServer(Server server) {
		
		return serverRepository.save(server);
	}

	@Override
	public void delete(Server server) {
		
		serverRepository.delete(server);
		
	}

	@Override
	public List<Server> getAll() {
		
		return serverRepository.findAll();
	}

	@Override
	public List<ServerModel> getAllServer() {
		// Will implement later when doing Restful APIs
		return null;
	}

	@Override
	public Server getByName(String serverName) {
		
		return serverRepository.findByName(serverName);
	}

	@Override
	public Server getById(Long id) {
		
		return serverRepository.findOne(id);
	}

	@Override
	public Server updateServer(Server server) {

		return serverRepository.saveAndFlush(server);
	}

	@Override
	public List<Server> findbyCluster(Cluster cluster) {
		
		return serverRepository.findByCluster(cluster);
	}

	@Override
	public List<Server> findByNotAssigned(AppInstance appInstance) {
		List<Server> servers = serverRepository.findAll();
		servers.removeIf(obj->obj.getAppInstance()!=null);
	
		
		return servers;
	}

	@Override
	public void removeAppInstance(AppInstance appInstance) {
		serverRepository.removeAppInstance(appInstance);
		
	}

	@Override
	public void updateAppInstance(AppInstance appInstance, Long id) {
		serverRepository.updateAppInstance(appInstance, id);
		
	}

	@Override
	public Server findByAppInstance(AppInstance appInstance) {

		return serverRepository.findByAppInstance(appInstance);
	}

}
