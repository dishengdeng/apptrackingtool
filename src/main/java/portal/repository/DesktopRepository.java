package portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import portal.entity.AppInstance;
import portal.entity.Desktop;


public interface DesktopRepository  extends JpaRepository<Desktop, Long>  {
    @Query("select t from Desktop t where t.desktopName = :desktopName")
    Desktop findByName(@Param("desktopName") String desktopName);
    
    @Query("select t from Desktop t where t.appInstance = :appInstance")
    Desktop findByAppInstance(@Param("appInstance") AppInstance appInstance);
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Modifying    
    @Query("update Desktop t set t.appInstance=null where t.appInstance = :appInstance")
    void removeAppInstance(@Param("appInstance") AppInstance appInstance);
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Modifying    
    @Query("update Desktop t set t.appInstance=:appInstance where t.id = :id")
    void updateAppInstance(@Param("appInstance") AppInstance appInstance,@Param("id") Long id); 
}