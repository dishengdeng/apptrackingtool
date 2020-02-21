package portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import portal.entity.Department;
import portal.entity.Zacfield;

public interface ZacfieldRepository extends JpaRepository<Zacfield, Long>{

	@Query("select t from Zacfield t where t.department=:department")
	List<Zacfield> findbyDepartment(@Param("department") Department department);
	
	@Query("select t from Zacfield t where t.department=:department and t.fieldName=:fieldName")
	Zacfield findbyDepartmentAndFieldName(@Param("department") Department department,@Param("fieldName") String fieldName);
}
