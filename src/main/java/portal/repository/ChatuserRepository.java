package portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import portal.entity.Chatuser;



public interface ChatuserRepository extends JpaRepository<Chatuser, Long>{
    @Query("select t from Chatuser t where t.username = :username")
    Chatuser findByName(@Param("username") String username);
}
