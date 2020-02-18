package portal.service.Impl;


import java.util.ArrayList;
import java.util.Arrays;


import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import org.springframework.web.multipart.MultipartFile;

import portal.ImportDTO.AppInventoryDTO;
import portal.entity.Appdepartment;
import portal.entity.Department;
import portal.models.Alert;
import portal.repository.AppRepository;
import portal.repository.AppdepartmentRepository;
import portal.repository.CompanyRepository;
import portal.repository.SiteRepository;
import portal.service.ImportService;

import portal.utility.AppinventoryMap;
import portal.utility.ColumnHeaderValidator;
import portal.utility.InvalidTemplateFormatException;
import portal.utility.JSONObjectWithEmpty;
import portal.validator.AppInventoryImportValidator;

@Service
public class ImportServiceImp implements ImportService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ImportServiceImp.class);

	@Autowired
	private ColumnHeaderValidator columnHeaderValidator;

	@Autowired
	private AppdepartmentRepository appdepartmentRepository;
	@Autowired
	private AppRepository appRepository;
	@Autowired
	private SiteRepository siteRepository;
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private SimpMessageSendingOperations messagingTemplate;

	
	private final int threadPoolNumber=3;

	private ExecutorService execSvc = Executors.newFixedThreadPool(threadPoolNumber);
	
	@Override
	public CompletableFuture<JSONArray> getAppInventory(MultipartFile file) throws InvalidTemplateFormatException, Exception {
		Assert.notNull(file);
		LOGGER.info("getting App Inventory");
		Workbook workbook = WorkbookFactory.create(file.getInputStream());
		
		Sheet sheet = workbook.getSheetAt(0);
		
		DataFormatter dataFormatter = new DataFormatter();
		
		columnHeaderValidator.TemplateFormatValidate(sheet.getRow(0));
		
		JSONArray excelData = new JSONArray();
		for(Row row:sheet)
		{
			if(row.getRowNum()>0)
			{
				JSONObjectWithEmpty obj=new JSONObjectWithEmpty();
				obj.put(AppinventoryMap.ApplicationName.name(), 
						(new AppInventoryImportValidator<String>())
						.AppinventoryDataValidate(AppinventoryMap.ApplicationName,row.getCell(AppinventoryMap.ApplicationName.getColumnIndex()))
						);
				obj.put(AppinventoryMap.ApplicationType.name(), dataFormatter.formatCellValue(row.getCell(AppinventoryMap.ApplicationType.getColumnIndex())));
				obj.put(AppinventoryMap.ApplicationPurpose.name(), dataFormatter.formatCellValue(row.getCell(AppinventoryMap.ApplicationPurpose.getColumnIndex())));
				obj.put(AppinventoryMap.South.name(), 
						(new AppInventoryImportValidator<String>())
						.AppinventoryDataValidate(AppinventoryMap.South,row.getCell(AppinventoryMap.South.getColumnIndex()))
						);
				obj.put(AppinventoryMap.Calgary.name(),
						(new AppInventoryImportValidator<String>())
						.AppinventoryDataValidate(AppinventoryMap.Calgary,row.getCell(AppinventoryMap.Calgary.getColumnIndex()))
						);
				obj.put(AppinventoryMap.Central.name(),
						(new AppInventoryImportValidator<String>())
						.AppinventoryDataValidate(AppinventoryMap.Central,row.getCell(AppinventoryMap.Central.getColumnIndex()))
						);
				obj.put(AppinventoryMap.Edmonton.name(),
						(new AppInventoryImportValidator<String>())
						.AppinventoryDataValidate(AppinventoryMap.Edmonton,row.getCell(AppinventoryMap.Edmonton.getColumnIndex()))
						);
				obj.put(AppinventoryMap.North.name(),
						(new AppInventoryImportValidator<String>())
						.AppinventoryDataValidate(AppinventoryMap.North,row.getCell(AppinventoryMap.North.getColumnIndex()))
						);
				//getting sites
				List<String> sites=Arrays.asList(StringUtils.split(dataFormatter.formatCellValue(row.getCell(AppinventoryMap.Site.getColumnIndex())),";"));
				JSONArray sitesArray= new JSONArray();
				sites.forEach(site->{
					sitesArray.put(site);
				});
				
				obj.put(AppinventoryMap.Site.name(), sitesArray);
				obj.put(AppinventoryMap.BusinessLead.name(), dataFormatter.formatCellValue(row.getCell(AppinventoryMap.BusinessLead.getColumnIndex())));
				obj.put(AppinventoryMap.ApplicationOwner.name(), dataFormatter.formatCellValue(row.getCell(AppinventoryMap.ApplicationOwner.getColumnIndex())));
				obj.put(AppinventoryMap.Goverinplace.name(),
						(new AppInventoryImportValidator<String>())
						.AppinventoryDataValidate(AppinventoryMap.Goverinplace,row.getCell(AppinventoryMap.Goverinplace.getColumnIndex()))
						);
				obj.put(AppinventoryMap.Userbase.name(), dataFormatter.formatCellValue(row.getCell(AppinventoryMap.Userbase.getColumnIndex())));
				obj.put(AppinventoryMap.PowerUser.name(), dataFormatter.formatCellValue(row.getCell(AppinventoryMap.PowerUser.getColumnIndex())));
				obj.put(AppinventoryMap.FrontlineUser.name(), dataFormatter.formatCellValue(row.getCell(AppinventoryMap.FrontlineUser.getColumnIndex())));
				obj.put(AppinventoryMap.SubjectMatterExpert.name(), dataFormatter.formatCellValue(row.getCell(AppinventoryMap.SubjectMatterExpert.getColumnIndex())));
				obj.put(AppinventoryMap.Trainer.name(), dataFormatter.formatCellValue(row.getCell(AppinventoryMap.Trainer.getColumnIndex())));
				obj.put(AppinventoryMap.UserAdmin.name(), dataFormatter.formatCellValue(row.getCell(AppinventoryMap.UserAdmin.getColumnIndex())));
				obj.put(AppinventoryMap.SystemAdmin.name(), dataFormatter.formatCellValue(row.getCell(AppinventoryMap.SystemAdmin.getColumnIndex())));
				obj.put(AppinventoryMap.AppServerSupport.name(), dataFormatter.formatCellValue(row.getCell(AppinventoryMap.AppServerSupport.getColumnIndex())));
				obj.put(AppinventoryMap.DBServerSupport.name(), dataFormatter.formatCellValue(row.getCell(AppinventoryMap.DBServerSupport.getColumnIndex())));
				obj.put(AppinventoryMap.NetworkSupport.name(), dataFormatter.formatCellValue(row.getCell(AppinventoryMap.NetworkSupport.getColumnIndex())));
				//getting Vendors
				List<String> vendors=Arrays.asList(StringUtils.split(dataFormatter.formatCellValue(row.getCell(AppinventoryMap.Vendor.getColumnIndex())),";"));
				JSONArray vendorsArray= new JSONArray();
				vendors.forEach(vendor->{
					vendorsArray.put(vendor);
				});				
				
				obj.put(AppinventoryMap.Vendor.name(), vendorsArray);				
				obj.put(AppinventoryMap.Contractinplace.name(),
						(new AppInventoryImportValidator<String>())
						.AppinventoryDataValidate(AppinventoryMap.Contractinplace,row.getCell(AppinventoryMap.Contractinplace.getColumnIndex()))
						);
				obj.put(AppinventoryMap.Contractdetail.name(), dataFormatter.formatCellValue(row.getCell(AppinventoryMap.Contractdetail.getColumnIndex())));
				obj.put(AppinventoryMap.ExpirationDate.name(),
						(new AppInventoryImportValidator<String>())
						.AppinventoryDataValidate(AppinventoryMap.ExpirationDate,row.getCell(AppinventoryMap.ExpirationDate.getColumnIndex()))
						);
				obj.put(AppinventoryMap.Frequency.name(), dataFormatter.formatCellValue(row.getCell(AppinventoryMap.Frequency.getColumnIndex())));
				obj.put(AppinventoryMap.VendorSla.name(), 
						(new AppInventoryImportValidator<String>())
						.AppinventoryDataValidate(AppinventoryMap.VendorSla,row.getCell(AppinventoryMap.VendorSla.getColumnIndex()))
						);
				obj.put(AppinventoryMap.AhsItSla.name(), 
						(new AppInventoryImportValidator<String>())
						.AppinventoryDataValidate(AppinventoryMap.AhsItSla,row.getCell(AppinventoryMap.AhsItSla.getColumnIndex()))
						);
				obj.put(AppinventoryMap.ApplicationVersion.name(), dataFormatter.formatCellValue(row.getCell(AppinventoryMap.ApplicationVersion.getColumnIndex())));
				obj.put(AppinventoryMap.Broadmap.name(), dataFormatter.formatCellValue(row.getCell(AppinventoryMap.Broadmap.getColumnIndex())));
				obj.put(AppinventoryMap.IMP.name(), 
						(new AppInventoryImportValidator<String>())
						.AppinventoryDataValidate(AppinventoryMap.IMP,row.getCell(AppinventoryMap.IMP.getColumnIndex()))
						);
				obj.put(AppinventoryMap.Cshrecimit.name(), 
						(new AppInventoryImportValidator<String>())
						.AppinventoryDataValidate(AppinventoryMap.Cshrecimit,row.getCell(AppinventoryMap.Cshrecimit.getColumnIndex()))
						);
				obj.put(AppinventoryMap.Note.name(), dataFormatter.formatCellValue(row.getCell(AppinventoryMap.Note.getColumnIndex())));
				excelData.put(obj);
			}
		}
		
		return CompletableFuture.completedFuture(excelData);
	}
	


	@Override
	public void testExcutor() throws Exception {
		
		LOGGER.info("i am here");
		Thread.sleep(20000L);

		LOGGER.info("i wakeup after 20 sec");

	}



	@Override
	public CompletableFuture<List<Appdepartment>> importAppdepartment(JSONArray importData,Department department) throws Exception {
		List<Appdepartment> appDepartmentList=new ArrayList<Appdepartment>();
		List<Callable<AppInventoryDTO>> appInventoryList = new ArrayList<Callable<AppInventoryDTO>>();
		for(Object importObj:importData)
		{
			appInventoryList.add(	new AppInventoryDTO((JSONObject)importObj,
									department,
									appdepartmentRepository,
									appRepository,
									siteRepository,
									companyRepository));
		}
		
		List<Future<AppInventoryDTO>> results=execSvc.invokeAll(appInventoryList);
	
		for(Future<AppInventoryDTO> appInventoryFuture:results)
		{
			AppInventoryDTO appInventoryDTO=appInventoryFuture.get();
			appDepartmentList.add(appInventoryDTO.getAppdepartment());
			
		}
		Alert alert=new Alert();
		alert.setTitle("Job Completion");
		alert.setContent("Import have been done for client \""+department.getDepartmentName()+"\"!");

		this.messagingTemplate.convertAndSend("/subject/alert", alert);
		return CompletableFuture.completedFuture(appDepartmentList);
	}

}
