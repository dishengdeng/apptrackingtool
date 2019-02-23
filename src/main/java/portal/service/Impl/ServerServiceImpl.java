package portal.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import portal.entity.AppInstance;
import portal.entity.Cluster;
import portal.entity.File;
import portal.entity.Server;
import portal.models.ServerModel;
import portal.repository.ServerRepository;
import portal.service.AppInstanceService;
import portal.service.FileService;
import portal.service.ServerService;
@Service
public class ServerServiceImpl implements ServerService{
	@Autowired
	private ServerRepository serverRepository;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private AppInstanceService appInstanceService;
	
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

	@Override
	public List<AppInstance> getAppInstancesNotContainServer(Server server) {
		List<AppInstance> appInstances=appInstanceService.getAll();
		appInstances.removeIf(obj->obj.getServers().contains(server));
		return appInstances;
	}

	@Override
	public void removFiles(String upload_foler, Server server) {
		if(server.getFiles().size()>0)
		{
			for(File file:server.getFiles())
			{
				fileService.removeFile(upload_foler,server.getId().toString(), file);
			}			
		}
	}

}
