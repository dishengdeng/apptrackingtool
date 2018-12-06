package portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import portal.entity.AppInstance;
import portal.entity.Cluster;
import portal.entity.Server;


public interface ServerRepository  extends JpaRepository<Server, Long>  {
    @Query("select t from Server t where t.serverName = :serverName")
    Server findByName(@Param("serverName") String serverName);
    
    @Query("select t from Server t where t.cluster = :cluster")
    List<Server> findByCluster(@Param("cluster") Cluster cluster);
    
    @Query("select t from Server t where t.appInstance = :appInstance")
    Server findByAppInstance(@Param("appInstance") AppInstance appInstance);
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Modifying    
    @Query("update Server t set t.appInstance=null where t.appInstance = :appInstance")
    void removeAppInstance(@Param("appInstance") AppInstance appInstance);
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Modifying    
    @Query("update Server t set t.appInstance=:appInstance where t.id = :id")
    void updateAppInstance(@Param("appInstance") AppInstance appInstance,@Param("id") Long id); 
}
