package portal.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import portal.entity.Application;

public interface AppRepository extends JpaRepository<Application, Long>  {
    @Query("select t from Application t where t.AppName = :appName")
    Application findByName(@Param("appName") String appName);
    
    @Query("select t from Application t where t.id = :id")
    Application findByApp(@Param("id") Long id);
    
}
