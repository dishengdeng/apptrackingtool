package portal.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	public boolean uploadFile(MultipartFile file,String UPLOADED_FOLDER,String id);
	ResponseEntity<Resource> downloadFile(String UPLOAD_FOLDER,String id,String file, HttpServletRequest request);
	public boolean removeFile(String UPLOAD_FOLDER,String id,String fileName);
	public String getFileName(String fileOriginalName);
}
