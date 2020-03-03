package portal.service.Impl;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
import portal.entity.Company;
import portal.entity.Department;
import portal.entity.SLARole;
import portal.entity.Site;
import portal.entity.Stakeholder;
import portal.entity.Stakeholderext;
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
import portal.utility.RACI;
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
	public void importAppdepartment(JSONArray importData,Department department) throws Exception {
	
		List<Callable<AppInventoryDTO>> appInventoryList = new ArrayList<Callable<AppInventoryDTO>>();
		
		Queue<String> queue = new LinkedList<>();
		final Object obj=new Object();
		for(Object importObj:importData)
		{
			appInventoryList.add(	new AppInventoryDTO((JSONObject)importObj,
									department,
									appdepartmentRepository,
									appRepository,
									siteRepository,
									companyRepository,
									queue,
									obj));
		}
		
		execSvc.invokeAll(appInventoryList);


		Alert alert=new Alert();
		alert.setTitle("Job Completion");
		alert.setContent("Import have been done for client \""+department.getDepartmentName()+"\"!");

		this.messagingTemplate.convertAndSend("/subject/alert", alert);

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
									zacRepository
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



	@Override
	public void importAppdepartmentWithSingleThread(JSONArray importData, Department department) throws Exception {
		for(Object obj:importData)
		{
			JSONObject data=(JSONObject)obj;
			if(ObjectUtils.isEmpty(appdepartmentRepository.findbyAppNameAndDepartment(department,data.getString(AppinventoryMap.ApplicationName.name()))))
			{
				Appdepartment appdepartment= new Appdepartment();

				//zone
				appdepartment.setSouth(data.getString(AppinventoryMap.South.name()));
				appdepartment.setNorth(data.getString(AppinventoryMap.North.name()));
				appdepartment.setCentral(data.getString(AppinventoryMap.Central.name()));
				appdepartment.setCalgary(data.getString(AppinventoryMap.Calgary.name()));
				appdepartment.setEdmonton(data.getString(AppinventoryMap.Edmonton.name()));
				


				appdepartment.setBusinesslead(data.getString(AppinventoryMap.BusinessLead.name()));
				
				appdepartment.setAppowner(data.getString(AppinventoryMap.ApplicationOwner.name()));
				
				appdepartment.setGoverinplace(data.getString(AppinventoryMap.Goverinplace.name()));
				
				appdepartment.setUserbase(data.getString(AppinventoryMap.Userbase.name()));
		

				//support information
				appdepartment.setSme(data.getString(AppinventoryMap.SubjectMatterExpert.name()));
				appdepartment.setTrainer(data.getString(AppinventoryMap.Trainer.name()));
				appdepartment.setUseradmin(data.getString(AppinventoryMap.UserAdmin.name()));
				appdepartment.setSystemadmin(data.getString(AppinventoryMap.SystemAdmin.name()));
				appdepartment.setServersupport(data.getString(AppinventoryMap.AppServerSupport.name()));
				appdepartment.setDbsupport(data.getString(AppinventoryMap.DBServerSupport.name()));
				appdepartment.setNetworksupport(data.getString(AppinventoryMap.NetworkSupport.name()));
				
				//Contract information
				appdepartment.setContractinplace(data.getString(AppinventoryMap.Contractinplace.name()));
				appdepartment.setContractdetail(data.getString(AppinventoryMap.Contractdetail.name()));
				appdepartment.setExpireDate(data.getString(AppinventoryMap.ExpirationDate.name()));
				appdepartment.setFrequency(data.getString(AppinventoryMap.Frequency.name()));
				appdepartment.setVendorsla(data.getString(AppinventoryMap.VendorSla.name()));
				appdepartment.setAhsitsla(data.getString(AppinventoryMap.AhsItSla.name()));
				
				appdepartment.setBroadmap(data.getString(AppinventoryMap.Broadmap.name()));
				appdepartment.setImp(data.getString(AppinventoryMap.IMP.name()));
				appdepartment.setCshrecimit(data.getString(AppinventoryMap.Cshrecimit.name()));
				appdepartment.setNote(data.getString(AppinventoryMap.Note.name()));
				Appdepartment newEntity=appdepartmentRepository.saveAndFlush(appdepartment);
				
				
				newEntity.setDepartment(department);
				
				//Application
				Application applicationEntity=appRepository.findByName(data.getString(AppinventoryMap.ApplicationName.name()));
				Application application=ObjectUtils.isEmpty(applicationEntity)?new Application(data.getString(AppinventoryMap.ApplicationName.name())):applicationEntity;
				application.setAppType(data.getString(AppinventoryMap.ApplicationType.name()));
				application.setAppPurpose(data.getString(AppinventoryMap.ApplicationPurpose.name()));
				application.setAppVersion(data.getString(AppinventoryMap.ApplicationVersion.name()));
				newEntity.setApplication(appRepository.saveAndFlush(application));
				LOGGER.info("Application is"+application.getAppName());	
				
				//site
				JSONArray sites=data.getJSONArray(AppinventoryMap.Site.name());
				
				if(!ObjectUtils.isEmpty(sites) && sites.length()>0)
				{
					for(Object siteName:sites)
					{
				
						

						Site siteEntity=siteRepository.findByName((String)siteName);
						Site site=ObjectUtils.isEmpty(siteEntity)?siteRepository.saveAndFlush(new Site((String) siteName)):siteEntity;
						appdepartmentRepository.saveSite(newEntity.getId(), site.getId());
						
						
					}				
				}

			
				//vendor information
				JSONArray vendors=data.getJSONArray(AppinventoryMap.Vendor.name());
				if(!ObjectUtils.isEmpty(vendors) && vendors.length()>0)
				{
					for(Object vendorName:vendors)
					{
						Company companyEntity=companyRepository.findByName((String)vendorName);
						Company company=ObjectUtils.isEmpty(companyEntity)?companyRepository.saveAndFlush(new Company((String)vendorName)):companyEntity;
						appdepartmentRepository.saveVendor(newEntity.getId(), company.getId());
					}	
				}
				
				appdepartmentRepository.saveAndFlush(newEntity);
			
			}			
			
		}

		
	}



	@Override
	public void importStakeholderextWiteSingleThread(JSONArray importData, Department department) throws Exception {
		for(Object obj:importData)
		{
			JSONObject data= (JSONObject)obj;
			SLARole slaRoleEntity=slaRoleRepository.findByName(data.getString(StakeholderMap.Role.name()));
			Stakeholder stakeholderEntity=stakeholderRepository.findByName(data.getString(StakeholderMap.Name.name()));
			if(ObjectUtils.isEmpty(stakeholderextRepository.findbyStakeholderNameAndDepartment(department,slaRoleEntity,stakeholderEntity)))
			{
					LOGGER.info("importting stakeholder "+data.getString(StakeholderMap.Name.name()));
					Stakeholderext stakeholderext=new Stakeholderext();

					stakeholderext.setInfluence(data.getString(StakeholderMap.Influence.name()));
					stakeholderext.setInterest(data.getString(StakeholderMap.Interest.name()));
					stakeholderext.setNote(data.getString(StakeholderMap.Notes.name()));	
					

					stakeholderext.setDepartment(department);


				
				
				Stakeholder stakeholder=ObjectUtils.isEmpty(stakeholderEntity)?
										new Stakeholder(data.getString(StakeholderMap.Name.name())):
											stakeholderEntity;
							
				stakeholder.setPosition(data.getString(StakeholderMap.Position.name()));
				stakeholder.setEmail(data.getString(StakeholderMap.Email.name()));
				stakeholder.setPhone(ObjectUtils.isEmpty(data.getString(StakeholderMap.Phone.name()))?null:Long.parseLong(data.getString(StakeholderMap.Phone.name())));

				stakeholderext.setStakeholder(stakeholderRepository.saveAndFlush(stakeholder));
				
				
				SLARole slaRole=ObjectUtils.isEmpty(slaRoleEntity)?
							new SLARole(data.getString(StakeholderMap.Role.name())):
								slaRoleEntity;
						
				stakeholderext.setRole(slaRoleRepository.saveAndFlush(slaRole));
				
				if(!ObjectUtils.isEmpty(data.get(StakeholderMap.RACI.name())))
				{
					JSONArray array= data.getJSONArray(StakeholderMap.RACI.name());
					Set<RACI> raci=new HashSet<RACI>();
					for(Object e:array)
					{
						raci.add(RACI.valueOf((String)e));
					}
					stakeholderext.setRaciforsyschanges(raci);
				}

				
				
				
				stakeholderextRepository.saveAndFlush(stakeholderext);
				
				//-get updated stakeholder
				
				Stakeholder updatedStakeholder=stakeholderRepository.findByName(data.getString(StakeholderMap.Name.name()));
				Site siteEntity=siteRepository.findByName(data.getString(StakeholderMap.Location.name()));
				Site site=ObjectUtils.isEmpty(siteEntity)?
										siteRepository.saveAndFlush(new Site(data.getString(StakeholderMap.Location.name()))):
											siteEntity;
										
				updatedStakeholder.setSite(siteRepository.saveAndFlush(site));
				stakeholderRepository.saveAndFlush(updatedStakeholder);
			}
		}
		


		
	}

}
