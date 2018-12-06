package portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import portal.entity.AppInstance;
import portal.entity.Company;



public interface CompanyRepository  extends JpaRepository<Company, Long>  {
    @Query("select t from Company t where t.companyName = :companyName")
    Company findByName(@Param("companyName") String companyName);
    
    @Query("select t from Company t where t.appInstance = :appInstance")
    Company findByAppInstance(@Param("appInstance") AppInstance appInstance);
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Modifying    
    @Query("update Company t set t.appInstance=null where t.appInstance = :appInstance")
    void removeAppInstance(@Param("appInstance") AppInstance appInstance);
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Modifying    
    @Query("update Company t set t.appInstance=:appInstance where t.id = :id")
    void updateAppInstance(@Param("appInstance") AppInstance appInstance,@Param("id") Long id);     
}