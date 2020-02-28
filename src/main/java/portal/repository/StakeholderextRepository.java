package portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import portal.entity.Department;
import portal.entity.SLARole;
import portal.entity.Stakeholder;
import portal.entity.Stakeholderext;

public interface StakeholderextRepository extends JpaRepository<Stakeholderext, Long>{
	@Query("select t from Stakeholderext t where t.department=:department and t.role=:role and t.stakeholder = :stakeholder")
	Stakeholderext findbyStakeholderNameAndDepartment(@Param("department") Department department,@Param("role") SLARole role,@Param("stakeholder") Stakeholder stakeholder);
}
