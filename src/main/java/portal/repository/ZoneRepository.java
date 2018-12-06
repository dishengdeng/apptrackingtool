package portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import portal.entity.Zone;

public interface ZoneRepository  extends JpaRepository<Zone, Long>  {
    @Query("select t from Zone t where t.zoneName = :zoneName")
    Zone findByName(@Param("zoneName") String zoneName);
}
