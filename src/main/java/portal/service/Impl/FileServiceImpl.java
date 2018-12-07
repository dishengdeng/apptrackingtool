package portal.service.Impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import portal.service.FileService;

@Service
public class FileServiceImpl implements FileService{

	@Override
	public boolean uploadFile(MultipartFile file, String UPLOAD_FOLDER,String id) {
		boolean result= false;
		try
		{
			byte[] bytes = file.getBytes();
			Path path = Paths.get(getfileName(UPLOAD_FOLDER,id,file.getOriginalFilename()));
			Files.write(path, bytes);
			result = true;
			
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ResponseEntity<Resource> downloadFile(String UPLOAD_FOLDER,String id,String file, HttpServletRequest request) {
		  // Load file as Resource
        Resource resource = loadFileAsResource(getfileName(UPLOAD_FOLDER,id,file));
        if(resource!=null)
        {

		        // Try to determine file's content type
		        String contentType = null;
		        try {
		            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		        } catch (IOException ex) {
		        	ex.printStackTrace();
		        }
		
		        // Fallback to the default content type if type could not be determined
		        if(contentType == null) {
		            contentType = "application/octet-stream";
		        }
		
		        return ResponseEntity.ok()
		                .contentType(MediaType.parseMediaType(contentType))
		                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
		                .body(resource);
        }
        else
        {
	        return new ResponseEntity<Resource>(HttpStatus.NO_CONTENT);
	        				
        }

	}
	
	private Resource loadFileAsResource(String fileName)
	{
		Resource resource = null;
		try {
            Path filePath = Paths.get(fileName).normalize();
            resource = new UrlResource(filePath.toUri());
            if(!resource.exists()) {
                resource=null;
            }
        } catch (MalformedURLException ex) {
        	ex.printStackTrace();
        }
		
		return resource;
	}
	
	private String getfileName(String UPLOAD_FOLDER,String id,String filename)
	{
		return UPLOAD_FOLDER+Long.valueOf(id)+"_"+filename;
	}
	

}
