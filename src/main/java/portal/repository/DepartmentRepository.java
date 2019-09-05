package portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import portal.entity.Department;



public interface DepartmentRepository  extends JpaRepository<Department, Long>  {
    @Query("select t from Department t where t.departmentName = :departmentName")
    Department findByName(@Param("departmentName") String departmentName);    
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Modifying
    @Query("update AppInstance t set t.department=:department where t.id = :id")
    void updateAppIstanceDepartment(@Param("id") Long id,@Param("department") Department department);
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Modifying
    @Query("update AppInstance t set t.department=null where t.department=:department")
    void removeAllDepartment(@Param("department") Department department);
    

}