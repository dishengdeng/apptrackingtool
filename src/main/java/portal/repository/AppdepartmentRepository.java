package portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import portal.entity.Appdepartment;
import portal.entity.Department;


public interface AppdepartmentRepository extends JpaRepository<Appdepartment, Long>{
	@Query("select t from Appdepartment t inner join t.application k where t.department=:department and k.AppName = :appName")
	Appdepartment findbyAppNameAndDepartment(@Param("department") Department department,@Param("appName") String appName);
	
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Modifying	
	@Query(value="insert into appdepartsite (appdepartment_id,site_id) values (:appdepartment,:site)", nativeQuery = true)
	void saveSite(@Param("appdepartment") Long appdepartment,@Param("site") long site);
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Modifying	
	@Query(value="insert into appdepartvendor (appdepartment_id,vendor_id) values (:appdepartment,:vendor)", nativeQuery = true)
	void saveVendor(@Param("appdepartment") Long appdepartment,@Param("vendor") long vendor);

}
