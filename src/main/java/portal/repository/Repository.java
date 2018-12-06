package portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import portal.entity.Support;



public interface Repository  extends JpaRepository<Support, Long>  {
    @Query("select t from Support t where t.supportName = :supportName")
    Support findByName(@Param("supportName") String supportName);
}