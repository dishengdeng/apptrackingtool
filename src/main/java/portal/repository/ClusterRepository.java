package portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import portal.entity.Cluster;


public interface ClusterRepository  extends JpaRepository<Cluster, Long>  {
    @Query("select t from Cluster t where t.clusterName = :clusterName")
    Cluster findByName(@Param("clusterName") String clusterName);
}
