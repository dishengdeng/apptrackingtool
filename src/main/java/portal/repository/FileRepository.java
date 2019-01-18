package portal.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import portal.entity.File;
import portal.utility.FileType;

public abstract interface FileRepository extends JpaRepository<File, Long>{
	
    

    @Query("select t from File t where t.filetype = :type and t.attachment=:filename")
    File findByTypeAndName(@Param("type") FileType type ,@Param("filename") String filename);
 

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Modifying    
    @Query("delete from File t where t.filetype = :type and t.attachment=:filename")
    void deleteByTypeAndName(@Param("type") FileType type,@Param("filename") String filename);;
    

}
