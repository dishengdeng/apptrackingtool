package portal.service.Impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import portal.entity.Appdepartment;
import portal.entity.Application;
import portal.entity.Department;
import portal.entity.File;

import portal.entity.Zac;
import portal.entity.Zacfield;
import portal.entity.Zaclist;
import portal.entity.Zacmap;
import portal.models.App;
import portal.models.DepartmentModel;
import portal.models.FileModel;
import portal.repository.AppRepository;
import portal.repository.DepartmentRepository;
import portal.repository.ZacRepository;
import portal.repository.ZaclistRepository;
import portal.repository.ZacmapRepository;

import portal.service.DepartmentService;
import portal.service.FileService;
import portal.service.ZacfieldService;


@Service
public class DepartmentServiceImpl implements DepartmentService{

	@Autowired
	private DepartmentRepository departmentRepository;
	
	
	@Autowired
	private AppRepository applicationRepository;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private ZacfieldService zacfieldService;
	
	@Autowired
	private ZacRepository zacRepository;
	
	@Autowired
	private ZaclistRepository zaclistRepository;
	
	@Autowired
	private ZacmapRepository zacmapRepository;

	
	@Override
	public Department addDepartment(Department department) {

		return departmentRepository.save(department);
	}

	@Override
	public void delete(Department department) {
		departmentRepository.delete(department);
		
	}

	@Override
	public List<Department> getAll() {
	
		return departmentRepository.findAll();
	}

	@Override
	public List<DepartmentModel> getAllDepartment() {
		List<DepartmentModel> departmentModels = new ArrayList<DepartmentModel>();
		List<Department> departments=departmentRepository.findAll();
		for(Department department:departments)
		{
			DepartmentModel departmentModel= new DepartmentModel();
			departmentModel.setId(department.getId());
			departmentModel.setDepartmentName(department.getDepartmentName());
			departmentModel.setDescription(department.getDescription());
			departmentModel.setGoal(department.getGoal());
			departmentModel.setPainpoint(department.getPainpoint());
			departmentModel.setPurpose(department.getPurpose());
			departmentModel.setRoadMap(department.getRoadMap());
			departmentModel.setStragicplan(department.getStragicplan());
			departmentModels.add(departmentModel);
		}
		return departmentModels;
	}

	@Override
	public Department getByName(String departmentName) {

		return departmentRepository.findByName(departmentName);
	}

	@Override
	public Department getById(Long id) {

		return departmentRepository.findOne(id);
	}

	@Override
	public Department updateDepartment(Department department) {
		department.setFiles(departmentRepository.findOne(department.getId()).getFiles());
		return departmentRepository.saveAndFlush(department);
	}




	@Override
	public void removFiles(String upload_foler, Department department) {
		if(department.getFiles().size()>0)
		{
			for(File file:department.getFiles())
			{
				fileService.removeFile(upload_foler,department.getId().toString(), file);
			}			
		}

		
		
	}

	@Override
	public Set<Zacmap> getZacmap(Department department) {
		Set<Zacmap> zacmaps = new HashSet<Zacmap>();
		zacmaps=department.getZaclists().stream().collect(Collectors.groupingBy(Zaclist::getZacmap)).keySet();


		return zacmaps;

	}
	


	@Override
	public Zacmap saveZacmap(JSONObject zacmapObj) {

		Zacmap zacmap = new Zacmap();
		
		Application app=applicationRepository.findOne(zacmapObj.getLong("application"));
		zacmap.setApplication(app);
		zacmap.setDetail(zacmapObj.getString("detail"));
		Set<Zaclist> zaclist=new HashSet<Zaclist>();
		
		for(Object obj : zacmapObj.getJSONArray("zaclist"))
		{
			JSONObject listObj=(JSONObject) obj;
			Zaclist zaclistobj=new Zaclist();
			
			Department department=departmentRepository.findOne(listObj.getLong("department"));
			zaclistobj.setDepartment(department);
			
			Zacfield zacfield=zacfieldService.findone(listObj.getLong("zacfield"));
			zaclistobj.setZacfield(zacfield);
			
			if(!ObjectUtils.isEmpty(listObj.getString("zac")))
			{
				Zac zac=zacRepository.findOne(listObj.getLong("zac"));
				zaclistobj.setZac(zac);
			}
			zaclist.add(zaclistobj);
		}
		
		zacmap.setZaclists(zaclist);
		
		return zacmapRepository.save(zacmap);
	}

