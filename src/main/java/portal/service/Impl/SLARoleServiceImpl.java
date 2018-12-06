package portal.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import portal.entity.SLARole;
import portal.models.SLARoleModel;
import portal.repository.SLARoleRepository;
import portal.service.SLARoleService;
@Service
public class SLARoleServiceImpl implements SLARoleService{

	@Autowired
	private SLARoleRepository slaRoleRepository;
	
	@Override
	public SLARole addSLARole(SLARole SLARole) {

		return slaRoleRepository.save(SLARole);
	}

	@Override
	public void delete(SLARole SLARole) {
		slaRoleRepository.delete(SLARole);
		
	}

	@Override
	public List<SLARole> getAll() {

		return slaRoleRepository.findAll();
	}

	@Override
	public List<SLARoleModel> getAllSLARole() {

		List<SLARoleModel> slaRoleModels = new 	ArrayList<SLARoleModel>();
		List<SLARole> slaRoles = slaRoleRepository.findAll();
		for(SLARole slaRole:slaRoles)
		{
			SLARoleModel slaRoleModel = new SLARoleModel();
			slaRoleModel.setId(slaRole.getId());
			slaRoleModel.setSLARoleName(slaRole.getSLARoleName());
			slaRoleModel.setResponsibility(slaRole.getResponsibility());
			slaRoleModel.setRaciforsyschanges(slaRole.getRaciforsyschanges());
			slaRoleModel.setInfluence(slaRole.getInfluence());
			slaRoleModel.setInterest(slaRole.getInterest());
			slaRoleModel.setDescription(slaRole.getDescription());
			slaRoleModels.add(slaRoleModel);
		}
		return slaRoleModels;
	}

	@Override
	public SLARole getByName(String SLARoleName) {
		
		return slaRoleRepository.findByName(SLARoleName);
	}

	@Override
	public SLARole getById(Long id) {

		return slaRoleRepository.findOne(id);
	}

	@Override
	public SLARole updateSLARole(SLARole SLARole) {
	
		return slaRoleRepository.saveAndFlush(SLARole);
	}

}
