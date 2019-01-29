package portal.service.Impl;
import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import portal.entity.Department;
import portal.entity.SLARole;
import portal.entity.Stakeholder;
import portal.models.StakeholderModel;
import portal.repository.StakeholderRepository;
import portal.service.StakeholderService;

@Service
public class StakeholderServiceImpl implements StakeholderService{

	@Autowired
	private StakeholderRepository stakeholderRepository;
	@Override
	public Stakeholder addStakeholder(Stakeholder stakeholder) {

		return stakeholderRepository.save(stakeholder);
	}

	@Override
	public void delete(Stakeholder stakeholder) {

		stakeholderRepository.delete(stakeholder);
	}

	@Override
	public List<Stakeholder> getAll() {

		return stakeholderRepository.findAll();
	}

	@Override
	public List<StakeholderModel> getAllStakeholder() {

		List<StakeholderModel> stakeholderModels = new ArrayList<StakeholderModel>();
		List<Stakeholder> stakeholders = stakeholderRepository.findAll();
		for(Stakeholder stakeholder:stakeholders)
		{
			StakeholderModel stakeholderModel = new StakeholderModel();
			stakeholderModel.setId(stakeholder.getId());
			stakeholderModel.setFirstname(stakeholder.getFirstname());
			stakeholderModel.setLastname(stakeholder.getLastname());
			stakeholderModel.setEmail(stakeholder.getEmail());
			stakeholderModel.setNote(stakeholder.getNote());
			stakeholderModel.setPosition(stakeholder.getPosition());
			stakeholderModel.setStakeholderName(stakeholder.getStakeholderName());
			stakeholderModels.add(stakeholderModel);
		}
		return stakeholderModels;
	}

	@Override
	public Stakeholder getByName(String stakeholderName) {
		
		return stakeholderRepository.findByName(stakeholderName);
	}

	@Override
	public Stakeholder getById(Long id) {

		return stakeholderRepository.findOne(id);
	}

	@Override
	public Stakeholder updateStakeholder(Stakeholder stakeholder) {

		return stakeholderRepository.saveAndFlush(stakeholder);
	}

	@Override
	public List<Stakeholder> findbyDepartment(Department department) {
		
		return stakeholderRepository.findByDepartment(department);
	}

	@Override
	public void removeDepartment(Department department) {
		stakeholderRepository.removeDepartment(department);
		
	}

	@Override
	public void removeRole(SLARole role) {
		stakeholderRepository.removeRole(role);
		
	}

	@Override
	public List<Stakeholder> getUnassginedStakeholders() {
		List<Stakeholder> stakeholders=stakeholderRepository.findAll();
		 stakeholders.removeIf(obj->!ObjectUtils.isEmpty(obj.getDepartment()));
		 return stakeholders;
	}

}
