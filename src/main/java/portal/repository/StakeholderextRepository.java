package portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import portal.entity.Department;
import portal.entity.Stakeholderext;

public interface StakeholderextRepository extends JpaRepository<Stakeholderext, Long>{
	@Query("select t from Stakeholderext t inner join t.stakeholder k where t.department=:department and k.stakeholderName = :stakeholderName")
	Stakeholderext findbyStakeholderNameAndDepartment(@Param("department") Department department,@Param("stakeholderName") String appName);
}
