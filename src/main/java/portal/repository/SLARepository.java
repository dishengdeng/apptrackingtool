package portal.repository;





import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import portal.entity.AppInstance;
import portal.entity.SLA;




public interface SLARepository  extends JpaRepository<SLA, Long>  {
    @Query("select t from SLA t where t.slaName = :slaName")
    SLA findByName(@Param("slaName") String slaName);

    @Query("select t from SLA t where t.appInstance = :appInstance")
    SLA findByAppInstance(@Param("appInstance") AppInstance appInstance);

    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Modifying    
    @Query("update SLA t set t.appInstance=null where t.appInstance = :appInstance")
    void removeAppInstance(@Param("appInstance") AppInstance appInstance);
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Modifying    
    @Query("update SLA t set t.appInstance=:appInstance where t.id = :id")
    void updateAppInstance(@Param("appInstance") AppInstance appInstance,@Param("id") Long id);   
    
}