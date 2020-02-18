package portal.service;



import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import portal.entity.File;
import portal.utility.FileType;

public interface FileService {
	public File uploadFile(MultipartFile file,String UPLOADED_FOLDER,String id,File fileEntity);
	ResponseEntity<Resource> downloadFile(String UPLOAD_FOLDER,String fileTypeId,portal.entity.File file, HttpServletRequest request);
	public boolean removeFile(String UPLOAD_FOLDER,String fileTypeId,portal.entity.File file);
	public String getFileName(String fileOriginalName);
	
	public File findByTypeAndName(String fileName,FileType type);
	
	public File findById(Long id);
	
	ResponseEntity<Resource> downloadTemplate(String filepath,String filename,HttpServletRequest request);
	


}
