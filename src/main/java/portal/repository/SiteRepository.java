package portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import portal.entity.Site;
import portal.entity.Zone;

public interface SiteRepository  extends JpaRepository<Site, Long>  {
    @Query("select t from Site t where t.siteName = :siteName")
    Site findByName(@Param("siteName") String siteName);
    
    @Query("select t from Site t where t.zone = :zone")
    List<Site> findAllbyZone(@Param("zone") Zone zone);   
    
}
