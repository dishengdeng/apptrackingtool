package portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import portal.entity.AppInstance;
import portal.entity.Application;
import portal.entity.Department;
import portal.entity.Support;



public interface AppInstanceRepository  extends JpaRepository<AppInstance, Long>  {
    @Query("select t from AppInstance t where t.appInstanceName = :appInstanceName")
    AppInstance findByName(@Param("appInstanceName") String appInstanceName);
 
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Modifying 
    @Query("update AppInstance t set t.department=null where t.id = :id")
    void removeDeparmentbyInstanceId(@Param("id") Long id);

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Modifying
    @Query("update AppInstance t set t.department=null where t.department = :department")
    void removeDeparment(@Param("department") Department department);
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Modifying 
    @Query("update AppInstance t set t.support=null where t.id = :id")
    void removeSupportbyInstanceId(@Param("id") Long id);

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Modifying
    @Query("update AppInstance t set t.support=null where t.support = :support")
    void removeSupport(@Param("support") Support support);
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Modifying
    @Query("update AppInstance t set t.application=null where t.application = :application")
    void removeApplication(@Param("application") Application application);
    
    @Query("select t from AppInstance t where t.application is null")
    List<AppInstance> findUnAssingedAppInstances();
}