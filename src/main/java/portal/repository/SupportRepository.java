package portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import portal.entity.Support;
import portal.utility.SupportType;



public interface SupportRepository  extends JpaRepository<Support, Long>  {
    @Query("select t from Support t where t.supportName = :supportName")
    Support findByName(@Param("supportName") String supportName);
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Modifying    
    @Query("update Support t "
    		+ "set t.supportName=:supportName, "
    		+ "t.secondarysupport=:secondarysupport, "
    		+ "t.othersupport=:othersupport, "
    		+ "t.appowner=:appowner, "
    		+ "t.trainer=:trainer, "
    		+ "t.sme=:sme, "
    		+ "t.businesslead=:businesslead, "
    		+ "t.coes=:coes, "
    		+ "t.ahshours=:ahshours, "
    		+ "t.useradmin=:useradmin, "
    		+ "t.sysadmin=:sysadmin, "
    		+ "t.networksupport=:networksupport, "
    		+ "t.supporttype=:supporttype, "
    		+ "t.commonissue=:commonissue, "
    		+ "t.phone=:phone, "
    		+ "t.email=:email, "
    		+ "t.note=:note, "
    		+ "t.location=:location "
    		+ "where t.id = :id")    
    void updateDetail(@Param("supportName") String supportName,@Param("secondarysupport") String secondarysupport,
    		@Param("othersupport") String othersupport,@Param("appowner") String appowner,
    		@Param("trainer") String trainer,@Param("sme") String sme,
    		@Param("businesslead") String businesslead,@Param("coes") String coes,
    		@Param("ahshours") String ahshours,@Param("useradmin") String useradmin,
    		@Param("sysadmin") String sysadmin,@Param("networksupport") String networksupport,
    		@Param("supporttype") SupportType supporttype,@Param("commonissue") String commonissue,
    		@Param("phone") String phone,@Param("email") String email,
    		@Param("note") String note,@Param("location") String location,
    		@Param("id") Long id);
    
}