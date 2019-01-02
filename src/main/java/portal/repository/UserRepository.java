package portal.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import portal.entity.User;

public interface UserRepository extends JpaRepository<User, Long>  {
    @Query("select t from User t where t.username = :username")
    User findByName(@Param("username") String username);
    
}
