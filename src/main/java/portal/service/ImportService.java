package portal.service;



import java.util.concurrent.CompletableFuture;

import org.json.JSONArray;

import org.springframework.scheduling.annotation.Async;

import org.springframework.web.multipart.MultipartFile;


import portal.entity.Department;

import portal.utility.InvalidTemplateFormatException;


public interface ImportService {
	@Async("importTaskExecutor")
	public CompletableFuture<JSONArray> getAppInventory(MultipartFile file) throws InvalidTemplateFormatException, Exception;
	@Async("importTaskExecutor")
	public void testExcutor() throws Exception;
	
	@Async("importTaskExecutor")
	public void importAppdepartment(JSONArray importData,Department department) throws Exception;
	
	@Async("importTaskExecutor")
	public void importAppdepartmentWithSingleThread(JSONArray importData,Department department) throws Exception;
	
	@Async("importTaskExecutor")
	public CompletableFuture<JSONArray> getStakeholders(MultipartFile file) throws InvalidTemplateFormatException, Exception;
	
	@Async("importTaskExecutor")
	public void importStakeholderext(JSONArray importData,Department department) throws Exception;
	
	@Async("importTaskExecutor")
	public void importStakeholderextWiteSingleThread(JSONArray importData,Department department) throws Exception;
	
	
	@Async("importTaskExecutor")
	public CompletableFuture<JSONArray> getZacs(MultipartFile file,Department department) throws InvalidTemplateFormatException, Exception;
	
	@Async("importTaskExecutor")
	public void importZacmap(JSONArray importData,Department department) throws Exception;
	
	@Async("importTaskExecutor")
	public void importZacmapWithSingleThread(JSONArray importData,Department department) throws Exception;
}
