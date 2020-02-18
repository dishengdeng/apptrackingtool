package portal.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import portal.entity.Site;
import portal.entity.Stakeholder;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


public interface StakeholderRepository  extends JpaRepository<Stakeholder, Long>  {
    @Query("select t from Stakeholder t where t.stakeholderName = :stakeholderName")
    Stakeholder findByName(@Param("stakeholderName") String stakeholderName);
    

    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Modifying    
    @Query("update Stakeholder t "
    		+ "set t.stakeholderName=:stakeholderName, "
    		+ "t.note=:note, "
    		+ "t.site=:site, "
    		+ "t.phone=:phone, "
    		+ "t.position=:position, "
    		+ "t.businessunit=:businessunit, "
    		+ "t.email=:email "
    		+ "where t.id = :id")    
    void updateDetail(@Param("stakeholderName") String stakeholderName,@Param("note") String note,
    		@Param("site") Site site,@Param("phone") Long phone,
    		@Param("position") String position,@Param("businessunit") String businessunit,@Param("email") String email,
    		@Param("id") Long id	
    		);
    

}