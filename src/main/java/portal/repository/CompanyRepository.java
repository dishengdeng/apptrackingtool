package portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import portal.entity.Company;



public interface CompanyRepository  extends JpaRepository<Company, Long>  {
    @Query("select t from Company t where t.companyName = :companyName")
    Company findByName(@Param("companyName") String companyName);
      
    
//    @Query("select t from Company t where t.application = :application")
//    Company findByApplication(@Param("application") Application application);
//    
//    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
//    @Modifying    
//    @Query("update Company t set t.application=null where t.application = :application")
//    void removeApplication(@Param("application") Application application);
//    
//    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
//    @Modifying    
//    @Query("update Company t set t.application=:application where t.id = :id")
//    void updateApplication(@Param("application") Application application,@Param("id") Long id);  
    
 
    
}