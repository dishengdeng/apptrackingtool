package portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import portal.entity.License;



public interface LicenseRepository  extends JpaRepository<License, Long>  {
//    @Query("select t from License t where t.licenseNumber = :licenseNumber")
//    License findByNumber(@Param("licenseNumber") String licenseNumber);
//    
//    @Query("select t from License t where t.appInstance = :appInstance")
//    License findByAppInstance(@Param("appInstance") AppInstance appInstance);
//    
//    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
//    @Modifying    
//    @Query("update License t set t.appInstance=null where t.appInstance = :appInstance")
//    void removeAppInstance(@Param("appInstance") AppInstance appInstance);
//    
//    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
//    @Modifying    
//    @Query("update License t set t.appInstance=:appInstance where t.id = :id")
//    void updateAppInstance(@Param("appInstance") AppInstance appInstance,@Param("id") Long id);
}