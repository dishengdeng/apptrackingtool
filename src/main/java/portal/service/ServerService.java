package portal.service;

import java.util.List;

import portal.entity.AppInstance;
import portal.entity.Cluster;
import portal.entity.Server;

import portal.models.ServerModel;



public interface ServerService {
	public Server addServer(Server server);
    void delete(Server server);
    List<Server> getAll();
    List<ServerModel> getAllServer();
    Server getByName(String serverName);
    Server getById(Long id);
    Server updateServer(Server server);
    List<Server> findbyCluster(Cluster cluster);
    
    List<Server> findByNotAssigned(AppInstance appInstance);
    void removeAppInstance(AppInstance appInstance);
    void updateAppInstance(AppInstance appInstance,Long id);
    
    Server findByAppInstance(AppInstance appInstance);
    
    List<AppInstance> getAppInstancesNotContainServer(Server server);
}
