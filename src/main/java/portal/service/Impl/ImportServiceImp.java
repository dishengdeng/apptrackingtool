package portal.service.Impl;


import java.util.ArrayList;
import java.util.Arrays;


import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

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
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import portal.ImportDTO.AppInventoryDTO;
import portal.ImportDTO.StakeholderextDTO;
import portal.ImportDTO.ZacMapDTO;
import portal.entity.Appdepartment;
import portal.entity.Application;
import portal.entity.Department;
import portal.entity.Zac;
import portal.entity.Zacfield;
import portal.entity.Zaclist;
import portal.entity.Zacmap;
import portal.models.Alert;
import portal.repository.AppRepository;
import portal.repository.AppdepartmentRepository;
import portal.repository.CompanyRepository;
import portal.repository.SLARoleRepository;
import portal.repository.SiteRepository;
import portal.repository.StakeholderRepository;
import portal.repository.StakeholderextRepository;
import portal.repository.ZacRepository;
import portal.repository.ZacfieldRepository;
import portal.repository.ZaclistRepository;
import portal.repository.ZacmapRepository;
import portal.service.ImportService;
import portal.service.ZacfieldService;
import portal.utility.AppinventoryMap;

import portal.utility.InvalidTemplateFormatException;
import portal.utility.JSONObjectWithEmpty;
import portal.utility.StakeholderMap;
import portal.utility.ZacMap;
import portal.validator.AppInventoryImportValidator;
import portal.validator.StakeholderImportValidator;
import portal.validator.ZacImportValidator;

