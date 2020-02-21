package portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import portal.entity.Zaclist;





public interface ZaclistRepository extends JpaRepository<Zaclist, Long>{
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Modifying	
	@Query(value="insert into zaclist (department_id,zac_id,zacmap_id,zacfield_id) values (:department,:zac,:zacmap,:zacfield)", nativeQuery = true)
    public Zaclist saveZaclist(@Param("department") Long department,@Param("zac") Long zac,@Param("zacmap") Long zacmap,@Param("zacfield") Long zacfield);
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Modifying	
	@Query(value="update zaclist set zacfield_id=:zacfield where id=:id", nativeQuery = true)
    public Zaclist updateZaclist(
    		@Param("zacfield") Long zacfield,
    		@Param("id") Long id);
}
