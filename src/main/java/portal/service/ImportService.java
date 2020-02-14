package portal.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.json.JSONArray;
import org.springframework.scheduling.annotation.Async;

import org.springframework.web.multipart.MultipartFile;

import portal.entity.Appdepartment;
import portal.entity.Department;
import portal.utility.InvalidTemplateFormatException;


public interface ImportService {
	@Async("importTaskExecutor")
	public CompletableFuture<JSONArray> getAppInventory(MultipartFile file) throws InvalidTemplateFormatException, Exception;
	@Async("importTaskExecutor")
	public void testExcutor() throws Exception;
	

	public List<Appdepartment> importAppdepartment(JSONArray importData,Department department) throws Exception;
}
