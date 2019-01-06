package portal.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import portal.entity.Role;


public interface RoleRepository extends JpaRepository<Role, Long>  {
    @Query("select t from Role t where t.name = :name")
    Role findByName(@Param("name") String name);
}
