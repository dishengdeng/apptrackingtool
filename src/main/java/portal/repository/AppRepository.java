package portal.repository;



import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import portal.entity.Application;


public interface AppRepository extends JpaRepository<Application, Long>  {
    @Query("select t from Application t where t.AppName = :appName")
    Application findByName(@Param("appName") String appName);
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Modifying
    @Query("update Application t set "
    		+ "t.AppName=:AppName, "
    		+ "t.status=:status, "
    		+ "t.AppVersion=:AppVersion, "
    		+ "t.AppType=:AppType, "
    		+ "t.AppAliase=:AppAliase, "
    		+ "t.AppPrerequisite=:AppPrerequisite, "
    		+ "t.AppPurpose=:AppPurpose, "
    		+ "t.AppDecomminsionDate=:AppDecomminsionDate, "
    		+ "t.AppComments=:AppComments, "
    		+ "t.notes=:notes, "    		
    		+ "t.AppGovernance=:AppGovernance, "
    		+ "t.AppSupportByCapSys=:AppSupportByCapSys"
    		+ " where t.id = :id")
    void saveDetails(@Param("AppName") String AppName,@Param("status") String status,
    		@Param("AppVersion") String AppVersion,@Param("AppType") String AppType,
    		@Param("AppAliase") String AppAliase,@Param("AppPrerequisite") String AppPrerequisite,
    		@Param("AppPurpose") String AppPurpose,@Param("AppDecomminsionDate") Date AppDecomminsionDate,
    		@Param("AppComments") String AppComments,@Param("notes") String notes,@Param("AppGovernance") String AppGovernance,
    		@Param("AppSupportByCapSys") String AppSupportByCapSys,@Param("id") Long id);
    

    
}
