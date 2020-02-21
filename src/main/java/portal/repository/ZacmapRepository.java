package portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import portal.entity.Application;
import portal.entity.Department;
import portal.entity.Zacmap;

public interface ZacmapRepository extends JpaRepository<Zacmap, Long>{
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Modifying	
	@Query(value="insert into zacmap (application_id,detail) values (:application,:detail)", nativeQuery = true)
    public Zacmap saveZacmap(@Param("application") Long application,@Param("detail") String detail);
    
	@Query("select t from Zaclist t inner join t.department k inner join t.zacmap j where t.department=:department and j.application = :application")
	Zacmap findbyAppNameAndDepartment(@Param("department") Department department,@Param("application") Application application);
}
