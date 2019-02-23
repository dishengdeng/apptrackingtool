package portal.service.Impl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import portal.repository.FileRepository;
import portal.service.FileService;
import portal.service.SecurityService;
import portal.utility.Convertor;
import portal.utility.FileType;

@Service
public class FileServiceImpl implements FileService{
	
	@Autowired
	private FileRepository fileRepository;

	@Autowired
	private SecurityService securityService;
	
	@Override
	public portal.entity.File uploadFile(MultipartFile file, String UPLOAD_FOLDER,String FileTypeId,portal.entity.File fileEntity) {
		portal.entity.File result= fileEntity;
		
		try
		{	if(!file.getOriginalFilename().isEmpty())
			{
				boolean isFileExist=new File(getfileName(UPLOAD_FOLDER,FileTypeId,fileEntity.getAttachment())).exists();
				byte[] bytes = file.getBytes();
				Path path = Paths.get(getfileName(UPLOAD_FOLDER,FileTypeId,file.getOriginalFilename()));
				Files.write(path, bytes);

				
				if(!isFileExist)
				{
					fileEntity.setCreatedat(Convertor.JavaCurrentDate());
					fileEntity.setCreatedby(securityService.findLoggedInUsername());
					result= fileRepository.save(fileEntity);
				}
			}
			
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ResponseEntity<Resource> downloadFile(String UPLOAD_FOLDER,String FileTypeId,portal.entity.File fileEntity, HttpServletRequest request) {
		  // Load file as Resource
        Resource resource = loadFileAsResource(getfileName(UPLOAD_FOLDER,FileTypeId,fileEntity.getAttachment()));
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
		                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileEntity.getAttachment() + "\"")
		                .body(resource);
        }
        else
        {
	        return new ResponseEntity<Resource>(HttpStatus.NO_CONTENT);
	        				
        }

	}
	
	@Override
	public boolean removeFile(String UPLOAD_FOLDER, String FileTypeId,portal.entity.File fileEntity) {
		
		File file =  new File(getfileName(UPLOAD_FOLDER,FileTypeId,fileEntity.getAttachment()));
		if(file.delete())
		{

			fileEntity.removeAllDependence();
			fileRepository.delete(fileEntity);
			return true;
		}
		else
		{
			return false;
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
		return UPLOAD_FOLDER+Long.valueOf(id)+"_"+getFileName(filename);
	}

	@Override
	public String getFileName(String fileOriginalName) {
		
		return FilenameUtils.getName(fileOriginalName);
	}

	@Override
	public portal.entity.File findByTypeAndName(String fileName, FileType type) {

		return fileRepository.findByTypeAndName(type, fileName);
	}

	@Override
	public portal.entity.File findById(Long id) {
	
		return fileRepository.findOne(id);
	}



}

	




	


