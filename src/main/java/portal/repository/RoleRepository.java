package portal.repository;



import org.springframework.data.jpa.repository.JpaRepository;


import portal.entity.Role;


public interface RoleRepository extends JpaRepository<Role, Long>  {
    
}