@Service
public class ImportServiceImp implements ImportService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ImportServiceImp.class);


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
	@Autowired
	private StakeholderextRepository stakeholderextRepository;
	@Autowired
	private StakeholderRepository stakeholderRepository;
	@Autowired
	private SLARoleRepository slaRoleRepository;
	@Autowired
	private ZacmapRepository zacmapRepository;
	@Autowired
	private ZaclistRepository zaclistRepository;
	@Autowired
	private ZacfieldRepository zacfieldRepository;
	@Autowired
	private ZacRepository zacRepository;
	
	@Autowired
	private ZacfieldService zacfieldService;
	
	@Autowired
	private JdbcTemplate JdbcTemplate;
	
	private final int threadPoolNumber=3;

	private ExecutorService execSvc = Executors.newFixedThreadPool(threadPoolNumber);
	
	@Override
	public CompletableFuture<JSONArray> getAppInventory(MultipartFile file) throws InvalidTemplateFormatException, Exception {
		Assert.notNull(file);
		LOGGER.info("getting App Inventory");
		Workbook workbook = WorkbookFactory.create(file.getInputStream());
		
		Sheet sheet = workbook.getSheetAt(0);
		
		DataFormatter dataFormatter = new DataFormatter();
		
		new AppInventoryImportValidator<>().TemplateFormatValidate(sheet.getRow(0));
		
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



	@Override
	public CompletableFuture<JSONArray> getStakeholders(MultipartFile file)
			throws InvalidTemplateFormatException, Exception {
		Assert.notNull(file);
		LOGGER.info("getting Stakeholder");
		Workbook workbook = WorkbookFactory.create(file.getInputStream());
		
		Sheet sheet = workbook.getSheetAt(0);
		
		DataFormatter dataFormatter = new DataFormatter();
		
		new StakeholderImportValidator<>().TemplateFormatValidate(sheet.getRow(0));
		
		JSONArray excelData = new JSONArray();
		for(Row row:sheet)
		{
			if(row.getRowNum()>0)
			{
				JSONObjectWithEmpty obj=new JSONObjectWithEmpty();
				obj.put(StakeholderMap.Name.name(), 
						(new StakeholderImportValidator<String>())
						.StakeholderDataValidate(StakeholderMap.Name,row.getCell(StakeholderMap.Name.getColumnIndex()))
						);
				obj.put(StakeholderMap.Position.name(), dataFormatter.formatCellValue(row.getCell(StakeholderMap.Position.getColumnIndex())));
				obj.put(StakeholderMap.Location.name(), dataFormatter.formatCellValue(row.getCell(StakeholderMap.Location.getColumnIndex())));
				obj.put(StakeholderMap.Role.name(), dataFormatter.formatCellValue(row.getCell(StakeholderMap.Role.getColumnIndex())));
				obj.put(StakeholderMap.Email.name(), 
						(new StakeholderImportValidator<String>())
						.StakeholderDataValidate(StakeholderMap.Email,row.getCell(StakeholderMap.Email.getColumnIndex()))
						);
				obj.put(StakeholderMap.Phone.name(), 
						(new StakeholderImportValidator<String>())
						.StakeholderDataValidate(StakeholderMap.Phone,row.getCell(StakeholderMap.Phone.getColumnIndex()))
						);
				obj.put(StakeholderMap.Influence.name(), 
						(new StakeholderImportValidator<String>())
						.StakeholderDataValidate(StakeholderMap.Influence,row.getCell(StakeholderMap.Influence.getColumnIndex()))
						);
				obj.put(StakeholderMap.Interest.name(), 
						(new StakeholderImportValidator<String>())
						.StakeholderDataValidate(StakeholderMap.Interest,row.getCell(StakeholderMap.Interest.getColumnIndex()))
						);
				obj.put(StakeholderMap.RACI.name(), 
						(new StakeholderImportValidator<JSONArray>())
						.StakeholderDataValidate(StakeholderMap.RACI,row.getCell(StakeholderMap.RACI.getColumnIndex()))
						);
				obj.put(StakeholderMap.Notes.name(), dataFormatter.formatCellValue(row.getCell(StakeholderMap.Notes.getColumnIndex())));
				excelData.put(obj);
			}
		}
		
		return CompletableFuture.completedFuture(excelData);
	}



	@Override
	public void importStakeholderext(JSONArray importData, Department department)
			throws Exception {

		List<Callable<StakeholderextDTO>> StakeholderextDTOList = new ArrayList<Callable<StakeholderextDTO>>();
		for(Object importObj:importData)
		{

			StakeholderextDTOList.add(	new StakeholderextDTO((JSONObject)importObj,
									department,
									stakeholderextRepository,
									stakeholderRepository,
									slaRoleRepository,
									siteRepository));
		
		}

		execSvc.invokeAll(StakeholderextDTOList);


		Alert alert=new Alert();
		alert.setTitle("Job Completion");
		alert.setContent("Import have been done for client \""+department.getDepartmentName()+"\"!");
		
		this.messagingTemplate.convertAndSend("/subject/alert", alert);
		

	}



	@Override
	public CompletableFuture<JSONArray> getZacs(MultipartFile file,Department department) throws InvalidTemplateFormatException, Exception {
		Assert.notNull(file);
		LOGGER.info("getting Zac");
		Workbook workbook = WorkbookFactory.create(file.getInputStream());
		
		Sheet sheet = workbook.getSheetAt(0);
		Map<String,Integer> zacFieldIndex=new ZacImportValidator<>(zacfieldService).TemplateFormatValidate(sheet.getRow(0), department);
		DataFormatter dataFormatter = new DataFormatter();
		
		JSONArray excelData = new JSONArray();
		for(Row row:sheet)
		{
			if(row.getRowNum()>0)
			{
				JSONObjectWithEmpty obj=new JSONObjectWithEmpty();
				obj.put(ZacMap.APPLICATION.name(),
						(new ZacImportValidator<String>()).ZacDataValidate(ZacMap.APPLICATION, row.getCell(zacFieldIndex.get(ZacMap.APPLICATION.getValue())))
						);

				for(String fieldName:zacFieldIndex.keySet())
				{
					if(!ZacMap.APPLICATION.getValue().equals(fieldName) && !ZacMap.DETAIL.getValue().equals(fieldName))
					{
						
						obj.put(fieldName, 
								(new ZacImportValidator<String>()).ZacDataValidate(ZacMap.ZACFIELDS, row.getCell(zacFieldIndex.get(fieldName))								
								));
					
					}

				}
				

				obj.put(ZacMap.DETAIL.name(), 
						dataFormatter.formatCellValue(
								row.getCell(zacFieldIndex.get(ZacMap.DETAIL.getValue()))
								)
						);
				excelData.put(obj);
			}
		}
			
		return CompletableFuture.completedFuture(excelData);
	}



	@Override
	public void importZacmap(JSONArray importData, Department department) throws Exception {
		List<Callable<ZacMapDTO>> ZacMapDTOList = new ArrayList<Callable<ZacMapDTO>>();
		for(Object importObj:importData)
		{

			ZacMapDTOList.add(	new ZacMapDTO((JSONObject)importObj,
									department,
									zacmapRepository,
									zaclistRepository,
									appRepository,
									zacfieldRepository,
									zacRepository,
									JdbcTemplate
									));
		
		}

		execSvc.invokeAll(ZacMapDTOList);


		Alert alert=new Alert();
		alert.setTitle("Job Completion");
		alert.setContent("Import have been done for client \""+department.getDepartmentName()+"\" Zacmap Import");
		
		this.messagingTemplate.convertAndSend("/subject/alert", alert);
		
	}



	@Override
	public void importZacmapWithSingleThread(JSONArray importData, Department department) throws Exception {
		for(Object obj:importData)
		{
			JSONObject data=(JSONObject)obj;
			Application application=appRepository.findByName(data.getString(ZacMap.APPLICATION.name()));
			Zacmap zacmap=zacmapRepository.findbyAppNameAndDepartment(department,application);
			if(ObjectUtils.isEmpty(zacmap))
			{
				LOGGER.info("importing ZacMap");
				Zacmap newZacMap= new Zacmap();
				newZacMap.setDetail(data.getString(ZacMap.DETAIL.name()));
				newZacMap.setApplication(application);
				Zacmap newZapMapEntity=zacmapRepository.saveAndFlush(newZacMap);

				List<Zacfield> zacFields=zacfieldRepository.findbyDepartment(department);
				for(Zacfield zacField:zacFields)
				{

					Zac zac=zacRepository.findByName(data.getString(zacField.getFieldName()));
					Zaclist newZaclist= new Zaclist();
	
					newZaclist.setDepartment(department);
		
					newZaclist.setZac(zac);
					newZaclist.setZacfield(zacField);
					newZaclist.setZacmap(newZapMapEntity);
					zaclistRepository.save(newZaclist);
				
				
					
				}

			}			
		}
		
		Alert alert=new Alert();
		alert.setTitle("Job Completion");
		alert.setContent("Import have been done for client \""+department.getDepartmentName()+"\" Zacmap Import");
		
		this.messagingTemplate.convertAndSend("/subject/alert", alert);

		
	}

}
