package portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import portal.entity.Department;



public interface DepartmentRepository  extends JpaRepository<Department, Long>  {
    @Query("select t from Department t where t.departmentName = :departmentName")
    Department findByName(@Param("departmentName") String departmentName);
}