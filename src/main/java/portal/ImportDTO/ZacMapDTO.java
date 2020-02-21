package portal.ImportDTO;



import java.util.List;
import java.util.concurrent.Callable;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import portal.entity.Application;
import portal.entity.Department;

import portal.entity.Zac;
import portal.entity.Zacfield;
import portal.entity.Zaclist;
import portal.entity.Zacmap;
import portal.repository.AppRepository;

import portal.repository.ZacRepository;
import portal.repository.ZacfieldRepository;
import portal.repository.ZaclistRepository;
import portal.repository.ZacmapRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import portal.utility.ZacMap;

public class ZacMapDTO implements Callable<ZacMapDTO>{
	private static final Logger LOGGER = LoggerFactory.getLogger(ZacMapDTO.class);
	private final JSONObject data;
	
	private final ZacmapRepository zacmapRepository;
	
	private final Department department;
	
	private final ZaclistRepository zaclistRepository;


	private final JdbcTemplate JdbcTemplate;

	private final AppRepository appRepository;
	
	private final ZacfieldRepository zacfieldRepository;
	
	private final ZacRepository zacRepository;
	
	public ZacMapDTO(final JSONObject _data,
							final Department _department,
							final ZacmapRepository _zacmapRepository,
							final ZaclistRepository _zaclistRepository,	
							final AppRepository _appRepository,
							final ZacfieldRepository _zacfieldRepository,
							final ZacRepository _zacRepository,
							final JdbcTemplate _JdbcTemplate
							)
	{
		this.data=_data;
		this.department=_department;
		this.zacmapRepository=_zacmapRepository;
		this.zaclistRepository=_zaclistRepository;

		this.appRepository=_appRepository;
		this.zacfieldRepository=_zacfieldRepository;
		this.zacRepository=_zacRepository;
		this.JdbcTemplate=_JdbcTemplate;
	}

	






	@Override
	@Transactional
	public  ZacMapDTO call() throws Exception {

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
		

									
							



			return this;			


	}



}
