package portal.ImportDTO;


import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;


import portal.entity.Department;
import portal.entity.SLARole;
import portal.entity.Site;
import portal.entity.Stakeholder;
import portal.entity.Stakeholderext;

import portal.repository.SLARoleRepository;
import portal.repository.SiteRepository;
import portal.repository.StakeholderRepository;
import portal.repository.StakeholderextRepository;

import portal.utility.RACI;
import portal.utility.StakeholderMap;

public class StakeholderextDTO implements Callable<StakeholderextDTO>{
	private static final Logger LOGGER = LoggerFactory.getLogger(StakeholderextDTO.class);
	private final JSONObject data;
	
	private final StakeholderextRepository stakeholderextRepository;
	
	private final Department department;
	
	private final StakeholderRepository stakeholderRepository;
	
	private final SLARoleRepository slaRoleRepository;
	
	private final SiteRepository siteRepository;
	


	
	private Stakeholderext stakeholderext;
	
	public StakeholderextDTO(final JSONObject _data,
							final Department _department,
							final StakeholderextRepository _stakeholderextRepository,
							final StakeholderRepository _stakeholderRepository,
							final SLARoleRepository _slaRoleRepository,
							final SiteRepository _siteRepository
							)
	{
		this.data=_data;
		this.stakeholderextRepository=_stakeholderextRepository;
		this.department=_department;
		this.stakeholderRepository=_stakeholderRepository;
		this.slaRoleRepository=_slaRoleRepository;
		this.siteRepository=_siteRepository;

	}

	


	public Stakeholderext getStakeholderext() {
		return stakeholderext;
	}




	public void setStakeholderext(Stakeholderext stakeholderext) {
		this.stakeholderext = stakeholderext;
	}




	@Override
	@Transactional
	public  StakeholderextDTO call() throws Exception {

			if(ObjectUtils.isEmpty(stakeholderextRepository.findbyStakeholderNameAndDepartment(department,data.getString(StakeholderMap.Name.name()))))
			{
					LOGGER.info("importting stakeholder "+data.getString(StakeholderMap.Name.name()));
					Stakeholderext stakeholderext=new Stakeholderext();

					stakeholderext.setInfluence(data.getString(StakeholderMap.Influence.name()));
					stakeholderext.setInterest(data.getString(StakeholderMap.Interest.name()));
					stakeholderext.setNote(data.getString(StakeholderMap.Notes.name()));	
					
					Stakeholderext newEntity=stakeholderextRepository.saveAndFlush(stakeholderext);
					newEntity.setDepartment(department);

	
				
				Stakeholder stakeholderEntity=stakeholderRepository.findByName(data.getString(StakeholderMap.Name.name()));
				Stakeholder stakeholder=ObjectUtils.isEmpty(stakeholderEntity)?
										new Stakeholder(data.getString(StakeholderMap.Name.name())):
											stakeholderEntity;
							
				stakeholder.setPosition(data.getString(StakeholderMap.Position.name()));
				stakeholder.setEmail(data.getString(StakeholderMap.Email.name()));
				stakeholder.setPhone(data.getLong(StakeholderMap.Phone.name()));

				newEntity.setStakeholder(stakeholder);
				
				SLARole slaRoleEntity=slaRoleRepository.findByName(data.getString(StakeholderMap.Role.name()));
				SLARole slaRole=ObjectUtils.isEmpty(slaRoleEntity)?
							new SLARole(data.getString(StakeholderMap.Role.name())):
								slaRoleEntity;
						
							newEntity.setRole(slaRole);
				
				JSONArray array= data.getJSONArray(StakeholderMap.RACI.name());
				Set<RACI> raci=new HashSet<RACI>();
				for(Object e:array)
				{
					raci.add(RACI.valueOf((String)e));
				}
				
				newEntity.setRaciforsyschanges(raci);
				
				stakeholderextRepository.saveAndFlush(newEntity);
				
				//-get updated stakeholder
				
				Stakeholder updatedStakeholder=stakeholderRepository.findByName(data.getString(StakeholderMap.Name.name()));
				Site siteEntity=siteRepository.findByName(data.getString(StakeholderMap.Location.name()));
				Site site=ObjectUtils.isEmpty(siteEntity)?
										siteRepository.saveAndFlush(new Site(data.getString(StakeholderMap.Location.name()))):
											siteEntity;
				
				updatedStakeholder.setSite(site);
				stakeholderRepository.saveAndFlush(updatedStakeholder);
			}


			return this;			


	}



}