	@Override
	public Zacmap updateZacmap(JSONObject zacmapObj) {
		
		Zacmap zacmap = zacmapRepository.findOne(zacmapObj.getLong("zacmap"));
		
		Application app=applicationRepository.findOne(zacmapObj.getLong("application"));
		zacmap.setApplication(app);
		zacmap.setDetail(zacmapObj.getString("detail"));

		
		for(Object obj : zacmapObj.getJSONArray("zaclist"))
		{
			JSONObject listObj=(JSONObject) obj;
			Zaclist zaclistobj=zaclistRepository.findOne(listObj.getLong("id"));
			
			Department department=departmentRepository.findOne(listObj.getLong("department"));
			zaclistobj.setDepartment(department);
			
			Zacfield zacfield=zacfieldService.findone(listObj.getLong("zacfield"));
			zaclistobj.setZacfield(zacfield);
			
			if(!ObjectUtils.isEmpty(listObj.getString("zac")))
			{
				Zac zac=zacRepository.findOne(listObj.getLong("zac"));
				zaclistobj.setZac(zac);
			}
			else
			{
				zaclistobj.setZac(null);
			}

			zacmap.getZaclists().add(zaclistobj);

		}
		

		
		return zacmapRepository.saveAndFlush(zacmap);
	}

	@Override
	public boolean saveZacfield(JSONObject zacfieldObj) {
		Department department=departmentRepository.findOne(zacfieldObj.getLong("department"));


		Zacfield zacfield=new Zacfield();
		zacfield.setDepartment(department);
		zacfield.setFieldName(zacfieldObj.getString("fieldname"));
		Zacfield newEntity=zacfieldService.save(zacfield);
		
    	if(department.getZaclists().size()>0)
    	{
 
   
    		for(Zacmap zacmap : getZacmap(department))
			{
	
	    		Zaclist zaclist= new Zaclist();
	    		zaclist.setDepartment(department);
	    		zaclist.setZacfield(newEntity);
	    		zaclist.setZacmap(zacmap);	    		
	    		zaclistRepository.saveAndFlush(zaclist);
			}


    		
    	}

    	return ObjectUtils.isEmpty(newEntity)? false:true;

	}

	@Override
	public List<FileModel> getfiles(Department department, HttpServletRequest request) {
		List<FileModel> files= new ArrayList<FileModel>();
		for(File file :department.getFiles())
		{
			files.add(new FileModel(file.getId(),file.getAttachment(),file.getCreatedat(),file.getCreatedby(),department.getId()));
		}
		return files;
	}

	@Override
	public List<App> getApplications(Department department, HttpServletRequest request) {
		
		List<App> apps= new ArrayList<App>();
		for(Appdepartment app :department.getAppdepartments())
		{
			if(!ObjectUtils.isEmpty(app.getApplication())) apps.add(new App(app.getApplication().getId(),
																	app.getApplication().getAppName(),
																	ObjectUtils.isEmpty(app.getApplication().getAppDecomminsionDate())? null:app.getApplication().getAppDecomminsionDate().toString(),
																			ObjectUtils.isEmpty(app.getApplication().getNotes())?"":app.getApplication().getNotes(),
																	"/applicationdetail?app="+app.getApplication().getId()
																	));
			if(!ObjectUtils.isEmpty(app.getAppInstance())) apps.add(new App(app.getAppInstance().getId(),
					app.getAppInstance().getAppInstanceName(),
					null,
					ObjectUtils.isEmpty(app.getAppInstance().getNotes())?"":app.getApplication().getNotes(),
					"/instancedetail?id="+app.getAppInstance().getId()
					));			
			
		}
		return apps;
	}





}
