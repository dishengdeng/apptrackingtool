package portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import portal.entity.Support;



public interface SupportRepository  extends JpaRepository<Support, Long>  {
    @Query("select t from Support t where t.supportName = :supportName")
    Support findByName(@Param("supportName") String supportName);
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Modifying
    @Query("update AppInstance t set t.support=null where t.support = :support")
    void removeAllSupport(@Param("support") Support support);
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Modifying
    @Query("update AppInstance t set t.support=:support where t.id = :id")
    void updateAppIstanceSupport(@Param("id") Long id,@Param("support") Support support);
}