package portal.repository;





import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import portal.entity.SLA;




public interface SLARepository  extends JpaRepository<SLA, Long>  {
    @Query("select t from SLA t where t.slaName = :slaName")
    SLA findByName(@Param("slaName") String slaName);

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Modifying
    @Query("update AppInstance t set t.sla=:sla where t.id = :id")
    void updateAppIstanceSLA(@Param("id") Long id,@Param("sla") SLA sla);
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Modifying
    @Query("update AppInstance t set t.sla=null where t.sla=:sla")
    void removeAllSLA(@Param("sla") SLA sla);

    
   
    
}