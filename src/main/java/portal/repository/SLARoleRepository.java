package portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import portal.entity.SLARole;


public interface SLARoleRepository  extends JpaRepository<SLARole, Long>  {
    @Query("select t from SLARole t where t.SLARoleName = :SLARoleName")
    SLARole findByName(@Param("SLARoleName") String SLARoleName);
}