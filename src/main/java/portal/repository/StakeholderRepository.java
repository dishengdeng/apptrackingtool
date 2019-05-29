package portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import portal.entity.Department;
import portal.entity.SLARole;
import portal.entity.Stakeholder;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


public interface StakeholderRepository  extends JpaRepository<Stakeholder, Long>  {
    @Query("select t from Stakeholder t where t.stakeholderName = :stakeholderName")
    Stakeholder findByName(@Param("stakeholderName") String stakeholderName);
    
    @Query("select t from Stakeholder t where t.department = :department")
    List<Stakeholder> findByDepartment(@Param("department") Department department);
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Modifying    
    @Query("update Stakeholder t set t.department=null where t.department = :department")
    void removeDepartment(@Param("department") Department department);
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Modifying    
    @Query("update Stakeholder t set t.role=null where t.role = :role")
    void removeRole(@Param("role") SLARole role);
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Modifying    
    @Query("update Stakeholder t "
    		+ "set t.stakeholderName=:stakeholderName, "
    		+ "t.note=:note, "
    		+ "t.firstname=:firstname, "
    		+ "t.lastname=:lastname, "
    		+ "t.address=:address, "
    		+ "t.phone=:phone, "
    		+ "t.position=:position, "
    		+ "t.email=:email, "
    		+ "t.influence=:influence, "
    		+ "t.interest=:interest, "
    		+ "t.raciforsyschanges=:raciforsyschanges "
    		+ "where t.id = :id")    
    void updateDetail(@Param("stakeholderName") String stakeholderName,@Param("note") String note,
    		@Param("firstname") String firstname,@Param("lastname") String lastname,
    		@Param("address") String address,@Param("phone") String phone,
    		@Param("position") String position,@Param("email") String email,
    		@Param("influence") String influence,@Param("interest") String interest,
    		@Param("raciforsyschanges") String raciforsyschanges, @Param("id") Long id	
    		);
}