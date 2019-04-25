package portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



import portal.entity.Contract;




public interface ContractRepository  extends JpaRepository<Contract, Long>  {
    @Query("select t from Contract t where t.contractName = :contractName")
    Contract findByName(@Param("contractName") String contractName);

//    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
//    @Modifying
//    @Query("update AppInstance t set t.contract=:contract where t.id = :id")
//    void updateAppIstanceContract(@Param("id") Long id,@Param("contract") Contract contract);
//    
//    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
//    @Modifying
//    @Query("update AppInstance t set t.contract=null where t.contract=:contract")
//    void removeAllContract(@Param("contract") Contract contract);
    
}