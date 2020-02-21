package portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import portal.entity.Zac;

public interface ZacRepository extends JpaRepository<Zac, Long>{
    @Query("select t from Zac t where t.rate = :rate")
    Zac findByName(@Param("rate") String rate);
}
